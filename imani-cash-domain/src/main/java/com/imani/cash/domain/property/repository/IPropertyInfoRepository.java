package com.imani.cash.domain.property.repository;

import com.imani.cash.domain.property.PropertyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author manyce400
 */
public interface IPropertyInfoRepository extends JpaRepository<PropertyInfo, Long> {

}
