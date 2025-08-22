package com.hatemkhabir.mosque_dashboards.service;


import com.hatemkhabir.mosque_dashboards.model.Mosque;
import com.hatemkhabir.mosque_dashboards.model.MosqueAdmin;
import com.hatemkhabir.mosque_dashboards.repository.MosqueAdminRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/*\
* registerMosqueAdmin(adminData) - Create new mosque admin account
getMosqueAdminProfile(adminId) - Retrieve admin details
updateMosqueAdminProfile(adminId, profileData) - Update admin information
resetAdminPassword(email) - Initiate password reset process
verifyAccount(verificationToken) - Confirm admin email
*
* */
@Service
@RequiredArgsConstructor
@Slf4j
public class SuperAdminService {

    private final MailSender mailSender;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SimpleMailMessage simpleMailMessage;
    private final MosqueAdminRepository mosqueAdminRepository;

    public void sendCredentials(Mosque mosque){
        try {
            String username = generateUsername(mosque.getMosqueName(), mosque.getId());
            String firstPassword = username + mosque.getId();
            String hashedPassword = passwordEncoder.encode(firstPassword);
            MosqueAdmin mosqueAdmin = MosqueAdmin.builder()
                    .mosque(mosque)
                    .username(username)
                    .passwordHash(hashedPassword)
                    .phoneNumber(mosque.getAdminPhone())
                    .email(mosque.getAdminEmail())
                    .build();
            mosqueAdminRepository.save(mosqueAdmin);
            sendEmail(mosque.getAdminEmail(),username,firstPassword,mosque.getMosqueName());
        }catch (Exception e) {
        log.error("Error creating creds and saving it to database : {}",e.getMessage());

        }
    }



    @Async

    private void sendEmail(String adminEmail, String username, String firstPassword, String mosqueName) {
            log.info("Trying to send email to {}",adminEmail);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(adminEmail);
            message.setSubject("Mosque Dashboard Login Credentials");
            message.setText("Dear Admin,\n\n" +
                    "Your admin account has been created for mosque: " + mosqueName + ".\n\n" +
                    "Username: " + username + "\n" +
                    "Password: " + firstPassword + "\n\n" +
                    "Please log in and change your password immediately.\n\n" +
                    "Regards,\nYour Team");
            mailSender.send(message);

    }

    private String generateUsername(String mosqueName, Long mosqueId) {
        return mosqueName.replaceAll(" ","")+'_'+mosqueId;
    }


}
