package com.imani.cash.domain.property.rental.repository;

import com.imani.cash.domain.property.rental.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author manyce400
 */
@Repository
public interface IApartmentRepository extends JpaRepository<Apartment, Long> {


}
