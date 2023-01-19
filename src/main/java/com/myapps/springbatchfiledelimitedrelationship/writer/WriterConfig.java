package com.myapps.springbatchfiledelimitedrelationship.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WriterConfig {

    @SuppressWarnings("rawtypes")
    @Bean
    public ItemWriter writer() {

        // Works too, but returns directly a lambda expression with no subroutine
        //return items -> items.forEach(System.out::println);

        // Gives more controlled flow with the subroutine usage
        return items -> {

            // Object because item can be either a Customer or a Transaction domain class
            for (Object item: items) {
                // Redundant toString(), only to emphasize that both domain classes must have these in their declarations
                System.out.println(items.toString());
            }

        };

    }
}
