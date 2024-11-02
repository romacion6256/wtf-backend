package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.Admin;
import uy.edu.um.wtf.entities.Card;
import uy.edu.um.wtf.entities.Snack;
import uy.edu.um.wtf.entities.User;
import uy.edu.um.wtf.serivces.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Map<String, String> cambios) {
        try {
            userService.actualizarUsuario(id, cambios);
            return ResponseEntity.ok("Usuario actualizado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{clientId}/assign-card")
    public ResponseEntity<String> assignCard(
            @PathVariable Long clientId,
            @RequestBody Card card
    ) {
        try {
            userService.assignCardToClient(clientId, card);
            return ResponseEntity.ok("Tarjeta asignada al cliente con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al asignar la tarjeta: " + e.getMessage());
        }
    }
}
