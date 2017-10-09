package com.zecevic.helena.repository;

import com.zecevic.helena.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAll();

    Address findById(long id);
}
