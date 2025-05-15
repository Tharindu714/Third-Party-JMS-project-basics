package com.deltacodex.ee.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessageSender {
    public static void main(String[] args) {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            Connection connection = activeMQConnectionFactory.createConnection();
            connection.setClientID("ActiveMQ-ClientApp-1.0.0");
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("activeMQTopic");

            MessageProducer producer = session.createProducer(topic);
            TextMessage textMessage = session.createTextMessage("Hello, This is ActiveMQ Message Sender");
            producer.send(textMessage);

            producer.close();
            session.close();
            connection.close();

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
