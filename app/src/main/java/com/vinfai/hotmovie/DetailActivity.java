package com.vinfai.hotmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by 12045 on 2017/2/5.
 */

public class DetailActivity extends AppCompatActivity {

    private String LOG_TAG = DetailFragment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new DetailFragment())
                .commit();
    }

    public static class DetailFragment extends android.app.Fragment {

        public DetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            Intent intent = getActivity().getIntent();
            String posterPath = intent.getStringExtra("poster");

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            ImageView posterImage = (ImageView) rootView.findViewById(R.id.detail_poster);
            loadPoster(posterPath, posterImage);

            TextView titleText = (TextView) rootView.findViewById(R.id.detail_title);
            titleText.setText(intent.getStringExtra("title"));

            TextView overViewText = (TextView) rootView.findViewById(R.id.detail_overview);
            overViewText.setText(intent.getStringExtra("overview"));

            TextView voteAverage = (TextView) rootView.findViewById(R.id.detail_voteAverager);
            voteAverage.setText(intent.getStringExtra("voteAverage"));

            TextView releaseDate = (TextView) rootView.findViewById(R.id.detail_releaseDate);
            releaseDate.setText(intent.getStringExtra("releaseDate"));

            return rootView;
        }

        private void loadPoster(String posterUri, ImageView currentView) {
            Picasso.with(getActivity().getBaseContext())
                    .load(posterUri).into(currentView);
        }
    }
}
