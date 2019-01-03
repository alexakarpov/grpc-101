package xyz.alexakarpov.grpc.greeting.server;

import io.grpc.stub.StreamObserver;
import xyz.alexakarpov.proto.greet.GreetRequest;
import xyz.alexakarpov.proto.greet.GreetResponse;
import xyz.alexakarpov.proto.greet.GreetServiceGrpc;
import xyz.alexakarpov.proto.greet.Greeting;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {

    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        //super.greet(request, responseObserver);
        System.out.println(String.format("Got remote request \n%s\nat %d",
                request.toString(),
                System.currentTimeMillis()));
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

        System.out.println(String.format("Sent the response:\n%s\nat %d",
                response.toString(),
                System.currentTimeMillis()));
        // complete rpc
        responseObserver.onCompleted();;
    }
}
