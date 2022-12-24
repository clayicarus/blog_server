package io.blog;

import com.obs.services.ObsClient;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.TemporarySignatureResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootTest(classes = ObsTest.class)
public class ObsTest {
    @Test
    public void test()
    {
        System.out.println(sk);
        System.out.println(ak);
        System.out.println(endPoint);
        System.out.println(bucketName);
    }
    @Test
    public void uploadTest()
    {
        // Endpoint以北京四为例，其他地区请按实际情况填写。
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        try {
            InputStream is = new FileInputStream("/home/clay/Desktop/89796924_p0.png");
            obsClient.putObject(bucketName, "objectname", is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void downloadTest()
    {
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
// URL有效期，3600秒
        long expireSeconds = 3600L;
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
        request.setBucketName(bucketName);
        request.setObjectKey("objectname");

        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);

        System.out.println("Getting object using temporary signature url:");
        System.out.println("\t" + response.getSignedUrl());
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
