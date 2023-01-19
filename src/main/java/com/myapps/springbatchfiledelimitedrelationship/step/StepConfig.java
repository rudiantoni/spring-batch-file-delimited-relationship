package com.myapps.springbatchfiledelimitedrelationship.step;

import com.myapps.springbatchfiledelimitedrelationship.domain.Customer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StepConfig {

    private final StepBuilderFactory stepBuilderFactory;
    @Value("${source.data.use.multifile}")
    private Boolean useMultifile;

    public StepConfig(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public Step step(
        MultiResourceItemReader<Customer> multiFileDelegatedReader,
        FlatFileItemReader singleFileReader,
        ItemWriter writer
    ) {
        if (useMultifile) {
            System.out.println("Using multi file data source.");
            return stepBuilderFactory
                .get("step")
                /*
                Reader and writer classes aren't used when the datasource can be more than one
                Amount of data to be processed each time this step is executed
                 */
                .chunk(1)
                /*
                You can either call a delegated reader with a new instance from it's implementation class
                or you can just pass an previously configured public annotated bean
                 */
                //.reader(new MultiFileDelegatedReader(singleFileReader))
                .reader(multiFileDelegatedReader) // arquivo único
                .writer(writer)
                .build();
        } else {
            System.out.println("Using single file data source.");
            return stepBuilderFactory
                .get("step")
                .chunk(1)
                .reader(singleFileReader)
                .writer(writer)
                .build();
        }
    }
}
