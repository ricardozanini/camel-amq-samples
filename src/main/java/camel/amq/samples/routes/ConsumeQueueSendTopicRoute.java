package camel.amq.samples.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConsumeQueueSendTopicRoute extends RouteBuilder {
    
    @Value("${amq.queue}")
    private String queue;
    @Value("${amq.topic}")
    private String topic;

    public ConsumeQueueSendTopicRoute() {

    }

    @Override
    public void configure() throws Exception {
        from(String.format("amqp:queue:%s::%s", queue, queue) )
            .routeId("consume-queue-send-topic")
            .log("${body}")
            .to(String.format("amqp:topic:%s", topic));
    }

}
