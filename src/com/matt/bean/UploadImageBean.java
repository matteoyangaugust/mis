package com.matt.bean;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UploadImageBean {
    private Integer course_sn;
    private String deletedImageSn;
    private List<MultipartFile> image;

    public String getDeletedImageSn() {
        return deletedImageSn;
    }

    public void setDeletedImageSn(String deletedImageSn) {
        this.deletedImageSn = deletedImageSn;
    }

    public Integer getCourse_sn() {
        return course_sn;
    }

    public void setCourse_sn(Integer course_sn) {
        this.course_sn = course_sn;
    }

    public List<MultipartFile> getImage() {
        return image;
    }

    public void setImage(List<MultipartFile> image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "UploadImageBean{" +
                "course_sn=" + course_sn +
                ", image=" + image +
                '}';
    }
}
