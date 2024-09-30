package uy.edu.um.wtf.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.Function;
import uy.edu.um.wtf.repository.FunctionRepository;

import java.util.List;


@RestController
@RequestMapping("/functions")
public class FunctionController {

    @Autowired
    private FunctionRepository functionRepository;

    @GetMapping
    public List<Function> getAllFunctions() {
        return functionRepository.findAll();
    }

    @PostMapping
    public Function createFunction(@RequestBody Function function) {
        return functionRepository.save(function);
    }
}
