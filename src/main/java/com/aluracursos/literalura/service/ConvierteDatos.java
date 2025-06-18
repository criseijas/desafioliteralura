package com.aluracursos.literalura.service;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir JSON", e);
        }
    }

}
