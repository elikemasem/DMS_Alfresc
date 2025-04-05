package org.corbantech.DMS_With_Alfresco.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> fromJsonToList(Map<String, T> responseBody, Class<T> clazz) {
        List<T> mainList = new ArrayList<>();

        if (responseBody == null) return mainList;

        Map<String, T> list = (Map<String, T>) responseBody.get("list");
        if (list == null) return mainList;

        List<Map<String, T>> entries = (List<Map<String, T>>) list.get("entries");
        if (entries == null) return mainList;

        for (Map<String, T> entry : entries) {
            Map<String, T> entryMap = (Map<String, T>) entry.get("entry");
            if (entryMap != null) {
                T object = objectMapper.convertValue(entryMap, clazz);
                mainList.add(object);
            }
        }
        return mainList;
    }

    public static <T> T fromJsonToObject(Map<String, Object> responseBody, Class<T> clazz) {
        if (responseBody == null) return null;
        T object = objectMapper.convertValue(responseBody.get("entry"), clazz);
        return object;
    }

    public static <T> T getErrorMessage(Map<String, Object> responseBody, Class<T> clazz) {
        if (responseBody == null) return null;
        T object = objectMapper.convertValue(responseBody.get("error"), clazz);
        return object;
    }
}
