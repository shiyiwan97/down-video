package com.shiyiwan.vedio_download;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.List;

@RestController
@CrossOrigin
public class VDController {

    @Autowired
    VDService vDService;

    @Autowired
    VDMapper vdMapper;

    @GetMapping("/getResource")
    public String getResource(@RequestParam("id") String id) {
        vDService.translateMp4ToMp3("input.mp4");
        return "ok";
    }

    @GetMapping("/getVideo")
    public String getVideo(@RequestParam("url") String url, @RequestParam("seconds") int seconds) {
        return vDService.getVideo(url, seconds);
    }

    @GetMapping("/listFile")
    public List<DownloadFile> listFile() {
        return vDService.listFile();
    }

    @GetMapping("/getFile")
    public void getFile(HttpServletResponse httpServletResponse, @RequestParam("fileName") String fileName) {
        vDService.getFile(httpServletResponse, fileName);
    }

    @GetMapping("/sql")
    public void sql() {
        vdMapper.getDownloadState();
    }

}
