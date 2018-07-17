/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.learningsetpreparator;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pl.learningsetpreparator.entities.FoodResource;
import pl.learningsetpreparator.entities.URLPhotoResource;
import pl.learningsetpreparator.enumparameters.Freshness;
import pl.learningsetpreparator.enumparameters.Size;

/**
 *
 * @author maciejszwaczka
 */
public class BingSearchHelper {
    public BingSearchHelper()
    {
        
    }
    public List<URLPhotoResource> getResultsImages(FoodResource res)
    {
        HttpClient httpclient = HttpClients.createDefault();
        Set<String> foodPhotosAddresses=new HashSet<>();
        try
        {
            /*for(Freshness freshness:Freshness.values())
            {
                for(Size size:Size.values())
                {*/
                    for(int i=0;400>i;i++)
                    {
                        URIBuilder builder = new URIBuilder("https://api.cognitive.microsoft.com/bing/v7.0/images/search");

                        builder.setParameter("q", res.getName());
                        builder.setParameter("count", "150");
                        /*builder.setParameter("size", size.toString());
                        builder.setParameter("freshness",freshness.toString());*/
                        builder.setParameter("offset", Integer.toString((150)*i));
                        builder.setParameter("mkt", "en-us");
                        builder.setParameter("safeSearch", "Moderate");
                        URI uri = builder.build();
                        HttpGet request = new HttpGet(uri);
                        request.setHeader("Ocp-Apim-Subscription-Key", "5ce7dd43b63c43d48fbeca48ba1fd3de");

                        HttpResponse response = httpclient.execute(request);
                        HttpEntity entity = response.getEntity();

                        if (entity != null) 
                        {
                            Set newSet=parseJson(EntityUtils.toString(entity));
                            foodPhotosAddresses.addAll(newSet);
                            System.out.println(foodPhotosAddresses.size());
                        }
                    }
                    System.out.println(foodPhotosAddresses.size());
                    Thread.sleep(1000);
                    /*for(int i=0;6>i;i++)
                    {
                        URIBuilder builder = new URIBuilder("https://api.cognitive.microsoft.com/bing/v7.0/images/search");

                        builder.setParameter("q", "dumplings");
                        builder.setParameter("count", "150");
                        builder.setParameter("offset", Integer.toString((150)*i));
                        builder.setParameter("mkt", "en-us");
                        builder.setParameter("freshness",freshness.toString());
                        builder.setParameter("safeSearch", "Moderate");
                        URI uri = builder.build();
                        HttpGet request = new HttpGet(uri);
                        request.setHeader("Ocp-Apim-Subscription-Key", "570ecf20f16a44b8892a20ad1999fbc9");

                        HttpResponse response = httpclient.execute(request);
                        HttpEntity entity = response.getEntity();

                        if (entity != null) 
                        {
                            Set newSet=parseJson(EntityUtils.toString(entity));
                            foodPhotosAddresses.addAll(newSet);
                            System.out.println(foodPhotosAddresses.size());
                        }
                    }*/
                /*}
            }*/
            System.out.println(foodPhotosAddresses.size());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println(foodPhotosAddresses.size());
        List<URLPhotoResource> photosAddresses=new ArrayList<>();
        for(String addr:foodPhotosAddresses)
        {
            String parts[]=addr.split("/");
            String name=parts[parts.length-1];
            parts=name.split("\\?");
            name=parts[0];
            try {
                photosAddresses.add(new URLPhotoResource(new URL(addr),name));
            } catch (MalformedURLException ex) {
                Logger.getLogger(BingSearchHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return photosAddresses;
    }
    private Set<String> parseJson(String requestStr)
    {
        int x = requestStr.indexOf("{");
        Set<String> newSet=new HashSet<>();
        requestStr = requestStr.substring(x);
        try { 
            JSONObject json = new JSONObject(requestStr.trim());
            JSONObject request=new JSONObject(requestStr);
            JSONArray rateArray=request.getJSONArray("value");
            System.out.println(rateArray.length());
            for(int i=0;rateArray.length()>i;i++)
            {
                JSONObject obj=rateArray.getJSONObject(i);
                newSet.add(obj.getString("contentUrl"));
            }
        } catch (JSONException ex) {
            Logger.getLogger(BingSearchHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newSet;
    }
}
