package com.example.authorservice.grpc;


import com.example.authorservice.AuthorRepository;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import tech.utkorsho.grpc.Author;
import tech.utkorsho.grpc.BookAuthorServiceGrpc;

import java.util.Optional;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class BookAuthorServerService extends BookAuthorServiceGrpc.BookAuthorServiceImplBase {
    private final AuthorRepository repository;

    @Override
    public void getAuthor(Author request, StreamObserver<Author> responseObserver) {
        log.info("Received request to get author with id: {}", request.getId());
        try {
            Optional<com.example.authorservice.Author> author = repository.findById(request.getId());
            if (author.isPresent()) {
                log.info("Author found");
                responseObserver.onNext(Author.newBuilder().setId(author.get().getId()).setName(author.get().getName()).setEmail(author.get().getEmail()).setBio(author.get().getBio()).build());
                responseObserver.onCompleted();
            } else {
                log.error("Author not found");
                responseObserver.onError(new EntityNotFoundException("Author not found"));
            }
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
