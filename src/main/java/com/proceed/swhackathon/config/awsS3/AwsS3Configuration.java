package com.proceed.swhackathon.config.awsS3;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class AwsS3Configuration {
    @Autowired
    private Environment env;

    @Bean
    public AmazonS3 amazonS3Client() {
        Regions clientRegions = Regions.valueOf(env.getRequiredProperty("s3.tree.bucket.regions"));

        return AmazonS3ClientBuilder.standard()
                .withRegion(clientRegions)
                .enablePathStyleAccess()  // 빌드 된 클라이언트에 대한 path-style 액세스 사용
                .build();
    }
}
