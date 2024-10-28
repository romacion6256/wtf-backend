package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Branch findByBranchName (String name);
}