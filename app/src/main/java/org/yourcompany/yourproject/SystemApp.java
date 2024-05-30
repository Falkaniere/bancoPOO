package org.yourcompany.yourproject;

import org.yourcompany.yourproject.models.AccountModel;
import org.yourcompany.yourproject.models.ClientsModel;
import org.yourcompany.yourproject.models.IClientsModel;
import org.yourcompany.yourproject.models.IMenuModel;
import org.yourcompany.yourproject.models.enums.ManageAccountsMenuEnum;
import org.yourcompany.yourproject.models.enums.ManageClientsMenuEnum;
import org.yourcompany.yourproject.models.enums.StartMenuEnum;
import org.yourcompany.yourproject.services.filesService.FileService;
import org.yourcompany.yourproject.services.manageAccounts.ManageAccounts;
import org.yourcompany.yourproject.services.manageAccounts.ManageAccountsImpl;
import org.yourcompany.yourproject.services.manageClients.ManageClients;
import org.yourcompany.yourproject.services.manageClients.ManageClientsImpl;
import org.yourcompany.yourproject.services.scannerService.ScannerService;
import org.yourcompany.yourproject.services.scannerService.ScannerServiceImpl;

class SystemApp implements IMenuModel, ClientsModel, AccountModel {

    private final ScannerService scannerService;
    private ManageClients manageClients;
    private ManageAccounts manageAcccounts;

    public SystemApp() {
        this.scannerService = new ScannerServiceImpl();
    }

    @Override
    public StartMenuEnum onStartMenu() {
        System.out.println("1-Gerenciar CLIENTES");
        System.out.println("2-Gerenciar CONTAS");
        System.out.println("3-SAIR");

        String option = scannerService.inputScanner("Escolha uma opção: ");

        return StartMenuEnum.fromValue(Integer.parseInt(option));
    }

    @Override
    public ManageClientsMenuEnum onManageClientsMenu(FileService fileService) {
        manageClients = new ManageClientsImpl(fileService);

        System.out.println("1-Cadastrar CLIENTE");
        System.out.println("2-Consultar CLIENTE");
        System.out.println("3-Remover CLIENTE");
        System.out.println("4-Atualizar CLIENTE");
        System.out.println("5-Voltar ao MENU INICIAL");

        String option = scannerService.inputScanner("Escolha uma opção: ");

        return ManageClientsMenuEnum.fromValue(Integer.parseInt(option));
    }

    @Override
    public ManageAccountsMenuEnum onManageAccountsMenu(FileService fileService) {
        manageAcccounts = new ManageAccountsImpl(fileService);

        System.out.println("1-Criar CONTA para um CLIENTE");
        System.out.println("2-Sacar dinheiro de uma CONTA de um CLIENTE");
        System.out.println("3-Depositar dinheiro para uma CONTA de um CLIENTE");
        System.out.println("4-Verificar saldo de uma CONTA de um CLIENTE");
        System.out.println("5-Transferir dinheiro de uma CONTA de um CLIENTE para outro CLIENTE");
        System.out.println("6-Voltar ao MENU INICIAL");

        String option = scannerService.inputScanner("Escolha uma opção: ");

        return ManageAccountsMenuEnum.fromValue(Integer.parseInt(option));
    }

    @Override
    public void registerClient() {
        System.out.println("CADASTRO DE CLIENTE");
        String name = scannerService.inputScanner("Digite o nome do cliente: ");
        String cpf = scannerService.inputScanner("Digite o CPF do cliente: ");
        String email = scannerService.inputScanner("Digite o email do cliente: ");
        String phone = scannerService.inputScanner("Digite o telefone do cliente: ");
        String address = scannerService.inputScanner("Digite o nome da rua do cliente: Ex: Rua Haddock Lobo");

        IClientsModel client = new IClientsModel(name, cpf, email, phone, address);

        boolean result = manageClients.registerClient(client);

        if (result) {
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Cliente já cadastrado!");
        }
    }

