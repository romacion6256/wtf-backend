package uy.edu.um.wtf.serivces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.exceptions.InvalidInformation;
import uy.edu.um.wtf.exceptions.NonExistingEntitie;
import uy.edu.um.wtf.repository.MovieRepository;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> obtenerTodasLasPeliculas() {
        return movieRepository.findAll();
    }

    public Movie agregarPelicula(Movie movie) throws InvalidInformation {
        if (movie.getMovieName() == null || movie.getMovieName().isEmpty()) {
            throw new InvalidInformation("El nombre de la pelicula no puede ser nulo o vacio");
        }
        if (movieRepository.findByMovieName(movie.getMovieName()).isPresent()) {
            throw new InvalidInformation("La pelicula ya existe");
        }
        if (movie.getDirectorName().isEmpty()) {
            throw new InvalidInformation("Nombre del director vacio");
        }
        if (movie.getDuration() == null) {
            throw new InvalidInformation("Duracion de la pelicula no puede ser nula");
        }
        return movieRepository.save(movie);
    }
}
