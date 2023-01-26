package com.semicolon.ewallet.user;

import com.semicolon.ewallet.Exception.RegistrationException;
import com.semicolon.ewallet.user.dto.LoginRequest;



import com.semicolon.ewallet.user.dto.ChangePasswordRequest;

import com.semicolon.ewallet.user.dto.SignUpRequest;
import com.semicolon.ewallet.user.dto.SignUpResponse;
import com.semicolon.ewallet.user.email.EmailSender;
import com.semicolon.ewallet.user.email.EmailService;
import com.semicolon.ewallet.user.token.Token;

import com.semicolon.ewallet.user.token.TokenService;

import com.semicolon.ewallet.user.token.ResendTokenRequest;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import java.util.Objects;




@Service
public class UserServiceImpl implements UserService {

    @Autowired
    EmailSender emailSender;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenService tokenService;

@Autowired
    private EmailService emailService;



    @Override
    public SignUpResponse register(SignUpRequest signUpRequest) throws MessagingException{
        boolean emailExists = userRepository
                .findByEmailAddressIgnoreCase(signUpRequest.getEmailAddress())
                .isPresent();

        if(emailExists)
            throw new IllegalStateException("Email Address already exists");

        User user = new User(
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getEmailAddress(),
                signUpRequest.getPassword()
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

    @Override
    public String resetPassword(ChangePasswordRequest changePasswordRequest) {
        boolean passwordExists = userRepository.
                findUserByPassword(
                        changePasswordRequest.getPassword()).isPresent();

        boolean emailExists = userRepository.
                findByEmailAddressIgnoreCase(
                        changePasswordRequest.getEmailAddress()).
                isPresent();

        if (passwordExists && emailExists) {
            User user = userRepository.
                    findUserById(changePasswordRequest.getId());
            user.setPassword(changePasswordRequest.getNewPassword());
            userRepository.save(user);
        }
        else
            throw new RuntimeException("Invalid details");

        return "New password created";
    }

    @Override
    public String resendToken(ResendTokenRequest resendTokenRequest) throws MessagingException {
        User foundUser = userRepository.findByEmailAddressIgnoreCase(resendTokenRequest.getEmailAddress()).orElseThrow(()->new
                IllegalStateException("this email does not exist"));
        SecureRandom random = new SecureRandom();
        String token = String.valueOf(1000 + random.nextInt(9999));
        emailService.send(resendTokenRequest.getEmailAddress(), buildEmail(foundUser.getFirstName(), token) );
        return "token has been resent successfully";

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
        return token;
    }

    public String login(LoginRequest loginRequest){
        var validEmail = userRepository.findByEmailAddressIgnoreCase(loginRequest.getEmailAddress());
        if (Objects.isNull(validEmail)) throw new RegistrationException("EMAIL ADDRESS OR PASSWORD DOES NOT MATCH");

       try{
           if (!validEmail.get().getPassword().equals(loginRequest.getPassword()));
       } catch (Exception e) {
           throw new RuntimeException(e);
       }

//      if(validEmail.get().getIsVerified().equals(false)){
//          throw new RegistrationException("Account not yet verified");
//        }
       return "Login Successful";

    }



}
