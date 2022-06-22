package com.house.management.repository;

import com.house.management.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
@Transactional
public interface HouseRepository extends JpaRepository<House, Integer> {

    House getHouseByAddress(String address);

}
