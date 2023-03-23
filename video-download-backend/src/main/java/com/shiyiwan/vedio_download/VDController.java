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
    VDService VDService;

    @GetMapping("/getResource")
    public String getResource(@RequestParam("id") String id) {
        return "ok";
    }

    @GetMapping("/getVideo")
    public String getVideo(@RequestParam("url") String url, @RequestParam("seconds") int seconds) {
        try {
            VDService.getVideo(url, seconds);
            return "ok";
        } catch (AWTException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @GetMapping("/listFile")
    public List<DownloadFile> listFile() {
        return VDService.listFile();
    }

    @GetMapping("/getFile")
    public void getFile(HttpServletResponse httpServletResponse, @RequestParam("fileName") String fileName) {
        VDService.getFile(httpServletResponse, fileName);
    }
}
