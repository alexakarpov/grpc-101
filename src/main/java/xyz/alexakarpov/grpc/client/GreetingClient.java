package xyz.alexakarpov.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {
    public static void main(String[] args) {
        System.out.println("Hi from client");
        ManagedChannel chan = ManagedChannelBuilder
                .forAddress("localhost", 50051)
//                .usePlaintext()
                .build();

        System.out.println("Creating stubs");

        // do something

        System.out.println("Shutting channel down");
        chan.shutdown();
    }
}
