package com.imani.cash.domain.service.property.rental;

import com.imani.cash.domain.property.rental.Apartment;
import com.imani.cash.domain.property.rental.Floor;
import com.imani.cash.domain.property.rental.repository.IFloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * @author manyce400
 */
@Service(FloorProducerService.SPRING_BEAN)
public class FloorProducerService implements IFloorProducerService {


    @Autowired
    private IFloorRepository iFloorRepository;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FloorProducerService.class);

    public static final String SPRING_BEAN = "com.imani.cash.domain.service.property.rental.FloorProducerService";


    @Transactional
    @Override
    public void createFloorApartments(Floor floor, Integer numberOfApts) {
        Assert.notNull(floor, "Floor cannot be null");
        Assert.notNull(floor.getProperty(), "Floor Property cannot be null");
        Assert.notNull(numberOfApts, "numberOfApts cannot be null");

        LOGGER.info("Generating -> {} # of Apartments on property floor:=> {}", numberOfApts, floor);

        for (int i = 1; i<= numberOfApts; i++) {
            Apartment apartment = Apartment.builder()
                    .floor(floor)
                    .isRented(false)
                    .build();
            floor.addToApartments(apartment);
        }

        // Save the floor with all the new apartments added.
        iFloorRepository.save(floor);
    }
}
