package com.joshskeen.rxjava_example.model;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import timber.log.Timber;

public class GithubServiceManager {


    private GithubService mService;

    public GithubServiceManager() {
        mService = GithubService.getInstance();
    }

    public void fetchUserDetails() {
        //TODO: plain old callbacks implementation
        //first, request the users...
        mService.requestUsers(new Callback<GithubUsersResponse>() {
            @Override
            public void success(GithubUsersResponse githubUsersResponse, Response response) {
                Timber.i("Request Users request completed");
                final List<GithubUserDetail> githubUserDetails = new ArrayList<GithubUserDetail>();
                for (GithubUserDetail githubUserDetail : githubUserDetails) {
                    mService.requestUserDetails(githubUserDetail.mLogin, new Callback<GithubUserDetail>() {
                        @Override
                        public void success(GithubUserDetail githubUserDetail, Response response) {
                            Timber.i("User Detail request completed for user : " + githubUserDetail.mLogin);
                            githubUserDetails.add(githubUserDetail);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Timber.e("Request User Detail Failed!!!!", error);
                        }
                    });
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e("Request User Failed!!!!", error);
            }
        });
    }

    public Observable<List<GithubUserDetail>> rxFetchUserDetails() {
        //TODO: RxJava Implementation
        return null;
    }

}
