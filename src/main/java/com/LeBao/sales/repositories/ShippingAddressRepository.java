package com.LeBao.sales.repositories;

import com.LeBao.sales.DTO.PersonalInfoDTO;
import com.LeBao.sales.entities.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {


}
