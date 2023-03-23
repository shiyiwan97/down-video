package com.shiyiwan.vedio_download;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Component
public class VDService {

    @Value("${video-download.folder}")
    private String VIDEO_DOWNLOAD_FOLDER;

    public void getVideo(String url, int seconds) throws AWTException {

        ChromeOptions chromeOptions = new ChromeOptions();
        //指定驱动
        System.setProperty("webdriver.chrome.driver", "./src/main/lib/chromedriver.exe");
        //允许跨域请求
        chromeOptions.addArguments("--remote-allow-origins=*");
        //添加插件
        chromeOptions.addExtensions(new File("./src/main/lib/cococut.crx"));
        //设置下载地址
        HashMap<String, Object> prefsMap = new HashMap();
        prefsMap.put("download.default_directory", VIDEO_DOWNLOAD_FOLDER);
        chromeOptions.setExperimentalOption("prefs", prefsMap);

        WebDriver chromeDriver = new ChromeDriver(chromeOptions);

        chromeDriver.get(url);
//        List<String> windowHandleList = new ArrayList(chromeDriver.getWindowHandles());
//        String lastPageHandle = windowHandleList.stream().filter(handle -> !handle.equals(chromeDriver.getWindowHandle())).collect(Collectors.toList()).get(0);
        chromeDriver.switchTo().window(chromeDriver.getWindowHandle());

        Robot robot = new Robot();
        moveMouseAndClickLeft(robot, 825, 70);
        moveMouseAndClickLeft(robot, 650, 200);
        moveMouseAndClickLeft(robot, 520, 220);
        moveMouseAndClickLeft(robot, 550, 460);
        moveMouseAndClickLeft(robot, 670, 390);
        moveMouse(robot, 520, 900);
        moveMouseAndClickLeft(robot, 520, 670);

        try {
            Thread.sleep((seconds + 10) * 1000L / 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        moveMouseAndClickLeft(robot, 350, 40);
        moveMouseAndClickLeft(robot, 640, 620);

        chromeDriver.quit();
    }

    /**
     * 鼠标移动到x,y处并间隔2秒
     *
     * @param robot robot
     * @param x     坐标x
     * @param y     坐标y
     */
    private static void moveMouse(Robot robot, int x, int y) {
        robot.mouseMove(x, y);
        robot.delay(2000);
    }

    /**
     * 在x,y处按下鼠标左键并间隔2秒
     *
     * @param robot robot
     * @param x     坐标x
     * @param y     坐标y
     */
    private static void moveMouseAndClickLeft(Robot robot, int x, int y) {
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(2000);
    }

    public List<DownloadFile> listFile() {
        File folder = new File(VIDEO_DOWNLOAD_FOLDER);
        File[] files = folder.listFiles();
        List<DownloadFile> fileNameList = new ArrayList<>();
        for (File file : files) {
            fileNameList.add(new DownloadFile(file.getName()));
        }
        return fileNameList;
    }

    public void getFile(HttpServletResponse httpServletResponse, String fileName) {
        //UTF-8解码
        try {
            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //这里写要让前端下载的文件的路径
//        Path filePath = Paths.get(VIDEO_DOWNLOAD_FOLDER,fileName);
        File file = new File(VIDEO_DOWNLOAD_FOLDER + '\\' + fileName);
        //设置编码格式，防止下载的文件内乱码
        httpServletResponse.setCharacterEncoding("UTF-8");
        //获取路径文件对象
        String realFileName = file.getName();
        //设置响应头类型，这里可以根据文件类型设置，text/plain、application/vnd.ms-excel等
        httpServletResponse.setHeader("content-type", "application/octet-stream;charset=UTF-8");
        httpServletResponse.setContentType("application/octet-stream;charset=UTF-8");
        //如果不设置响应头大小，可能报错：“Excel 已完成文件级验证和修复。此工作簿的某些部分可能已被修复或丢弃”
        httpServletResponse.addHeader("Content-Length", String.valueOf(file.length()));
        try {
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(realFileName.trim(), StandardCharsets.UTF_8.toString()));
//            httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("UTF-8")));
        } catch (
                UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        //初始化文件流字节缓存
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        try {
            //开始写入
            OutputStream os = httpServletResponse.getOutputStream();
            //写入完成，创建文件流
            bis = new BufferedInputStream(new FileInputStream(file));
            // bis.read(data)：将字符读入数组。在某个输入可用、发生I/O错误或者已到达流的末尾前，此方法一直阻塞。
            // 读取的字符数，如果已到达流的末尾，则返回 -1
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws AWTException {
        new VDService().getVideo("https://m.toutiao.com/video/7037391373284147719/", 1000);
    }

}
