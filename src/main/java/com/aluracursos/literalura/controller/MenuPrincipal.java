package com.aluracursos.literalura.controller;

import com.aluracursos.literalura.service.LibroService;

import java.util.Scanner;

public class MenuPrincipal {

    private final Scanner teclado = new Scanner(System.in);
    private final LibroService servicio;

    public MenuPrincipal(LibroService servicio) {
        this.servicio = servicio;
    }

    public void mostrarMenu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n===== LITERAlura =====");
            System.out.println("1 - Buscar libro por título");
            System.out.println("2 - Listar libros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar libros por idioma");
            System.out.println("5 - Top 10 libros más descargados");
            System.out.println("0 - Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(teclado.nextLine());

                switch (opcion) {
                    case 1 -> {
                        System.out.print("Ingrese el título del libro: ");
                        String titulo = teclado.nextLine();
                        servicio.buscarYGuardarLibro(titulo);
                    }
                    case 2 -> servicio.listarLibros();
                    case 3 -> servicio.listarAutores();
                    case 4 -> {
                        System.out.print("Ingrese el idioma (ej: en, es, fr): ");
                        String idioma = teclado.nextLine();
                        servicio.listarLibrosPorIdioma(idioma);
                    }
                    case 5 -> servicio.top10LibrosMasDescargados();
                    case 0 -> System.out.println("Saliendo de la aplicación...");
                    default -> System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
            }
        }
    }
}
