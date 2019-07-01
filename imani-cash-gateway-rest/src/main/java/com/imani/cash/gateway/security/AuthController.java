package com.imani.cash.gateway.security;

import com.google.common.collect.ImmutableList;
import com.imani.cash.domain.service.user.CustomUserDetailsService;
import com.imani.cash.domain.service.user.IUserRecordAuthenticationService;
import com.imani.cash.domain.service.user.UserRecordAuthenticationService;
import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.UserRecordAuthentication;
import com.imani.cash.domain.user.gateway.message.UserTransactionGatewayMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {



    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    @Qualifier(CustomUserDetailsService.SPRING_BEAN)
    private UserDetailsService userDetailsService;


    @Autowired
    @Qualifier(UserRecordAuthenticationService.SPRING_BEAN)
    private IUserRecordAuthenticationService iUserRecordAuthenticationService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AuthController.class);



    @PostMapping("/signin")
    public ResponseEntity execJWTSignInRequest(@RequestBody UserRecord userRecord) {
        LOGGER.info("Executing JWT signin request for user:=> {}", userRecord);

        try {
            String username = userRecord.getEmbeddedContactInfo().getEmail();

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            LOGGER.info("UserDetails found=> {}", userDetails);

            List<String> roles = ImmutableList.of("TenantUser");
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRecord.getEmbeddedContactInfo().getEmail(), userRecord.getPassword()));
            System.out.println("auth = " + auth);
            String token = jwtTokenProvider.createToken(username, roles);

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            LOGGER.error("Exception was thrown", e);
            throw new BadCredentialsException("Invalid username/password supplied");
        }

    }

    @PostMapping("/user/login")
    public UserRecordAuthentication execImaniUserLogin(@RequestBody UserTransactionGatewayMessage userTransactionGatewayMessage) {
        LOGGER.info("Executing IMani login process on gateway message:=> {}", userTransactionGatewayMessage);
        UserRecordAuthentication userRecordAuthentication = iUserRecordAuthenticationService.authenticateAndLogInUserRecord(userTransactionGatewayMessage);
        return userRecordAuthentication;
    }

    @PostMapping("/user/logout")
    public UserRecordAuthentication execImaniUserLogout(@RequestBody UserRecord userRecord) {
        LOGGER.info("Executing Imani login for user:=> {}", userRecord.getEmbeddedContactInfo().getEmail());
        UserRecordAuthentication userRecordAuthentication = iUserRecordAuthenticationService.authenticateAndLogOutUserRecord(userRecord);
        return userRecordAuthentication;
    }


}
