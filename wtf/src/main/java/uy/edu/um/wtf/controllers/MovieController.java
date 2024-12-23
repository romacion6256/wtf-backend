package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.*;
import uy.edu.um.wtf.exceptions.InvalidInformation;
import uy.edu.um.wtf.repository.FunctionRepository;
import uy.edu.um.wtf.repository.GenreRepository;
import uy.edu.um.wtf.repository.MovieRepository;
import uy.edu.um.wtf.repository.ReservationProfitRepository;
import uy.edu.um.wtf.serivces.MovieService;
import uy.edu.um.wtf.serivces.UserService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ReservationProfitRepository reservationProfitRepository;

    @Autowired
    private FunctionRepository functionRepository;

    @GetMapping("/obtenerTodas")
    public List<Movie> obtenerTodas () {
        List<Movie> movies = movieService.obtenerTodasLasPeliculas();
        return new ResponseEntity<>(movies, HttpStatus.OK).getBody();
    }

    @PostMapping("/agregarPelicula/{idAdmin}")
    public ResponseEntity<String> agregarPelicula (@PathVariable Long idAdmin, @RequestBody Map<String, String> payload) throws InvalidInformation {
        String movieName = payload.get("movieName");
        int movieYear = Integer.parseInt(payload.get("movieYear"));
        String nombreDirector = payload.get("nombreDirector");
        Integer duracion = Integer.parseInt(payload.get("duracion"));
        String genre = payload.get("genero");

        Genre genero = genreRepository.findByName(genre).orElseThrow(() -> new InvalidInformation("El genero no existe"));
        User administrador = userService.getUserById(idAdmin);

        Movie pelicula = new Movie();
        pelicula.setMovieName(movieName);
        pelicula.setYear(movieYear);
        pelicula.setDirectorName(nombreDirector);
        pelicula.setPuntuacion(0);
        pelicula.setDuration(duracion);
        pelicula.setAdmin((Admin) administrador);
        pelicula.setGenre(genero);

        try {
            movieService.agregarPelicula(pelicula);
            return ResponseEntity.ok("Pelicula agregada correctamente");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPelicula(@PathVariable Long id) {
        try {
            movieService.eliminarPelicula(id);
            return new ResponseEntity<>("Película eliminada correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar la película: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizarPuntuaciones")
    public ResponseEntity<Void> actualizarPuntuaciones() {
        movieService.actualizarPuntuacionesDePeliculas();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/obtenerProfitPorPelicula")
    public ResponseEntity<Map<String, BigDecimal>> obtenerProfitPorPelicula() {
        // Obtener todas las películas
        List<Movie> movies = movieService.obtenerTodasLasPeliculas();

        Map<String, BigDecimal> movieProfits = new HashMap<>();

        for (Movie movie : movies) {
            // Obtener todas las funciones para cada película
            List<Function> functions = functionRepository.findByMovie(movie);

            // Sumar el profit de cada función
            BigDecimal totalProfit = BigDecimal.ZERO;
            for (Function function : functions) {
                // Obtener todas las reservas con status "PAGO" para la función
                List<ReservationProfit> reservationProfits = reservationProfitRepository.findByFunctionIdAndStatus(function.getIdFunction(), "PAGO");

                // Sumar los montos
                for (ReservationProfit reservationProfit : reservationProfits) {
                    totalProfit = totalProfit.add(reservationProfit.getMonto());
                }
            }

            // Agregar el profit total por película al mapa
            movieProfits.put(movie.getMovieName(), totalProfit);
        }

        // Ordenar el mapa por valores (profit) de mayor a menor
        Map<String, BigDecimal> sortedMovieProfits = movieProfits.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // Orden descendente
                .collect(
                        LinkedHashMap::new,
                        (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                        LinkedHashMap::putAll
                );

        // Retornar la respuesta con los beneficios de cada película ordenados
        return new ResponseEntity<>(sortedMovieProfits, HttpStatus.OK);
    }

}
