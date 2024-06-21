package example.kafka.balance;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class BalanceProducerConfig {
    @Bean
    public BalanceProducer createBalanceProducer() {
        var accountType = new AccountType("Retirement", "Retirement account");
        var account = new Account("123456Tu", accountType);

        return new BalanceProducer(List.of(account));
    }

    @Bean
    public ProducerFactory<String, Balance> producerBalanceFactory() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
    @Bean
    public KafkaTemplate<String, Balance> kafkaBalanceTemplate() {
        return new KafkaTemplate<>(producerBalanceFactory());
    }
}
