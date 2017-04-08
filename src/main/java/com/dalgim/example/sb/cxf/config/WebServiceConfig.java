package com.dalgim.example.sb.cxf.config;

import com.dalgim.example.sb.cxf.endpoint.FruitService;
import com.dalgim.example.sb.cxf.endpoint.FruitServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * Created by dalgim on 08.04.2017.
 */
@Configuration
public class WebServiceConfig {

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
        return endpoint;
    }

}
