package org.yourcompany.yourproject.models;

import java.util.Random;

public class IAccountModel {

    private String id;
    private String clientId;
    private String accountId;
    private String accountNumber;
    private String agency;
    private String balance;

    public IAccountModel(String clientId, String accountId, String accountNumber, String agency, String balance) {
        this.id = generateId();
        this.clientId = clientId;
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.agency = agency;
        this.balance = balance;
    }

    // getters
    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAgency() {
        return agency;
    }

    public String getBalance() {
        return balance;
    }

    // setters
    public void setId(String id) {
        this.id = id;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String generateId() {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            char randomChar = allowedChars.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
