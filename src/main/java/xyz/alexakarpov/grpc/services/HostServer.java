package xyz.alexakarpov.grpc.services;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import xyz.alexakarpov.grpc.services.greet.GreetServiceImpl;
import xyz.alexakarpov.grpc.services.math.MathServiceImpl;

import java.io.IOException;

import static xyz.alexakarpov.grpc.utils.Utils.timeStr;

public class HostServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(String.format("Going to start the services from thread %d at %s",
                Thread.currentThread().getId(),
                timeStr()));
        Server server = ServerBuilder.forPort(50051)
                .addService(new GreetServiceImpl())
                .addService(new MathServiceImpl())
                .build();
        server.start();
        System.out.println(String.format("Server started at %s", timeStr()));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println(String.format("Received shutdown request in %d at %s",
                    Thread.currentThread().getId(),
                    timeStr()));
            server.shutdown();
        }));

        System.out.println(String.format("Hook added on %d at %s",
                Thread.currentThread().getId(),
                timeStr()));
        server.awaitTermination();
        System.out.println("This will never print.");
    }
}
