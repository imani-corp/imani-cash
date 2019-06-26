package com.imani.cash.domain.service.user;

import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.repository.IUserRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(CustomUserDetailsService.SPRING_BEAN)
public class CustomUserDetailsService implements UserDetailsService {



    @Autowired
    private IUserRecordRepository iUserRecordRepository;


    public static final String SPRING_BEAN = "com.imani.cash.domain.service.user.CustomUserDetailsService";


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LOGGER.debug("Attempting to find UserRecord with email:=> {}", userName);
        UserRecord userRecord = iUserRecordRepository.findByUserEmail(userName);
        LOGGER.debug("UserRecord found: {}", userRecord);

        if(userRecord != null) {
            JwtUserDetails userDetails = new JwtUserDetails(userRecord);
            return userDetails;
        }

        throw new UsernameNotFoundException("Provided user name cannot be found in the system");
    }
}
