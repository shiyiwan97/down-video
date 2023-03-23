package com.shiyiwan.vedio_download;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        //Headless模式是系统的一种配置模式。在系统可能缺少显示设备、键盘或鼠标这些外设的情况下可以使用该模式。
        new SpringApplicationBuilder(Application.class).headless(false).run();
    }

}