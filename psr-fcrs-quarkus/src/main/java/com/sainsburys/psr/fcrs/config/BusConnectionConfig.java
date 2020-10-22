package com.sainsburys.psr.fcrs.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.RedeliveryPolicy;
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

@EnableJms
@Configuration
public class BusConnectionConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;

    @Value("${spring.activemq.prefetch:50}")
    private int prefetchLimit;

    @Value("${psr.fcrs.redelivery.delay:15000}")
    private long redeliveryDelay;

    @Value("${psr.fcrs.redelivery.multiplier:2}")
    private int backOffMultiplier;

    @Value("${psr.fcrs.redelivery.max:3}")
    private int maximumRedeliveries;

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
            ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Primary
    @Bean
    public ConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMq = new ActiveMQConnectionFactory(brokerUrl);
        activeMq.setUserName(username);
        activeMq.setPassword(password);
        activeMq.setTrustAllPackages(true);

        ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
        prefetchPolicy.setQueuePrefetch(prefetchLimit);

        activeMq.setRedeliveryPolicy(createRedeliveryPolicy());
        activeMq.setPrefetchPolicy(prefetchPolicy);

        JmsPoolConnectionFactory jmsPoolConnectionFactory = new JmsPoolConnectionFactory();
        jmsPoolConnectionFactory.setConnectionFactory(activeMq);

        return jmsPoolConnectionFactory;
    }

    private RedeliveryPolicy createRedeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setRedeliveryDelay(redeliveryDelay);
        redeliveryPolicy.setUseExponentialBackOff(true);
        redeliveryPolicy.setBackOffMultiplier(backOffMultiplier);
        redeliveryPolicy.setUseCollisionAvoidance(true);
        redeliveryPolicy.setMaximumRedeliveries(maximumRedeliveries);
        return redeliveryPolicy;
    }
}
