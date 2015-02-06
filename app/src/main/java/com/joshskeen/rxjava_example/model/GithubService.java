package com.joshskeen.rxjava_example.model;

import java.util.List;

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
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setLog(message -> System.out.println(message))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return new GithubService(restAdapter.create(GithubAPIInterface.class));
    }

    @Override
    public void requestUserDetails(@Path("username") String username, Callback<GithubUserDetail> callback) {
        mGithubAPI.requestUserDetails(username, callback);
    }

    @Override
    public void requestUsers(Callback<GithubUsersResponse> callback) {
        mGithubAPI.requestUsers(callback);
    }

    @Override
    public Observable<GithubUserDetail> rxRequestUserDetails(@Path("username") String username) {
        return mGithubAPI.rxRequestUserDetails(username);
    }

    @Override
    public Observable<List<GithubUser>> rxRequestUsers() {
        return mGithubAPI.rxRequestUsers();
    }

    @Override
    public List<GithubUser> requestUsers(){
        return mGithubAPI.requestUsers();
    }
}
