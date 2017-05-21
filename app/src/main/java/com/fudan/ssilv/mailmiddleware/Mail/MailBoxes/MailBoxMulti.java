package com.fudan.ssilv.mailmiddleware.Mail.MailBoxes;

import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fudan.ssilv.mailmiddleware.FileReceiveService;
import com.fudan.ssilv.mailmiddleware.IFileReceiveService;
import com.fudan.ssilv.mailmiddleware.Mail.Core.Mail;
import com.fudan.ssilv.mailmiddleware.Mail.Strategy.AverangeStrategy;
import com.fudan.ssilv.mailmiddleware.Mail.Strategy.MailReceiveStrategy;
import com.fudan.ssilv.mailmiddleware.Mail.Strategy.SingleFastestStrategy;
import com.fudan.ssilv.mailmiddleware.Mail.Strategy.WeightedStratrgy;
import com.fudan.ssilv.mailmiddleware.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by ssilv on 2017/4/24.
 */

public class MailBoxMulti extends MailBox {
    private ArrayList<MailBoxConfig> mailBoxConfigs;

    public MailBoxMulti(){}
    public MailBoxMulti(ArrayList<MailBoxConfig> mailBoxConfigs)
    {
        this.mailBoxConfigs = mailBoxConfigs;
    }
    public void addMailBoxConfig(MailBoxConfig mailBoxConfig){
        mailBoxConfigs.add(mailBoxConfig);
    }
    public void setMailBoxConfigs(ArrayList<MailBoxConfig> mailBoxConfigs)
    {
        this.mailBoxConfigs = mailBoxConfigs;
    }
    public void send(final String fName){
        for (final MailBoxConfig mailBoxConfig : mailBoxConfigs){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
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
            });
            t.start();

        }
    }
    public void receive(TextView textView, ProgressBar progressBar, String attachmentPath)
    {
        ArrayList<Message> messages;
        for (IFileReceiveService iFileReceiveService : MainActivity.fileReceiveServiceList)
        {
            iFileReceiveService.setAttachmentPath(attachmentPath);
            iFileReceiveService.receiveFileList();
        }
        int last = MainActivity.receiveCount;
        while (MainActivity.receiveCount < MainActivity.fileReceiveServiceList.size()){

        }
        messages = MainActivity.fileReceiveServiceList.get(0).getMailList();
        MainActivity.receiveCount = 0;
        MailReceiveStrategy mailReceiveStrategy = new AverangeStrategy();
        //MailReceiveStrategy mailReceiveStrategy = new SingleFastestStrategy();
        //MailReceiveStrategy mailReceiveStrategy = new WeightedStratrgy();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("开始下载时间：" + df.format(new Date()));// new Date()为获取当前系统时间
        for (int i=0; i<messages.size();i++)
        {
            int decision = mailReceiveStrategy.decide(i);
            System.out.println(decision);
            try {
                MainActivity.fileReceiveServiceList.get(decision).receiveFile(messages.get(i).getSubject());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        /*
        while (MainActivity.receiveFileCount < messages.size())
        {
            if (last < MainActivity.receiveFileCount)
            {
                last = MainActivity.receiveFileCount;
                progressBar.setProgress(MainActivity.receiveCount);
            }
        }
        if (last < MainActivity.receiveFileCount)
        {
            last = MainActivity.receiveFileCount;
            progressBar.setProgress(MainActivity.receiveCount);
        }*/
    }
}
