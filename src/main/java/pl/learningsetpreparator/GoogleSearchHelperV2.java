/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.learningsetpreparator;
import it.unipi.di.acube.searchapi.WebsearchApi;
import it.unipi.di.acube.searchapi.callers.GoogleSearchApiCaller;
import it.unipi.di.acube.searchapi.model.WebsearchResponse;
import java.net.URI;
/**
 *
 * @author maciejszwaczka
 */
public class GoogleSearchHelperV2 {
    
    private static final String key="AIzaSyAd5baKt88AghF84A_7LEpDZxWip1adkDw";
    
    private static final String cx="007433787246304610195:znvi3xfzmhk";
    
    private WebsearchApi api;
    
    public GoogleSearchHelperV2()
    {
        GoogleSearchApiCaller caller = new GoogleSearchApiCaller(cx, key);
        api = new WebsearchApi(caller);
    }
    
    public WebsearchResponse getResponse(String foodName,int numOfResults) throws Exception{
        WebsearchResponse response = api.query(foodName, numOfResults);
        for(URI addr:response.getCalledURIs())
        {
            System.out.println(addr);
        }
        return response;
    }

}
