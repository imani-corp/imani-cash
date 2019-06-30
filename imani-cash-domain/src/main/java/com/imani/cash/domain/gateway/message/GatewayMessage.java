package com.imani.cash.domain.gateway.message;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author manyce400
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GatewayMessage {



    protected MessageTxnStatusE messageTxnStatusE;


    public GatewayMessage() {

    }

    public MessageTxnStatusE getMessageTxnStatusE() {
        return messageTxnStatusE;
    }

    public void setMessageTxnStatusE(MessageTxnStatusE messageTxnStatusE) {
        this.messageTxnStatusE = messageTxnStatusE;
    }


}
