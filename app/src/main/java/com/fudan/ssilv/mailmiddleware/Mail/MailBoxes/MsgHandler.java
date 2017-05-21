package com.fudan.ssilv.mailmiddleware.Mail.MailBoxes;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by ssilv on 2017/4/11.
 */

public class MsgHandler extends Handler {
    private Activity activity;
    public MsgHandler(Activity activity){
        this.activity=(Activity)new WeakReference(activity).get();//使用弱引用WeakReferenc，以避免Handler内存泄露
    }
    @Override
    public void handleMessage(Message msg) {

        switch (msg.arg1) {
            case 1:
                showInfo("正在查找云端文件...");
                break;
            case 2:
                showInfo("正在回收文件...");
                break;
            case 3:
                showInfo("回收完成！");
                break;
            case 4:
                showInfo("同步文件失败！");
                break;
            default:
                break;
        }
        super.handleMessage(msg);
    }
    public void showInfo(String info)
    {
        Toast.makeText(activity.getApplicationContext(),info, Toast.LENGTH_SHORT).show();
    }

}
