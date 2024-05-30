package org.yourcompany.yourproject.models.enums;

public enum ManageAccountsMenuEnum {

    CRIAR_CONTA(1),
    SACAR_DINHEIRO(2),
    DEPOSITAR_DINHEIRO(3),
    CONSULTAR_SALDO(4),
    TRANSFERIR_DINHEIRO(5),
    VOLTAR_AO_MENU_PRINCIPAL(6);

    private final int value;

    ManageAccountsMenuEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ManageAccountsMenuEnum fromValue(int value) {
        for (ManageAccountsMenuEnum menuEnum : ManageAccountsMenuEnum.values()) {
            if (menuEnum.getValue() == value) {
                return menuEnum;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
