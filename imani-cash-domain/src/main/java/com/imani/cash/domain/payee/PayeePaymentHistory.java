package com.imani.cash.domain.payee;

import com.imani.cash.domain.payment.ACHPaymentInfo;

import javax.persistence.*;

/**
 * Historical record of payments that have been made to a specific Payee.
 *
 * @author manyce400
 */
@Entity
@Table(name="PayeePaymentHistory")
public class PayeePaymentHistory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    // ACH Account that was debited with this payment transaction
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="routingNumber", column = @Column(name="ACHDebitRoutingNumber") ),
            @AttributeOverride(name="bankAcctNumber", column = @Column(name="ACHDebitBankAcctNumber")),
            @AttributeOverride(name="financialInstitution", column = @Column(name="ACHDebitFinancialInstitution")),
            @AttributeOverride(name="paymentGatewayAcctID", column = @Column(name="ACHDebitPaymentGatewayAcctID"))
    } )
    private ACHPaymentInfo achDebitPaymentInfo;

    // ACH Account that was credited with this payment transaction
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="routingNumber", column = @Column(name="ACHCreditRoutingNumber") ),
            @AttributeOverride(name="bankAcctNumber", column = @Column(name="ACHDebitCreditAcctNumber")),
            @AttributeOverride(name="financialInstitution", column = @Column(name="ACHCreditFinancialInstitution")),
            @AttributeOverride(name="paymentGatewayAcctID", column = @Column(name="ACHCreditPaymentGatewayAcctID"))
    } )
    private ACHPaymentInfo achCreditPaymentInfo;

    /**
     * PayeeRecord associated with this payment.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PayeeRecordID", nullable = true)
    private PayeeRecord payeeRecord;
}
