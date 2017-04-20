package com.dalgim.example.sb.cxf.wsstimestamp.config;

import com.dalgim.example.sb.cxf.wsstimestamp.endpoint.FruitService;
import com.dalgim.example.sb.cxf.wsstimestamp.endpoint.FruitServiceImpl;
import com.google.common.collect.Maps;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.xml.ws.Endpoint;
import java.util.Map;

import static org.apache.wss4j.common.ConfigurationConstants.ACTION;
import static org.apache.wss4j.common.ConfigurationConstants.MUST_UNDERSTAND;
import static org.apache.wss4j.common.ConfigurationConstants.REQUIRE_TIMESTAMP_EXPIRES;
import static org.apache.wss4j.common.ConfigurationConstants.TIMESTAMP_PRECISION;
import static org.apache.wss4j.common.ConfigurationConstants.TIMESTAMP_STRICT;
import static org.apache.wss4j.common.ConfigurationConstants.TTL_FUTURE_TIMESTAMP;
import static org.apache.wss4j.common.ConfigurationConstants.TTL_TIMESTAMP;

/**
 * Created by dalgim on 08.04.2017.
 */
@Configuration
public class WebServiceServerConfig {

    private static final String SERVLET_URL_PATH = "/api";
    private static final String SERVICE_URL_PATH = "/FruitService";

    @Bean
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), SERVLET_URL_PATH + "/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public FruitService fruitService() {
        return new FruitServiceImpl();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), fruitService());
        endpoint.publish(SERVICE_URL_PATH);
        endpoint.getInInterceptors().add(wss4JInInterceptor());
        endpoint.getInInterceptors().add(loggingInInterceptor());
        endpoint.getOutInterceptors().add(loggingOutInterceptor());
        endpoint.getOutInterceptors().add(wss4JOutInterceptor());
        return endpoint;
    }

    private WSS4JOutInterceptor wss4JOutInterceptor() {
        Map<String, Object> securityProperties = Maps.newHashMap();
        securityProperties.put(ACTION, "Timestamp");
        securityProperties.put(MUST_UNDERSTAND, "true");
        securityProperties.put(TIMESTAMP_PRECISION, "true");
        securityProperties.put(TTL_TIMESTAMP, "60");
        return new WSS4JOutInterceptor(securityProperties);
    }

    private WSS4JInInterceptor wss4JInInterceptor() {
        Map<String, Object> securityProperties = Maps.newHashMap();
        securityProperties.put(ACTION, "Timestamp");
        securityProperties.put(TTL_TIMESTAMP, "40");
        securityProperties.put(TTL_FUTURE_TIMESTAMP, "30");
        securityProperties.put(REQUIRE_TIMESTAMP_EXPIRES, "true");
        securityProperties.put(TIMESTAMP_STRICT, "true");
        return new WSS4JInInterceptor(securityProperties);
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
