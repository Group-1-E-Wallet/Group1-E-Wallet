package com.semicolon.ewallet.registration;

import com.semicolon.ewallet.email.EmailSender;
import com.semicolon.ewallet.email.EmailService;
import com.semicolon.ewallet.exception.RegistrationException;
import com.semicolon.ewallet.registration.dto.LoginRequest;
import com.semicolon.ewallet.registration.dto.SignUpRequest;
import com.semicolon.ewallet.registration.dto.SignUpResponse;
import com.semicolon.ewallet.token.Token;
import com.semicolon.ewallet.token.TokenService;
import com.semicolon.ewallet.user.User;
import com.semicolon.ewallet.user.UserService;
import com.semicolon.ewallet.user.dto.ResendTokenRequest;
import com.semicolon.ewallet.user.dto.TokenConfirmationRequest;
import jakarta.mail.MessagingException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;
@Service
public class RegistrationServiceImpl implements RegistrationService{
    @Autowired
    UserService userService;
    @Autowired

    private EmailService emailService;
    @Autowired
    TokenService tokenService;
    @Autowired
    EmailSender emailSender;
    @Override
    public SignUpResponse register (SignUpRequest signUpRequest) throws MessagingException {
        boolean emailExists = userService
                .getByEmailAddress(signUpRequest.getEmailAddress())
                .isPresent();
        if (emailExists)throw new IllegalStateException("Email Address already exists");

        User user = new User(
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getEmailAddress(),
                hashPassword(signUpRequest.getPassword())
        );

        userService.getUser(user);
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

    @Override
    public String login (LoginRequest loginRequest) {
        var foundUser = userService.getByEmailAddress(loginRequest.getEmailAddress());
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
        User foundUser = userService.getByEmailAddress(resendTokenRequest.getEmailAddress()).orElseThrow(() -> new
                IllegalStateException("this email does not exist"));
        if (foundUser.getIsVerified().equals(true)) throw new RegistrationException("Already verified");
        String token = generateToken(foundUser);
        emailService.send(resendTokenRequest.getEmailAddress(), buildEmail(foundUser.getFirstName(), token));
        return "token has been resent successfully";

    }

    @Override
    public String tokenConfirmation (TokenConfirmationRequest tokenConfirmationRequest){
        var token = tokenService.getConfirmationToken(tokenConfirmationRequest.getToken())
                .orElseThrow(() -> new RegistrationException("Invalid Token"));

        if (token.getExpiredAt().isBefore(LocalDateTime.now()))
            throw new IllegalStateException("Token has expired");
        tokenService.setTokenConfirmationAt(token.getToken());
        userService.enableUser(tokenConfirmationRequest.getEmail());
        return "User Has Been Confirmed";
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

}
