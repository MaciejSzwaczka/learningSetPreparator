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
import javax.imageio.ImageIO;
import pl.learningsetpreparator.entities.URLPhotoResource;
/**
 *
 * @author maciejszwaczka
 */
public class DownloadFilesHelper {
    
    public static void downloadFilesFromUrl(List<URLPhotoResource> addresses,String name)
    {
        System.setProperty("http.agent", "");       
        ImageResizer imgHelper=new ImageResizer(800,800);
        File folderWithPhotos=new File(System.getProperty("user.dir")+"\\Learning set\\"+name);
        folderWithPhotos.mkdir();
        System.out.println(folderWithPhotos.getAbsolutePath());
        for(URLPhotoResource url:addresses)
        {
            try{
                File newFile=new File(folderWithPhotos.getAbsolutePath()+"\\"+url.getName());
                String[] parts=url.getName().split("\\.");                
                HttpURLConnection connection = (HttpURLConnection) url.getUrl().openConnection();
                connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
                BufferedImage image =null;
                try{
                    image = ImageIO.read(connection.getInputStream());
                    image=imgHelper.resizeImage(image);
                    newFile.createNewFile();
                    ImageIO.write(image, parts[parts.length-1],newFile);
                    }
                    catch(NullPointerException e)
                    {
                        System.out.println(url.getUrl());
                        e.printStackTrace();
                    }
                    catch(IIOException e)
                    {
                        System.out.println(url.getUrl());
                        e.printStackTrace();
                    }    
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            
        }
    }
}