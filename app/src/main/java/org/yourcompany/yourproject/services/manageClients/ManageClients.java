package org.yourcompany.yourproject.services.manageClients;

import org.yourcompany.yourproject.models.IClientsModel;

public interface ManageClients {

    boolean registerClient(IClientsModel client);

    IClientsModel consultClient(String cpf);

    boolean removeClient(String cpf);

    boolean updateClient(IClientsModel updatedClient);

    boolean createAccount(String cpf, String accountId);
}
