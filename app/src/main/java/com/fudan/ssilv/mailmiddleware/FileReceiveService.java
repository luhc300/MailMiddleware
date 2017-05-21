package com.fudan.ssilv.mailmiddleware;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.fudan.ssilv.mailmiddleware.Mail.Core.MailReceive;
import com.fudan.ssilv.mailmiddleware.Mail.Core.ReceiveOneMail;
import com.fudan.ssilv.mailmiddleware.Mail.MailBoxes.MailBoxConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;

/**
 * Created by ssilv on 2017/4/24.
 */

public class FileReceiveService extends Service {
    public class MyBinder extends Binder implements IFileReceiveService
    {
        public void setMailBoxConfig(MailBoxConfig mailBoxConfig)
        {
            FileReceiveService.this.mailBoxConfig = mailBoxConfig;
        }
        public void setAttachmentPath(String attachmentPath)
        {
            FileReceiveService.this.path =attachmentPath;
        }

        @Override
        public ArrayList<Message> getMailList() {
            return FileReceiveService.this.mailList;
        }

        public void receiveFileList()
        {
            //System.out.println("Service Thread:" + Thread.currentThread().getId());
            //System.out.println("Service Process:" + Process.myPid());
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    MailReceive mailReceive = new MailReceive();
                    mailReceive.setSmtpServer(mailBoxConfig.getSmtpServer());
                    mailReceive.setPop3Server(mailBoxConfig.getPop3Server());
                    mailReceive.setUsername(mailBoxConfig.getUsername());
                    mailReceive.setPassword(mailBoxConfig.getPassword());
                    try {
                        FileReceiveService.this.mailList = mailReceive.receiveMailList();
                        synchronized (this)
                        {
                            MainActivity.receiveCount += 1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        }
        public void receiveFile(final String fileName)
        {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = getNameMessage(fileName);
                    ReceiveOneMail rom = new ReceiveOneMail((MimeMessage) message);
                    rom.setAttachPath(path);
                    try {
                        rom.saveAttachMent((Part)message);
                        synchronized (this)
                        {
                            MainActivity.receiveFileCount += 1;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
    }
    MyBinder binder = new MyBinder();
    private static final String TAg = "BinderService";
    private MailBoxConfig mailBoxConfig;
    private ArrayList<Message> mailList;
    private String path;
    @Override
    public IBinder onBind(Intent intent)
    {
        Log.i(TAg, "BinderService===>onBind()");
        return binder;
    }

    @Override
    public void onCreate()
    {
        Log.i(TAg, "BinderService===>onCreate()");
        super.onCreate();
    }
    @Override
    public void onDestroy()
    {
        Log.i(TAg, "BinderService===>onDestroy()");
        super.onDestroy();
    }
    @Override
    public boolean onUnbind(Intent intent)
    {
        Log.i(TAg, "BinderService===>onUnbind()");
        return super.onUnbind(intent);
    }
    public String getFrom()
    {
        return null;
    }
    public Message getNameMessage(String fileName)
    {
        for (Message message:mailList){
            try {
                if (message.getSubject().equals(fileName)){
                    return message;
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
