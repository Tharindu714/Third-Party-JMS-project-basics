package com.deltacodex.ee.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessageReceiver {
    public static void main(String[] args) {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            Connection connection = activeMQConnectionFactory.createConnection();
            connection.setClientID("ActiveMQ-ClientApp-1.1.0");
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("activeMQTopic");

            MessageConsumer consumer = session.createConsumer(topic);
            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try {
                        System.out.println("Received message: " + message.getBody(String.class));
                    } catch (JMSException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            while (true) {}

//            consumer.close();
//            session.close();
//            connection.close();

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
