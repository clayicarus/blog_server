package io.blog.service.impl;

import com.obs.services.ObsClient;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.TemporarySignatureResponse;
import io.blog.ResponseResult;
import io.blog.exception.BlogException;
import io.blog.service.UploadService;
import io.blog.utils.PathUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.blog.enums.AppHttpCodeEnum;

@Service
public class UploadServiceImpl implements UploadService {

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //判断文件类型
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        //对原始文件名进行判断
        if(!originalFilename.endsWith(".png")){
            throw new BlogException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        String filePath = PathUtils.generateFilePath(originalFilename);
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        try {
            obsClient.putObject(bucketName, filePath, img.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.okResult(getUrl(filePath));
    }
    private String getUrl(String key)
    {
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        // URL有效期，3600秒
        long expireSeconds = 3600L;
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
        request.setBucketName(bucketName);
        request.setObjectKey(key);

        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);

        System.out.println("Getting object using temporary signature url:");
        System.out.println("\t" + response.getSignedUrl());
        return response.getSignedUrl();
    }
    @Value("${huaweicloud-obs.end-point}")
    private String endPoint;
    @Value("${huaweicloud-obs.access-key}")
    private String ak;
    @Value("${huaweicloud-obs.secret-key}")
    private String sk;
    @Value("${huaweicloud-obs.bucket-name}")
    private String bucketName;

}
