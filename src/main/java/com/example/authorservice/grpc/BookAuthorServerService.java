package com.example.authorservice.grpc;

import com.example.Author;
import com.example.BookAuthorServiceGrpc;
import com.example.authorservice.AuthorRepository;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@GrpcService
@RequiredArgsConstructor
public class BookAuthorServerService extends BookAuthorServiceGrpc.BookAuthorServiceImplBase {
    private final AuthorRepository repository;

    @Override
    public void getAuthor(Author request, StreamObserver<Author> responseObserver) {
        Optional<com.example.authorservice.Author> author = repository.findById(request.getId());
        if (author.isPresent()) {
            responseObserver.onNext(Author.newBuilder()
                    .setId(author.get().getId())
                    .setName(author.get().getName())
                    .setEmail(author.get().getEmail())
                    .setBio(author.get().getBio())
                    .build());
        } else {
            responseObserver.onError(new EntityNotFoundException());
        }
        responseObserver.onCompleted();
    }
}
