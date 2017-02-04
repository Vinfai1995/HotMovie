package com.vinfai.hotmovie;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 12045 on 2017/2/4.
 */

public class HotMovieAdapter extends ArrayAdapter<HotMovie> {

//    创建日志标签
    private static String LOG_TAG = HotMovieAdapter.class.getSimpleName();

    public HotMovieAdapter(@NonNull Activity context, @NonNull List<HotMovie> hotMovies) {
        super(context, 0, hotMovies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,@NonNull ViewGroup parent) {
//        获取Item位置
        HotMovie hotMovie = getItem(position);

//        如果视图不存在，则获取其，并加载到布局文件中
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.list_item_movie,
                    parent, false);
        }

//        获取图片资源并填充到布局文件中
        ImageView posterView = (ImageView) convertView.findViewById(R.id.list_item_poster);
        posterView.setImageResource(hotMovie.image);
        return convertView;
    }
}
