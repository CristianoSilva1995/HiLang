package utils;

import java.util.Map;

/**
 * Class containing various helper methods used throughout the project code
 * @author Arkadiusz Nowak
 */

public class Helper {
    // Gets the id of the first occurrence of value in a given Map
    public static Integer getKeyIdByValue(Map<Integer, String> data, String value ) {
        Integer id = null;
        // iterate each entry of hashmap
        for(Map.Entry<Integer, String> entry: data.entrySet()) {
            // if given value is equal to value from entry
            // print the corresponding key
            if(entry.getValue().equals(value)) {
                //System.out.println("The key for value " + value + " is " + entry.getKey());
                id = entry.getKey();
                return id;
            }
        }
        return id;
    }
}