package org.yourcompany.yourproject;

import org.yourcompany.yourproject.models.enums.ManageAccountsMenuEnum;
import org.yourcompany.yourproject.models.enums.ManageClientsMenuEnum;
import org.yourcompany.yourproject.models.enums.StartMenuEnum;
import org.yourcompany.yourproject.services.filesService.FileService;
import org.yourcompany.yourproject.services.filesService.FileServiceImpl;
import org.yourcompany.yourproject.services.manageClients.ManageClientsImpl;

public class App {

    Boolean finishSystem = false;

    public void getStart() {
        FileService fileService = new FileServiceImpl();

        while (finishSystem == false) {
            SystemApp systemApp = new SystemApp();
            createMockDbFile(fileService);

            onShowPrompt(fileService, systemApp);
        }
    }

    private void onShowPrompt(FileService fileService, SystemApp systemApp) {
        StartMenuEnum startMenuOption = systemApp.onStartMenu();

        if (startMenuOption == null) {
            System.out.println("Opção inválida. Tente novamente.");
            onShowPrompt(fileService, systemApp);
        } else {
            switch (startMenuOption) {
                case GERENCIAR_CLIENTES -> {
                    // Gerenciar clientes
                    onManageClients(fileService, systemApp);
                }

                case GERENCIAR_CONTAS -> {
                    // Gerenciar contas
                    onManageAccounts(fileService, systemApp);
                }

                case SAIR -> {
                    // Sair
                    finishSystem = true;
                }

                default -> {
                }
            }
        }
    }

    private void onManageClients(FileService fileService, SystemApp systemApp) {
        ManageClientsMenuEnum menuOption = systemApp.onManageClientsMenu(fileService);

        if (menuOption == null) {
            System.out.println("Opção inválida. Tente novamente.");
            onManageClients(fileService, systemApp);
        } else {
            switch (menuOption) {
                case CADASTRAR_CLIENTE -> {
                    // Cadastrar cliente
                    systemApp.registerClient();
                }

                case CONSULTAR_CLIENTE -> {
                    // Consultar cliente
                    systemApp.consultClient();
                }

                case REMOVER_CLIENTE -> {
                    // Remover cliente
                    systemApp.removeClient();
                }

                case ATUALIZAR_CLIENTE -> {
                    // Atualizar cliente
                    systemApp.updateClient();
                }

                case VOLTAR_AO_MENU_PRINCIPAL -> {
                    // Voltar ao menu inicial
                    onShowPrompt(fileService, systemApp);
                }

                default -> {
                }
            }
        }
    }

    private void onManageAccounts(FileService fileService, SystemApp systemApp) {
        ManageAccountsMenuEnum menuOption = systemApp.onManageAccountsMenu(fileService);

        if (menuOption == null) {
            System.out.println("Opção inválida. Tente novamente.");
            onManageAccounts(fileService, systemApp);
        } else {
            switch (menuOption) {
                case CRIAR_CONTA -> {
                    // Criar conta
                    systemApp.createAccount();
                }

                case SACAR_DINHEIRO -> {
                    // Sacar dinheiro
                    systemApp.withdrawMoney();
                }

                case DEPOSITAR_DINHEIRO -> {
                    // Depositar dinheiro
                    systemApp.depositMoney();
                }

                case CONSULTAR_SALDO -> {
                    // Consultar saldo
                    systemApp.checkBalance();
                }

                case TRANSFERIR_DINHEIRO -> {
                    // Transferir dinheiro
                    systemApp.transferMoney();
                }

                case VOLTAR_AO_MENU_PRINCIPAL -> {
                    // Voltar ao menu principal
                    onShowPrompt(fileService, systemApp);
                }

                default -> {
                }
            }
        }

    }

    private void createMockDbFile(FileService fileService) {
        fileService.createFile(ManageClientsImpl.PATH);
    }

    public static void main(String[] args) {
        new App().getStart();
    }
}
