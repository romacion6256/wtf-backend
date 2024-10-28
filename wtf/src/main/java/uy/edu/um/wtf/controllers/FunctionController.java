package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.Branch;
import uy.edu.um.wtf.entities.Function;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.entities.Room;
import uy.edu.um.wtf.exceptions.InvalidInformation;
import uy.edu.um.wtf.repository.BranchRepository;
import uy.edu.um.wtf.repository.MovieRepository;
import uy.edu.um.wtf.repository.RoomRepository;
import uy.edu.um.wtf.serivces.FunctionService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@RestController
@RequestMapping("/api/function")
@CrossOrigin(origins = "http://localhost:3000")
public class FunctionController {

    @Autowired
    private FunctionService functionService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BranchRepository branchRepository;

    @PostMapping("/agregarFuncion/{idAdmin}")
    public ResponseEntity<String> agregarFuncion(@PathVariable Long id,@RequestBody Map<String, String> payload) throws InvalidInformation {
        String format = payload.get("format");
        Boolean subtitled = Boolean.parseBoolean(payload.get("subtitled"));
        LocalDate date = LocalDate.parse(payload.get("date"));
        LocalTime time = LocalTime.parse(payload.get("time"));
        Movie movie = movieRepository.findByMovieName(payload.get("movie")).orElseThrow(() -> new InvalidInformation("La pelicula no existe"));

        Branch branch = branchRepository.findByBranchName(sucursal);
        int branchid = branch.getIdBranch()
        String sala = payload.get("sala");
        Room room = roomRepository.findByBranchAndNumber(branch , sala).orElseThrow(() -> new InvalidInformation("La sala no existe"));


        Function funcion = new Function()

        try {
            functionService.agregarFuncion(funcion);
            return ResponseEntity.ok("Funcion agregada correctamente");
        } catch (InvalidInformation e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
