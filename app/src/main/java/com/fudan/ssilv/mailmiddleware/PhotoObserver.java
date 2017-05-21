package com.fudan.ssilv.mailmiddleware;

/**
 * Created by ssilv on 2017/3/22.
 */

import android.os.FileObserver;
import android.util.Log;
import android.widget.Toast;

import com.fudan.ssilv.mailmiddleware.Mail.MailBoxes.MailBox;
import com.fudan.ssilv.mailmiddleware.Mail.MailBoxes.NetEaseMailBoxConfig;

import static com.fudan.ssilv.mailmiddleware.MainActivity.mailBox;

public class PhotoObserver extends FileObserver {
    public String absolutePath;
    public PhotoObserver(String path) {
        super(path, FileObserver.ALL_EVENTS);
        absolutePath = path;
    }
    @Override
    public void onEvent(int event, String path) {
        if (path == null) {
            return;
        }
        //a new file or subdirectory was created under the monitored directory
        if ((FileObserver.CREATE & event)!=0) {
            Log.d("Create",absolutePath + "/" + path + " is createdn");
            String pic = absolutePath + "/" + path;

            mailBox.send(pic);
        }

    }
}
