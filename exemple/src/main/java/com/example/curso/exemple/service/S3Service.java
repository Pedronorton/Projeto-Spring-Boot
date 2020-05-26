package com.example.curso.exemple.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    private static final Logger LOG = LoggerFactory.getLogger(S3Service.class);


    public URI uploadFile(MultipartFile multipartFile) throws IOException, URISyntaxException { //irei receber um arquivo desse tipo na requisição
        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream is = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uploadFile(is, fileName, contentType);
        }
        catch (IOException e){
            throw new RuntimeException("Erro de IO "+e.getMessage());
        }
    }

    public URI uploadFile(InputStream is, String fileName, String contentType) throws IOException, URISyntaxException { //irei receber um arquivo desse tipo na requisição
       try {
           ObjectMetadata meta = new ObjectMetadata();
           meta.setContentType(contentType);
           LOG.info("Iniciano o upload");
           s3client.putObject(bucketName, fileName, is, meta); // joga pro s3
           LOG.info("Upload finalizado");

           return s3client.getUrl(bucketName, fileName).toURI();
       }
       catch (URISyntaxException e){
            throw new RuntimeException("Erro ao converter URI em URL");
       }
    }

}
