package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
}