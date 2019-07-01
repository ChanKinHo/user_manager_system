package com.qf.email_serviceimpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceimplApplicationTests {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void contextLoads() throws Exception {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);

        messageHelper.setFrom("chankinho@sina.com");

        messageHelper.addTo("876204671@qq.com");

        messageHelper.setSubject("注册验证！");

        messageHelper.setText("验证码:");

        messageHelper.setSentDate(new Date());

        javaMailSender.send(mimeMessage);
    }

}
