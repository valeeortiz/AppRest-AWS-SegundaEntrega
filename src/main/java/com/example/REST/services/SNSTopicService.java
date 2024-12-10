package com.example.REST.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
public class SNSTopicService{
    
    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey}")
    private String secretAccessKeyId;

    @Value("${aws.sessionToken}")
    private String sessionToken;

    @Value("${aws.topic}")
    private String topic;

    @Bean
    public SnsClient snsClient(){
        AwsSessionCredentials credenciales = AwsSessionCredentials.create(accessKeyId, secretAccessKeyId, sessionToken);
        return SnsClient.builder()
        .credentialsProvider(StaticCredentialsProvider.create(credenciales))
        .region(Region.US_EAST_1)
        .build();

    }

    public boolean mandarCorreo(String mensaje, String titulo){
        
        SnsClient snsClient = snsClient();
        try {
            PublishRequest request = PublishRequest.builder()
            .message(mensaje)
            .subject(titulo)
            .topicArn(topic)
            .build();

            PublishResponse respuesta = snsClient.publish(request);
            return respuesta.sdkHttpResponse().isSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    } 

}
