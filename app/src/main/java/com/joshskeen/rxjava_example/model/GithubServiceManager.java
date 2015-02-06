package com.joshskeen.rxjava_example.model;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class GithubServiceManager {

    private GithubService mService;
    private EventBus mBus;

    public GithubServiceManager() {
        mService = GithubService.getInstance();
        mBus = EventBus.getDefault();
    }


    //The Nested Callbacks Way
    public void fetchUserDetails() {
        //first, request the users...
        mService.requestUsers(new Callback<GithubUsersResponse>() {
            @Override
            public void success(final GithubUsersResponse githubUsersResponse,
                                final Response response) {
                Timber.i("Request Users request completed");
                final List<GithubUserDetail> githubUserDetails = new ArrayList<>();
                //next, loop over each item in the response
                for (GithubUserDetail githubUserDetail : githubUserDetails) {
                    //request a detail object for that user
                    mService.requestUserDetails(githubUserDetail.mLogin,
                            new Callback<GithubUserDetail>() {
                                @Override
                                public void success(GithubUserDetail githubUserDetail,
                                                    Response response) {
                                    Timber.i("User Detail request completed for user : " + githubUserDetail.mLogin);
                                    githubUserDetails.add(githubUserDetail);
                                    if (githubUserDetails.size() == githubUsersResponse.mGithubUsers.size()) {
                                        //we've downloaded'em all - notify all who are interested!
                                        mBus.post(new UserDetailsLoadedCompleteEvent(githubUserDetails));
                                    }
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

    public void rxFetchUserDetails() {
        //request the users
        mService.rxRequestUsers().concatMap(Observable::from)
        .concatMap((GithubUser githubUser) ->
                        //request the details for each user
                        mService.rxRequestUserDetails(githubUser.mLogin)
        )
        //accumulate them as a list
        .toList()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        //post them on the eventbus
        .subscribe(githubUserDetails -> {
            EventBus.getDefault().post(new UserDetailsLoadedCompleteEvent(githubUserDetails));
        });
    }


}
