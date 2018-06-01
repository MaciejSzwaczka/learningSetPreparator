/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.learningsetpreparator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import pl.learningsetpreparator.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import pl.learningsetpreparator.entities.*;
/**
 *
 * @author maciejszwaczka
 */
public class Main {
    public static void main(String[] args)  {
            FoodNamesFileReader reader=new FoodNamesFileReader();
            List<String> foodNames=reader.readFoodNames();
            for(String foodName:foodNames) {
                GoogleSearchHelper helper = new GoogleSearchHelper();
                List<URLPhotoResource> addresses = helper.getResultsImages(foodName);
                DownloadFilesHelper.downloadFilesFromUrl(addresses,foodName);
                int ind=foodNames.indexOf(foodName);
                if(ind!=0 && ind%5==0)
                {
                    try {
                        System.out.println("indeks");
                        Thread.sleep(1000*61*61*24);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
    }
}
