/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.learningsetpreparator;
import pl.learningsetpreparator.*;
import java.net.URL;
import java.util.List;
import pl.learningsetpreparator.entities.*;
/**
 *
 * @author maciejszwaczka
 */
public class Main {
    public static void main(String[] args)
    {
        GoogleSearchHelper helper=new GoogleSearchHelper();
        List<URLPhotoResource> addresses=helper.getResultsImages("food");
        DownloadFilesHelper.downloadFilesFromUrl(addresses);
    }
}
