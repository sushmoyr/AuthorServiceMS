package com.example.authorservice.grpc;

import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {
    @Bean
    public GrpcServerConfigurer customConfig() {
        return serverBuilder -> {
            serverBuilder.maxInboundMessageSize(1024 * 1024);
            serverBuilder.maxInboundMetadataSize(1024 * 1024);
        };
    }
}

