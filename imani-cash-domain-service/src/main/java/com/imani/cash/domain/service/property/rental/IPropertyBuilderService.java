package com.imani.cash.domain.service.property.rental;

import com.imani.cash.domain.property.IHasPropertyData;
import com.imani.cash.domain.property.rental.Property;

import java.util.Optional;

/**
 * @author manyce400
 */
public interface IPropertyBuilderService {


    public Optional<Property> buildAndRecordProperty(IHasPropertyData iHasPropertyData);

}
