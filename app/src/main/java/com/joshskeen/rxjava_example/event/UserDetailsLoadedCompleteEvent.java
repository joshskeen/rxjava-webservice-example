package com.joshskeen.rxjava_example.event;

import com.joshskeen.rxjava_example.model.GithubUserDetail;

import java.util.List;

import lombok.ToString;

@ToString
public class UserDetailsLoadedCompleteEvent {
    private List<GithubUserDetail> mGithubUserDetails;

    public UserDetailsLoadedCompleteEvent(List<GithubUserDetail> githubUserDetails) {
        mGithubUserDetails = githubUserDetails;
    }

    public List<GithubUserDetail> getGithubUserDetails() {
        return mGithubUserDetails;
    }
}
