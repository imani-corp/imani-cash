package com.imani.cash.domain.service.property.rental;

import com.imani.cash.domain.property.rental.Floor;

/**
 * @author manyce400
 */
public interface IFloorProducerService {

    public void createFloorApartments(Floor floor, Integer numberOfApts);
}
