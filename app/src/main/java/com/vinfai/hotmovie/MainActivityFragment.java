package com.vinfai.hotmovie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {

//    创建自定义的Adapter
    private HotMovieAdapter movieAdapter;

    HotMovie[] hotMovies = {
            new HotMovie(R.drawable.im_01),
            new HotMovie(R.drawable.im_02),
            new HotMovie(R.drawable.im_03),
            new HotMovie(R.drawable.im_04)
    };

    public MainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        加载视图
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

//        为Adapter传递数据
        movieAdapter = new HotMovieAdapter(getActivity(),
                Arrays.asList(hotMovies));

//        ListView获取数据，并设置Adapter
        ListView listView = (ListView) rootView.findViewById(R.id.listview_movie);
        listView.setAdapter(movieAdapter);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
