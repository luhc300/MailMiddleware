package com.fudan.ssilv.mailmiddleware;

/**
 * Created by ssilv on 2017/4/18.
 */

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fudan.ssilv.mailmiddleware.Mail.Core.MailReceive;
import com.fudan.ssilv.mailmiddleware.Mail.Core.ReceiveOneMail;
import com.fudan.ssilv.mailmiddleware.Mail.MailBoxes.MailBoxConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.Message;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;

/**
 * 生成该类的对象，并调用execute方法之后
 * 首先执行的是onProExecute方法
 * 其次执行doInBackgroup方法
 *
 */
public class ReceiveMailAsyncTask extends AsyncTask<String, Integer, String> {

    private TextView textView;
    private ProgressBar progressBar;
    private MailBoxConfig mailBoxConfig;

    public ReceiveMailAsyncTask(TextView textView, ProgressBar progressBar, MailBoxConfig mailBoxConfig) {
        super();
        this.textView = textView;
        this.progressBar = progressBar;
        this.mailBoxConfig = mailBoxConfig;
    }


    /**
     * 这里的Integer参数对应AsyncTask中的第一个参数
     * 这里的String返回值对应AsyncTask的第三个参数
     * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
     * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
     */
    @Override
    protected String doInBackground(String... params) {
        MailReceive mailReceive = new MailReceive();
        mailReceive.setSmtpServer(mailBoxConfig.getSmtpServer());
        mailReceive.setPop3Server(mailBoxConfig.getPop3Server());
        mailReceive.setUsername(mailBoxConfig.getUsername());
        mailReceive.setPassword(mailBoxConfig.getPassword());
        String attachmentPath = params[0];

        try {
            publishProgress(1,0,0);
            ArrayList<Message> mailList = mailReceive.receiveMailList();
            int length = mailList.size();
            publishProgress(2,0,length);
            int i = 0;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println("开始下载时间：" + df.format(new Date()));// new Date()为获取当前系统时间
            for (Message message : mailList)
            {
                Runtime.getRuntime().gc();
                i += 1;
                publishProgress(0,i,length);
                ReceiveOneMail rom = new ReceiveOneMail((MimeMessage) message);
                rom.setAttachPath(attachmentPath);
                rom.saveAttachMent((Part)message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "回收失败";
        }
       return "回收成功" ;
    }


    /**
     * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
     * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
     */
    @Override
    protected void onPostExecute(String result) {
        textView.setText(result);
    }


    //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
    @Override
    protected void onPreExecute() {
        textView.setText("开始回收邮件");
    }


    /**
     * 这里的Intege参数对应AsyncTask中的第二个参数
     * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
     * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        int state = values[0];
        int current = values[1];
        int total = values[2];
        progressBar.setMax(total + 1);
        switch (state)
        {
            case 1:
                textView.setText("正在连接并搜索...");
                progressBar.setProgress(1);
                break;

            case 2:
                textView.setText("已搜索到" + total + "份同步文件");
                progressBar.setProgress(1);
                break;

            case 0:
                textView.setText("正在同步第（" + current + "/" + total + ")份文件...");
                progressBar.setProgress(current+1);
                break;

        }
    }





}
