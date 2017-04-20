package com.dalgim.example.sb.cxf.wsstimestamp.config;

import com.dalgim.example.sb.cxf.wsstimestamp.endpoint.FruitService;
import com.google.common.collect.Maps;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

import static org.apache.wss4j.common.ConfigurationConstants.ACTION;
import static org.apache.wss4j.common.ConfigurationConstants.MUST_UNDERSTAND;
import static org.apache.wss4j.common.ConfigurationConstants.REQUIRE_TIMESTAMP_EXPIRES;
import static org.apache.wss4j.common.ConfigurationConstants.TIMESTAMP_PRECISION;
import static org.apache.wss4j.common.ConfigurationConstants.TIMESTAMP_STRICT;
import static org.apache.wss4j.common.ConfigurationConstants.TTL_FUTURE_TIMESTAMP;
import static org.apache.wss4j.common.ConfigurationConstants.TTL_TIMESTAMP;

/**
 * Created by dalgim on 09.04.2017.
 */
@Configuration
public class WebServiceClientConfig {

    @Bean
    public FruitService jaxWsProxyFactoryBean(@Value("${fruitService.address}") String address) {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(FruitService.class);
        jaxWsProxyFactoryBean.setAddress(address);
        FruitService fruitService = (FruitService) jaxWsProxyFactoryBean.create();
        Client client = ClientProxy.getClient(fruitService);
        configureEndpoint(client.getEndpoint());
        return fruitService;
    }

    private void configureEndpoint(Endpoint endpoint) {
        endpoint.getInInterceptors().add(loggingInInterceptor());
        endpoint.getInInterceptors().add(wss4JInInterceptor());
        endpoint.getOutInterceptors().add(loggingOutInterceptor());
        endpoint.getOutInterceptors().add(wss4JOutInterceptor());
    }

    private WSS4JOutInterceptor wss4JOutInterceptor() {
        Map<String, Object> securityProperties = Maps.newHashMap();
        securityProperties.put(ACTION, "Timestamp");
        securityProperties.put(MUST_UNDERSTAND, "true");
        securityProperties.put(TTL_TIMESTAMP, "120");
        securityProperties.put(TIMESTAMP_PRECISION, "false");
        return new WSS4JOutInterceptor(securityProperties);
    }

    private WSS4JInInterceptor wss4JInInterceptor() {
        Map<String, Object> properties = Maps.newHashMap();
        properties.put(ACTION, "Timestamp");
        properties.put(TTL_TIMESTAMP, "30");
        properties.put(TTL_FUTURE_TIMESTAMP, "100");
        properties.put(TIMESTAMP_STRICT, "true");
        properties.put(REQUIRE_TIMESTAMP_EXPIRES, "true");
        return new WSS4JInInterceptor(properties);
    }

    private LoggingInInterceptor loggingInInterceptor() {
        LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
        loggingInInterceptor.setPrettyLogging(true);
        return loggingInInterceptor;
    }

    private LoggingOutInterceptor loggingOutInterceptor() {
        LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
        loggingOutInterceptor.setPrettyLogging(true);
        return loggingOutInterceptor;
    }

}
