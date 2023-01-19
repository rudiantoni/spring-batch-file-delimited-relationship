package com.myapps.springbatchfiledelimitedrelationship.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
public class SingleFileReaderConfig {

    @Value("${source.data.file.path}")
    private String resourceFilePath;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public FlatFileItemReader singleFileReader(
        LineMapper lineMapper
    ) {
        if (resourceFilePath == null) {
            throw new IllegalArgumentException("Data source file cannot be null. Is the property source.data.file.path set?");
        }
        Resource resource = new FileSystemResource(resourceFilePath);
        return new FlatFileItemReaderBuilder()
            .name("reader")
            .resource(resource)
            .lineMapper(lineMapper)
            .build();
    }

}
