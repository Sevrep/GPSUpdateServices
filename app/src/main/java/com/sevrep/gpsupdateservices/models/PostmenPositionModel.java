package com.sevrep.gpsupdateservices.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by : Ivan Peter Semilla on 08/10/2021
 */
public class PostmenPositionModel {

    @SerializedName("id")
    private int id;
    @SerializedName("postmen_id")
    private int postmen_id;
    @SerializedName("postmen_lat")
    private String postmen_lat;
    @SerializedName("postmen_long")
    private String postmen_long;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;

    public PostmenPositionModel(int id, int postmen_id, String postmen_lat, String postmen_long, String created_at, String updated_at) {
        this.id = id;
        this.postmen_id = postmen_id;
        this.postmen_lat = postmen_lat;
        this.postmen_long = postmen_long;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostmen_id() {
        return postmen_id;
    }

    public void setPostmen_id(int postmen_id) {
        this.postmen_id = postmen_id;
    }

    public String getPostmen_lat() {
        return postmen_lat;
    }

    public void setPostmen_lat(String postmen_lat) {
        this.postmen_lat = postmen_lat;
    }

    public String getPostmen_long() {
        return postmen_long;
    }

    public void setPostmen_long(String postmen_long) {
        this.postmen_long = postmen_long;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
