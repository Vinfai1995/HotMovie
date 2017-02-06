package com.vinfai.hotmovie;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivityFragment extends android.app.Fragment {

    private String LOG_TAG = MainActivityFragment.class.getSimpleName();

    //    创建Adapter
    HotMovieAdapter movieAdapter;

    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstnaceState) {
        super.onCreate(savedInstnaceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_manu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                upData();
                return true;
            case R.id.action_setting:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

//        加载视图
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

//        为Adapter传递数据
        movieAdapter = new HotMovieAdapter(getActivity(),
                new ArrayList<MovieData>());

//        Gridview获取数据，并设置Adapter
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_gridview);
        gridView.setAdapter(movieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("poster", movieAdapter.getItem(i).getPosterUri());
                intent.putExtra("title", movieAdapter.getItem(i).getMovieTitles());
                intent.putExtra("overview", movieAdapter.getItem(i).getOverview());
                intent.putExtra("voteAverage", movieAdapter.getItem(i).getVoteAverage());
                intent.putExtra("releaseDate", movieAdapter.getItem(i).getReleaseDate());
                Log.d(LOG_TAG, "put data: " + movieAdapter.getItem(i).toString());
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        upData();
    }

    public void upData() {
        RefreshDataMenu getMovieData = new RefreshDataMenu();
        getMovieData.execute();
    }


//    参考自Udacity的Sunshine项目
    public class RefreshDataMenu extends AsyncTask<Void, Void, String[][]> {

        private String LOG_TAG = RefreshDataMenu.class.getSimpleName();
        private String movieJsonStr;
        private String mode;
        private int page;
        private String language;

        @Override
        protected String[][] doInBackground(Void... voids) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            movieJsonStr = null;

            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            mode = sharedPreferences.getString(getString(R.string.pref_movieSort_key), getString(R.string.pref_movieSort_defalut));
            page = 1;
            language = sharedPreferences.getString(getString(R.string.pref_language_key), getString(R.string.pref_language_defalutValue));

            try {
                final String BASE_URL =
                        "http://api.themoviedb.org/3/movie/" + mode + "?";
                final String APIKEY_PARAM = "api_key";
                final String PAGE_PARAM = "page";
                final String LANGUAGE_PARAM = "language";

                Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(APIKEY_PARAM,
                                BuildConfig.TheMovieDb_Key)
                        .appendQueryParameter(PAGE_PARAM,
                                String.valueOf(page))
                        .appendQueryParameter(LANGUAGE_PARAM, language)
                        .build();

                URL url = new URL(buildUri.toString());
                Log.d(LOG_TAG, "URL: " + url);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader
                        (new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                movieJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("Place holder Fragment", "Error", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Place holder Fragment", "Error closing stream", e);
                    }
                }
            }
            try {
                return getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String[][] getMovieDataFromJson(String movieJsonStr) throws JSONException {
            JSONObject movieData = new JSONObject(movieJsonStr);
            JSONArray resultArray = movieData.getJSONArray("results");

            String[][] resultStrs = new String[resultArray.length()][5];

            for (int i = 0; i < resultArray.length(); i++) {
                String posterPath = "http://image.tmdb.org/t/p/w/185";
                JSONObject movieDataInfo = resultArray.getJSONObject(i);
                posterPath += movieDataInfo.getString("poster_path");
                resultStrs[i][0] = posterPath;

//                解析电影名称
                String movieTitle = movieDataInfo.getString("title");
                resultStrs[i][1] = movieTitle;

//                解析电影简介
                String movieOverview = movieDataInfo.getString("overview");
                resultStrs[i][2] = movieOverview;

//                解析用户评分
                String voteAverage = movieDataInfo.getString("vote_average");
                resultStrs[i][3] = voteAverage;

//                解析发布时间
                String releaseDate = movieDataInfo.getString("release_date");
                resultStrs[i][4] = releaseDate;
            }

            Log.d("数据预览", resultStrs[0][0] + " + "
                    + "电影名称" + resultStrs[0][1] + "剧情简介" + resultStrs[0][2]
                    + "用户评分" + resultStrs[0][3] + "发布时间" + resultStrs[0][4]);
            return resultStrs;
        }

        @Override
        protected void onPostExecute(String[][] resultStrs) {
            if (resultStrs != null) {
                movieAdapter.clear();
                for (int i = 0; i < resultStrs.length; i++) {
                    movieAdapter.add(new MovieData(
                            resultStrs[i][0],
                            resultStrs[i][1],
                            resultStrs[i][2],
                            resultStrs[i][3],
                            resultStrs[i][4]
                    ));
                }
            }
        }
    }
}
