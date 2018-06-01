package pl.learningsetpreparator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maciejszwaczka on 01.06.2018.
 */
public class FoodNamesFileReader {
    public static File fileWithNames=new File(System.getProperty("user.dir")+"\\src\\main\\java\\pl\\learningsetpreparator\\foodNames.txt");
    public List<String> readFoodNames()
    {
        List<String> foodNames=new ArrayList<>();
        try(BufferedReader buffReader=new BufferedReader(new FileReader(fileWithNames)))
        {
            String foodName;
            while((foodName=buffReader.readLine())!=null)
            {
                foodNames.add(foodName);
                System.out.println(foodName);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return foodNames;
    }
}
