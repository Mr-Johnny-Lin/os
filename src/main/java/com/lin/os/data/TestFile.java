package com.lin.os.data;

import org.springframework.web.multipart.MultipartFile;

public class TestFile {
    private MultipartFile data;
    private String type;

    public String getType() {
        return type;
    }

    public void setText(MultipartFile text) {
        this.data = text;
        type=".txt";
    }

    public MultipartFile getData() {
        return data;
    }
}
