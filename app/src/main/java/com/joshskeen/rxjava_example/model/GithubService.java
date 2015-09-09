package com.joshskeen.rxjava_example.model;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Path;
import rx.Observable;

public class GithubService {
    public static final int PER_PAGE = 1;
    public static String ENDPOINT = "https://api.github.com";

    private GithubAPIInterface mGithubAPI;

    public GithubService(GithubAPIInterface githubAPI) {
        mGithubAPI = githubAPI;
    }

    public static GithubService getInstance() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return new GithubService(restAdapter.create(GithubAPIInterface.class));
    }

    public void requestUserDetails(@Path("username") String username, Callback<GithubUserDetail> callback) {
        mGithubAPI.requestUserDetails(username, callback);
    }

    public void requestUsers(Callback<List<GithubUser>> callback) {
        mGithubAPI.requestUsers(PER_PAGE, callback);
    }

    public Observable<GithubUserDetail> rxRequestUserDetails(@Path("username") String username) {
        return mGithubAPI.rxRequestUserDetails(username);
    }

    public Observable<List<GithubUser>> rxRequestUsers() {
        return mGithubAPI.rxRequestUsers(PER_PAGE);
    }

}
