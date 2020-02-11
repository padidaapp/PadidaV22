package com.example.padidav2;

public class Upload {
    private String bookname, imgUrl;


    public Upload(String imgName, String imgUrl) {
        this.bookname = imgName;
        this.imgUrl = imgUrl;
    }

    public String getImgName() {
        return bookname;
    }

    public void setImgName(String imgName) {
        this.bookname = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

