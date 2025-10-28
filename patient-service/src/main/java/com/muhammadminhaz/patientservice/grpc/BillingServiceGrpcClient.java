package com.muhammadminhaz.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;


@Service
public class BillingServiceGrpcClient {
    private final BillingServiceGrpc.BillingServiceBlockingStub stub;
    private static final Logger logger =  Logger.getLogger(BillingServiceGrpcClient.class.getName());

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort
            ) {
        logger.info("Initializing BillingServiceGrpcClient at {}:{}" + serverAddress + serverPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort).usePlaintext().build();
        stub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBilling(String patientId, String name, String email) {
        BillingRequest request = BillingRequest.newBuilder().setPatientId(patientId).setName(name).setEmail(email).build();
        BillingResponse response = stub.createBillingAccount(request);
        logger.info("Received {}\n" + response);

        return response;
    }
}
