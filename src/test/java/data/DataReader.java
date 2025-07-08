package data;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class DataReader {

    public static List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {

        //JSON TO STRING //commons.io dependency
        String jsonContent= FileUtils.readFileToString(new File(filePath));

        //json string to hash map //jackson databind dependency
        ObjectMapper mapper=new ObjectMapper();
        //the list contains 2 hash maps means 2 data sets
        List<HashMap<String,String>> data=mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
        return data;
    }
}
