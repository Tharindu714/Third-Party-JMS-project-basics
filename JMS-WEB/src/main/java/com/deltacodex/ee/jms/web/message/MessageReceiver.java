package com.deltacodex.ee.jms.web.message;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

//Message Driven Bean
@MessageDriven(activationConfig = {
//        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/myQueue"), //For Queue Connections
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "activeMQTopic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "activeMQTopic"),
        @ActivationConfigProperty(propertyName = "resourceAdapter", propertyValue = "activemq-rar-6.1.6"),
        //@ActivationConfigProperty(propertyName = "maxPoolSize", propertyValue = "1"), //Server-specific -->> (maxSession or maxPoolSize)
        //@ActivationConfigProperty(propertyName = "poolResizeQuantity", propertyValue = "1"), //Server-specific
})
public class MessageReceiver implements MessageListener {

    @PostConstruct
    public void init() {
        System.out.println("MessageReceiver init...");
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(this + " Received message: "+message.getJMSMessageID()+" : " + message.getBody(String.class));
            //OUTPUT >> com.deltacodex.ee.jms.web.message.MessageReceiver@75cecc43 Received message: ID:57-192.168.57.55(98:a2:ee:3e:d1:ab)-60700-1746737930643 : I'm JMS Provider 49
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
