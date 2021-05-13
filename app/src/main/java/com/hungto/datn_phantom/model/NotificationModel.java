package com.hungto.datn_phantom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationModel {
    private String image, body;
    private boolean readed;

    public NotificationModel(String image, String body, boolean readed) {
        this.image = image;
        this.body = body;
        this.readed = readed;
    }
}
