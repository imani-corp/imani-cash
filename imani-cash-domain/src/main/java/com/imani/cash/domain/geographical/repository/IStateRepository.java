package com.imani.cash.domain.geographical.repository;

import com.imani.cash.domain.geographical.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author manyce400
 */
@Repository
public interface IStateRepository extends JpaRepository<State, Long> {

}
