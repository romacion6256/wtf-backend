package uy.edu.um.wtf.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RegisterController {
    public ResponseEntity<?> Register(@RequestBody Map<String, String> userData) {
        String nombreUsuario = userData.get("nombre");
        String email = userData.get("email");
        String contraseña = userData.get("contraseña");



    }
}
