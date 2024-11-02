package uy.edu.um.wtf.serivces;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Function;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.exceptions.InvalidInformation;
import uy.edu.um.wtf.repository.FunctionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FunctionService {

    @Autowired
    private FunctionRepository functionRepository;

    public Function agregarFuncion(Function funcion) throws InvalidInformation {
        if (funcion.getFormat() == null || funcion.getFormat().isEmpty()) {
            throw new InvalidInformation("El formato no puede ser nulo o vacio");
        }
        if (funcion.getSubtitled() == null) {
            throw new InvalidInformation("El subtitulado no puede ser nulo");
        }
        if (funcion.getDate() == null) {
            throw new InvalidInformation("La fecha no puede ser nula");
        }
        if (funcion.getTime() == null) {
            throw new InvalidInformation("La hora no puede ser nula");
        }
        if (funcion.getMovie() == null) {
            throw new InvalidInformation("La pelicula no puede ser nula");
        }
        if (funcion.getRoom() == null) {
            throw new InvalidInformation("La sala no puede ser nula");
        }
        if (functionRepository.findByFormatAndSubtitledAndDateAndTimeAndMovieAndRoom(funcion.getFormat(), funcion.getSubtitled(), funcion.getDate(), funcion.getTime(), funcion.getMovie(), funcion.getRoom()).isPresent()) {
            throw new InvalidInformation("La funcion ya existe");
        }
        return functionRepository.save(funcion);
    }

    public List<Function> obtenerTodas () {
        return functionRepository.findAll();
    }

    public void eliminarFuncion(Long id) {
        Optional<Function> funcion = functionRepository.findByIdFunction(id);
        if (funcion.isPresent()) {
            functionRepository.delete(funcion.get());
        } else {
            throw new EntityNotFoundException("La funcion con ID " + id + " no existe.");
        }
    }
}
