package com.promise.www.schoollibrary.ModelClass;

import java.io.Serializable;

public class NoteModel implements Serializable {

    private String name, content, time;

    public NoteModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
