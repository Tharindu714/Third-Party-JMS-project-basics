package com.deltacodex.ee.jms.web.servlet;

import jakarta.annotation.Resource;
import jakarta.jms.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/home")
public class Home extends HttpServlet {
    @Resource(lookup = "jms/myQueueConnectionFactory")
    private QueueConnectionFactory queueConnectionFactory;

    @Resource(lookup = "jms/myQueue")
    private Queue queue;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("<h1>Home</h1>");
        try {

            QueueConnection connection = queueConnectionFactory.createQueueConnection();

            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            jakarta.jms.QueueSender sender = session.createSender(queue);

            for (int i = 0; i < 50; i++) {
                TextMessage message = session.createTextMessage();
                message.setText("I'm JMS Provider " + i);
                sender.send(message);
            }

            sender.close();
            session.close();
            connection.close();

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}

//OUTPUT >> com.deltacodex.ee.jms.web.message.MessageReceiver@31ccd3fa Received message: ID:43-192.168.57.55(89:44:53:c7:ed:7f)-1-1746738631096 : I'm JMS Provider 42
//https://repo1.maven.org/maven2/org/apache/activemq/activemq-rar/6.1.6/
