package com.semicolon.ewallet.user;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semicolon.ewallet.exception.RegistrationException;
import com.semicolon.ewallet.kyc.card.CardRequest;
import com.semicolon.ewallet.kyc.card.CardService;
import com.semicolon.ewallet.token.Token;
import com.semicolon.ewallet.user.dto.*;
import com.semicolon.ewallet.email.EmailSender;
import com.semicolon.ewallet.email.EmailService;
import com.semicolon.ewallet.token.TokenService;
import com.squareup.okhttp.*;
import com.semicolon.ewallet.user.dto.ChangePasswordRequest;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    EmailSender emailSender;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenService tokenService;
    @Autowired
    CardService cardService;
    @Autowired
    private EmailService emailService;
    //To get your secret key secured
    private final String SECRET_KEY = System.getenv("PAYSTACK_SECRET_KEY");

            @Override
            public String forgotPassword (ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
                var foundUser = userRepository.findByEmailAddressIgnoreCase(forgotPasswordRequest
                        .getEmail());
                if (Objects.isNull(foundUser)) throw new RegistrationException("user does not exist");
                String token = generateToken(foundUser.get());
                emailSender.send(foundUser.get().getEmailAddress(), buildForgotPasswordEmail(foundUser.get().getLastName(), token));
                return token;
            }

            private String buildForgotPasswordEmail (String lastName, String token){
                return "Here's the link to reset your password"
                        + "                                      "
                        + "                                        "
                        + "<p>Hello \"" + lastName + "\",</p>"
                        + "<p>You have requested to reset your password.</p>"
                        + "<p>Click the link below to change your password:</p>"
                        + "<p><a href=\"" + token + "\">Change my password</a></p>"
                        + "<br>"
                        + "<p>Ignore this email if you do remember your password, "
                        + "or you have not made the request.</p>";
            }
            @Override
            public String resetPassword (ResetPasswordRequest resetPasswordRequest){
                var tokenChecked = tokenService.getConfirmationToken(resetPasswordRequest.getToken())
                        .orElseThrow(() -> new RegistrationException("Token does not exist"));

                if (tokenChecked.getConfirmedAt().isBefore(LocalDateTime.now())) {
                    throw new RegistrationException("Token has expired");
                }
                if (tokenChecked.getConfirmedAt() != null) {
                    throw new RegistrationException("Token has been used");
                }
                tokenService.getConfirmationToken(tokenChecked.getToken());
                var user = userRepository.findByEmailAddressIgnoreCase(resetPasswordRequest.getEmailAddress());
                user.get().setPassword(hashPassword(resetPasswordRequest.getPassword()));
                userRepository.save(user.get());
                return "Your password successfully updated.";
            }
            private String hashPassword (String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


        @Override
            public void enableUser (String email){
                var foundEmail = userRepository.findByEmailAddressIgnoreCase(email).orElseThrow(() -> new RegistrationException("invalid email"));
                foundEmail.setIsVerified(true);
            }

            public String changePassword (ChangePasswordRequest changePasswordRequest){
                var user = userRepository.findByEmailAddressIgnoreCase(changePasswordRequest.getEmailAddress())
                        .orElseThrow(() -> new RegistrationException("invalid details"));

                if (!BCrypt.checkpw(changePasswordRequest.getOldPassword(), user.getPassword())) {
                    throw new RegistrationException("invalid details");
                }
                if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword()))
                    throw new RegistrationException("password do not match");
                user.setPassword(hashPassword(changePasswordRequest.getNewPassword()));
                userRepository.save(user);
                return "password updated";
            }


        @Override
        public void getUser(User user) {
            userRepository.save(user);
        }

    @Override
    public Optional<User> getByEmailAddress(String emailAddress){
        return Optional.ofNullable(userRepository.findByEmailAddressIgnoreCase(emailAddress).orElseThrow(()-> new RegistrationException("user does not exist")));
    }

    @Override
            public String completeRegistration (CompleteRegistrationRequest completeRegistrationRequest) throws IOException{
                var user = userRepository.findByEmailAddressIgnoreCase(completeRegistrationRequest
                        .getEmailAddress()).orElseThrow(() -> new RegistrationException("Email Address already exists"));

                kycUpdate(completeRegistrationRequest, user);
                user.getCards().add(cardService.addCard(completeRegistrationRequest.getCardRequest()));
                nextOfKinUpdate(completeRegistrationRequest, user);
                userRepository.save(user);
                return "User details updated";
            }

            private void nextOfKinUpdate (CompleteRegistrationRequest completeRegistrationRequest, User user){
                user.getNextOfKin().setFullName(completeRegistrationRequest.getFullName());
                user.getNextOfKin().setEmailAddress(completeRegistrationRequest.getEmailAddress());
                user.getNextOfKin().setPhoneNumber(completeRegistrationRequest.getPhoneNumber());
                user.getNextOfKin().setRelationship(completeRegistrationRequest.getRelationship());
            }


            private void kycUpdate (CompleteRegistrationRequest completeRegistrationRequest, User user){
                user.getKyc().setHomeAddress(completeRegistrationRequest.getHomeAddress());
                user.getKyc().setIdentification(completeRegistrationRequest.getIdentity());
            }

            @Override
            public void validateBvn (AddAccountRequest addAccountRequest) throws IOException {

                OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");

                JSONObject json = new JSONObject();
                try {
                    json.put("bvn", addAccountRequest.getBvn());
                    json.put("account_number", addAccountRequest.getAccountNumber());
                    json.put("bank_code", addAccountRequest.getBankCode());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                RequestBody body = RequestBody.create(mediaType, json.toString());

                Request request = new Request.Builder()
                        .url("https://api.paystack.co/bvn/match")
                        .post(body)
                        .addHeader("Authorization", "Bearer " + SECRET_KEY)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = client.newCall(request).execute();
                log.info(response.body().string());

            }

            @Override
            public String validateAccount (CardRequest cardRequest) throws IOException {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://api.paystack.co/decision/bin/"
                                + cardRequest.getCardNumber().substring(0, 6))
                        .get()
                        .addHeader("Authorization", "Bearer " + SECRET_KEY)
                        .build();

                Response response = client.newCall(request).execute();
                return response.body().string();
            }
            public String generateToken (User user){
            SecureRandom random = new SecureRandom();
            String token = String.valueOf(1000 + random.nextInt(9999));
            Token confirmationToken = new Token(token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(10),
                    user
            );
            tokenService.saveConfirmationToken(confirmationToken);
            return confirmationToken.getToken();
        }
}
