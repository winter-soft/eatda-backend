package com.proceed.swhackathon.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.proceed.swhackathon.exception.s3.EmptyFileException;
import com.proceed.swhackathon.exception.s3.FileUploadFailedException;
import com.proceed.swhackathon.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class AwsS3Service {
    private final AmazonS3Client amazonS3Client;
    private static final String S3_ADDRESS = "https://s3.ap-northeast-2.amazonaws.com/ghdcksgml.s3.bucket/";

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String upload(MultipartFile multipartFile) {
        validateFileExists(multipartFile);

        String fileName = CommonUtils.buildFileName(multipartFile.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new FileUploadFailedException();
        }

        return S3_ADDRESS + fileName;
    }

    private void validateFileExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new EmptyFileException();
        }
    }
}
