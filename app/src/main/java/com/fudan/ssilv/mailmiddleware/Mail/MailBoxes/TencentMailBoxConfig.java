package com.fudan.ssilv.mailmiddleware.Mail.MailBoxes;

/**
 * Created by ssilv on 2017/2/17.
 */
public class TencentMailBoxConfig extends MailBoxConfig {
    public TencentMailBoxConfig()
    {
        smtpServer = "smtp.qq.com";
        pop3Server = "pop.qq.com";
        /*from = "317791246@qq.com";
        displayName = "卢皓川";
        username = "317791246";
        password = "rvtrlssocovpcbdj";//"zfmsirqkkooobhad";
        to = "317791246@qq.com";
        */
        from = "lhcbysj@qq.com";
        displayName = "卢皓川";
        username = "lhcbysj";
        password = "wikfzqtgqhleddcj";//"zfmsirqkkooobhad";
        to = "lhcbysj@qq.com";


    }
    public TencentMailBoxConfig (String smtpServer,String pop3Server, String from,String displayName,String username,String password,String to,String subject,String content) {
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
