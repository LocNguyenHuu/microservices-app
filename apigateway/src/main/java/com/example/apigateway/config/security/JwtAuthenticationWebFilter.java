package com.example.apigateway.config.security;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public JwtAuthenticationWebFilter(final ReactiveAuthenticationManager authenticationManager,
                                      final JwtAuthenticationConverter converter,
                                      final UnauthorizedAuthenticationEntryPoint entryPoint) {

        super(authenticationManager);

        setAuthenticationConverter(converter);
        setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(entryPoint));
        setRequiresAuthenticationMatcher(new JWTHeadersExchangeMatcher());
    }

    private static class JWTHeadersExchangeMatcher implements ServerWebExchangeMatcher {
        @Override
        public Mono<MatchResult> matches(final ServerWebExchange exchange) {
            Mono<ServerHttpRequest> request = Mono.just(exchange).map(ServerWebExchange::getRequest);
            return request.map(ServerHttpRequest::getHeaders)
                    .filter(h -> h.containsKey(AUTHORIZATION_HEADER))
                    .flatMap($ -> MatchResult.match())
                    .switchIfEmpty(MatchResult.notMatch());
        }
    }

}