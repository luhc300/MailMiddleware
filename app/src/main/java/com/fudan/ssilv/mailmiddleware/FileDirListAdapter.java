package com.fudan.ssilv.mailmiddleware;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.util.List;

/**
 * Created by Qiujuer on 14-3-6.
 */
public class FileDirListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Bitmap mIcon;
    private List<String> paths;

    public FileDirListAdapter(Context context, List<String> paths) {
        this.mInflater = LayoutInflater.from(context);
        this.paths = paths;
        this.mIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.folder);
    }

    public int getCount() {
        return paths.size();
    }

    public Object getItem(int position) {
        return paths.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.file_row, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.icon.setImageBitmap(mIcon);
        holder.text.setText(new File(paths.get(position).toString()).getName());
        return convertView;
    }

    private class ViewHolder {
        TextView text;
        ImageView icon;
    }
}
