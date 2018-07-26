package camel.amq.samples;

import org.apache.camel.CamelContext;
import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "camel.amq.samples")
public class CamelAMQApplication {

    @Value("${amq.url}")
    private String amqUrl;
    
    @Value("${amq.username}")
    private String userName;
    
    @Value("${amq.password}")
    private String password;
    
    public static void main(String[] args) {
        SpringApplication.run(CamelAMQApplication.class, args);
    }

    @Bean
    ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(new CamelHttpTransportServlet(), "/camel/*");
        servlet.setName("CamelServlet");
        return servlet;
    }

    @Bean("amqp")
    AMQPComponent amqpComponent(CamelContext context) {
        final AMQPComponent amqpComponent = new AMQPComponent(context);
        final JmsConnectionFactory connectionFactory = new JmsConnectionFactory();
        connectionFactory.setRemoteURI(amqUrl);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        amqpComponent.setConnectionFactory(connectionFactory);
        return amqpComponent;
    }
}
