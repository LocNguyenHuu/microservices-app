package com.example.apigateway.config.security;

import com.example.core.utils.http.errors.ServiceError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UnauthorizedAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private static final String UNAUTHORIZED_ERROR_CODE = "unauthorized";
    private static final String UNAUTHORIZED_ERROR_MESSAGE = "Unauthorized";
    private final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    @Override
    public Mono<Void> commence(final ServerWebExchange exchange, final AuthenticationException e) {
        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        final ServiceError serviceError = new ServiceError(UNAUTHORIZED_ERROR_CODE, UNAUTHORIZED_ERROR_MESSAGE);

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
