package com.hung.sneakery.service.impl;

import com.hung.sneakery.dto.request.PaymentRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.Bid;
import com.hung.sneakery.entity.Transaction;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.enums.EPaymentType;
import com.hung.sneakery.repository.BidRepository;
import com.hung.sneakery.repository.TransactionRepository;
import com.hung.sneakery.service.StripeService;
import com.hung.sneakery.utils.SneakeryConstant;
import com.hung.sneakery.utils.SneakeryUtil;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class StripeServiceImpl implements StripeService {

    @Resource
    private TransactionRepository transactionRepository;

    @Resource
    private BidRepository bidRepository;

    @Resource
    private SneakeryUtil sneakeryUtil;

    private static final String CLIENT_BASE_URL = System.getenv("CLIENT_BASE_URL");
    private static final String STRIPE_SECRET_KEY = System.getenv("STRIPE_SECRET_KEY");

    @Override
    @SneakyThrows
    public BaseResponse processPayment(final PaymentRequest paymentRequest) {
        Stripe.apiKey = STRIPE_SECRET_KEY; //NOSONAR
        User user = sneakeryUtil.getCurrentUser();

        Customer customer = findOrCreateCustomer(user.getEmail(), user.getUsername());

        Session session = createPaymentSession(paymentRequest, customer);

        return new BaseResponse(true, session.getUrl());
    }

    @Override
    @SneakyThrows
    public BaseResponse handleSuccessPayment(final String checkoutSessionId, final EPaymentType type, final Long productId) {
        Session session = Session.retrieve(checkoutSessionId);
        User user = sneakeryUtil.getCurrentUser();
        Bid bid = bidRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(SneakeryConstant.BID_NOT_FOUND));
        Transaction transaction = Transaction.builder()
                .amount(session.getAmountTotal() / 100)
                .type(type)
                .bid(bid)
                .user(user)
                .build();
        transactionRepository.save(transaction);
        return new BaseResponse(true, "Pay successfully");
    }

    private Customer findOrCreateCustomer(final String email, final String name) throws StripeException {
        CustomerCollection customers = searchCustomersByEmail(email);
        if (customers.getData().isEmpty()) {
            return createCustomer(name, email);
        } else {
            return customers.getData().get(0);
        }
    }

    private CustomerCollection searchCustomersByEmail(final String email) throws StripeException {
        CustomerListParams params = CustomerListParams.builder().setEmail(email).build();
        return Customer.list(params);
    }

    private Customer createCustomer(final String name, final String email) throws StripeException {
        CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                .setName(name)
                .setEmail(email)
                .build();
        return Customer.create(customerCreateParams);
    }

    private Session createPaymentSession(PaymentRequest paymentRequest, Customer customer) throws StripeException {
        SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCustomer(customer.getId())
                .setSuccessUrl(CLIENT_BASE_URL + "/success?sessionId={CHECKOUT_SESSION_ID}")
                .setCancelUrl(CLIENT_BASE_URL + "/cancel");

        paramsBuilder.addLineItem(
                SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .putMetadata("app_id", String.valueOf(1))
                                                        .setName(paymentRequest.getPurpose())
                                                        .build())
                                        .setCurrency("USD")
                                        .setUnitAmountDecimal(BigDecimal.valueOf(paymentRequest.getAmount() * 100))
                                        .build())
                        .build());

        return Session.create(paramsBuilder.build());
    }
}