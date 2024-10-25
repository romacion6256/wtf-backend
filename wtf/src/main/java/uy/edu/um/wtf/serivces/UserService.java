package uy.edu.um.wtf.serivces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Admin;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.entities.User;
import uy.edu.um.wtf.exceptions.ExistingUser;
import uy.edu.um.wtf.exceptions.InvalidInformation;
import uy.edu.um.wtf.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

}
