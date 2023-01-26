package com.semicolon.ewallet.user;
import com.semicolon.ewallet.Exception.RegistrationException;
import com.semicolon.ewallet.kyc.card.CardRequest;
import com.semicolon.ewallet.kyc.Identification;
import com.semicolon.ewallet.kyc.card.CardService;
import com.semicolon.ewallet.user.dto.AddAccountRequest;
import com.semicolon.ewallet.user.dto.CompleteRegistrationRequest;
import com.semicolon.ewallet.user.dto.SignUpRequest;
import com.semicolon.ewallet.user.dto.SignUpResponse;
import com.semicolon.ewallet.user.email.EmailSender;
import com.semicolon.ewallet.user.token.Token;
import com.semicolon.ewallet.user.token.TokenService;
import com.squareup.okhttp.*;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    private final String SECRET_KEY = System.getenv("PAYSTACK_SECRET_KEY");
    @Autowired
    EmailSender emailSender;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenService tokenService;

    @Autowired
    CardService cardService;

    @Override
    public SignUpResponse register(SignUpRequest signUpRequest) throws MessagingException{
        boolean emailExists=userRepository
                .findByEmailAddressIgnoreCase(signUpRequest.getEmailAddress())
                .isPresent();
        if(emailExists)throw new RegistrationException("Email Address already exists");
        User user = new User(
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getEmailAddress(),
                signUpRequest.getPassword()
        );
        userRepository.save(user);
        String token = generateToken(user);
        emailSender.send(signUpRequest.getEmailAddress(), buildEmail(signUpRequest.getFirstName(), token));

        SignUpResponse sign = new SignUpResponse();
        sign.setEmail(signUpRequest.getEmailAddress());
        sign.setFirstName(signUpRequest.getFirstName());
        sign.setLastName(signUpRequest.getLastName());
        sign.setToken(token);


        return sign;
    }

    @Override
    public User getByEmailAddress(String emailAddress) {
        return userRepository.findByEmailAddressIgnoreCase(emailAddress).orElseThrow(()-> new RegistrationException("user does not exist"));
    }

    @Override
    public User getIdentification(Identification id) {
        return userRepository.saveUserIdentification(id);
    }

    @Override
    public String completeRegistration( CompleteRegistrationRequest completeRegistrationRequest) {
        var user=userRepository.findByEmailAddressIgnoreCase(completeRegistrationRequest
                .getEmailAddress()).orElseThrow(()-> new RegistrationException("Email Address already exists"));

        kycUpdate(completeRegistrationRequest, user);
        user.getCards().add(cardService.addCard(completeRegistrationRequest.getCardRequest()));
        nextOfKinUpdate(completeRegistrationRequest, user);
        userRepository.save(user);
        return "User details updated";
    }

    private void nextOfKinUpdate(CompleteRegistrationRequest completeRegistrationRequest, User user) {
        user.getNextOfKin().setFullName(completeRegistrationRequest.getFullName());
        user.getNextOfKin().setEmailAddress(completeRegistrationRequest.getEmailAddress());
        user.getNextOfKin().setPhoneNumber(completeRegistrationRequest.getPhoneNumber());
        user.getNextOfKin().setRelationship(completeRegistrationRequest.getRelationship());
    }

    private void kycUpdate(CompleteRegistrationRequest completeRegistrationRequest, User user) {
        user.getKyc().setHomeAddress(completeRegistrationRequest.getHomeAddress());
        user.getKyc().setIdentification(completeRegistrationRequest.getIdentity());
    }

    @Override
    public void validateBvn(AddAccountRequest addAccountRequest) throws IOException {

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        JSONObject json = new JSONObject();
        try{
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
                .addHeader("Authorization", "Bearer "+SECRET_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        log.info(response.body().string());

    }

    @Override
    public String validateAccount(CardRequest cardRequest) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.paystack.co/decision/bin/"
                        +cardRequest.getCardNumber().substring(0, 6))
                .get()
                .addHeader("Authorization", "Bearer "+SECRET_KEY)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    private String buildEmail(String firstName, String token){
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

    public String generateToken(User user){
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
