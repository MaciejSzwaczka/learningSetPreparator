/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.learningsetpreparator;
import pl.learningsetpreparator.entities.*;
import java.util.List;
import java.util.ArrayList;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.client.json.JsonFactory;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport.Builder;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Result.Image;
import com.google.api.services.customsearch.model.Search;
import java.net.URL;
import pl.learningsetpreparator.ImageResizer;


/**
 *
 * @author maciejszwaczka
 */
public class GoogleSearchHelper {
    private static final String key="AIzaSyAmtrmEVVwHMHiRkd1274JaJPdUAP8CRF4";
    private static final String cx="007433787246304610195:znvi3xfzmhk";
    private Customsearch customSearch=null;
    public GoogleSearchHelper()
    {
        NetHttpTransport.Builder httpBuilder=new NetHttpTransport.Builder();
        JsonFactory jsonFactory=new JacksonFactory();
        Customsearch.Builder builder=new Customsearch.Builder(httpBuilder.build(),jsonFactory,null);
        customSearch=builder.setApplicationName("Learning set preparator").build();
        
    }
    public List<URLPhotoResource> getResultsImages(String param)
    {
        Customsearch.Cse.List listOfImages=null;
        List<URLPhotoResource> downloadedImages=new ArrayList<>();
        for(int i=0;20>i;i++)
        {
            try{
                listOfImages=customSearch.cse().list(param);
                listOfImages.setKey(key);
                listOfImages.setSearchType("image");
                listOfImages.setCx(cx);
                listOfImages.setStart(10l*i);
                listOfImages.setNum(10l);
                listOfImages.setQ(param);
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
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        return downloadedImages;        
    }
}
