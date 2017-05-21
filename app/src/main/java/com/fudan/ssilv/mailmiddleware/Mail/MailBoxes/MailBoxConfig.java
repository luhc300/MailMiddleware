package com.fudan.ssilv.mailmiddleware.Mail.MailBoxes;


/**
 * Created by ssilv on 2017/2/16.
 */
public abstract class MailBoxConfig {
    protected String smtpServer;
    protected String pop3Server;
    protected String from;
    protected String displayName;
    protected String username;
    protected String password;
    protected String to;
    protected String subject;
    protected String content;
    public MailBoxConfig()
    {

    }

    public void setSmtpServer(String smtpServer)
    {
        this.smtpServer = smtpServer;
    }
    public void setPop3Server(String pop3Server) { this.pop3Server = pop3Server; }
    public void setFrom(String from)
    {
        this.from = from;
    }
    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public void setTo(String to)
    {
        this.to = to;
    }
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public String getPop3Server() { return pop3Server; }

    public String getFrom() {
        return from;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

}
