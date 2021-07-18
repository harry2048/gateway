package com.baidu.gateway.filter;

import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.mock.web.server.MockWebSession;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.Principal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntPredicate;

class AddSignatureGatewayFilterFactory2Test {
    private AddSignatureGatewayFilterFactory2 factory = new AddSignatureGatewayFilterFactory2();
    @Test
    void apply() {
        AddSignatureGatewayFilterFactory2.FromMeGatewayFilter apply =
                (AddSignatureGatewayFilterFactory2.FromMeGatewayFilter)factory.apply(new Object());
        ServerWebExchange exchange = new DemoExchange();
        GatewayFilterChain chain = exchange1 -> Mono.empty();
        apply.getOrder();
        apply.filter(exchange, chain);
        DemoReq2 req = new DemoReq2(exchange.getRequest());
        Flux<DataBuffer> body = req.getBody();

        DemoRes res = new DemoRes(exchange.getResponse());
        res.writeWith(body);
    }
}
class DemoExchange implements ServerWebExchange{

    @Override
    public ServerHttpRequest getRequest() {
        ServerHttpRequest req = new ServerHttpRequest() {
            @Override
            public String getId() {
                return "2";
            }

            @Override
            public RequestPath getPath() {
                return new RequestPath() {
                    @Override
                    public PathContainer contextPath() {
                        return null;
                    }

                    @Override
                    public PathContainer pathWithinApplication() {
                        return null;
                    }

                    @Override
                    public RequestPath modifyContextPath(String contextPath) {
                        return null;
                    }

                    @Override
                    public String value() {
                        return null;
                    }

                    @Override
                    public List<Element> elements() {
                        return null;
                    }
                };
            }

            @Override
            public MultiValueMap<String, String> getQueryParams() {
                return null;
            }

            @Override
            public MultiValueMap<String, HttpCookie> getCookies() {
                return new LinkedMultiValueMap<>();
            }

            @Override
            public String getMethodValue() {
                return null;
            }

            @Override
            public URI getURI() {
                URI uri = null;
                try {
                    uri = new URI("www.baidu.com");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return uri;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                DataBuffer da = new DataBuffer() {
                    @Override
                    public DataBufferFactory factory() {
                        return null;
                    }

                    @Override
                    public int indexOf(IntPredicate predicate, int fromIndex) {
                        return 0;
                    }

                    @Override
                    public int lastIndexOf(IntPredicate predicate, int fromIndex) {
                        return 0;
                    }

                    @Override
                    public int readableByteCount() {
                        return 10;
                    }

                    @Override
                    public int writableByteCount() {
                        return 0;
                    }

                    @Override
                    public int capacity() {
                        return 0;
                    }

                    @Override
                    public DataBuffer capacity(int capacity) {
                        return null;
                    }

                    @Override
                    public int readPosition() {
                        return 0;
                    }

                    @Override
                    public DataBuffer readPosition(int readPosition) {
                        return null;
                    }

                    @Override
                    public int writePosition() {
                        return 0;
                    }

                    @Override
                    public DataBuffer writePosition(int writePosition) {
                        return null;
                    }

                    @Override
                    public byte getByte(int index) {
                        return 0;
                    }

                    @Override
                    public byte read() {
                        return 0;
                    }

                    @Override
                    public DataBuffer read(byte[] destination) {
                        return null;
                    }

                    @Override
                    public DataBuffer read(byte[] destination, int offset, int length) {
                        return null;
                    }

                    @Override
                    public DataBuffer write(byte b) {
                        return null;
                    }

                    @Override
                    public DataBuffer write(byte[] source) {
                        return null;
                    }

                    @Override
                    public DataBuffer write(byte[] source, int offset, int length) {
                        return null;
                    }

                    @Override
                    public DataBuffer write(DataBuffer... buffers) {
                        return null;
                    }

                    @Override
                    public DataBuffer write(ByteBuffer... buffers) {
                        return null;
                    }

                    @Override
                    public DataBuffer slice(int index, int length) {
                        return null;
                    }

                    @Override
                    public ByteBuffer asByteBuffer() {
                        return null;
                    }

                    @Override
                    public ByteBuffer asByteBuffer(int index, int length) {
                        return null;
                    }

                    @Override
                    public InputStream asInputStream() {
                        return null;
                    }

                    @Override
                    public InputStream asInputStream(boolean releaseOnClose) {
                        return null;
                    }

                    @Override
                    public OutputStream asOutputStream() {
                        return null;
                    }

                    @Override
                    public String toString(int index, int length, Charset charset) {
                        return null;
                    }
                };
                return Flux.just(da);
            }

            @Override
            public HttpHeaders getHeaders() {
                return new HttpHeaders();
            }
        };
        return req;
    }

    @Override
    public ServerHttpResponse getResponse() {
        return new MockServerHttpResponse();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>();
    }

    @Override
    public Mono<WebSession> getSession() {
        WebSession se = new MockWebSession();
        return Mono.just(se);
    }

    @Override
    public <T extends Principal> Mono<T> getPrincipal() {
        return null;
    }

    @Override
    public Mono<MultiValueMap<String, String>> getFormData() {
        return null;
    }

    @Override
    public Mono<MultiValueMap<String, Part>> getMultipartData() {
        return null;
    }

    @Override
    public LocaleContext getLocaleContext() {
        return null;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return new ClassPathXmlApplicationContext();
    }

    @Override
    public boolean isNotModified() {
        return false;
    }

    @Override
    public boolean checkNotModified(Instant lastModified) {
        return false;
    }

    @Override
    public boolean checkNotModified(String etag) {
        return false;
    }

    @Override
    public boolean checkNotModified(String etag, Instant lastModified) {
        return false;
    }

    @Override
    public String transformUrl(String url) {
        return null;
    }

    @Override
    public void addUrlTransformer(Function<String, String> transformer) {

    }

    @Override
    public String getLogPrefix() {
        return null;
    }
}