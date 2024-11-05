package uy.edu.um.wtf.serivces;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.entities.Reservation;
import uy.edu.um.wtf.entities.Function;
import uy.edu.um.wtf.exceptions.InvalidInformation;
import uy.edu.um.wtf.repository.FunctionRepository;
import uy.edu.um.wtf.repository.MovieRepository;
import uy.edu.um.wtf.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private FunctionRepository functionRepository;


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
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public void eliminarPelicula(Long id) {
        Optional<Movie> pelicula = movieRepository.findById(id);
        if (pelicula.isPresent()) {
            movieRepository.delete(pelicula.get());
        } else {
            throw new EntityNotFoundException("La película con ID " + id + " no existe.");
        }
    }

//    public void actualizarPuntuacionPromedio(Long movieId) {
//        // Obtener los id_funcion de la película
//        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Película no encontrada"));
//        List<Long> functionIds = movie.getFunctions().stream()
//                .map(Function::getIdFunction)
//                .collect(Collectors.toList());
//
//        // Buscar las reservas con rating no nulo para esos id_funcion
//        List<Reservation> reservasConRating = reservationRepository.findByFunctionIdInAndRatingIsNotNull(functionIds);
//
//        // Calcular el promedio de los ratings
//        double promedio = reservasConRating.stream()
//                .mapToDouble(Reservation::getRating)
//                .average()
//                .orElse(0.0);
//
//        // Guardar el promedio en la película
//        movie.setPuntuacion((float) promedio);
//        movieRepository.save(movie);
//    }

    public void actualizarPuntuacionesDePeliculas() {
        // Obtener todas las películas
        List<Movie> peliculas = movieRepository.findAll();

        // Recorrer cada película y actualizar su puntuación promedio
        for (Movie pelicula : peliculas) {
            // Obtener los id_funcion de las funciones asociadas a la película
            List<Long> idFunciones = functionRepository.findDistinctFunctionIdsByMovieId(pelicula.getIdMovie());

            if (!idFunciones.isEmpty()) {
                // Buscar reservas con calificaciones no nulas para esos id_funcion
                List<Reservation> reservasConRating = reservationRepository.findByFunctionIdInAndRatingIsNotNull(idFunciones);

                // Calcular el promedio de los ratings
                double promedio = reservasConRating.stream()
                        .mapToDouble(Reservation::getRating)
                        .average()
                        .orElse(0.0);

                // Actualizar la puntuación de la película
                pelicula.setPuntuacion((float) promedio);
                movieRepository.save(pelicula);
            }
        }
    }

}
