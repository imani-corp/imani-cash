package com.imani.cash.gateway.rest.user;

import com.imani.cash.domain.service.user.IUserRecordManagementService;
import com.imani.cash.domain.service.user.UserRecordManagementService;
import com.imani.cash.domain.user.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author manyce400
 */
@RestController
@RequestMapping(path = "/userrecord")
public class UserManagementController {


    @Autowired
    @Qualifier(UserRecordManagementService.SPRING_BEAN)
    private IUserRecordManagementService iUserRecordManagementService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UserManagementController.class);


    @PostMapping(path= "/register/new", consumes = "application/json")
    public void registerNewUser(@RequestBody UserRecord userRecord) {
        LOGGER.info("Attempting to register new Imani Cash user:=> {}", userRecord);
        iUserRecordManagementService.registerUserRecord(userRecord);
    }


}
