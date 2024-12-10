package com.example.REST.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class FotoPerfilService {

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey}")
    private String secretAccessKeyId;

    @Value("${aws.sessionToken}")
    private String sessionToken;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Bean
    public AmazonS3 clienteS3(){
        AWSCredentials credenciales = new BasicSessionCredentials(accessKeyId, secretAccessKeyId, sessionToken);

        return AmazonS3ClientBuilder.standard()
        .withRegion("us-east-1")
        .withCredentials(new AWSStaticCredentialsProvider(credenciales))
        .build();

    } 

    public String fotoPerfilURL(MultipartFile multipartFile, String nombreArchivo){
        AmazonS3 cliente = clienteS3();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        try {
            PutObjectRequest objeto = new PutObjectRequest(bucketName, nombreArchivo, multipartFile.getInputStream(), objectMetadata);
            cliente.putObject(objeto);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return cliente.getUrl(bucketName, nombreArchivo).toString();
    }


}
