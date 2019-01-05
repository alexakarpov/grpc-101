package xyz.alexakarpov.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import xyz.alexakarpov.proto.demo.*;

import java.util.Iterator;

import static xyz.alexakarpov.grpc.utils.Utils.timeStr;

public class AllClient {
    public static void main(String[] args) {
        System.out.println(String.format("Client execustion started at %s",
                timeStr()));
        ManagedChannel chan = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        GreetServiceGrpc.GreetServiceBlockingStub syncGreetClient = GreetServiceGrpc.newBlockingStub(chan);
        MathServiceGrpc.MathServiceBlockingStub syncMathClient = MathServiceGrpc.newBlockingStub(chan);

        // unary stuff
        doGreetingDance(syncGreetClient, "Alex", "Foo");
        doGreetingDance(syncGreetClient, "Bill", "Bar");
        doGreetingDance(syncGreetClient, "Karl", "Baz");

        System.out.println("Now let's do some addition");
        doAddDance(syncMathClient, 21, 21);
        System.out.println("Shutting channel down");

        // Server streaming
        GreetManyTimesRequest req = GreetManyTimesRequest.newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("Alejandro").build())
                .build();

        Iterator<GreetManyTimesResponse> gmtrIterator = syncGreetClient.greetManyTimes(req);
        gmtrIterator.forEachRemaining(greetManyTimesResponse -> {
            System.out.println(String.format("Got a response of %s at %s",
                    greetManyTimesResponse.getResult(),
                    timeStr()));
        });

        chan.shutdown();
    }

    private static void doGreetingDance(GreetServiceGrpc.GreetServiceBlockingStub syncGreetClient,
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

    private static void doAddDance(MathServiceGrpc.MathServiceBlockingStub syncMathClient,
                                        int firstNum,
                                        int secondNum) {
        Addition a;
        AddRequest r;
        AddResponse response;
        a = Addition.newBuilder()
                .setFirstNum(firstNum)
                .setSecondNum(secondNum)
                .build();

        r = AddRequest.newBuilder()
                .setAddition(a)
                .build();

        System.out.println(String.format("Sending the RPC at %s with request:\n%s",
                timeStr(),
                r.toString()));
        response = syncMathClient.add(r);

        System.out.println(String.format("Received response at %s\n%s",
                timeStr(),
                response.getResult()));
    }

}
