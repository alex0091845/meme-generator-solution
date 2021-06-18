package org.tritonhacks.memegenerator;

public class RandomMeme {

    private String postLink;
    private String subreddit;
    private String title;
    private String url;

    public RandomMeme(String postLink, String subreddit, String title, String url) {
        this.postLink = postLink;
        this.subreddit = subreddit;
        this.title = title;
        this.url = url;
    }

    public String getPostLink() {
        return this.postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

    public String getSubreddit() {
        return this.subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}