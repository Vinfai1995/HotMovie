package com.vinfai.hotmovie;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by 12045 on 2017/2/4.
 */

public class HotMovieAdapter extends ArrayAdapter<String> {

//    创建日志标签
    private static String LOG_TAG = HotMovieAdapter.class.getSimpleName();

    public HotMovieAdapter( Activity context,
                            ArrayList<String> hotMovies) {
        super(context, 0, hotMovies);
    }


    @Override
    public View getView(int position,  View convertView,
                         ViewGroup parent) {

//        如果视图不存在，则获取其，并加载到布局文件中
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.gridview_item, parent, false);
        }

//        获取Item位置
        String posterUri = getItem(position);

//        获取图片资源并填充到布局文件中
        ImageView posterView = (ImageView) convertView
                .findViewById(R.id.image_item);
        loadPoster(posterUri, posterView);

        return convertView;
    }

    private void loadPoster(String posterUri, ImageView currentView) {
        Picasso.with(getContext()).load(posterUri).into(currentView);
    }
}
