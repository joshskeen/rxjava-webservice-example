package com.joshskeen.rxjava_example.model;

import rx.Observable;

public class DataStore {

    private GithubServiceManager mManager;

    public DataStore(GithubServiceManager manager) {
        mManager = manager;
    }

    public Observable<GithubUser> cachedUsers() {
        return mManager.rxFetchUsers().cache();
    }

}