    @Override
    public void consultClient() {
        System.out.println("CONSULTA DE CLIENTE");
        String cpf = scannerService.inputScanner("Digite o CPF do cliente: ");

        IClientsModel client = manageClients.consultClient(cpf);

        if (client != null) {
            System.out.println("Nome: " + client.getName());
            System.out.println("CPF: " + client.getCpf());
            System.out.println("Email: " + client.getEmail());
            System.out.println("Telefone: " + client.getPhone());
            System.out.println("Endereço: " + client.getAddress());

            if (!client.getAccountId().isEmpty()) {
                System.out.println("Conta: " + client.getAccountId());
            }

        } else {
            System.out.println("Cliente não encontrado!");
        }
    }

    @Override
    public void removeClient() {
        System.out.println("REMOÇÃO DE CLIENTE");
        String cpf = scannerService.inputScanner("Digite o CPF do cliente: ");

        boolean result = manageClients.removeClient(cpf);

        if (result) {
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Cliente não encontrado!");
        }
    }

    @Override
    public void updateClient() {
        System.out.println("ATUALIZAÇÃO DE CLIENTE");
        String cpf = scannerService.inputScanner("Digite o CPF do cliente: ");

        IClientsModel client = manageClients.consultClient(cpf);

        if (client != null) {
            String name = scannerService.inputScanner("Digite o nome do cliente: ");
            String email = scannerService.inputScanner("Digite o email do cliente: ");
            String phone = scannerService.inputScanner("Digite o telefone do cliente: ");
            String address = scannerService.inputScanner("Digite o endereço do cliente: ");

            IClientsModel updatedClient = new IClientsModel(name, cpf, email, phone, address);
            updatedClient.setId(client.getId());

            boolean result = manageClients.updateClient(updatedClient);

            if (result) {
                System.out.println("Cliente atualizado com sucesso!");
            } else {
                System.out.println("Cliente não encontrado!");
            }

        } else {
            System.out.println("Cliente não encontrado!");
        }
    }

    @Override
    public void createAccount() {
        System.out.println("CRIAÇÃO DE CONTA");
        String cpf = scannerService.inputScanner("Digite o CPF do cliente: ");

        boolean result = manageAcccounts.createAccount(cpf);

        if (result) {
            System.out.println("Conta criada com sucesso!");
        } else {
            System.out.println("Cliente não encontrado!");
        }
    }

    @Override
    public void withdrawMoney() {
        System.out.println("SAQUE DE DINHEIRO");
        String accountNumber = scannerService.inputScanner("Digite o número da conta: ");
        double value = Double.parseDouble(scannerService.inputScanner("Digite o valor do saque: "));

        boolean result = manageAcccounts.withdrawMoney(accountNumber, value);

        if (result) {
            System.out.println("Saque realizado com sucesso!");
        } else {
            System.out.println("Saque não realizado!");
        }
    }

    @Override
    public void depositMoney() {
        System.out.println("DEPÓSITO DE DINHEIRO");
        String accountNumber = scannerService.inputScanner("Digite o número da conta: ");
        double value = Double.parseDouble(scannerService.inputScanner("Digite o valor do depósito: "));

        boolean result = manageAcccounts.depositMoney(accountNumber, value);

        if (result) {
            System.out.println("Depósito realizado com sucesso!");
        } else {
            System.out.println("Depósito não realizado!");
        }
    }

    @Override
    public void checkBalance() {
        System.out.println("CONSULTA DE SALDO");
        String accountNumber = scannerService.inputScanner("Digite o número da conta: ");

        String balance = manageAcccounts.checkBalance(accountNumber);

        if (balance != null) {
            System.out.println("Saldo: " + balance);
        } else {
            System.out.println("Conta não encontrada!");
        }
    }

    @Override
    public void transferMoney() {
        System.out.println("TRANSFERÊNCIA DE DINHEIRO");
        String accountNumber = scannerService.inputScanner("Digite o número da conta de origem: ");
        String targetAccountNumber = scannerService.inputScanner("Digite o número da conta de destino: ");
        double value = Double.parseDouble(scannerService.inputScanner("Digite o valor da transferência: "));

        boolean result = manageAcccounts.transferMoney(accountNumber, targetAccountNumber, value);

        if (result) {
            System.out.println("Transferência realizada com sucesso!");
        } else {
            System.out.println("Transferência não realizada!");
        }
    }
}
