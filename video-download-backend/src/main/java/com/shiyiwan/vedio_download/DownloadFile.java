package com.shiyiwan.vedio_download;

import lombok.Data;

@Data
public class DownloadFile {

    private String fileName;

    public DownloadFile(String fileName) {
        this.fileName = fileName;
    }
}
