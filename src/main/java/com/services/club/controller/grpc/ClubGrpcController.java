package com.services.club.controller.grpc;

import com.services.club.entity.Club;
import com.services.club.service.ClubService;
import com.services.grpc.server.club.*;
import io.grpc.stub.StreamObserver;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import javax.management.InstanceAlreadyExistsException;
import java.util.UUID;

@GrpcService
@AllArgsConstructor
public class ClubGrpcController extends ClubServiceGrpc.ClubServiceImplBase {

    private final ClubService clubService;

    @Override
    public void getClub(Empty request, StreamObserver<ClubResponse> responseObserver) {
        try {
            Club club = clubService.getAccount();
            ProtoClub protoClub = clubToProto(club);
            ClubResponse response = ClubResponse.newBuilder()
                    .setClub(protoClub).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (NotFoundException e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void addClub(CreateClubRequest request, StreamObserver<ClubResponse> responseObserver) {
        int seeCapital = request.getSeedCapital();
        Club club = new Club(seeCapital);
        try {
            clubService.addClubAccount(club);
            ProtoClub protoClub = clubToProto(club);
            ClubResponse response = ClubResponse.newBuilder()
                    .setClub(protoClub).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (InstanceAlreadyExistsException e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void spendMoney(MoneyRequest request, StreamObserver<StringResponse> responseObserver) {
        int moneyAmount = request.getMoneyAmount();
        try {
            clubService.spendMoney(moneyAmount);
            StringResponse response = StringResponse.newBuilder()
                    .setText(String.format("Club spent %s $",moneyAmount)).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (NotFoundException e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void earnMoney(MoneyRequest request, StreamObserver<StringResponse> responseObserver) {
        int moneyAmount = request.getMoneyAmount();
        try {
            clubService.earnMoney(moneyAmount);
            StringResponse response = StringResponse.newBuilder()
                    .setText(String.format("Club earned %s $",moneyAmount)).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (NotFoundException e){
            responseObserver.onError(e);
        }
    }

    private ProtoClub clubToProto(Club club){
        UUID clubId = club.getId();
        String id = clubId.toString();
        ProtoClub protoClub = ProtoClub.newBuilder()
                .setId(id)
                .setMoneyAmount(club.getMoneyAmount())
                .setSeedCapital(club.getSeedCapital())
                .build();

        return protoClub;
    }
}
