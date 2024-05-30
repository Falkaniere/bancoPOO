package org.yourcompany.yourproject.services.manageAccounts;

import java.util.Random;

import org.yourcompany.yourproject.models.IAccountModel;
import org.yourcompany.yourproject.models.IClientsModel;
import org.yourcompany.yourproject.services.filesService.FileService;
import org.yourcompany.yourproject.services.manageClients.ManageClients;
import org.yourcompany.yourproject.services.manageClients.ManageClientsImpl;

public class ManageAccountsImpl implements ManageAccounts {

    public static final String PATH = "account.txt";

    private final FileService fileService;
    private final ManageClients manageClients;

    public ManageAccountsImpl(FileService fileService) {
        this.fileService = fileService;
        this.manageClients = new ManageClientsImpl(fileService);
    }

    @Override
    public boolean createAccount(String cpf) {
        IClientsModel client = manageClients.consultClient(cpf);

        if (client == null) {
            return false;
        }

        if (client.getAccountId() != null && !client.getAccountId().isEmpty()) {
            System.err.println("Cliente já possui conta!");
            return false;
        }

        String accountId = generateId();
        saveAccount(accountId, client);
        boolean result = updateClient(accountId, client);

        return result;
    }

    private void saveAccount(String accountId, IClientsModel client) {
        StringBuilder sb = new StringBuilder();
        sb.append("id:");
        sb.append(accountId);
        sb.append(",clientId:");
        sb.append(client.getCpf().trim());
        sb.append(",accountId:");
        sb.append(accountId);
        sb.append(",accountNumber:");
        sb.append(generateAccountNumber());
        sb.append(",agency:");
        sb.append(generateAgency());
        sb.append(",balance:");
        sb.append("0");
        sb.append(";");

        fileService.writeToFile(PATH, sb.toString());
    }

    private boolean updateClient(String accountId, IClientsModel client) {
        client.setAccountId(accountId);
        return manageClients.updateClient(client);
    }

    @Override
    public boolean withdrawMoney(String accountNumber, double value) {
        IAccountModel account = consultAccount(accountNumber);

        if (account == null) {
            System.err.println("Conta não encontrada!");
            return false;
        }

        double balance = Double.parseDouble(account.getBalance());
        if (balance < value) {
            System.err.println("Saldo insuficiente!");
            return false;
        }

        balance -= value;
        account.setBalance(String.valueOf(balance));
        boolean result = updateAccount(account);

        if (!result) {
            System.err.println("Erro ao atualizar conta!");
            return false;
        }

        return true;
    }

    @Override
    public boolean depositMoney(String accountId, double value) {

        IAccountModel account = consultAccount(accountId);

        if (account == null) {
            System.err.println("Conta não encontrada!");
            return false;
        }

        double balance = Double.parseDouble(account.getBalance());

        balance += value;
        account.setBalance(String.valueOf(balance));
        boolean result = updateAccount(account);

        if (!result) {
            System.err.println("Erro ao atualizar conta!");
            return false;
        }

        return true;
    }

    @Override
    public String checkBalance(String accountId) {
        IAccountModel account = consultAccount(accountId);

        if (account == null) {
            System.err.println("Conta não encontrada!");
            return null;
        }

        return account.getBalance();
    }

    @Override
    public boolean transferMoney(String accountId, String targetAccountId, double value) {
        boolean withdrawResult = withdrawMoney(accountId, value);
        if (withdrawResult) {
            return depositMoney(targetAccountId, value);
        }

        return false;
    }

    @Override
    public IAccountModel consultAccount(String accountNumber) {
        String client = fileService.readFromFile(PATH);
        String[] accounts = client.split(";");
        for (String account : accounts) {
            String[] clientData = account.split(",");
            for (String data : clientData) {
                if (data.contains(accountNumber)) {
                    String[] accountInfo = account.split(",");
                    String clientId = accountInfo[1].replace("clientId:", "");
                    String accountId = accountInfo[2].replace("accountId:", "");
                    String agency = accountInfo[4].replace("agency:", "");
                    String balance = accountInfo[5].replace("balance:", "");

                    IAccountModel newAccount = new IAccountModel(clientId, accountId, accountNumber, agency, balance);
                    return newAccount;
                }
            }
        }

        return null;
    }

    @Override
    public boolean removeAccount(String accountId) {
        boolean result = false;

        String tempFilePath = "tempFile.txt";
        fileService.createFile(tempFilePath);

        String reader = fileService.readFromFile(PATH);
        String[] accounts = reader.split(";");
        for (String account : accounts) {
            if (!account.contains("accountId:" + accountId.trim() + ",")) {
                fileService.writeToFile(tempFilePath, account + ";");
            } else {
                result = true;
            }
        }

        if (result) {
            fileService.clearFile(PATH);
            String tempData = fileService.readFromFile(tempFilePath);
            fileService.writeToFile(PATH, tempData);
        }

        fileService.removeFile(tempFilePath);
        return result;
    }

    @Override
    public boolean updateAccount(IAccountModel account) {
        if (consultAccount(account.getAccountNumber()) == null) {
            return false;
        }

        boolean removeResult = removeAccount(account.getAccountId());

        if (removeResult) {
            updateAccountFile(account);
            return true;
        }

        return false;
    }

    private void updateAccountFile(IAccountModel account) {
        StringBuilder sb = new StringBuilder();
        sb.append("id:");
        sb.append(account.getAccountId());
        sb.append(",clientId:");
        sb.append(account.getClientId());
        sb.append(",accountId:");
        sb.append(account.getAccountId());
        sb.append(",accountNumber:");
        sb.append(account.getAccountNumber());
        sb.append(",agency:");
        sb.append(account.getAgency());
        sb.append(",balance:");
        sb.append(account.getBalance());
        sb.append(";");

        fileService.writeToFile(PATH, sb.toString());
    }

    private String generateAccountNumber() {
        Random random = new Random();

        int number = random.nextInt(900000) + 100000;
        return String.valueOf(number);
    }

    private String generateAgency() {
        Random random = new Random();

        int number = random.nextInt(9000) + 1000;
        return String.valueOf(number);
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
