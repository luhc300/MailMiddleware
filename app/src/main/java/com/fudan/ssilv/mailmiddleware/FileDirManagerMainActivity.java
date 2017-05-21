package com.fudan.ssilv.mailmiddleware;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileDirManagerMainActivity extends ListActivity {
    private List<String> paths = null;
    private String rootPath ="/";
    private String curPath = "/";
    private TextView mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager_main);
        mPath = (TextView) findViewById(R.id.mPath);
        mPath.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getFileDir(new File(curPath).getParent());
            }
        });
        mPath.setEnabled(true);
        Button buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent data = new Intent(FileDirManagerMainActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("file", curPath);
                data.putExtras(bundle);
                setResult(2, data);
                finish();

            }
        });
        paths = new ArrayList<String>();
        String state = Environment.getExternalStorageState();
        /*
        if(Environment.MEDIA_MOUNTED.equals(state)) {

            rootPath = Environment.getExternalStorageDirectory().getPath();
        }
        else{
            rootPath = Environment.getDataDirectory().getPath();
        }*/
        getFileDir(rootPath);
    }


    private void getFileDir(String filePath) {
        paths.clear();
        mPath.setText(curPath = filePath);
        //设置向上是否可用
        if (filePath.equals(rootPath))
            mPath.setEnabled(false);
        else
            mPath.setEnabled(true);
        File f = new File(filePath);
        File[] files = f.listFiles();
        //判断当前下是否有文件夹
        if ((files == null)||(files.length <= 0))
            return;
        for (int i = 0; i < files.length; i++) {
            //过滤一遍
            //1.是否为文件夹
            //2.是否可访问
            if (files[i].isDirectory() && files[i].listFiles() != null) {
                paths.add(files[i].getPath());
            }
        }
        setListAdapter(new FileDirListAdapter(this, paths));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        this.getFileDir(paths.get(position));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.file_manager_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String sdDir = "/sdcard/";
        switch (id) {
            case R.id.action_dir_camera:
                this.getFileDir(sdDir + "camera");
                break;
            case R.id.action_dir_download:
                this.getFileDir(sdDir + "download");
                break;
            case R.id.action_dir_music:
                this.getFileDir(sdDir + "music");
                break;
            case R.id.action_dir_photo:
                this.getFileDir(sdDir + "photo");
                break;
            case R.id.action_dir_video:
                this.getFileDir(sdDir + "video");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
