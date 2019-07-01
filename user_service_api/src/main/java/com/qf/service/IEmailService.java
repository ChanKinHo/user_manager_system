package com.qf.service;

import com.qf.entity.Email;

public interface IEmailService {

    public void sendCode(String email,int code);

    public void sendUrl(Email email);
}
