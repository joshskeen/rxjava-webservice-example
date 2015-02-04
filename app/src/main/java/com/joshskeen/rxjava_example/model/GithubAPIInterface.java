package com.joshskeen.rxjava_example.model;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

interface GithubAPIInterface {

    @GET("/users/{username}")
    public void requestUserDetails(@Path("username") String username, Callback<GithubUserDetail> callback);

    @GET("/users")
    public void requestUsers(Callback<GithubUsersResponse> callback);

    @GET("/users/{username}")
    public Observable<GithubUserDetail> rxRequestUserDetails(@Path("username") String username);

    @GET("/users")
    public Observable<GithubUsersResponse> rxRequestUsers();
}
