package com.semicolon.ewallet.user;
import com.semicolon.ewallet.exception.RegistrationException;
import com.semicolon.ewallet.kyc.card.CardRequest;
import com.semicolon.ewallet.kyc.card.CardService;
import com.semicolon.ewallet.user.dto.*;
import com.semicolon.ewallet.user.email.EmailSender;
import com.semicolon.ewallet.user.email.EmailService;
import com.semicolon.ewallet.user.token.Token;
import com.semicolon.ewallet.user.token.TokenService;
import com.squareup.okhttp.*;
import com.semicolon.ewallet.user.dto.ResendTokenRequest;
import com.semicolon.ewallet.user.dto.LoginRequest;
import com.semicolon.ewallet.user.dto.ChangePasswordRequest;
import com.semicolon.ewallet.user.dto.SignUpRequest;
import com.semicolon.ewallet.user.dto.SignUpResponse;
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
    private final String SECRET_KEY = System.getenv("PAYSTACK_SECRET_KEY");


            @Override
            public SignUpResponse register (SignUpRequest signUpRequest) throws MessagingException {
                boolean emailExists = userRepository
                        .findByEmailAddressIgnoreCase(signUpRequest.getEmailAddress())
                        .isPresent();
                if (emailExists)throw new IllegalStateException("Email Address already exists");

                User user = new User(
                        signUpRequest.getFirstName(),
                        signUpRequest.getLastName(),
                        signUpRequest.getEmailAddress(),
                        hashPassword(signUpRequest.getPassword())
                );

                userRepository.save(user);
                String token = generateToken(user);
                emailSender.send(signUpRequest.getEmailAddress(),
                        buildEmail(signUpRequest.getFirstName(), token));
                emailSender.send(signUpRequest.getEmailAddress(), buildEmail(signUpRequest.getFirstName(), token));

                SignUpResponse sign = new SignUpResponse();
                sign.setEmailAddress(signUpRequest.getEmailAddress());
                sign.setFirstName(signUpRequest.getFirstName());
                sign.setLastName(signUpRequest.getLastName());
                sign.setToken(token);
                return sign;
            }
            private String hashPassword (String password){
                return BCrypt.hashpw(password, BCrypt.gensalt());
            }
            public String login (LoginRequest loginRequest) {
            var foundUser = userRepository.findByEmailAddressIgnoreCase(loginRequest.getEmailAddress());
            if (Objects.isNull(foundUser)) throw new RegistrationException("user does not exist");
            if (foundUser.get().getIsVerified().equals(false)) throw new RegistrationException("user has not been verified");
            try {
                if (!BCrypt.checkpw(loginRequest.getPassword(), foundUser.get().getPassword())) {
                    throw new RegistrationException("password does not match");
                }
            } catch (RegistrationException e) {
                throw new RuntimeException(e);
            }
            return "Login Successful";
        }

            @Override
            public String resendToken (ResendTokenRequest resendTokenRequest) throws MessagingException {
                User foundUser = userRepository.findByEmailAddressIgnoreCase(resendTokenRequest.getEmailAddress()).orElseThrow(() -> new
                        IllegalStateException("this email does not exist"));
                if (foundUser.getIsVerified().equals(true)) throw new RegistrationException("Already verified");
                String token = generateToken(foundUser);
                emailService.send(resendTokenRequest.getEmailAddress(), buildEmail(foundUser.getFirstName(), token));
                return "token has been resent successfully";

            }


    private String buildEmail (String firstName, String token){
                return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                        "\n" +
                        "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                        "\n" +
                        "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                        "    <tbody><tr>\n" +
                        "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                        "        \n" +
                        "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                        "          <tbody><tr>\n" +
                        "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                        "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                        "                  <tbody><tr>\n" +
                        "                    <td style=\"padding-left:10px\">\n" +
                        "                  \n" +
                        "                    </td>\n" +
                        "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                        "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                        "                    </td>\n" +
                        "                  </tr>\n" +
                        "                </tbody></table>\n" +
                        "              </a>\n" +
                        "            </td>\n" +
                        "          </tr>\n" +
                        "        </tbody></table>\n" +
                        "        \n" +
                        "      </td>\n" +
                        "    </tr>\n" +
                        "  </tbody></table>\n" +
                        "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                        "    <tbody><tr>\n" +
                        "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                        "      <td>\n" +
                        "        \n" +
                        "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                        "                  <tbody><tr>\n" +
                        "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                        "                  </tr>\n" +
                        "                </tbody></table>\n" +
                        "        \n" +
                        "      </td>\n" +
                        "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                        "    </tr>\n" +
                        "  </tbody></table>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                        "    <tbody><tr>\n" +
                        "      <td height=\"30\"><br></td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                        "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                        "        \n" +
                        "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + firstName + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + token + "</p></blockquote>\n Link will expire in 10 minutes. <p>See you soon</p>" +
                        "        \n" +
                        "      </td>\n" +
                        "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td height=\"30\"><br></td>\n" +
                        "    </tr>\n" +
                        "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                        "\n" +
                        "</div></div>";
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

            @Override
            public User getByEmailAddress (String emailAddress){
                return userRepository.findByEmailAddressIgnoreCase(emailAddress).orElseThrow(() -> new RegistrationException("user does not exist"));
            }
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

            @Override
            public void enableUser (String email){
                var foundEmail = userRepository.findByEmailAddressIgnoreCase(email).orElseThrow(() -> new RegistrationException("invalid email"));
                foundEmail.setIsVerified(true);
            }

            @Override
            public String tokenConfirmation (TokenConfirmationRequest tokenConfirmationRequest){
                var token = tokenService.getConfirmationToken(tokenConfirmationRequest.getToken())
                        .orElseThrow(() -> new RegistrationException("Invalid Token"));

                if (token.getExpiredAt().isBefore(LocalDateTime.now()))
                    throw new IllegalStateException("Token has expired");
                tokenService.setTokenConfirmationAt(token.getToken());
                enableUser(tokenConfirmationRequest.getEmail());
                return "User Has Been Confirmed";
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
            public String completeRegistration (CompleteRegistrationRequest completeRegistrationRequest){
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
    @Override
    public String verifyReceiversAccount(VerifyReceiversAccountRequest verifyReceiversAccountRequest) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.paystack.co/bank/resolve/"
                +verifyReceiversAccountRequest.getAccountNumber()+verifyReceiversAccountRequest.getBankCode())
                .get()
                .addHeader("Authorization", "Bearer " + SECRET_KEY)
        return null;
    }



}
