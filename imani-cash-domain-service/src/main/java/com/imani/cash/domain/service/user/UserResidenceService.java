package com.imani.cash.domain.service.user;

import com.imani.cash.domain.property.PropertyTypeE;
import com.imani.cash.domain.property.rental.Apartment;
import com.imani.cash.domain.property.rental.Property;
import com.imani.cash.domain.property.rental.RentalAgreement;
import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.UserResidence;
import com.imani.cash.domain.user.repository.IUserResidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * @author manyce400
 */
@Service(UserResidenceService.SPRING_BEAN)
public class UserResidenceService implements IUserResidenceService {



    @Autowired
    private IUserResidenceRepository iUserResidenceRepository;


    public static final String SPRING_BEAN = "com.imani.cash.domain.service.user.UserResidenceService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UserResidenceService.class);


    @Transactional
    @Override
    public void buildUserResidence(UserRecord userRecord, Property property, boolean primaryResidence) {
        Assert.notNull(userRecord, "UserRecord cannot be null");
        Assert.notNull(property, "Property cannot be null");
        Assert.isTrue(property.getPropertyTypeE() == PropertyTypeE.SingleFamily, "SingleFamily PropertyType expected");

        LOGGER.debug("Building residence for user:=> {}", userRecord.getEmbeddedContactInfo().getEmail());

        UserResidence userResidence =  UserResidence.builder()
                .userRecord(userRecord)
                .property(property)
                .primaryResidence(primaryResidence)
                .build();

        iUserResidenceRepository.save(userResidence);
    }

    @Transactional
    @Override
    public void buildUserResidence(UserRecord userRecord, Property property, RentalAgreement rentalAgreement, boolean primaryResidence) {
        Assert.notNull(userRecord, "UserRecord cannot be null");
        Assert.notNull(property, "Property cannot be null");
        Assert.notNull(property, "RentalAgreement cannot be null");
        Assert.isTrue(property.getPropertyTypeE() == PropertyTypeE.SingleFamily, "SingleFamily PropertyType expected");

        LOGGER.debug("Building residence for user:=> {} with rental agreement", userRecord.getEmbeddedContactInfo().getEmail());

        UserResidence userResidence =  UserResidence.builder()
                .userRecord(userRecord)
                .property(property)
                .rentalAgreement(rentalAgreement)
                .primaryResidence(primaryResidence)
                .build();

        iUserResidenceRepository.save(userResidence);
    }

    @Transactional
    @Override
    public void buildUserResidence(UserRecord userRecord, Property property, Apartment apartment, boolean primaryResidence) {
        Assert.notNull(userRecord, "UserRecord cannot be null");
        Assert.notNull(property, "Property cannot be null");
        Assert.isTrue(property.getPropertyTypeE() == PropertyTypeE.MultiFamily, "MultiFamily PropertyType expected");

        LOGGER.debug("Building residence for user:=> {}", userRecord.getEmbeddedContactInfo().getEmail());

        UserResidence userResidence =  UserResidence.builder()
                .userRecord(userRecord)
                .property(property)
                .apartment(apartment)
                .primaryResidence(primaryResidence)
                .build();

        iUserResidenceRepository.save(userResidence);
    }

    @Transactional
    @Override
    public void buildUserResidence(UserRecord userRecord, Property property, Apartment apartment, RentalAgreement rentalAgreement, boolean primaryResidence) {
        Assert.notNull(userRecord, "UserRecord cannot be null");
        Assert.notNull(property, "Property cannot be null");
        Assert.notNull(property, "RentalAgreement cannot be null");
        Assert.isTrue(property.getPropertyTypeE() == PropertyTypeE.MultiFamily, "MultiFamily PropertyType expected");

        LOGGER.debug("Building residence for user:=> {}", userRecord.getEmbeddedContactInfo().getEmail());

        UserResidence userResidence =  UserResidence.builder()
                .userRecord(userRecord)
                .property(property)
                .apartment(apartment)
                .rentalAgreement(rentalAgreement)
                .primaryResidence(primaryResidence)
                .build();

        iUserResidenceRepository.save(userResidence);
    }
}
