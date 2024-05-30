package org.yourcompany.yourproject.services.manageClients;

import org.yourcompany.yourproject.models.IClientsModel;
import org.yourcompany.yourproject.services.filesService.FileService;

public class ManageClientsImpl implements ManageClients {

    public static final String PATH = "clients.txt";

    private final FileService fileService;

    public ManageClientsImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public boolean registerClient(IClientsModel client) {
        if (consultClient(client.getCpf()) != null) {
            return false;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("name:");
        sb.append(client.getName().trim());
        sb.append(",cpf:");
        sb.append(client.getCpf().trim());
        sb.append(",email:");
        sb.append(client.getEmail().trim());
        sb.append(",phone:");
        sb.append(client.getPhone().trim());
        sb.append(",address:");
        sb.append(client.getAddress().trim());
        sb.append(",accountId:");
        sb.append(client.getAccountId().trim());
        sb.append(";");

        fileService.writeToFile(PATH, sb.toString());
        return true;
    }

    @Override
    public IClientsModel consultClient(String cpf) {
        String client = fileService.readFromFile(PATH);
        String[] clients = client.split(";");
        for (String c : clients) {
            String[] clientData = c.split(",");
            for (String data : clientData) {
                if (data.contains(cpf)) {
                    String[] clientInfo = c.split(",");
                    String name = clientInfo[0].replace("name:", "");
                    String clientCpf = clientInfo[1].replace("cpf:", "");
                    String email = clientInfo[2].replace("email:", "");
                    String phone = clientInfo[3].replace("phone:", "");
                    String address = clientInfo[4].replace("address:", "");
                    String accountId = clientInfo[5].replace("accountId:", "");
                    IClientsModel newClient = new IClientsModel(name, clientCpf, address, phone, email);
                    newClient.setAccountId(accountId);

                    return newClient;
                }
            }
        }

        return null;
    }

    @Override
    public boolean removeClient(String cpf) {
        boolean result = false;

        String tempFilePath = "tempFile.txt";
        fileService.createFile(tempFilePath);

        String reader = fileService.readFromFile(PATH);
        String[] courses = reader.split(";");
        for (String course : courses) {
            if (!course.contains("cpf:" + cpf.trim() + ",")) {
                fileService.writeToFile(tempFilePath, course + ";");
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
    public boolean updateClient(IClientsModel updatedClient) {
        if (consultClient(updatedClient.getCpf()) == null) {
            return false;
        }

        boolean removeResult = removeClient(updatedClient.getCpf());

        if (removeResult) {
            return registerClient(updatedClient);
        }

        return false;
    }

    @Override
    public boolean createAccount(String cpf, String accountId) {
        IClientsModel client = consultClient(cpf);
        if (client != null) {
            client.setAccountId(accountId);
            return updateClient(client);
        }

        return false;
    }
}
