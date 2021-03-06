package xyz.alexakarpov.grpc.services.greet;

import io.grpc.stub.StreamObserver;
import xyz.alexakarpov.proto.demo.*;

import static xyz.alexakarpov.grpc.utils.Utils.timeStr;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {

    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        //super.demo(request, responseObserver);
        System.out.println(String.format("Server in thread %d got remote request at %s:\n%s",
                Thread.currentThread().getId(),
                timeStr(),
                request.toString()));
        // extract fields
        Greeting greeting = request.getGreeting();
        String firstName = greeting.getFirstName();

        // create the response
        String result = "Hello " + firstName;
        GreetResponse response = GreetResponse.newBuilder()
                .setResult(result)
                .build();

        // send the response
        responseObserver.onNext(response);

        System.out.println(String.format("Sent the response:\n%s\nat %s",
                response.toString(),
                timeStr()));
        // complete rpc
        responseObserver.onCompleted();;
    }

    @Override
    public void greetManyTimes(GreetManyTimesRequest request, StreamObserver<GreetManyTimesResponse> responseObserver) {
        String firstName = request.getGreeting().getFirstName();

        try {
            for (int i = 0; i < 10; i++) {
                String result = String.format("Hello %d at %s", i, timeStr());
                GreetManyTimesResponse response = GreetManyTimesResponse.newBuilder()
                        .setResult(result)
                        .build();
                responseObserver.onNext(response);
                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            responseObserver.onCompleted();
        }
    }
}
