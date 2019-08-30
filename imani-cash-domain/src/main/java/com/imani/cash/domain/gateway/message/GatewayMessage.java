package com.imani.cash.domain.gateway.message;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Optional;

/**
 * @author manyce400
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GatewayMessage {



    protected MessageTxnStatusE messageTxnStatusE;

    protected Optional<String> txnStatusMessage;


    public GatewayMessage() {

    }

    public MessageTxnStatusE getMessageTxnStatusE() {
        return messageTxnStatusE;
    }

    public void setMessageTxnStatusE(MessageTxnStatusE messageTxnStatusE) {
        this.messageTxnStatusE = messageTxnStatusE;
    }

    public Optional<String> getTxnStatusMessage() {
        return txnStatusMessage;
    }

    public void setTxnStatusMessage(Optional<String> txnStatusMessage) {
        this.txnStatusMessage = txnStatusMessage;
    }
}
