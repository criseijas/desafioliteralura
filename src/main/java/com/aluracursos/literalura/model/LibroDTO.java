package com.aluracursos.literalura.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LibroDTO {

    private String title;
    private List<String> languages;
    private int download_count;
    private List<Map<String, String>> authors;

    public String getTitle() {
        return title;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public int getDownload_count() {
        return download_count;
    }

    public List<Map<String, String>> getAuthors() {
        return authors;
    }

    public String getAuthorName() {
        if (authors != null && !authors.isEmpty()) {
            return authors.get(0).getOrDefault("name", "Autor desconocido");
        }
        return "Autor desconocido";
    }

    public String getLanguage() {
        return (languages != null && !languages.isEmpty()) ? languages.get(0) : "Desconocido";
    }

}
