package com.imani.cash.domain.service.geographical;

import com.imani.cash.domain.geographical.Borough;

import java.util.List;
import java.util.Optional;

/**
 * @author manyce400
 */
public interface IBoroughService {

    public Optional<Borough> findByID(Long id);

    public Optional<Borough> findByName(String name);

    public List<Borough> findAll();
}
