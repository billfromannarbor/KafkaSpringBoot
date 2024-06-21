package example.kafka;

import example.kafka.balance.Account;
import example.kafka.balance.AccountType;
import example.kafka.balance.Balance;
import example.kafka.balance.BalanceProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Kafka Balance Producer

When you instantiate the producer, it automatically hooks up to Kafta via app configuration.
It has a single method produceBalance which produces a random balance amount for a random account chosen from the available accounts list.

AccountType - Name/Description
Account - accountNumber and accountType
Balance - amount and account

The Balance object is converted to json and sent to Kafka

Tests
Create a new AccountType
Create a new Account
Create a new Balance
Call produceBalance on Balance

 */

public class BalanceProducerTests {
    @Test
    void createBalanceProducerAndProduceBalance() {
        var accountType = new AccountType("Retirement", "Retirement account");
        var account = new Account("123456Tu", accountType);

        List<Account> accounts = new ArrayList<>();
        accounts.add(account);

        var balanceProducer = new BalanceProducer(accounts);

        Balance balance = balanceProducer.produceBalance();

        System.out.println(balance);
    }

    @Test
    void createAccountType() {
        var accountType = new AccountType("Retirement", "Retirement account");
        assertEquals(accountType.getName(), "Retirement");
        assertEquals(accountType.getDescription(), "Retirement account");
    }


    @Test
    void createAccount() {
        var accountType = new AccountType("Retirement", "Retirement account");
        var account = new Account("123456Tu", accountType);
        assertEquals(account.getAccountNumber() , "123456Tu");
        assertEquals(account.getAccountType(), accountType);
    }

    @Test
    void createBalance() {
        var accountType = new AccountType("Retirement", "Retirement account");
        var account = new Account("123456Tu", accountType);
        var balance = new Balance(100.00, account);

        assertEquals(balance.getAmount(), 100.00);
        assertEquals(account, balance.getAccount());

        BalanceProducer balanceProducer = new BalanceProducer(List.of(account));

        Balance balance1 = balanceProducer.produceBalance();
        assertEquals(account, balance1.getAccount());

    }
}
