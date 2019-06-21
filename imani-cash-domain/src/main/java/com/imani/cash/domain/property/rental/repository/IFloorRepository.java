package com.imani.cash.domain.property.rental.repository;

import com.imani.cash.domain.property.rental.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author manyce400
 */
@Repository
public interface IFloorRepository extends JpaRepository<Floor, Long> {



}
