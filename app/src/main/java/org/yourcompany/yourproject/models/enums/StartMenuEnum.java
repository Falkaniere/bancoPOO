package org.yourcompany.yourproject.models.enums;

public enum StartMenuEnum {
    GERENCIAR_CLIENTES(1),
    GERENCIAR_CONTAS(2),
    SAIR(3);

    private final int value;

    StartMenuEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StartMenuEnum fromValue(int value) {
        for (StartMenuEnum menuEnum : StartMenuEnum.values()) {
            if (menuEnum.getValue() == value) {
                return menuEnum;
            }
        }
        return null;
    }
}
