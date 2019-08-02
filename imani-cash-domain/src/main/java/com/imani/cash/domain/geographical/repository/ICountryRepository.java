package com.imani.cash.domain.geographical.repository;

import com.imani.cash.domain.geographical.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author manyce400
 */
@Repository
public interface ICountryRepository extends JpaRepository<Country, Long> {


}
