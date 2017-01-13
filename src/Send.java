import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;

/**
 * Created by christof on 13.01.17.
 */
public class Send {

    private final static String QUEUE_NAME = "time_queue";


    public static void main(String... args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        Address address = new Address("192.168.1.103");
        Address[] addresses = {address};
        Connection connection = factory.newConnection(addresses);
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        while (true) {
            LocalDateTime dateTime = LocalDateTime.now();
            Thread.sleep(1000);

            String message = "Time now is: " + dateTime.format(formatter);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        }


//        channel.close();
//        connection.close();
    }
}
