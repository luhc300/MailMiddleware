package com.fudan.ssilv.mailmiddleware.Mail.Core;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.*;
import java.security.Security;
import java.util.*;

/**
 * Created by ssilv on 2017/4/10.
 */
public class MailReceive {
    private String smtpServer;
    private String pop3Server;
    private String username;
    private String password;
    public MailReceive()
    {

    }

    public MailReceive(String smtpServer, String pop3Server, String username, String password)
    {
        this.smtpServer = smtpServer;
        this.pop3Server = pop3Server;
        this.username = username;
        this.password = password;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getPop3Server() {
        return pop3Server;
    }

    public void setPop3Server(String pop3Server) {
        this.pop3Server = pop3Server;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public ArrayList<Message> receiveMailList() throws Exception {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtpServer);
        props.put("mail.smtp.auth", "true");
        //props.setProperty("mail.smtp.ssl.enable", "true");
        if (pop3Server.equals("pop.qq.com"))
        {
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.pop3.socketFactory.fallback", "false");
            props.setProperty("mail.pop3.port", "465");
            props.setProperty("mail.pop3.socketFactory.port", "995");
        }

        Session session = Session.getInstance(props, null);
        URLName urln = new URLName("pop3", pop3Server, 110, null,
                username, password);
        Store store = session.getStore(urln);
        System.out.println("Ready to Connect");
        store.connect();
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        System.out.println("Searching...");
        SearchTerm term = new FromStringTerm("卢皓川");
        Message[] message = folder.search(term);

        ReceiveOneMail pmm = null;
        ArrayList<Message> mailList = new ArrayList<Message>();
        for (int i = message.length-1; i >= 0; i--) {

            pmm = new ReceiveOneMail((MimeMessage) message[i]);

            System.out.println("======================");
            System.out.println("Message " + i + " subject: " + pmm.getSubject());
            //System.out.println("Message " + i + "  containAttachment: "+ pmm.isContainAttach((Part) message[i]));
            //System.out.println("Message " + i + " form: " + pmm.getFrom());
            pmm.setDateFormat("yy年MM月dd日 HH:mm");
            //System.out.println("Message " + i + " sentdate: "+ pmm.getSentDate());
            //System.out.println("Message " + i + " Message-ID: "+ pmm.getMessageId());
            mailList.add(message[i]);

        }
        return mailList;
    }
}
