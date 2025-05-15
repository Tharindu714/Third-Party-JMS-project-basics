# 📦 ActiveMQ Messaging Project

> **Objective:** Showcase a complete point-to-point messaging system using Apache ActiveMQ, featuring producer, consumer, and broker configuration. This guide walks through installation, setup, running examples, and best practices.

---

## 📁 Project Structure

```
ActiveMQ_Project/
├── broker/
│   └── activemq.xml           # Broker configuration (queues, persistence)
├── producer/                  # Java producer application (Maven)
│   ├── src/main/java/...      # JMS Producer code
│   └── pom.xml
├── consumer/                  # Java consumer application (Maven)
│   ├── src/main/java/...      # JMS Consumer code
│   └── pom.xml
├── scripts/                   # Convenience scripts
│   ├── start-broker.sh        # Start ActiveMQ broker
│   └── stop-broker.sh         # Stop ActiveMQ broker
├── screenshots/               # Configuration and output images
│   ├── broker-console.png
│   ├── producer-output.png
│   └── consumer-output.png
└── README.md                  # (this file)
```

---

## 🛠️ Prerequisites & Installation

1. **Java Development Kit (JDK) 11+** ☕

   * Download from [Eclipse Adoptium](https://adoptium.net/).
   * Set `JAVA_HOME` and add `$JAVA_HOME/bin` to your `PATH`.

2. **Apache Maven 3.6+** 📦

   * Download from [Maven Official Site](https://maven.apache.org/download.cgi).
   * Unzip and add `bin/` to your `PATH`.

3. **Apache ActiveMQ 5.17+** 🚦

   * Download the latest release from [ActiveMQ Downloads](https://activemq.apache.org/components/classic/download/).
   * Extract to `C:/ActiveMQ` (Windows) or `/opt/activemq` (Linux/Mac).

4. **Environment Setup** 🌐

   ```bash
   export ACTIVEMQ_HOME="/opt/activemq"
   export PATH="$ACTIVEMQ_HOME/bin:$PATH"
   ```

---

## 1. Configure & Start ActiveMQ Broker

1. **Verify `activemq.xml`** in `broker/activemq.xml`:

   * Define the queue under `<destinationPolicy>` and `<queues>`.
   * Enable persistence or memory-based store.

2. **Start Broker** 🚀

   ```bash
   # Linux/Mac
   cd $ACTIVEMQ_HOME
   bin/activemq start

   # Or use script
   ./scripts/start-broker.sh
   ```

3. **Access Management Console** 🌐

   * Open [http://localhost:8161/admin](http://localhost:8161/admin) (username/password: `admin/admin`).
   * Confirm your queue (e.g., `TEST.QUEUE`) appears under **Queues**.

---

## 2. Build & Run Producer

The **producer** sends `TextMessage` instances to the configured queue.

1. **Build** the producer:

   ```bash
   cd producer
   mvn clean package
   ```

2. **Run** the JAR:

   ```bash
   java -jar target/producer-1.0.jar
   ```

3. **Usage**:

   * Enter text messages in the console.
   * Type `exit` to terminate.

**Key Code Snippet:**

```java
ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
Connection connection = factory.createConnection();
Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
Queue queue = session.createQueue("TEST.QUEUE");
MessageProducer producer = session.createProducer(queue);
...
producer.send(session.createTextMessage(input));
```

---

## 3. Build & Run Consumer

The **consumer** listens on the queue and processes incoming messages.

1. **Build** the consumer:

   ```bash
   cd consumer
   mvn clean package
   ```

2. **Run** the JAR:

   ```bash
   java -jar target/consumer-1.0.jar
   ```

3. **Behavior**:

   * The consumer prints each received `TextMessage`.
   * Acknowledgement handled per session mode.

![How to Create a Topic](https://github.com/user-attachments/assets/6b7039be-faf0-485e-97ec-18c050a2d6fe)

**Key Code Snippet:**

```java
Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
MessageConsumer consumer = session.createConsumer(queue);
consumer.setMessageListener(msg -> {
    TextMessage text = (TextMessage) msg;
    System.out.println("Received: " + text.getText());
    msg.acknowledge();
});
```

---

## 4. Acknowledgement Modes Explained 🔍

| Mode                  | Description                                        | Use Case                        |
| --------------------- | -------------------------------------------------- | ------------------------------- |
| `AUTO_ACKNOWLEDGE`    | Broker auto-acknowledges after `onMessage` returns | Simple fire-and-forget flows    |
| `CLIENT_ACKNOWLEDGE`  | Client must call `message.acknowledge()` manually  | Guaranteed processing & retries |
| `DUPS_OK_ACKNOWLEDGE` | Lazy ack; may result in duplicates on failure      | High-throughput scenarios       |

---

## 5. Best Practices & Extensions ✨

* **Durable Subscriptions:** Use `Topic` with durable subscriber for pub/sub patterns.
* **SSL/TLS:** Secure your broker connections via SSL (configure in `activemq.xml`).
* **Clustering:** Set up a network of brokers for high availability.
* **Monitoring:** Integrate JMX metrics via VisualVM or Prometheus.

---

## 🎉 Conclusion

You now have a full-fledged ActiveMQ example: broker setup, producer, consumer, and deep dives into JMS features. Extend this template for advanced messaging patterns and scalable architectures. Happy coding! 🚀

---

## ˙✧˖°📸⋆｡ ˚ Some of Screenshots of Installation Process

**How to Start ActiveMQ third Party Application**

![ActiveMQ_Start](https://github.com/user-attachments/assets/82f2a785-2049-4934-8fcb-8e76a3bb8ece)

**ActiveMQ Dashboard**

![ActiveMQ GUI 1](https://github.com/user-attachments/assets/787fd476-2929-4559-a237-35ede8fd57f3)

Then Download **apache-activemq-6.1.6-bin.zip** >> https://activemq.apache.org/components/classic/download/classic-06-01-06

![Download This   Set JDK 17 in Payara Configuration](https://github.com/user-attachments/assets/07b15aeb-a355-4bf5-aab1-4a833d69a0fd)

**Download the RAR File and Deploy it >> Watch it below image**

![ActiveMQ-rar-activation](https://github.com/user-attachments/assets/3b5088fd-e6d0-4152-8953-c5c3c787b200)
![Deploy activemq](https://github.com/user-attachments/assets/7fb6aa5d-1370-4b03-9fce-3b2159790e0f)


**Open Localhost Admin Console & navigate Admin Object Resources >>**

![admin object console](https://github.com/user-attachments/assets/a00f6d8e-6288-49c8-a115-f3477c7eb6f8)

Create New **ActiveMQTopic object Resource** 

![admin object console2](https://github.com/user-attachments/assets/ebb51d0e-9d66-4f3c-a7ea-cc699ea333e8)

**Navigate Admin Connector Resources >>**

![connector resource step](https://github.com/user-attachments/assets/6c5b13df-3e9f-4bd3-9609-9ba9b0246f68)

Create New **ActiveMQConnectorPool** 

![create a new thread pool](https://github.com/user-attachments/assets/02c5a125-8583-42d6-a71f-8a5f31d17165)
![next step and tick ping enable](https://github.com/user-attachments/assets/f1064bc4-bbde-4a9e-bfa2-b8f1f8a6d848)

**Then Ping Test the Connection**

![ping successful](https://github.com/user-attachments/assets/5152989a-f83d-41c8-a7c0-3103a68144fa)

Set your Admin Console **Resource Adapter Config** Like below -- Default Username & Password: **admin**

![Resource Adpter Config](https://github.com/user-attachments/assets/16a26d38-77c7-4d63-9942-b4e06057ed6d)
