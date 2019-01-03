package xyz.alexakarpov.grpc.greeting.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

import static xyz.alexakarpov.grpc.utils.Utils.timeStr;

public class GreetingServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(String.format("Going to start the server from thread %d at %s",
                Thread.currentThread().getId(),
                timeStr()));
        Server server = ServerBuilder.forPort(50051)
                .addService(new GreetServiceImpl())
                .build();
        server.start();
        System.out.println(String.format("Server started at %s", timeStr()));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println(String.format("Received shutdown request in %d at %s",
                    Thread.currentThread().getId(),
                    timeStr()));
            server.shutdown();
        }));

        System.out.println(String.format("Hook add on %d at %s",
                Thread.currentThread().getId(),
                timeStr()));
        server.awaitTermination();
        System.out.println("This will never print.");
    }
}
