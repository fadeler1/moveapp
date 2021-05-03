package cl.moveapp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.moveapp.demo.model.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {}
