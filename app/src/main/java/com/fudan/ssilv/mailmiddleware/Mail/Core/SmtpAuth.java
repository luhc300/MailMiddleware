package com.fudan.ssilv.mailmiddleware.Mail.Core;

/**
 * Created by ssilv on 2017/2/15.
 */
public class SmtpAuth extends javax.mail.Authenticator {
    private String username,password;

    public SmtpAuth(String username,String password){
        this.username = username;
        this.password = password;
    }
    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication(username,password);
    }
}
