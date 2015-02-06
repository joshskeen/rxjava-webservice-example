package com.joshskeen.rxjava_example.model;

import java.util.List;

public class UserDetailsLoadedCompleteEvent {
    private List<GithubUserDetail> mGithubUserDetails;

    public UserDetailsLoadedCompleteEvent(List<GithubUserDetail> githubUserDetails) {
        mGithubUserDetails = githubUserDetails;
    }

    public List<GithubUserDetail> getGithubUserDetails() {
        return mGithubUserDetails;
    }
}
