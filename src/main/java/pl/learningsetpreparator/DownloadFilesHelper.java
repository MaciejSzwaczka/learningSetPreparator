/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.learningsetpreparator;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.net.ssl.SSLHandshakeException;
import pl.learningsetpreparator.entities.FoodResource;
import pl.learningsetpreparator.entities.URLPhotoResource;
/**
 *
 * @author maciejszwaczka
 */
public class DownloadFilesHelper {
    
    public static final String learningSetFolder="C:\\Users\\maciejszwaczka\\Documents\\NetBeansProjects\\LearningSetPreparator\\Learning set";
    
    public GoogleSearchHelper searcher;
    
    public BingSearchHelper bingHelper;
    
    public DownloadFilesHelper()
    {
        this.searcher=new GoogleSearchHelper();
        this.bingHelper= new BingSearchHelper();
    }
    public void downloadFilesOfFood(List<FoodResource> foodNames) {
       for(FoodResource foodRes:foodNames) {
           int remainingAmountOfPhotos=foodRes.getAmountOfPhotos();
            File folderWithPhotosOfDish=new File(learningSetFolder+"\\"+foodRes.getName());
            folderWithPhotosOfDish.mkdir();
            int portion=0;
            while(remainingAmountOfPhotos>0){
            /*List<URLPhotoResource> addresses = searcher.getResultsImages(foodRes.getName(),portion);*/
            List<URLPhotoResource> addresses = bingHelper.getResultsImages(foodRes);
            addresses.addAll(searcher.getResultsImages(foodRes));
            for(URLPhotoResource urlRes:addresses)
            {
                try {
                    downloadFileFromUrl(urlRes,folderWithPhotosOfDish);
                    remainingAmountOfPhotos--;
                } catch (Exception ex) {
                    System.out.println(urlRes.getUrl());
                    ex.printStackTrace();
                }
            }
            portion++;
            }
       }
    }
    public void downloadFileFromUrl(URLPhotoResource url,File folderWithPhotos) throws Exception
    {
        System.setProperty("http.agent", "");       
        ImageResizer imgHelper=new ImageResizer(800,800);
        File newFile=new File(folderWithPhotos.getAbsolutePath()+"\\"+url.getName());
        String[] parts=url.getName().split("\\.");                
        HttpURLConnection connection = (HttpURLConnection) url.getUrl().openConnection();
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
        BufferedImage image =null;
        image = ImageIO.read(connection.getInputStream());
        if(image!=null){
            image=imgHelper.resizeImage(image);
            newFile.createNewFile();
            ImageIO.write(image, parts[parts.length-1],newFile);
        }          
    }  
}