package com.example.demo.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ContactController {

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/contact")
    public String showContact() {
        return "user/contact";
    }

    @PostMapping("/contact")
    public String submitContact(HttpServletRequest request, @RequestParam("attachment") MultipartFile multipartFile) throws MessagingException {
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String mailSubject = fullname + "has sent a message";
        String mailContent = "<p><b>Sender name :" + fullname + "</p>";
        mailContent += "<p><b>Sender mail :" + email + "</p>";
        mailContent += "<p><b>Subject : " + subject + "</p>";
        mailContent += "<p><b>Content :" + content + "</p>";
        mailContent += "<hr><img src='cid:logoImage'/>";
        helper.setFrom("doanduc10393@gmail.com");
        helper.setTo("davidduc10312@gmail.com");
        helper.setSubject(mailSubject);
        helper.setText(mailContent);

        ClassPathResource resource = new ClassPathResource("/static/images/logo.png");
        helper.addInline("logoImage",resource);
        javaMailSender.send(message);
        return "message";
    }
}
