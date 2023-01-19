package com.myapps.springbatchfiledelimitedrelationship.domain;

public class Transaction {

    public String id;
    public String description;
    public Double value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id='" + id + '\'' +
            ", description='" + description + '\'' +
            ", value=" + value +
            '}';
    }
}
