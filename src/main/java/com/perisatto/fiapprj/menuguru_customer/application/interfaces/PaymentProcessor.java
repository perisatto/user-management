package com.perisatto.fiapprj.menuguru_customer.application.interfaces;

import com.perisatto.fiapprj.menuguru_customer.domain.entities.payment.Payment;

public interface PaymentProcessor {
	Payment createPayment(Payment payment) throws Exception;
}
