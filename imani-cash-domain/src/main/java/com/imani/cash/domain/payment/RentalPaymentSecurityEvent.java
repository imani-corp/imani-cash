package com.imani.cash.domain.payment;

import javax.persistence.*;

/**
 * @author manyce400
 */
public class RentalPaymentSecurityEvent {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    // Tracks the correlating entry made into the RentalPaymentHistory model.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RentalPaymentHistoryID", nullable = false)
    private RentalPaymentHistory rentalPaymentHistory;



}
