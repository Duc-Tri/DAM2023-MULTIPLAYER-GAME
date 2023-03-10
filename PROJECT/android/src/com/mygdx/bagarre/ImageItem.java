package com.mygdx.bagarre;

public class ImageItem {
    private int imageId;
    private String imageName;

    public ImageItem(int imageId, String imageName) {
        this.imageId = imageId;
        this.imageName = imageName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
