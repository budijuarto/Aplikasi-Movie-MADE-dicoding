package com.example.tabactivity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class AnimeTraining implements Parcelable {
    private String id_api, name, release_date, overview,  poster;

    public  AnimeTraining(){

    }

    public AnimeTraining(String id_api, String name, String release_date, String overview, String poster) {
        this.id_api = id_api;
        this.name = name;
        this.release_date = release_date;
        this.overview = overview;
        this.poster = poster;
    }

    public String getId_api() {
        return id_api;
    }

    public void setId_api(String id_api) {
        this.id_api = id_api;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_api);
        dest.writeString(this.name);
        dest.writeString(this.release_date);
        dest.writeString(this.overview);
        dest.writeString(this.poster);
    }

    public AnimeTraining(Parcel in) {
        this.id_api = in.readString();
        this.name = in.readString();
        this.release_date = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
    }

    public static final Creator<AnimeTraining> CREATOR = new Creator<AnimeTraining>() {
        @Override
        public AnimeTraining createFromParcel(Parcel source) {
            return new AnimeTraining(source);
        }

        @Override
        public AnimeTraining[] newArray(int size) {
            return new AnimeTraining[size];
        }
    };

    public AnimeTraining(JSONObject object, int type) {
        try {
            String id = object.getString("id");
            String title;
            String release_date;
            if(type ==1){
                title = object.getString("title");
                release_date = object.getString("release_date");
            }else {
                title = object.getString("name");
                release_date = object.getString("first_air_date");
            }
            String overview = object.getString("overview");
            String poster = object.getString("poster_path");
            this.id_api = id;
            this.name = title;
            this.release_date = release_date ;
            this.overview = overview;
            this.poster= poster;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
