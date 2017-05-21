package com.fudan.ssilv.mailmiddleware.Mail.MailBoxes;



/**
 * Created by ssilv on 2017/2/17.
 */
public class NetEaseMailBoxConfig extends MailBoxConfig{
    public NetEaseMailBoxConfig()
    {
        smtpServer = "smtp.163.com";
        pop3Server = "pop3.163.com";
        /*
        from = "18516053895@163.com";
        displayName = "卢皓川";
        username = "18516053895";
        password = "whosyourdaddy111";
        to = "18516053895@163.com";
        */
        from = "lhcbysj@163.com";
        displayName = "卢皓川";
        username = "lhcbysj";
        password = "wqluluwq1";
        to = "lhcbysj@163.com";
    }
    public NetEaseMailBoxConfig(String smtpServer,String pop3Server, String from,String displayName,String username,String password,String to,String subject,String content) {
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
