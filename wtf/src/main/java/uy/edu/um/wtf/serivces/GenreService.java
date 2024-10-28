package uy.edu.um.wtf.serivces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Genre;
import uy.edu.um.wtf.repository.GenreRepository;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> obtenerTodosLosGeneros() {return genreRepository.findAll();}

}
