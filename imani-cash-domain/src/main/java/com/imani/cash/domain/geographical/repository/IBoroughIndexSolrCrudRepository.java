package com.imani.cash.domain.geographical.repository;

import com.imani.cash.domain.geographical.BoroughIndex;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author manyce400
 */
@Repository
public interface IBoroughIndexSolrCrudRepository extends SolrCrudRepository<BoroughIndex, Long> {

}