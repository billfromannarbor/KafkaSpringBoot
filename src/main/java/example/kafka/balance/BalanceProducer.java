package example.kafka.balance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@NoArgsConstructor
@Data

public class BalanceProducer {
    List<Account> accounts;

    @Autowired
    private KafkaTemplate<String, Balance> kafkatemplate;

    public BalanceProducer(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Balance produceBalance() {
        if (accounts==null || accounts.isEmpty())
            throw new RuntimeException("Need at least one account");

        Balance balance = new Balance(175, accounts.get(0));
        kafkatemplate.send("AllBalances", balance);
        return balance;
    }
}
