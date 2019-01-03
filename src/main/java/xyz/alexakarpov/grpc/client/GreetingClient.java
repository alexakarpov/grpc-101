package xyz.alexakarpov.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import xyz.alexakarpov.proto.greet.*;

import static xyz.alexakarpov.grpc.utils.Utils.timeStr;

public class GreetingClient {
    public static void main(String[] args) {
        System.out.println(String.format("Client execustion started at %s",
                timeStr()));
        ManagedChannel chan = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

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
        g = Greeting.newBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .build();

        r = GreetRequest.newBuilder()
                .setGreeting(g)
                .build();

        System.out.println(String.format("Sending the RPC at %s with request:\n%s",
                timeStr(),
                r.toString()));
        response = syncGreetClient.greet(r);

        System.out.println(String.format("Received response at %s\n%s",
                timeStr(),
                response.getResult()));
    }
}
