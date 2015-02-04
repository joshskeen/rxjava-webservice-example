package com.joshskeen.rxjava_example.model;


//{
//        "login": "richcollins",
//        "id": 135,
//        "avatar_url": "https://avatars.githubusercontent.com/u/135?v=3",
//        "gravatar_id": "",
//        "url": "https://api.github.com/users/richcollins",
//        "html_url": "https://github.com/richcollins",
//        "followers_url": "https://api.github.com/users/richcollins/followers",
//        "following_url": "https://api.github.com/users/richcollins/following{/other_user}",
//        "gists_url": "https://api.github.com/users/richcollins/gists{/gist_id}",
//        "starred_url": "https://api.github.com/users/richcollins/starred{/owner}{/repo}",
//        "subscriptions_url": "https://api.github.com/users/richcollins/subscriptions",
//        "organizations_url": "https://api.github.com/users/richcollins/orgs",
//        "repos_url": "https://api.github.com/users/richcollins/repos",
//        "events_url": "https://api.github.com/users/richcollins/events{/privacy}",
//        "received_events_url": "https://api.github.com/users/richcollins/received_events",
//        "type": "User",
//        "site_admin": false
//        }

import com.google.gson.annotations.SerializedName;

import lombok.ToString;

@ToString
public class GithubUser {
    @SerializedName("id")
    public String mId;
    @SerializedName("login")
    public String mLogin;
    @SerializedName("avatar_url")
    public String mAvatarUrl;
}
