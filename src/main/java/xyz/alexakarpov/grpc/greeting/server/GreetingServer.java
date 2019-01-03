package xyz.alexakarpov.grpc.greeting.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GreetingServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(String.format("Hello from %d, gRPC",
                Thread.currentThread().getId()));
        Server server = ServerBuilder.forPort(50051)
                .build();
        System.out.println(String.format("Starting the server at %d", Thread.currentThread().getId()));
        server.start();
        System.out.println(String.format("Will this print - at %d?", Thread.currentThread().getId()));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
             System.out.println(String.format("Received shutdown request in %d",
                     Thread.currentThread().getId()));
            server.shutdown();
            System.out.println(String.format("We're all done here in %d", Thread.currentThread().getId()));
        }));

        System.out.println(String.format("How about this? - at %d", Thread.currentThread().getId()));
        server.awaitTermination();
        System.out.println("But surely not this!");
    }
}
