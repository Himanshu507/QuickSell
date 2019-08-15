package com.himanshu.quicksell.Model;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class Add_item_model {
    String title, model, year, cost, description, category;
    List<String> product_images = new ArrayList<>();

    public Add_item_model() {

    }

    public Add_item_model(String title, String model, String year, String cost, String description, String category, List<String> product_images) {
        this.title = title;
        this.model = model;
        this.year = year;
        this.cost = cost;
        this.description = description;
        this.category = category;
        this.product_images = product_images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getProduct_images() {
        return product_images;
    }

    public void setProduct_images(List<String> product_images) {
        this.product_images = product_images;
    }
}
