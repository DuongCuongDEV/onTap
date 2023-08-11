package com.example.cuongdvph20635_thi11.model;

import android.os.Parcel;
import android.os.Parcelable;

public class task implements Parcelable {
    private String id;
    private String createdAt;
    private String title;
    private String content;
    private String end_date;
    private int status;
    private String image;

    public task(String createdAt, String title, String content, String end_date, int status, String image) {
        this.createdAt = createdAt;
        this.title = title;
        this.content = content;
        this.end_date = end_date;
        this.status = status;
        this.image = image;
    }

    @Override
    public String toString() {
        return "task{" +
                "id='" + id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", end_date='" + end_date + '\'' +
                ", status=" + status +
                ", image='" + image + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getEnd_date() {
        return end_date;
    }

    public int getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    protected task(Parcel in) {
        createdAt = in.readString();
        title = in.readString();
        content = in.readString();
        end_date = in.readString();
        status = in.readInt();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createdAt);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(end_date);
        dest.writeInt(status);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<task> CREATOR = new Creator<task>() {
        @Override
        public task createFromParcel(Parcel in) {
            return new task(in);
        }

        @Override
        public task[] newArray(int size) {
            return new task[size];
        }
    };
}

