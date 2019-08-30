package com.imani.cash.domain.service.user;

import com.imani.cash.domain.contact.EmbeddedContactInfo;
import com.imani.cash.domain.gateway.message.MessageTxnStatusE;
import com.imani.cash.domain.security.encryption.IOneWayClearTextEncryption;
import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.gateway.message.UserTransactionGatewayMessage;
import com.imani.cash.domain.user.gateway.message.UserTransactionTypeE;
import com.imani.cash.domain.user.repository.IUserRecordRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class UserRecordManagementServiceTest {



    private UserRecord userRecord;

    private EmbeddedContactInfo embeddedContactInfo;


    @Mock
    private IUserRecordRepository iUserRecordRepository;

    @Mock
    private IOneWayClearTextEncryption iOneWayClearTextEncryption;

    @InjectMocks
    private UserRecordManagementService userRecordManagementService;




    @Before
    public void beforeTest() {
        embeddedContactInfo = EmbeddedContactInfo.builder()
                .email("testuser@imanicash.com")
                .build();

        userRecord = UserRecord.builder()
                .embeddedContactInfo(embeddedContactInfo)
                .firstName("Test")
                .lastName("User")
                .password("boss")
                .accountLocked(false)
                .unsuccessfulLoginAttempts(0)
                .build();
    }



    @Test
    public void testGetUserTransactionGatewayMessageOnSucess() {
        UserTransactionGatewayMessage userTransactionGatewayMessage = userRecordManagementService.getUserTransactionGatewayMessageOnSucess(userRecord, UserTransactionTypeE.RegisterNew);
        Assert.assertEquals(MessageTxnStatusE.Success, userTransactionGatewayMessage.getMessageTxnStatusE());
        Assert.assertEquals(UserTransactionTypeE.RegisterNew, userTransactionGatewayMessage.getUserTransactionTypeE());
        Assert.assertEquals(UserTransactionTypeE.RegisterNew, userTransactionGatewayMessage.getUserTransactionTypeE());
        Assert.assertEquals(userRecord, userTransactionGatewayMessage.getUserRecord());
    }

    @Test
    public void testGetUserTransactionGatewayMessageOnFail() {
        UserTransactionGatewayMessage userTransactionGatewayMessage = userRecordManagementService.getUserTransactionGatewayMessageOnFail(userRecord, UserTransactionTypeE.RegisterNew, "Validation fail");
        Assert.assertEquals(MessageTxnStatusE.Fail, userTransactionGatewayMessage.getMessageTxnStatusE());
        Assert.assertEquals(UserTransactionTypeE.RegisterNew, userTransactionGatewayMessage.getUserTransactionTypeE());
        Assert.assertEquals("Validation fail", userTransactionGatewayMessage.getTxnStatusMessage().get());
        Assert.assertEquals(UserTransactionTypeE.RegisterNew, userTransactionGatewayMessage.getUserTransactionTypeE());
        Assert.assertEquals(userRecord, userTransactionGatewayMessage.getUserRecord());
    }


    @Test
    public void testRegisterUserRecordSuccessfull() {
        Mockito.when(iOneWayClearTextEncryption.encryptClearText(Mockito.any())).thenReturn("$Hllkskskdll848433");
        UserTransactionGatewayMessage userTransactionGatewayMessage = userRecordManagementService.registerUserRecord(userRecord);
        Assert.assertEquals(MessageTxnStatusE.Success, userTransactionGatewayMessage.getMessageTxnStatusE());
        Assert.assertEquals(UserTransactionTypeE.RegisterNew, userTransactionGatewayMessage.getUserTransactionTypeE());
        Assert.assertEquals(userRecord, userTransactionGatewayMessage.getUserRecord());
    }

    @Test
    public void testRegisterUserRecordFail() {
        Mockito.when(iUserRecordRepository.findByUserEmailAndMobilePhone(Mockito.any(), Mockito.any())).thenReturn(userRecord);
        Mockito.when(iOneWayClearTextEncryption.encryptClearText(Mockito.any())).thenReturn("$Hllkskskdll848433");
        UserTransactionGatewayMessage userTransactionGatewayMessage = userRecordManagementService.registerUserRecord(userRecord);
        Assert.assertEquals(MessageTxnStatusE.Fail, userTransactionGatewayMessage.getMessageTxnStatusE());
        Assert.assertEquals(UserTransactionTypeE.RegisterNew, userTransactionGatewayMessage.getUserTransactionTypeE());
        Assert.assertEquals(userRecord, userTransactionGatewayMessage.getUserRecord());
    }

}
