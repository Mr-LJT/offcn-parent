package com.offcn.project.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author LJT
 * @CreateTime: 2021/5/6 15:38
 * @Description:
 */
@Data
public class OssTemplate {

    private String endpoint;

    private String bucketDomain;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    /**
     * 返回上传后的文件访问路径
     * @param inputStream
     * @param filename
     * @return
     */
    public String upload(InputStream inputStream,String filename){
        //1.加工文件名称
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String folderName = format.format(new Date());
        String last = filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID().toString().replace("-", "") + last;
        //2.创建一个oss实例
        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //3.上传文件流，需要制定bucket名称
        oss.putObject(bucketName,"pic/"+folderName+"/"+filename,inputStream);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        oss.shutdown();
        String url = "https://" + bucketDomain + "/pic/" + folderName + "/" + filename;
        return url;
    }

}
