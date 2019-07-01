package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Email;
import com.qf.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendCode(String email,int code)  {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setFrom("chankinho@sina.com");

            messageHelper.addTo(email);

            messageHelper.setSubject("注册验证！");

            messageHelper.setText("验证码:"+code);

            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendUrl(Email email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setFrom(email.getSender());

            messageHelper.addTo(email.getReceiver());

            messageHelper.setSubject(email.getTitle());

            messageHelper.setText(email.getContent());

            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
