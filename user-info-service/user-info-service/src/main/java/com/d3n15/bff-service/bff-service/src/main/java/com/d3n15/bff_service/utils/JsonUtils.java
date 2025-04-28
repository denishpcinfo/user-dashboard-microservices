package com.d3n15.bff_service.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> String toJson(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar objeto para JSON", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao desserializar JSON", e);
        }
    }

    public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, new TypeReference<List<T>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Erro ao desserializar lista JSON", e);
        }
    }
}