package com.so7.mudah.Model;

/**
 * Created by Rizal Y on 6/4/2017.
 */

public class InfoModel {
    private String _id;
    private  String title;
    private String image;
    private  String body;

    public InfoModel() {

    }

    public InfoModel(String _id, String title, String image, String body) {
        this._id = _id;
        this.title = title;
        this.image = image;
        this.body = body;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
