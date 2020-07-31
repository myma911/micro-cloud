package cn.aaron911.micro.sms.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class MQConfig {

    private static final String SMS_QUEUE = "sms";
    @Bean
    public Queue queue(){
        return new Queue(SMS_QUEUE,true);
    }
}
