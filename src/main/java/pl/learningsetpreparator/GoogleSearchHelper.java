/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.learningsetpreparator;
import com.google.api.client.http.HttpRequest;
import pl.learningsetpreparator.entities.*;
import java.util.List;
import java.util.ArrayList;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport.Builder;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Result.Image;
import com.google.api.services.customsearch.model.Search;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import pl.learningsetpreparator.ImageResizer;


/**
 *
 * @author maciejszwaczka
 */
public class GoogleSearchHelper {
    private static final String key="AIzaSyAd5baKt88AghF84A_7LEpDZxWip1adkDw";
    private static final String cx="007433787246304610195:znvi3xfzmhk";
    private Customsearch customSearch=null;
    public GoogleSearchHelper()
    {
        NetHttpTransport.Builder httpBuilder=new NetHttpTransport.Builder();
        GsonFactory fact=new GsonFactory();
        Customsearch.Builder builder=new Customsearch.Builder(httpBuilder.build(),fact,new HttpRequestInitializer() {
            public void initialize(HttpRequest httpRequest) {
                try {
                    // set connect and read timeouts
                    httpRequest.setConnectTimeout(1800000);
                    httpRequest.setReadTimeout(1800000);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        customSearch=builder.setApplicationName("My Project").build();
    }
    public List<URLPhotoResource> getResultsImages(FoodResource resource)
    {
        Customsearch.Cse.List listOfImages=null;
        List<URLPhotoResource> downloadedImages=new ArrayList<>();
        for(int i=0;10>i;i++)
        {
            try{
                listOfImages=customSearch.cse().list("image");
                listOfImages.setKey(key);
                listOfImages.setSearchType("image");
                listOfImages.setCx(cx);
                listOfImages.setStart(10l*i);
                listOfImages.setNum(10l);
                listOfImages.setQ(resource.getName());
                Search results= listOfImages.execute();
                for(Result res:results.getItems())
                {
                    String parts[]=res.getLink().split("/");
                    String name=parts[parts.length-1];
                    parts=name.split("\\?");
                    name=parts[0];
                    if(name.contains("."))
                    {
                        downloadedImages.add(new URLPhotoResource(new URL(res.getLink()),name));
                    }
                }
                Thread.sleep(1000);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return downloadedImages;        
    }
}
