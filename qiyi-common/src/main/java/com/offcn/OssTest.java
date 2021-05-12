package com.offcn;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author LJT
 * @CreateTime: 2021/5/6 15:13
 * @Description:
 */
public class OssTest {

    public static void main(String[] args) throws FileNotFoundException {
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5tP1WGQA3qseMDBssmAy";
        String accessKeySecret = "wdFO1COgwLXX6VNC4QXoevBFPAAFiH";
        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\LJT\\Pictures\\Saved Pictures\\32_8k.jpg"));
        oss.putObject("qiyi11","pic/001.jpg",inputStream);
        oss.shutdown();
        System.out.println("结束！！！！");
    }
}
