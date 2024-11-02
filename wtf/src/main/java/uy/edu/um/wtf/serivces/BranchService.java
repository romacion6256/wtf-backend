package uy.edu.um.wtf.serivces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Branch;
import uy.edu.um.wtf.entities.Card;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.repository.BranchRepository;
import uy.edu.um.wtf.repository.CardRepository;

import java.util.List;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;


    public List<Branch> obtenerTodasLasSucursales() {
        return branchRepository.findAll();
    }
}
