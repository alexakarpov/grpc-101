package xyz.alexakarpov.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import xyz.alexakarpov.proto.greet.*;

public class GreetingClient {
    public static void main(String[] args) {
        ManagedChannel chan = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        System.out.println("Creating stubs");
        GreetServiceGrpc.GreetServiceBlockingStub syncGreetClient = GreetServiceGrpc.newBlockingStub(chan);

        doMessageDance(syncGreetClient, "Alex", "Foo");
        doMessageDance(syncGreetClient, "Bill", "Bar");
        doMessageDance(syncGreetClient, "Karl", "Baz");

        System.out.println("Shutting channel down");
        chan.shutdown();
    }

    private static void doMessageDance(GreetServiceGrpc.GreetServiceBlockingStub syncGreetClient,
                                       String firstName,
                                       String lastName) {
        Greeting g;
        GreetRequest r;
        GreetResponse response;
        System.out.println("Building a Greeting");
        g = Greeting.newBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .build();

        System.out.println("Building a GreetRequest");
        r = GreetRequest.newBuilder()
                .setGreeting(g)
                .build();

        System.out.println("Sending the RPC");
        response = syncGreetClient.greet(r);

        System.out.println(String.format("Received %s", response.getResult()));
    }
}
