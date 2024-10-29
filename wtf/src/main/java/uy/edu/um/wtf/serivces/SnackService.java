package uy.edu.um.wtf.serivces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Snack;
import uy.edu.um.wtf.repository.SnackRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SnackService {

    @Autowired
    private SnackRepository snackRepository;

    public void agregarSnack(Snack snack) {
        if (snack == null) {
            throw new IllegalArgumentException("El snack no puede ser nulo");
        }
        if (snack.getDescription() == null || snack.getDescription().isEmpty()) {
            throw new IllegalArgumentException("La descripción del snack no puede ser nula o vacía");
        }
        if (snack.getPrice() == null || snack.getPrice().compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw new IllegalArgumentException("El precio del snack no puede ser nulo o menor o igual a 0");
        }
        if (snack.getAdmin() == null) {
            throw new IllegalArgumentException("El administrador del snack no puede ser nulo");
        }
        if (snackRepository.findByDescription(snack.getDescription()).isPresent()) {
            throw new IllegalArgumentException("El snack ya existe");
        }
        snackRepository.save(snack);
    }

    public List<Snack> listarSnacks() {
        List<Snack> snacks = snackRepository.findAll();
        return snacks;
    }
}
