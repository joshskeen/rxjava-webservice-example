package com.joshskeen.rxjava_example.model;

import com.joshskeen.rxjava_example.event.UserDetailsLoadedCompleteEvent;

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

    public GithubServiceManager() {
        mService = GithubService.getInstance();
    }

    //The Nested Callbacks Way
    public void fetchUserDetails() {
        //first, request the users...
        mService.requestUsers(new Callback<List<GithubUser>>() {
            @Override
            public void success(final List<GithubUser> githubUsers,
                                final Response response) {
                Timber.i("Request Users request completed");
                final List<GithubUserDetail> githubUserDetails = new ArrayList<>();
                //next, loop over each item in the response
                for (GithubUser user : githubUsers) {
                    //request a detail object for that user
                    mService.requestUserDetails(user.mLogin,
                            new Callback<GithubUserDetail>() {
                                @Override
                                public void success(GithubUserDetail githubUserDetail,
                                                    Response response) {
                                    Timber.i("User Detail request completed for user : " + githubUserDetail.mLogin);
                                    githubUserDetails.add(githubUserDetail);
                                    if (githubUserDetails.size() == githubUsers.size()) {
                                        //we've downloaded'em all - notify all who are interested!
                                        EventBus.getDefault().post(new UserDetailsLoadedCompleteEvent(githubUserDetails));
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

    public Observable<List<GithubUserDetail>> rxFetchUserDetails() {
        //request the users
        return mService.rxRequestUsers().concatMap(Observable::from)
                .concatMap((GithubUser githubUser) ->
                                //request the details for each user
                                mService.rxRequestUserDetails(githubUser.mLogin)
                )
                        //accumulate them as a list
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
