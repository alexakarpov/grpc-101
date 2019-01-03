package xyz.alexakarpov.grpc.greeting.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GreetingServer {
    public static long time() {
        return System.currentTimeMillis();
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(String.format("Hello from %d at %d",
                Thread.currentThread().getId(),
                time()));
        Server server = ServerBuilder.forPort(50051)
                .addService(new GreetServiceImpl())
                .build();
        System.out.println(String.format("Starting the server on %d at %d",
                Thread.currentThread().getId(),
                time()));
        server.start();
        System.out.println(String.format("Will this print - on %d at %d?", Thread.currentThread().getId(),
                time()));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
             System.out.println(String.format("Received shutdown request in %d at %d",
                     Thread.currentThread().getId(),
                     time()));
            server.shutdown();
            System.out.println(String.format("We're all done here in %d at %d",
                    Thread.currentThread().getId(),
                    time()));
        }));

        System.out.println(String.format("How about this? - at %d", Thread.currentThread().getId()));
        server.awaitTermination();
        System.out.println("But surely not this!");
    }
}
