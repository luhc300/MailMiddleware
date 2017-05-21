package com.fudan.ssilv.mailmiddleware.Mail.MailBoxes;



import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fudan.ssilv.mailmiddleware.Mail.Core.Mail;
import com.fudan.ssilv.mailmiddleware.Mail.Core.MailReceive;
import com.fudan.ssilv.mailmiddleware.Mail.Core.ReceiveOneMail;
import com.fudan.ssilv.mailmiddleware.ReceiveMailAsyncTask;

import javax.mail.Message;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ssilv on 2017/2/17.
 */
public class MailBox {
    private MailBoxConfig mailBoxConfig = null;

    public MailBox()
    {
    }
    public MailBox(MailBoxConfig mailBoxConfig)
    {
        this.mailBoxConfig = mailBoxConfig;
    }
    public MailBoxConfig getMailBoxConfig() {
        return mailBoxConfig;
    }

    public void setMailBoxConfig(MailBoxConfig mailBoxConfig) {
        this.mailBoxConfig = mailBoxConfig;
    }
    public void addMailBoxConfig(MailBoxConfig mailBoxConfig)
    {

    }
    public void setMailBoxConfigs(ArrayList<MailBoxConfig> mailBoxConfigs)
    {

    }
    public void send(String fName)
    {
        Mail mail = new Mail();
        mail.addAttachfile(fName);
        mail.setSmtpServer(mailBoxConfig.getSmtpServer());
        mail.setDisplayName(mailBoxConfig.getDisplayName());
        mail.setFrom(mailBoxConfig.getFrom());
        mail.setIfAuth(true);
        mail.setUserName(mailBoxConfig.getUsername());
        mail.setPassword(mailBoxConfig.getPassword());
        mail.setTo(mailBoxConfig.getTo());
        int lastIndex = fName.lastIndexOf("\\");
        if (lastIndex == -1)
        {
            lastIndex = fName.lastIndexOf("/");
        }
        String fileName = fName.substring(lastIndex+1);
        mail.setSubject(fileName);
        mail.setContent(fileName);
        mail.send();
    }
    public void receive(TextView textView, ProgressBar progressBar, String attachmentPath)
    {
        /*
        final MsgHandler msgHandler = new MsgHandler(activity);

        Thread thread=new Thread(new Runnable()
        {
            android.os.Message msg = null;
            @Override

            public void run()
            {
                MailReceive mailReceive = new MailReceive();
                mailReceive.setSmtpServer(mailBoxConfig.getSmtpServer());
                mailReceive.setPop3Server(mailBoxConfig.getPop3Server());
                mailReceive.setUsername(mailBoxConfig.getUsername());
                mailReceive.setPassword(mailBoxConfig.getPassword());

                try {
                    msg = msgHandler.obtainMessage();
                    msg.arg1 = 1;
                    msgHandler.sendMessage(msg);
                    ArrayList<Message> mailList = mailReceive.receiveMailList();
                    msg = msgHandler.obtainMessage();
                    msg.arg1 = 2;
                    msgHandler.sendMessage(msg);
                    for (Message message : mailList)
                    {
                        ReceiveOneMail rom = new ReceiveOneMail((MimeMessage) message);
                        rom.setAttachPath(attachmentPath);
                        rom.saveAttachMent((Part)message);
                    }
                    msg = msgHandler.obtainMessage();
                    msg.arg1 = 3;
                    msgHandler.sendMessage(msg);
                } catch (Exception e) {
                    msg = msgHandler.obtainMessage();
                    msg.arg1 = 4;
                    msgHandler.sendMessage(msg);
                }
            }
        });
        thread.start();

        MailReceive mailReceive = new MailReceive();
        mailReceive.setSmtpServer(mailBoxConfig.getSmtpServer());
        mailReceive.setPop3Server(mailBoxConfig.getPop3Server());
        mailReceive.setUsername(mailBoxConfig.getUsername());
        mailReceive.setPassword(mailBoxConfig.getPassword());

        try {
            ArrayList<Message> mailList = mailReceive.receiveMailList();
            for (Message message : mailList)
            {
                ReceiveOneMail rom = new ReceiveOneMail((MimeMessage) message);
                //System.out.println(rom.getSubject());
                rom.setAttachPath(attachmentPath);
                rom.saveAttachMent((Part)message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        ReceiveMailAsyncTask receiveMailAsyncTask = new ReceiveMailAsyncTask(textView,progressBar,this.mailBoxConfig);
        receiveMailAsyncTask.execute(attachmentPath);
    }

    public static void main(String[] args)
    {
        MailBox mailBox = new MailBox(new NetEaseMailBoxConfig());
        //mailBox.send("D:\\Programs\\Java\\CloudUploader\\gqbb.jpg");
        //mailBox.receive("D:\\Programs\\Java\\CloudUploader\\AttachmentTest");
    }
}
