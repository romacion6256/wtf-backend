package uy.edu.um.wtf.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.Card;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.entities.User;
import uy.edu.um.wtf.exceptions.InvalidInformation;
import uy.edu.um.wtf.repository.UserRepository;
import uy.edu.um.wtf.serivces.CardService;
import uy.edu.um.wtf.serivces.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        try {
            String email = loginData.get("email");
            String password = loginData.get("contraseña");

            User usuario = userService.validLogin(email, password);
            System.out.println(usuario.getEmail());
            session.isNew();
            session.setAttribute("user", usuario);
            return ResponseEntity.ok(usuario);

        } catch (InvalidInformation e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }

    @PatchMapping("/editarUsuario/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Map<String, String> cambios) {
        try {
            userService.actualizarUsuario(id, cambios);
            return ResponseEntity.ok("Usuario actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    @PostMapping("/agregarTarjeta/{id}")
    public ResponseEntity<String> agregarTarjeta(@PathVariable Long id, @RequestBody Map<String, String> tarjeta) {
        Long numero = Long.parseLong(tarjeta.get("numero"));
        int mesVencimiento = Integer.parseInt(tarjeta.get("mes"));
        int añoVencimiento = Integer.parseInt(tarjeta.get("año"));
        int cvv = Integer.parseInt(tarjeta.get("cvv"));

        // Validar que el usuario exista y sea cliente
        User cliente = userService.getUserById(id);
        if (!(cliente instanceof Client)) {
            return ResponseEntity.badRequest().body("El usuario no es un cliente válido.");
        }
        Client client = (Client) cliente;

        try {
            // Validar y guardar la tarjeta antes de asignarla al cliente
            Card card = new Card();
            card.setCardNumber(numero);
            card.setExpirationMonth(mesVencimiento);
            card.setExpirationYear(añoVencimiento);
            card.setCvv(cvv);

            // Llamar a la validación y creación en cardService
            cardService.addCard(card);

            // Asignar tarjeta al cliente y guardar
            client.setCard(card);
            userRepository.save(client);

            return ResponseEntity.ok("Tarjeta agregada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al agregar la tarjeta: " + e.getMessage());
        }
    }


}