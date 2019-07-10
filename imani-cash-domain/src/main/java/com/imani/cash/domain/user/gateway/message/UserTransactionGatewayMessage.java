package com.imani.cash.domain.user.gateway.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.imani.cash.domain.contact.EmbeddedContactInfo;
import com.imani.cash.domain.device.DeviceTypeE;
import com.imani.cash.domain.gateway.message.GatewayMessage;
import com.imani.cash.domain.gateway.message.MessageTxnStatusE;
import com.imani.cash.domain.user.UserLoginStatistic;
import com.imani.cash.domain.user.UserRecord;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Optional;

/**
 * @author manyce400
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTransactionGatewayMessage extends GatewayMessage {



    private UserTransactionTypeE userTransactionTypeE;

    private UserRecord userRecord;

    private Optional<UserLoginStatistic> userLoginStatistic;


    public UserTransactionGatewayMessage() {

    }


    public UserTransactionTypeE getUserTransactionTypeE() {
        return userTransactionTypeE;
    }

    public void setUserTransactionTypeE(UserTransactionTypeE userTransactionTypeE) {
        this.userTransactionTypeE = userTransactionTypeE;
    }

    public UserRecord getUserRecord() {
        return userRecord;
    }

    public void setUserRecord(UserRecord userRecord) {
        this.userRecord = userRecord;
    }

    public Optional<UserLoginStatistic> getUserLoginStatistic() {
        return userLoginStatistic;
    }

    public void setUserLoginStatistic(Optional<UserLoginStatistic> userLoginStatistic) {
        this.userLoginStatistic = userLoginStatistic;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userTransactionTypeE", userTransactionTypeE)
                .append("userRecord", userRecord)
                .append("userLoginStatistic", userLoginStatistic)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private UserTransactionGatewayMessage userTransactionGatewayMessage = new UserTransactionGatewayMessage();

        public Builder messageTxnStatusE(MessageTxnStatusE messageTxnStatusE) {
            userTransactionGatewayMessage.messageTxnStatusE = messageTxnStatusE;
            return this;
        }

        public Builder userTransactionTypeE(UserTransactionTypeE userTransactionTypeE) {
            userTransactionGatewayMessage.userTransactionTypeE = userTransactionTypeE;
            return this;
        }

        public Builder userRecord(UserRecord userRecord) {
            userTransactionGatewayMessage.userRecord = userRecord;
            return this;
        }

        public Builder userLoginStatistic(UserLoginStatistic userLoginStatistic) {
            userTransactionGatewayMessage.userLoginStatistic = userLoginStatistic != null ? Optional.of(userLoginStatistic) : Optional.empty();
            return this;
        }

        public UserTransactionGatewayMessage build() {
            return userTransactionGatewayMessage;
        }
    }


    public static void main(String[] args) {
        EmbeddedContactInfo embeddedContactInfo = EmbeddedContactInfo.builder()
                .email("testuser@imanicash.com")
                .build();

        UserRecord  userRecord = UserRecord.builder()
                .password("JohnSnow200#")
                .embeddedContactInfo(embeddedContactInfo)
                .build();



        UserLoginStatistic userLoginStatistic = UserLoginStatistic.builder()
                .deviceOS("IOS")
                .deviceTypeE(DeviceTypeE.IPhone)
                .deviceVersion("IPhone X")
                .iManiClientVersion("IMani IOS 2.0987")
                .build();

        UserTransactionGatewayMessage userTransactionGatewayMessage = UserTransactionGatewayMessage.builder()
                .userTransactionTypeE(UserTransactionTypeE.Login)
                .userRecord(userRecord)
                .userLoginStatistic(userLoginStatistic)
                .build();

        // Setup Jackson Mapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        try {
            String value = mapper.writeValueAsString(userTransactionGatewayMessage);
            System.out.println("value = " + value);

            UserTransactionGatewayMessage s = mapper.readValue(value, UserTransactionGatewayMessage.class);
            System.out.println("s = " + s);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}