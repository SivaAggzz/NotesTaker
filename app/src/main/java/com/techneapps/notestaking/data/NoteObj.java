package com.techneapps.notestaking.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "notes")
public class NoteObj implements Serializable {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    public long id;

    @ColumnInfo(name = "note_title")
    public String title;

    @ColumnInfo(name = "note_content")
    public String content;

    @ColumnInfo(name = "note_time_stamp")
    public long timeStamp;

    @Ignore
    public NoteObj() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public NoteObj(long id, String title, String content, long timeStamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.timeStamp = timeStamp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NoteObj)) return false;

        NoteObj noteObj = (NoteObj) o;

        if (id != noteObj.id) return false;
        return Objects.equals(title, noteObj.title);
    }


    @Override
    public int hashCode() {
        int result = 12;
        result = 81 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "Note{" +
                "note_id=" + id +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}