package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.um.wtf.entities.Genre;
import uy.edu.um.wtf.serivces.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
@CrossOrigin(origins = "http://localhost:3000")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping("/todos")
    public List<Genre> obtenerTodos() {
        List<Genre> generos = genreService.obtenerTodosLosGeneros();
        return new ResponseEntity<>(generos, HttpStatus.OK).getBody();
    }
}
