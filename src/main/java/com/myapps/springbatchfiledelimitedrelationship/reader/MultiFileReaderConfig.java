package com.myapps.springbatchfiledelimitedrelationship.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.*;

import java.io.IOException;

@Configuration
public class MultiFileReaderConfig {

    @Value("${source.data.files.path}") private String sourceDataFilesPath;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @StepScope
    @Bean
    public MultiResourceItemReader multiFileDelegatedReader(
            FlatFileItemReader multiFileReader
    ) {
        try {
            GenericApplicationContext genericApplicationContext = new GenericApplicationContext();
            Resource[] resources = genericApplicationContext.getResources("file:" + sourceDataFilesPath);
            return new MultiResourceItemReaderBuilder<>()
                    .name("multiFileDelegatedReader")
                    .resources(resources)
                    .delegate(new MultiFileDelegatedReader(multiFileReader))
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Unable to find or parse specified resources: " + sourceDataFilesPath + ".\n" + e.getMessage());
        }
    }

}
