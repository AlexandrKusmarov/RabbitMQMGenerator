import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import util.Alphabet;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/** This application generate a random messages with random lengths at range [1 - your param]
 *  and send it to RabbitMQ server on localhost.
 *  At firs you need to install RabbitMQ server on your localhost.
 *  To start send messages you need to start messageSender method with 3 params :
 *  1. Alphabet (Look at util.Alphabet class).
 *  2. Max length of generated message.
 *  3. Delay between sends messages in (ms).
 *  You also can change names of EXCHANGE, QUEUE and ROUTING_KEY but in this case you must change
 *  this names in messageReceiver app too.
 */

public class MessageProducer {

    private static final String EXCHANGE_NAME = "genericEx";
    private static final String QUEUE_NAME = "genericQ";
    private static final String ROUTING_KEY_NAME = "key1";

    private ConnectionFactory factory = new ConnectionFactory();

    {
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("localhost");
        factory.setPort(5672);
    }

    public void messageSender (String Alphabet, Integer maxLength, Integer delayBetweenMessages)
            throws IOException, TimeoutException {

        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel())
        {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY_NAME);

            MessageGenerator messageGenerator = new MessageGenerator();
            String message = "";

            while (true) {
                message = messageGenerator
                        .getRandomWord(messageGenerator.rndMsgLength(maxLength), Alphabet);
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_NAME, null, message.getBytes());
                System.out.println("Message sent :" + message);
                try {
                    Thread.sleep(delayBetweenMessages);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        MessageProducer messageProducer = new MessageProducer();
        messageProducer.messageSender(Alphabet.ALPHABET_ENG, 100, 100);
    }
}
