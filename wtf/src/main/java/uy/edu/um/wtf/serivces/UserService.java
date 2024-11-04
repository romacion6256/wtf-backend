package uy.edu.um.wtf.serivces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Admin;
import uy.edu.um.wtf.entities.Card;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.entities.User;
import uy.edu.um.wtf.exceptions.ExistingUser;
import uy.edu.um.wtf.exceptions.InvalidInformation;
import uy.edu.um.wtf.repository.CardRepository;
import uy.edu.um.wtf.repository.UserRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    public void addClient(Client client) throws InvalidInformation, ExistingUser {
        if (userRepository.findOneByEmail(client.getEmail()).isPresent()) {
            throw new ExistingUser("User with that email already exists");
        }
        if (!validEmail(client.getEmail())) {
            throw new InvalidInformation("Invalid email");
        }
        userRepository.save(client);
        System.out.println("Client saved successfully: " + client.getEmail());
    }

    public boolean validEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(emailRegex);
    }

    public User validLogin(String email, String password) throws InvalidInformation {
        User user = userRepository.findOneByEmail(email).orElseThrow(() -> new InvalidInformation("User not found"));
        if (!user.getPassword().equals(password)) {
            throw new InvalidInformation("Invalid password");
        }
        return user;
    }

    public Client registerClient(String nombre, String email, String password) throws InvalidInformation, ExistingUser {
        Client client = new Client();
        client.setUserName(nombre);
        client.setEmail(email);
        client.setPassword(password);
        client.setRole("CLIENT");
        try {
            addClient(client);
            return client;
        } catch (ExistingUser e) {
            throw new ExistingUser("User with that email already exists");
        }
    }

    public void addAdmin(Admin admin) throws InvalidInformation, ExistingUser {
        if (userRepository.findOneByEmail(admin.getEmail()).isPresent()) {
            throw new ExistingUser("User with that email already exists");
        }
        if (!validEmail(admin.getEmail())) {
            throw new InvalidInformation("Invalid email");
        }
        userRepository.save(admin);
        System.out.println("Admin saved successfully: " + admin.getEmail());
    }

    public Admin registerAdmin(String nombre, String email, String password) throws InvalidInformation, ExistingUser {
        Admin admin = new Admin();
        admin.setUserName(nombre);
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setRole("ADMIN");
        try {
            addAdmin(admin);
            return admin;
        } catch (ExistingUser e) {
            throw new ExistingUser("User with that email already exists");
        }
    }

    public User getUserById(Long id) {
        return userRepository.findOneById(id).orElse(null);
    }

    public void actualizarUsuario(Long id, Map<String, String> cambios) {

        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        cambios.forEach((campo, valor) -> {
            switch (campo) {
                case "userName":
                    usuario.setUserName(valor);
                    break;
                case "password":
                    usuario.setPassword(valor);
                    break;
                case "name":
                    usuario.setName(valor);
                    break;
                case "surname":
                    usuario.setSurname(valor);
                    break;
                case "email":
                    if (!validEmail(valor)) {
                        throw new IllegalArgumentException("Email inválido");
                    }

                    usuario.setEmail(valor);
                    break;
                case "phoneNumber":
                    if (valor.length()!=9) {
                        throw new IllegalArgumentException("Número de teléfono inválido");
                    }
                    usuario.setPhoneNumber(Long.valueOf(valor));
                    break;
                case "adress":
                    usuario.setAdress(valor);
                    break;
                case "document":
                    if (valor.length()!=8) {
                        throw new IllegalArgumentException("Número de documento inválido");
                    }
                    usuario.setDocument(Long.valueOf(valor));
                    break;
                case "birthDate":
                    usuario.setBirthDate(LocalDate.parse(valor));
                    break;

                default:
                    throw new IllegalArgumentException("Campo no válido: " + campo);
            }
        });
        userRepository.save(usuario);
    }

    public void assignCardToClient(Long clientId, Card card) {
        // Busca al cliente por su ID
        Client client = (Client) userRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + clientId));


        Card existingCard = client.getCard();
        client.setCard(null);
        // Verifica si ya tiene una tarjeta asociada
        if (existingCard != null) {
            cardRepository.deleteById(existingCard.getCardNumber());
        }

        // Valida la tarjeta y la guarda si es necesario
        if (cardRepository.findByCardNumber(card.getCardNumber()).isEmpty()) {
            cardRepository.save(card);
        }

        // Asocia la tarjeta al cliente
        client.setCard(card);

        // Guarda los cambios en el cliente
        userRepository.save(client);
    }

}
