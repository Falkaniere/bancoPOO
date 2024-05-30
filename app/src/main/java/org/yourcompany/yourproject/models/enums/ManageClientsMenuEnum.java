package org.yourcompany.yourproject.models.enums;

public enum ManageClientsMenuEnum {
    CADASTRAR_CLIENTE(1),
    CONSULTAR_CLIENTE(2),
    REMOVER_CLIENTE(3),
    ATUALIZAR_CLIENTE(4),
    VOLTAR_AO_MENU_PRINCIPAL(5);

    private final int value;

    ManageClientsMenuEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ManageClientsMenuEnum fromValue(int value) {
        for (ManageClientsMenuEnum menuEnum : ManageClientsMenuEnum.values()) {
            if (menuEnum.getValue() == value) {
                return menuEnum;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
