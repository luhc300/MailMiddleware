package com.fudan.ssilv.mailmiddleware;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Process;

import com.fudan.ssilv.mailmiddleware.Mail.MailBoxes.MailBox;
import com.fudan.ssilv.mailmiddleware.Mail.MailBoxes.MailBoxConfig;
import com.fudan.ssilv.mailmiddleware.Mail.MailBoxes.MailBoxMulti;
import com.fudan.ssilv.mailmiddleware.Mail.MailBoxes.NetEaseMailBoxConfig;
import com.fudan.ssilv.mailmiddleware.Mail.MailBoxes.SinaMailBoxConfig;
import com.fudan.ssilv.mailmiddleware.Mail.MailBoxes.TencentMailBoxConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends ActionBarActivity {
    public static final int FILE_RESULT_CODE = 1;
    private TextView textView;
    //public static MailBox mailBox = new MailBoxMulti(new ArrayList<MailBoxConfig>());
    public static MailBox mailBox = new MailBox(new SinaMailBoxConfig());
    public static ArrayList<MailBoxConfig> mailBoxConfigs = new ArrayList<MailBoxConfig>();
    public static ArrayList<IFileReceiveService> fileReceiveServiceList = new ArrayList<IFileReceiveService>();
    public static int receiveCount = 0;
    public static int receiveFileCount = 0;
    private IFileReceiveService netEaseFileReceiveService;
    private IFileReceiveService sinaFileReceiveService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
            配置邮箱信息
         */
        mailBoxConfigs.add(new NetEaseMailBoxConfig());
        mailBoxConfigs.add(new SinaMailBoxConfig());
        mailBox.setMailBoxConfigs(mailBoxConfigs);

        final Intent netEaseIntent = new Intent(MainActivity.this, NetEaseFileReceiveService.class);
        ServiceConnection netEaseServiceReceive = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                netEaseFileReceiveService = (IFileReceiveService) service;
                netEaseFileReceiveService.setMailBoxConfig(mailBoxConfigs.get(0));
                fileReceiveServiceList.add(netEaseFileReceiveService);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                netEaseFileReceiveService = null;
            }
        };
        bindService(netEaseIntent, netEaseServiceReceive, BIND_AUTO_CREATE);
        Intent sinaIntent = new Intent(MainActivity.this, SinaFileReceiveService.class);
        ServiceConnection sinaServiceReceive = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                sinaFileReceiveService = (IFileReceiveService) service;
                sinaFileReceiveService.setMailBoxConfig(mailBoxConfigs.get(1));
                fileReceiveServiceList.add(sinaFileReceiveService);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                sinaFileReceiveService = null;
            }
        };
        bindService(sinaIntent, sinaServiceReceive, BIND_AUTO_CREATE);


        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.fileText);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FileDirManagerMainActivity.class);
                startActivityForResult(intent, FILE_RESULT_CODE);
            }
        });
        Button startService = (Button) findViewById(R.id.start_service);
        Button stopService = (Button) findViewById(R.id.stop_service);
        Button receive = (Button) findViewById(R.id.receive);
        Button upload = (Button) findViewById(R.id.upload);
        startService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent startIntent = new Intent(MainActivity.this, ObserverService.class);
                startIntent.putExtra("path", textView.getText().toString());
                startService(startIntent);
            }
        });
        stopService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent stopIntent = new Intent(MainActivity.this,ObserverService.class);
                stopService(stopIntent);
            }
        });
        final TextView progressBarTextView = (TextView) findViewById(R.id.progressBarView);
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar) ;
        receive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //System.out.println("Main Thread:" + Thread.currentThread().getId());
                //System.out.println("Main Process:" + Process.myPid());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                System.out.println("开始时间：" + df.format(new Date()));// new Date()为获取当前系统时间
                mailBox.receive(progressBarTextView,progressBar,textView.getText().toString());


            }
        });
        upload.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                File root = new File(textView.getText().toString());
                File[] files = root.listFiles();
                for (File file : files) {
                    if (!file.isDirectory()) {
                        mailBox.send(file.getAbsolutePath());
                    }
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (FILE_RESULT_CODE == requestCode) {
            Bundle bundle = null;
            if (data != null && (bundle = data.getExtras()) != null) {
                textView.setText( bundle.getString("file"));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}


