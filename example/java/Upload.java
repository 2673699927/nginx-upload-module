package com.company;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.*;

/**
 * Created by minggui on 15-9-23.
 */
public class Upload {
    public static void main(String[] args) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost("http://dev.upload.com/upload.php");

            // 代理抓包
            HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");

            // 文件内容
            File mp4File = new File("/tmp/aaa.mp4");
            int length = (int)mp4File.length();
            System.out.println("文件长度:" + length);

            // 读取片段
            FileInputStream in = new FileInputStream(mp4File);
            DataInputStream dis= new DataInputStream(in);
            byte[] itemBuf = new byte[length];
            dis.read(itemBuf, 0, length);

            ByteArrayEntity fileEntity = new ByteArrayEntity(itemBuf, 0 , 2);
            System.out.println("post长度" + String.valueOf(fileEntity.getContentLength()));

            // 中文文件名要转成ISO编码不然会乱码
            String name = "hello中国人.mp4";
            String iso = new String(name.getBytes("utf-8"), "ISO-8859-1");

            httppost.setHeader("Content-Disposition", "attachment; name=\"file1\"; filename=\""+iso+"\"");
            httppost.setHeader("Cookie", "User=loginUser;");
            httppost.setHeader("X-Content-Range", "bytes 0-" + (fileEntity.getContentLength() - 1) + "/" + fileEntity.getContentLength());
            httppost.setHeader("Session-ID", "111");

            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();

            httppost.setEntity(fileEntity);
            httppost.setConfig(config);
            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String s = EntityUtils.toString(resEntity, "utf-8");
                    System.out.println(s);
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
