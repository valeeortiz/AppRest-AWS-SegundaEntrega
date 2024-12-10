package com.example.REST.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.example.REST.models.SesionesAlumnos;

@Service
public class SesionesService {
    
    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey}")
    private String secretAccessKeyId;

    @Value("${aws.sessionToken}")
    private String sessionToken;

    @Bean
    public AmazonDynamoDB clientDynamo(){
        AWSCredentials credenciales = new BasicSessionCredentials(accessKeyId, secretAccessKeyId, sessionToken);
        
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credenciales))
                .withRegion("us-east-1")
                .build();
    }

    public boolean loginSession(SesionesAlumnos sesiones) {
        AmazonDynamoDB dynamoDbClient = clientDynamo();
        try{
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDbClient);
        mapper.save(sesiones);
        return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean verifySession(String sessionString){
        SesionesAlumnos sesionEncontrada = getSessionString(sessionString);

        if (sesionEncontrada != null && sesionEncontrada.getActive()==true) {
            return true;     
        }
        return false;
    }

    public boolean logoutSession(String sessionString){
        SesionesAlumnos sesionEncontrada = getSessionString(sessionString);

        if (sesionEncontrada != null && sesionEncontrada.getActive()==true) {
            sesionEncontrada.setActive(false);
            AmazonDynamoDB dynamoDbClient = clientDynamo();
            DynamoDBMapper mapper = new DynamoDBMapper(dynamoDbClient);
            mapper.save(sesionEncontrada);
            return true;     
        }
        return false;
    }

    public SesionesAlumnos getSessionString(String sessionString){
        AmazonDynamoDB dynamoDbClient = clientDynamo();
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDbClient);
        DynamoDBScanExpression objeto = new DynamoDBScanExpression();
        objeto.addFilterCondition("sessionString", new Condition()
        .withComparisonOperator("EQ")
        .withAttributeValueList(new AttributeValue().withS(sessionString)));
        List<SesionesAlumnos> lista = mapper.scan(SesionesAlumnos.class, objeto);
        if(lista.size() == 0){
            return null;
        }else{
            return lista.get(0);
        }
    }

    
}
