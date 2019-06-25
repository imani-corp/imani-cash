package com.imani.cash.domain.service.user;

import com.imani.cash.domain.contact.EmbeddedContactInfo;
import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.UserRecordAuthentication;
import com.imani.cash.domain.user.repository.IUserRecordRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * @author manyce400 
 */
@RunWith(MockitoJUnitRunner.class)
public class UserRecordAuthenticationServiceTest {



    private UserRecord userRecord;

    private EmbeddedContactInfo embeddedContactInfo;
    
    @Mock
    private IUserRecordRepository iUserRecordRepository;
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @InjectMocks
    private UserRecordAuthenticationService userRecordAuthenticationService;


    
    @Before
    public void beforeTest() {
        embeddedContactInfo = EmbeddedContactInfo.builder()
                .email("testuser@imanicash.com")
                .build();

        userRecord = UserRecord.builder()
                .embeddedContactInfo(embeddedContactInfo)
                .firstName("Test")
                .lastName("User")
                .accountLocked(false)
                .unsuccessfulLoginAttempts(0)
                .build();

        // Mock out call to load user from DB
        Mockito.when(iUserRecordRepository.findByUserEmail("testuser@imanicash.com")).thenReturn(userRecord);
    }
    
    
    @Test
    public void testTrackUnsuccessfulLoginAttempts() {
        Integer unsuccessfulAttempts = userRecordAuthenticationService.trackUnsuccessfulLoginAttempts(userRecord);
        Assert.assertEquals(new Integer(1), unsuccessfulAttempts);
        Assert.assertFalse(userRecord.isAccountLocked());

        unsuccessfulAttempts = userRecordAuthenticationService.trackUnsuccessfulLoginAttempts(userRecord);
        Assert.assertEquals(new Integer(2), unsuccessfulAttempts);
        Assert.assertFalse(userRecord.isAccountLocked());

        unsuccessfulAttempts = userRecordAuthenticationService.trackUnsuccessfulLoginAttempts(userRecord);
        Assert.assertEquals(new Integer(3), unsuccessfulAttempts);
        Assert.assertFalse(userRecord.isAccountLocked());

        // After 3rd time verify that the UserRecord is now locked.
        unsuccessfulAttempts = userRecordAuthenticationService.trackUnsuccessfulLoginAttempts(userRecord);
        Assert.assertEquals(new Integer(3), unsuccessfulAttempts);
        Assert.assertTrue(userRecord.isAccountLocked());
    }

    @Test
    public void testExecuteUserRecordLogin() {
        UserRecordAuthentication userRecordAuthentication = userRecordAuthenticationService.executeUserRecordLogin(userRecord);
        Assert.assertTrue(userRecordAuthentication.getAuthenticated());
        Assert.assertTrue(userRecordAuthentication.getLoggedIn());
        Assert.assertFalse(userRecordAuthentication.getAccountLocked());
        Assert.assertEquals(userRecord, userRecordAuthentication.getUserRecord());
        Assert.assertEquals(new Integer(0), userRecordAuthentication.getUnsuccessfulLoginAttempts());
    }

    @Test
    public void testGetBadCredentialsUserRecordAuthentication() {
        UserRecordAuthentication userRecordAuthentication = userRecordAuthenticationService.getBadCredentialsUserRecordAuthentication(userRecord, 1);
        Assert.assertFalse(userRecordAuthentication.getAuthenticated());
        Assert.assertFalse(userRecordAuthentication.getLoggedIn());
        Assert.assertFalse(userRecordAuthentication.getAccountLocked());
        Assert.assertEquals(userRecord, userRecordAuthentication.getUserRecord());
        Assert.assertEquals(new Integer(1), userRecordAuthentication.getUnsuccessfulLoginAttempts());
    }
    
}
