package com.imani.cash.domain.service.property.billing;

import com.imani.cash.domain.property.billing.MonthlyRentalBill;
import com.imani.cash.domain.property.billing.PropertyService;
import com.imani.cash.domain.property.billing.PropertyServiceChargeExplained;
import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.UserResidence;
import com.imani.cash.domain.user.UserResidencePropertyService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author manyce400
 */
@Service(MonthlyPropertySvcChargeService.SPRING_BEAN)
public class MonthlyPropertySvcChargeService implements IMonthlyPropertySvcChargeService {



    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MonthlyPropertySvcChargeService.class);

    public static final String SPRING_BEAN = "com.imani.cash.domain.service.property.billing.MonthlyPropertySvcChargeService";


    @Override
    public Optional<List<PropertyServiceChargeExplained>> applyMonthlyPropertyServiceCharge(UserResidence userResidence, MonthlyRentalBill monthlyRentalBill) {
        Assert.notNull(userResidence, "UserResidence cannot be null");
        Assert.notNull(monthlyRentalBill, "MonthlyRentalBill cannot be null");

        UserRecord userRecord = userResidence.getUserRecord();

        List<PropertyServiceChargeExplained> propertyServiceChargeExplainedList = new ArrayList<>();

        // Get all the PropertyServices that this user has signed up for
        Set<UserResidencePropertyService> userResidencePropertyServiceSet = userResidence.getUserResidencePropertyServices();

        userResidencePropertyServiceSet.forEach(userResidencePropertyService -> {
            if (userResidencePropertyService.isActive()) {
                PropertyService propertyService = userResidencePropertyService.getPropertyService();
                LOGGER.info("Applying PropertyService: {} for user: {}", propertyService.getServiceName(), userRecord.getEmbeddedContactInfo().getEmail());
                Double cost = propertyService.getServiceMonthlyCost();

                PropertyServiceChargeExplained propertyServiceChargeExplained = PropertyServiceChargeExplained.builder()
                        .serviceMonthlyCost(propertyService.getServiceMonthlyCost())
                        .serviceName(propertyService.getServiceName())
                        .signedUpDate(propertyService.getCreateDate())
                        .build();
                propertyServiceChargeExplainedList.add(propertyServiceChargeExplained);
            }
        });

        if(propertyServiceChargeExplainedList.size() > 0) {
            return Optional.of(propertyServiceChargeExplainedList);
        }

        return Optional.empty();
    }
}
