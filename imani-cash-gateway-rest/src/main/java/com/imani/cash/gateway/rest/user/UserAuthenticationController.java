package com.imani.cash.gateway.rest.user;

import com.imani.cash.domain.service.user.IUserRecordAuthService;
import com.imani.cash.domain.service.user.UserRecordAuthService;
import com.imani.cash.domain.user.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author manyce400
 */
@RestController
@RequestMapping(path = "/userrecord")
public class UserAuthenticationController {




    @Autowired
    @Qualifier(UserRecordAuthService.SPRING_BEAN)
    private IUserRecordAuthService iUserRecordAuthService;


    @GetMapping(path="/list/all", produces = "application/json")
    public List<UserRecord> getAllUserRecord() {
        return iUserRecordAuthService.findAllUserRecord();
    }

}