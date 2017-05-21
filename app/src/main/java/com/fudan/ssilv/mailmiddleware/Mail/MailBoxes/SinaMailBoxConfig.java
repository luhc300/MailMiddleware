package com.fudan.ssilv.mailmiddleware.Mail.MailBoxes;

/**
 * Created by ssilv on 2017/2/17.
 */
public class SinaMailBoxConfig extends MailBoxConfig{
    public SinaMailBoxConfig()
    {
        smtpServer = "smtp.sina.com";
        pop3Server = "pop3.sina.com";
        /*
        from = "ssilv@sina.com";
        displayName = "卢皓川";
        username = "ssilv";
        password = "Wqluluwq1";
        to = "ssilv@sina.com";
         */
        from = "lhcbysj@sina.com";
        displayName = "卢皓川";
        username = "lhcbysj";
        password = "Wqluluwq1";
        to = "lhcbysj@sina.com";
    }
    public SinaMailBoxConfig(String smtpServer,String pop3Server, String from,String displayName,String username,String password,String to,String subject,String content) {
        this.smtpServer = smtpServer;
        this.pop3Server = pop3Server;
        this.from = from;
        this.displayName = displayName;
        this.username = username;
        this.password = password;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }
}
