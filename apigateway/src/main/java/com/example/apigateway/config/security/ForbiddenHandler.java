package com.example.apigateway.config.security;

import com.example.core.utils.http.errors.ServiceError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ForbiddenHandler implements ServerAccessDeniedHandler {

    private static final String FORBIDDEN_ERROR_CODE = "forbidden";
    private static final String FORBIDDEN_ERROR_MESSAGE = "Forbidden";
    private final HttpStatus httpStatus = HttpStatus.FORBIDDEN;

    @Override
    public Mono<Void> handle(final ServerWebExchange exchange, final AccessDeniedException e) {
        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        final ServiceError serviceError = new ServiceError(FORBIDDEN_ERROR_CODE, FORBIDDEN_ERROR_MESSAGE);
        try {
            return exchange.getResponse().writeWith(Flux.just(buildDataBuffer(exchange, serviceError)));
        } catch (JsonProcessingException e1) {
            return exchange.getResponse().writeWith(Flux.empty());
        }
    }

    private DataBuffer buildDataBuffer(final ServerWebExchange exchange, final ServiceError serviceError) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        final String jsonResponse = mapper.writeValueAsString(serviceError);
        return exchange.getResponse().bufferFactory().wrap(jsonResponse.getBytes());
    }

}
