package com.myapps.springbatchfiledelimitedrelationship.reader;

import com.myapps.springbatchfiledelimitedrelationship.domain.Customer;
import com.myapps.springbatchfiledelimitedrelationship.domain.Transaction;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

public class MultiFileDelegatedReader implements ItemStreamReader<Customer>, ResourceAwareItemReaderItemStream<Customer> {

    // Stores the current reading objects
    private Object currentObject;

    // This is the reader that can be used to read in fact the data from the source files
    private FlatFileItemReader<Object> delegate;

    public MultiFileDelegatedReader(FlatFileItemReader<Object> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Customer read() throws Exception {
        // If there's no already read object set the currentObject
        if (currentObject == null) {
            currentObject = delegate.read(); // Reads next register
        }

        // Create new Customer object and reset the read one
        Customer customer = (Customer) currentObject;
        currentObject = null;

        // If there's still data to be read and no EOF
        if (customer != null) {
            // Peek will read and add each Transaction to the Customer transactions when the readen data is a Transaction
            while(peek() instanceof Transaction) {
                // Now currentObject is not null anymore, since it's receiving data from peek()
                customer.getTransactionList().add((Transaction) currentObject);
            }
        }

        // Return the Customer with it's Transactions if any
        // Note: you MUST return something, otherwise you will never reach the next step (process or writer)
        return customer;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();
    }

    private Object peek() throws Exception {
        currentObject = delegate.read(); // Reads next register
        return currentObject;
    }

    @Override
    public void setResource(Resource resource) {
        delegate.setResource(resource);
    }
}
