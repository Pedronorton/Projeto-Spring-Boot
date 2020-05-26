package com.example.curso.exemple.service;


import com.example.curso.exemple.service.exception.FileException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

//Transforma um arquivo em jpg
@Service
public class ImageService {


    public BufferedImage getJpgImageFromFile(MultipartFile uploadFile){
        String extention = FilenameUtils.getExtension(uploadFile.getOriginalFilename());

        if(!"png".equals(extention) && !"jpg".equals(extention)){
            throw  new RuntimeException("Somente imagens PNG ou JPG são permitidas ");
        }

        try{
            BufferedImage img = ImageIO.read(uploadFile.getInputStream());
            if("png".equals(extention)){
                img = pngToJpg(img);
            }
            return img;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo");
        }
    }

    //Codigo ultra específico para a conversao
    private BufferedImage pngToJpg(BufferedImage img) {

        BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

        jpgImage.createGraphics().drawImage(img,0,0, Color.WHITE, null);
        return jpgImage;
    }

    public InputStream getInputStream(BufferedImage img, String extension){
        try{
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, extension, os);
            return new ByteArrayInputStream(os.toByteArray());
        }
        catch (IOException e){
            throw new RuntimeException("Erro ao ler o arquivo");
        }
    }


}
