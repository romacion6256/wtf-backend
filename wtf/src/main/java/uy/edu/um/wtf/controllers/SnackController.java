package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.Snack;
import uy.edu.um.wtf.repository.SnackRepository;

import java.util.List;


@RestController
@RequestMapping("/snacks")
public class SnackController {

    @Autowired
    private SnackRepository snackRepository;

    @GetMapping
    public List<Snack> getAllSnacks() {
        return snackRepository.findAll();
    }

    @PostMapping
    public Snack createSnack(@RequestBody Snack snack) {
        return snackRepository.save(snack);
    }
}

