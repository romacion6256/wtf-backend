package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.exceptions.ExistingUser;
import uy.edu.um.wtf.exceptions.InvalidInformation;
import uy.edu.um.wtf.serivces.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> clientRegister(@RequestBody Map<String, String> userData) {
        String nombreUsuario = userData.get("nombre");
        String email = userData.get("email");
        String contraseña = userData.get("contraseña");

        try {
            userService.registerClient(nombreUsuario, email, contraseña);
        } catch (InvalidInformation | ExistingUser e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Usuario creado correctamente");
    }
}
