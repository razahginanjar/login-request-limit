package com.example.Login.repositories;

import com.example.Login.entities.Vendors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorsRepository extends JpaRepository<Vendors, Integer> {
}
