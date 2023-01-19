package com.myapps.springbatchfiledelimitedrelationship.reader;

import com.myapps.springbatchfiledelimitedrelationship.domain.Customer;
import com.myapps.springbatchfiledelimitedrelationship.domain.Transaction;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LineMapperConfig {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public PatternMatchingCompositeLineMapper lineMapper() {
        PatternMatchingCompositeLineMapper lineMapper = new PatternMatchingCompositeLineMapper();
        // Tokenizers sequences, must be set a Map with a string and a LineTokenizer for each possible
        // setting.
        // So each string tells which LineTokenizer to use when a matching token is found.
        lineMapper.setTokenizers(tokenizers());

        // Maps a line data to a domain class
        lineMapper.setFieldSetMappers(fieldSetMappers());

        return lineMapper;
    }

    /**
     * Tokenizers
     */
    private Map<String, LineTokenizer> tokenizers() {
        // Maps all possible tokenizer settings
        Map<String, LineTokenizer> tokenizers = new HashMap<>();

        // Lines beginning with 0: must use a LineTokenizer for Customer class
        tokenizers.put("0*", customerLineTokenizer());
        // Lines beginning with 1: must use a LineTokenizer for Transaction class
        tokenizers.put("1*", transactionLineTokenizer());

        return tokenizers;
    }

    private LineTokenizer customerLineTokenizer() {
        // Source file is a delimited type, since each data are separated by certain characters
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        /*
        You can manually set the delimiter this way:
        lineTokenizer.setDelimiter(",");
         */
        /*
        Class attributes that will receive each value present in the line matched by the tokenizer
        In this case thats the Customer class
         */
        lineTokenizer.setNames("firstName", "lastName", "age", "email");

        /*
        Field values that will be inserted, in the same sequence as the setNames property
        They are based in 0, but since the first field just identifies the data type (Customer or Transaction),
        (see tokenizers() method) so the 0 index will not be included
         */
        lineTokenizer.setIncludedFields(1, 2, 3, 4);

        return lineTokenizer;
    }

    private LineTokenizer transactionLineTokenizer() {
        // Source file is a delimited type, since each data are separated by certain characters
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        /*
        Class attributes that will receive each value present in the line matched by the tokenizer
        In this case thats the Transaction class
         */
        lineTokenizer.setNames("id", "description", "value");

        /*
        Field values that will be inserted, in the same sequence as the setNames property
        They are based in 0, but since the first field just identifies the data type (Customer or Transaction),
        (see tokenizers() method) so the 0 index will not be included
         */
        lineTokenizer.setIncludedFields(1, 2, 3);

        return lineTokenizer;
    }

    /**
     * Field set mappers
     */
    @SuppressWarnings("rawtypes")
    private Map<String, FieldSetMapper> fieldSetMappers() {
        // Maps all the possible mappers
        Map<String, FieldSetMapper> fieldSetMappers = new HashMap<>();

        // Since the field set mapper are the same and only the token changes, we will use the same method to configure
        // both, but only sending a domain class as a parameter

        // Lines beginning with 0: must use a LineTokenizer for Customer class
        fieldSetMappers.put("0*", fieldSetMapper(Customer.class));
        // Lines beginning with 1: must use a LineTokenizer for Transaction class
        fieldSetMappers.put("1*", fieldSetMapper(Transaction.class));

        return fieldSetMappers;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private FieldSetMapper fieldSetMapper(Class domainClass) {
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        // Define the class that will receive the data
        fieldSetMapper.setTargetType(domainClass);
        return fieldSetMapper;
    }

}
