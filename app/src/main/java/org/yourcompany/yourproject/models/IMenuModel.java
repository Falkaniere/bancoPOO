package org.yourcompany.yourproject.models;

import org.yourcompany.yourproject.models.enums.ManageAccountsMenuEnum;
import org.yourcompany.yourproject.models.enums.ManageClientsMenuEnum;
import org.yourcompany.yourproject.models.enums.StartMenuEnum;
import org.yourcompany.yourproject.services.filesService.FileService;

public interface IMenuModel {

    StartMenuEnum onStartMenu();

    ManageClientsMenuEnum onManageClientsMenu(FileService fileService);

    ManageAccountsMenuEnum onManageAccountsMenu(FileService fileService);
}
