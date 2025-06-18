package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.model.LibroDTO;
import com.aluracursos.literalura.model.ResultadoBusqueda;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;

import java.util.List;

public class LibroService {

    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void buscarYGuardarLibro(String titulo) {
        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "+");
        String json = consumoAPI.obtenerDatos(url);

        ResultadoBusqueda resultado = conversor.obtenerDatos(json, ResultadoBusqueda.class);

        if (resultado.getResults().isEmpty()) {
            System.out.println("El libro no fue encontrado.");
            return;
        }

        LibroDTO libroDTO = resultado.getResults().get(0);

        if (libroRepository.existsByTitulo(libroDTO.getTitle())) {
            System.out.println("El libro ya está registrado en la base de datos.");
            return;
        }

        // Buscar o crear autor
        String nombreAutor = libroDTO.getAuthorName();
        Autor autor = autorRepository.findByNombre(nombreAutor)
                .orElseGet(() -> autorRepository.save(new Autor(nombreAutor)));

        // Crear y guardar libro
        Libro libro = new Libro(
                libroDTO.getTitle(),
                libroDTO.getLanguage(),
                libroDTO.getDownload_count(),
                autor
        );
        libroRepository.save(libro);
        System.out.println("Libro registrado con éxito: " + libro.getTitulo());
    }

    public void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    public void listarLibrosPorIdioma(String idioma) {
        List<Libro> libros = libroRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma especificado.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    public void top10LibrosMasDescargados() {
        List<Libro> libros = libroRepository.findTop10ByOrderByNumeroDeDescargasDesc();
        if (libros.isEmpty()) {
            System.out.println("No hay libros suficientes registrados.");
        } else {
            System.out.println("\nTop 10 libros más descargados:");
            libros.forEach(System.out::println);
        }
    }
}
