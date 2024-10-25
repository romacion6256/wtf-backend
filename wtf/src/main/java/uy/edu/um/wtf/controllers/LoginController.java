package uy.edu.um.wtf.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.User;
import uy.edu.um.wtf.exceptions.InvalidInformation;
import uy.edu.um.wtf.serivces.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        try {
            String email = loginData.get("email");
            String password = loginData.get("contraseña");

            User usuario = userService.validLogin(email, password);
            System.out.println(usuario.getEmail());
            session.isNew();
            session.setAttribute("user", usuario);

        } catch (InvalidInformation e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Logueado correctamente");
    }
}