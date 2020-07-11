package com.example.project_movies.model.Models;

public class article {

    private String url;
    private String suggested_link_text;
    private String summary_short;

    public article(String url, String suggested_link_text, String summary_short) {
        this.url = url;
        this.suggested_link_text = suggested_link_text;
        this.summary_short = summary_short;
    }

    public String getUrl() {
        return url;
    }

    public String getSuggested_link_text() {
        return suggested_link_text;
    }

    public String getSummary_short() {
        return summary_short;
    }
}
