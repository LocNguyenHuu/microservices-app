package com.example.core.utils.http.component;

import com.example.core.utils.http.errors.InternalErrorException;
import com.example.core.utils.http.model.HttpRequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

@Component
public class HttpClient {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public HttpClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    public <T> Mono<T> get(final HttpRequestInfo httpRequestInfo, final Class<T> responseType) {
        WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec = webClientBuilder.build().get();
        return executeWithoutBody(requestHeadersUriSpec, httpRequestInfo, responseType);
    }

    public <T> Mono<T> post(final HttpRequestInfo httpRequestInfo, final Class<T> responseType) {
        WebClient.RequestBodyUriSpec requestBodyUriSpec = webClientBuilder.build().post();
        return executeWithBody(requestBodyUriSpec, httpRequestInfo, responseType);
    }

    public <T> Mono<T> put(final HttpRequestInfo httpRequestInfo, final Class<T> responseType) {
        WebClient.RequestBodyUriSpec requestHeadersUriSpec = webClientBuilder.build().put();
        return executeWithBody(requestHeadersUriSpec, httpRequestInfo, responseType);
    }

    public <T> Mono<T> patch(final HttpRequestInfo httpRequestInfo, final Class<T> responseType) {
        WebClient.RequestBodyUriSpec requestHeadersUriSpec = webClientBuilder.build().patch();
        return executeWithBody(requestHeadersUriSpec, httpRequestInfo, responseType);
    }

    public <T> Mono<T> delete(final HttpRequestInfo httpRequestInfo, final Class<T> responseType) {
        WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec = webClientBuilder.build().delete();
        return executeWithoutBody(requestHeadersUriSpec, httpRequestInfo, responseType);
    }

    private <T> Mono<T> executeWithoutBody(final WebClient.RequestHeadersUriSpec<?> webClient, final HttpRequestInfo httpRequestInfo, final Class<T> responseType) {
        try {
            return webClient
                    .uri(buildUriFunction(httpRequestInfo))
                    .retrieve().bodyToMono(responseType);
        } catch (Exception exception) {
            return buildInternalErrorException();
        }
    }

    private <T> Mono<T> executeWithBody(final WebClient.RequestBodyUriSpec webClient, final HttpRequestInfo httpRequestInfo, final Class<T> responseType) {
        try {
            return webClient.uri(buildUriFunction(httpRequestInfo)).body(BodyInserters.fromObject(httpRequestInfo.getBody())).retrieve().bodyToMono(responseType);
        } catch (Exception exception) {
            return buildInternalErrorException();
        }
    }

    private Function<UriBuilder, URI> buildUriFunction(final HttpRequestInfo httpRequestInfo) {
        return uriBuilder ->
                uriBuilder
                        .scheme(httpRequestInfo.getProtocol())
                        .host(httpRequestInfo.getHost())
                        .path(httpRequestInfo.getPath())
                        .build();
    }

    private <T> Mono<T> buildInternalErrorException() {
        return Mono.error(new InternalErrorException());
    }

}
