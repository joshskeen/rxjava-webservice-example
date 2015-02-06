package com.joshskeen.rxjava_example.model;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
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



    //The RXJava Way
    private Observable<GithubUser> rxFetchUsers() {
        return mService.rxRequestUsers()
                .concatMap(githubUsersResponse ->
                        Observable.from(githubUsersResponse.mGithubUsers)).doOnError(throwable -> {
                            Timber.e("githubUsersResponse exception", throwable);
                        });
    }

    private Observable<GithubUserDetail> rxFetchDetails() {
        return rxFetchUsers()
                .concatMap((GithubUser githubUser) -> mService.rxRequestUserDetails(githubUser.mLogin)).doOnError(throwable -> {
                    Timber.e("userDetailsResponse exception", throwable);
                });
    }

    public void rxFetchUserDetails() {
        rxFetchDetails().cache().toList().subscribe(githubUserDetails -> {
            Timber.i("Got " + githubUserDetails.size() + " user details", githubUserDetails);
            EventBus.getDefault().post(new UserDetailsLoadedCompleteEvent(githubUserDetails));
        },
        throwable -> Timber.e("Request User Failed!!!!", throwable));
    }




}
