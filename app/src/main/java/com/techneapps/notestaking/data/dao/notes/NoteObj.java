package com.techneapps.notestaking.data.dao.notes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteObj implements Parcelable {

    public static final Parcelable.Creator<NoteObj> CREATOR = new Parcelable.Creator<NoteObj>() {
        @Override
        public NoteObj createFromParcel(Parcel in) {
            return new NoteObj(in);
        }

        @Override
        public NoteObj[] newArray(int size) {
            return new NoteObj[size];
        }
    };

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private long id;

    @ColumnInfo(name = "note_title")
    private String title;

    @ColumnInfo(name = "note_content")
    private String content;

    @ColumnInfo(name = "note_time_stamp")
    private long timeStamp;

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

    @Ignore
    private NoteObj(Parcel in) {
        title = in.readString();
        content = in.readString();
        timeStamp = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeLong(timeStamp);
    }
}