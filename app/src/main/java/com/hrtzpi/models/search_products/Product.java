package com.hrtzpi.models.search_products;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Product implements Serializable {

    @SerializedName("location_in_store_2")
    private String locationInStore2;

    @SerializedName("location_in_store_1")
    private String locationInStore1;

    @SerializedName("quantity_in_store")
    private String quantityInStore;

    @SerializedName("pro_details")
    private List<ProDetails> proDetails;

    @SerializedName("tag_name")
    private String tagName;

    @SerializedName("wishlist")
    private boolean wishlist;

    @SerializedName("serial_number")
    private String serialNumber;

    @SerializedName("video")
    private String video;

    @SerializedName("product_no")
    private String productNo;

    @SerializedName("photos")
    private List<String> photos;

    @SerializedName("subcategory_id")
    private String subcategoryId;

    @SerializedName("last_update_time")
    private String lastUpdateTime;

    @SerializedName("category_id")
    private String categoryId;

    @SerializedName("rate")
    private float rate;

    @SerializedName("price")
    private String price;

    @SerializedName("new_price")
    private String newPrice;

    @SerializedName("basic_unit")
    private String basicUnit;

    @SerializedName("name")
    private String name;

    @SerializedName("logo")
    private String logo;

    @SerializedName("details")
    private String details;

    @SerializedName("id")
    private int id;

    @SerializedName("gender")
    private int gender;

    @SerializedName("agerange")
    private int agerange;

    @SerializedName("weight")
    private String weight;
    @SerializedName("limited_in_store")
    private String limitedInStore;
    @SerializedName("measure")
    private List<Measure> measures;

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public List<ProDetails> getProDetails() {
        return proDetails;
    }

    public void setProDetails(List<ProDetails> proDetails) {
        this.proDetails = proDetails;
    }

    public void setLocationInStore2(String locationInStore2) {
        this.locationInStore2 = locationInStore2;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    public String getLocationInStore2() {
        return locationInStore2;
    }

    public void setLocationInStore1(String locationInStore1) {
        this.locationInStore1 = locationInStore1;
    }

    public String getLocationInStore1() {
        return locationInStore1;
    }

    public void setQuantityInStore(String quantityInStore) {
        this.quantityInStore = quantityInStore;
    }

    public Object getQuantityInStore() {
        return quantityInStore;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Object getTagName() {
        return tagName;
    }

    public void setWishlist(boolean wishlist) {
        this.wishlist = wishlist;
    }

    public boolean isWishlist() {
        return wishlist;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVideo() {
        return video;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setBasicUnit(String basicUnit) {
        this.basicUnit = basicUnit;
    }

    public String getBasicUnit() {
        return basicUnit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAgerange() {
        return agerange;
    }

    public void setAgerange(int agerange) {
        this.agerange = agerange;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLimitedInStore() {
        return limitedInStore;
    }

    public void setLimitedInStore(String limitedInStore) {
        this.limitedInStore = limitedInStore;
    }
}