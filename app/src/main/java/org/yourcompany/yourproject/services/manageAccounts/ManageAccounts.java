package org.yourcompany.yourproject.services.manageAccounts;

import org.yourcompany.yourproject.models.IAccountModel;

public interface ManageAccounts {

    boolean createAccount(String cpf);

    boolean withdrawMoney(String accountNumber, double value);

    boolean depositMoney(String accountId, double value);

    String checkBalance(String accountId);

    boolean transferMoney(String accountId, String targetAccountId, double value);

    IAccountModel consultAccount(String accountId);

    boolean removeAccount(String accountId);

    boolean updateAccount(IAccountModel account);
}
