package com.joshskeen.rxjava_example.model;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Path;
import rx.Observable;

public class GithubService implements GithubAPIInterface {
    public static String ENDPOINT = "https://api.github.com";
    private GithubAPIInterface mGithubAPI;


    public GithubService(GithubAPIInterface githubAPI) {
        mGithubAPI = githubAPI;
    }

    public static GithubService getInstance() {
        GithubAPIInterface githubAPI = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setLog(message -> System.out.println(message))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(GithubAPIInterface.class);
        return new GithubService(githubAPI);
    }

    @Override
    public void requestUserDetails(@Path("username") String username, Callback<GithubUserDetail> callback) {
        getInstance().requestUserDetails(username, callback);
    }

    @Override
    public void requestUsers(Callback<GithubUsersResponse> callback) {
        getInstance().requestUsers(callback);
    }

    @Override
    public Observable<GithubUserDetail> rxRequestUserDetails(@Path("username") String username) {
        return getInstance().rxRequestUserDetails(username);
    }

    @Override
    public Observable<GithubUsersResponse> rxRequestUsers() {
        return getInstance().rxRequestUsers();
    }

}
