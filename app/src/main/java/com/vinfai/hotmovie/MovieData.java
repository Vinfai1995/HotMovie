package com.vinfai.hotmovie;

import android.support.annotation.NonNull;

/**
 * Created by 12045 on 2017/2/5.
 */

public class MovieData {

    private String posterUri;
    private String movieTitles;
    private String overview;
    private String voteAverage;
    private String releaseDate;

//    初始化构造器
    public MovieData(String posterUri, String movieTitles, String overview, String voteAverage, String releaseDate) {
        this.posterUri = posterUri;
        this.movieTitles = movieTitles;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    public String getPosterUri() {
        return posterUri;
    }
    public String getMovieTitles() {
        return movieTitles;
    }
    public String getOverview() {
        return overview;
    }
    public String getVoteAverage() {
        return voteAverage;
    }
    public String getReleaseDate() {
        return releaseDate;
    }

    @NonNull
    public String toStrng() {
        return "MovieData{" +
                "posterUri='" + posterUri + '\'' +
                ", movieTitle='" + movieTitles + '\'' +
                ", overview='" + overview + '\'' +
                ", voteAverage='" + voteAverage + '\'' +
                ", releaseData='" + releaseDate + '\'' +
                '}';
    }
}
