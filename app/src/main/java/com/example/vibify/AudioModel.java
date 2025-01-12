package com.example.vibify;

import java.io.Serializable;

public class AudioModel implements Serializable {
    String Path;
    String Title;
    String Duration;

    public AudioModel(String path, String title, String duration) {
        Path = path;
        Title = title;
        Duration = duration;
    }

    public String getPath() {
        return Path;
    }

    public String getTitle() {
        return Title;
    }

    public String getDuration() {
        return Duration;
    }

    public void setPath(String path) {
        Path = path;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }
}
