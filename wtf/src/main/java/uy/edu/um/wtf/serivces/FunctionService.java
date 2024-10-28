package uy.edu.um.wtf.serivces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Function;
import uy.edu.um.wtf.exceptions.InvalidInformation;
import uy.edu.um.wtf.repository.FunctionRepository;

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
        if (functionRepository.findByFormatAndSubtitledAndDateAndTimeAnAndMovieAndRoom(funcion.getFormat(), funcion.getSubtitled(), funcion.getDate(), funcion.getTime(), funcion.getMovie(), funcion.getRoom()).isPresent()) {
            throw new InvalidInformation("La funcion ya existe");
        }
        return functionRepository.save(funcion);
    }

}
