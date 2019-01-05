package xyz.alexakarpov.grpc.services.math;

import io.grpc.stub.StreamObserver;
import xyz.alexakarpov.proto.demo.AddRequest;
import xyz.alexakarpov.proto.demo.AddResponse;
import xyz.alexakarpov.proto.demo.Addition;
import xyz.alexakarpov.proto.demo.MathServiceGrpc;

import static xyz.alexakarpov.grpc.utils.Utils.timeStr;

public class MathServiceImpl extends MathServiceGrpc.MathServiceImplBase {

    @Override
    public void add(AddRequest request, StreamObserver<AddResponse> observer) {
        System.out.println(String.format("Server got remote call at %s:\n%s",
                timeStr(),
                request.toString()));
        Addition add = request.getAddition();
        int first = add.getFirstNum();
        int second = add.getSecondNum();
        int result = first + second;
        AddResponse response = AddResponse.newBuilder()
                .setResult(result)
                .build();
        observer.onNext(response);
        System.out.println(String.format("Sent the response:\n%s\nat %s",
                response.toString(),
                timeStr()));
        observer.onCompleted();
    }
}
