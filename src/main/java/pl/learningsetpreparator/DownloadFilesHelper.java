/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.learningsetpreparator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import javax.imageio.ImageIO;
import pl.learningsetpreparator.entities.URLPhotoResource;
/**
 *
 * @author maciejszwaczka
 */
public class DownloadFilesHelper {
    public static final String addrOfFolder="C:\\Users\\maciejszwaczka\\Desktop\\Learning set";
    
    public static void downloadFilesFromUrl(List<URLPhotoResource> addresses)
    {
        System.setProperty("http.agent", "");       
        ImageResizer imgHelper=new ImageResizer(800,800);
        for(URLPhotoResource url:addresses)
        {
            BufferedImage image =null;

            try{
                File newFile=new File(addrOfFolder+"\\"+url.getName());
                String[] parts=url.getName().split("\\.");                
                newFile.createNewFile();
                HttpURLConnection connection = (HttpURLConnection) url.getUrl().openConnection();
                connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
                image = ImageIO.read(connection.getInputStream());
                image=imgHelper.resizeImage(image);
                ImageIO.write(image, parts[parts.length-1],newFile);       
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            
        }
    }
}