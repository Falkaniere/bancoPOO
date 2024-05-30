package org.yourcompany.yourproject.services.scannerService;

import java.util.Scanner;

public class ScannerServiceImpl implements ScannerService {

    /**
     * inputScanner method is used to get input from the user
     *
     * @param message message to be displayed to the user
     * @return user input
     */
    @Override
    public String inputScanner(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine();
    }
}
