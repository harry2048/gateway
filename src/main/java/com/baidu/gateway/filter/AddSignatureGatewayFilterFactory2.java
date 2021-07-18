package com.baidu.gateway.filter;

import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author gengwei  接出,验签，获取body，前提是数据为json
 */
@Component
public class AddSignatureGatewayFilterFactory2 extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        return new FromMeGatewayFilter();
    }

    class FromMeGatewayFilter implements GatewayFilter, Ordered {
        @Override
        public int getOrder() {
            return -2;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            // 重建response
            ServerHttpResponse responseDecorator = new DemoRes(exchange.getResponse());

            // 重建request
            ServerHttpRequest requestDecorator = new DemoReq2(exchange.getRequest());
            return chain.filter(exchange.mutate().request(requestDecorator).response(responseDecorator).build());
        }
    }
}
class DemoRes extends ServerHttpResponseDecorator{
    private final ServerHttpResponse delegate;
    public DemoRes(ServerHttpResponse delegate) {
        super(delegate);
        this.delegate = delegate;
    }
    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        if (body instanceof Flux) {
            Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
            return super.writeWith(fluxBody.map(dataBuffer -> {
                byte[] content = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(content);
                DataBufferUtils.release(dataBuffer);

                // 设置签名
                System.out.println("response body:"+new String(content));
                //this.getHeaders().set("headName","headValue");
                //String headerName = exchange.getResponse().getHeaders().getFirst("headerName");


                return delegate.bufferFactory().wrap(content);
            }));
        }
        return super.writeWith(body);
    }
}
class DemoReq2 extends ServerHttpRequestDecorator{
    private final ServerHttpRequest delegate;

    public DemoReq2(ServerHttpRequest delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    @Override
    public Flux<DataBuffer> getBody() {
        Flux<DataBuffer> map = delegate.getBody().map(dataBuffer -> {
            DataBuffer buf = dataBuffer.slice(0, dataBuffer.readableByteCount());
            byte[] b = new byte[buf.readableByteCount()];
            buf.read(b);
            // 验签...
            System.out.println("request body:"+new String(b));
            //this.getHeaders().set("headName","headValue");
            //String headerName = exchange.getRequest().getHeaders().getFirst("headerName");
            return dataBuffer;
        });
        return map;
    }
}