package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Function;

public interface FunctionRepository extends JpaRepository<Function, Long> {
}