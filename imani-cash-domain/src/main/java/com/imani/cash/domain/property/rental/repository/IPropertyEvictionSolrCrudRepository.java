package com.imani.cash.domain.property.rental.repository;

import com.imani.cash.domain.property.rental.PropertyEvictionIndex;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author manyce400
 */
@Repository
public interface IPropertyEvictionSolrCrudRepository extends SolrCrudRepository<PropertyEvictionIndex, Long> {


}
