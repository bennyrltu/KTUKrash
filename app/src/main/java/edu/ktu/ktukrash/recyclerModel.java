package edu.ktu.ktukrash;

import java.util.Comparator;

public class recyclerModel {
    private String title, description;
    private int img;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public static final Comparator<recyclerModel> By_TITLE_ASCENDING = new Comparator<recyclerModel>() {
        @Override
        public int compare(recyclerModel recyclerModel, recyclerModel t1) {
            return recyclerModel.getTitle().compareTo(t1.getTitle());
        }
    };

    public static final Comparator<recyclerModel> By_TITLE_DESCENDING = new Comparator<recyclerModel>() {
        @Override
        public int compare(recyclerModel recyclerModel, recyclerModel t1) {
            return t1.getTitle().compareTo(recyclerModel.getTitle());
        }
    };
}
