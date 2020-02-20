package com.techneapps.notestaking.data.dao.notes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteObj implements Parcelable {
    @SuppressWarnings("unused")
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
    private long id;
    private String title;
    private String content;
    private long timeStamp;

    public NoteObj() {
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