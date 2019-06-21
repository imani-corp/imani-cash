package com.imani.cash.domain.property.repository;

import com.imani.cash.domain.property.rental.Property;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author manyce400
 */
public interface IPropertyInfoRepository extends JpaRepository<Property, Long> {

}
