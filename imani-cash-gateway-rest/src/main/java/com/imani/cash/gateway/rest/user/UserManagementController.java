package com.imani.cash.gateway.rest.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imani.cash.domain.service.user.IUserRecordManagementService;
import com.imani.cash.domain.service.user.UserRecordManagementService;
import com.imani.cash.domain.user.gateway.message.UserTransactionGatewayMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private ObjectMapper mapper;

    @Autowired
    @Qualifier(UserRecordManagementService.SPRING_BEAN)
    private IUserRecordManagementService iUserRecordManagementService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UserManagementController.class);


//    @PostMapping(path= "/register/new", consumes = "application/json")
//    public UserTransactionGatewayMessage registerNewUser(@RequestBody UserRecord userRecord) {
//        LOGGER.info("Attempting to register new Imani Cash user:=> {}", userRecord);
//        UserTransactionGatewayMessage userRecordTransaction = iUserRecordManagementService.registerUserRecord(userRecord);
//        return userRecordTransaction;
//    }


    @PostMapping(path= "/register/new", consumes = "application/json")
    public UserTransactionGatewayMessage registerNewUser(@RequestBody UserTransactionGatewayMessage userTransactionGatewayMessage) {
        LOGGER.info("Attempting to register new Imani Cash from UserTransaction:=> {}", userTransactionGatewayMessage);
        UserTransactionGatewayMessage transactionResult = iUserRecordManagementService.registerUserRecord(userTransactionGatewayMessage.getUserRecord());

        System.out.println("transactionResult.getMessageTxnStatusE() = " + transactionResult.getMessageTxnStatusE());

        try {
            String value = mapper.writeValueAsString(transactionResult);
            System.out.println("Written from SpringBoot JacksonMapper value = " + value);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return transactionResult;
    }


}
