package example.kafka;

import example.kafka.balance.Balance;
import example.kafka.balance.BalanceProducer;
import example.kafka.controller.KafkaController;
import example.kafka.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class KafkaApplication implements CommandLineRunner {
	static ConfigurableApplicationContext context;

	@Autowired
	private Environment env;

	@Autowired
	BalanceProducer balanceProducer;

	@Value("$spring.kafka.bootstrap-servers")
	String bootstrapServers;

	final String groupId = "my-group-id";//env.getProperty("spring.kafka.consumer.group-id");

	@Autowired
	MessageProducer messageProducer;
	public static void main(String[] args) {

		context = SpringApplication.run(KafkaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Every 10 seconds, wake up and send a message
		while (true) {
			Thread.sleep(10000);
			messageProducer.sendMessage("TestTopic", "dog");
			Balance balance = balanceProducer.produceBalance();
			System.out.println(balance);
		}
		//MessageProducer messageProducer = context.getBean(MessageProducer.class);
		//System.out.println("Sending messages to Test Topic");


	}

	@KafkaListener(id = groupId, topics = "TestTopic")
	public void listen(String in) {
		System.out.println("Received Message: " + in);
	}
}
