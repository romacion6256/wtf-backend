package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.Admin;
import uy.edu.um.wtf.entities.Snack;
import uy.edu.um.wtf.entities.User;
import uy.edu.um.wtf.serivces.SnackService;
import uy.edu.um.wtf.serivces.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/snack")
@CrossOrigin(origins = "http://localhost:3000")
public class SnackController {

    @Autowired
    private SnackService snackService;

    @Autowired
    private UserService userService;

    @GetMapping("/listarSnacks")
    public List<Snack> obtenerTodas () {
        List<Snack> snacks = snackService.listarSnacks();
        return new ResponseEntity<>(snacks, HttpStatus.OK).getBody();
    }
    @PostMapping("/agregarSnack/{idAdmin}")
    public ResponseEntity<?> agregarSnack(@PathVariable Long idAdmin, @RequestBody Map<String, String> payload) {
        String description = payload.get("description");
        BigDecimal price = new BigDecimal(payload.get("price"));
        User administrador = userService.getUserById(idAdmin);

        Snack snack = new Snack();
        snack.setDescription(description);
        snack.setPrice(price);
        snack.setAdmin((Admin) administrador);
        try {
            snackService.agregarSnack(snack);
            return ResponseEntity.ok("Snack agregado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al agregar el snack");
        }
    }


}
