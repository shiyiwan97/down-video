package com.shiyiwan.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class CmdUtil {

    public static String translateMp4ToMp3(String ffmpegPath,String mp4Uri) {
        //-i 指定输入文件名，-q:a 0 设置音频质量为最高，-map a 指定只转换音频部分。
        String cmd = ffmpegPath + " -i \"" +  mp4Uri + "\" -q:a 0 -map a \"" + mp4Uri.substring(0, mp4Uri.lastIndexOf(".")) + ".mp3\"";
        return executeCMDCommand(cmd);
    }

    public static String executeLocalCmd(String cmd, File workpath) {
        try {
            String[] cmdA = {"cmd.exe", "/c", cmd};
            Process process = null;
            if (workpath == null) {
                process = Runtime.getRuntime().exec(cmdA);
            } else {
                process = Runtime.getRuntime().exec(cmdA, null, workpath);
            }
//             LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()));
            LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream(), "GBK"));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String executeCMDCommand(String cmdCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while((line=bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line+"\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
