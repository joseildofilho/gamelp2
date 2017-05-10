package com.game.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by root on 07/05/17.
 */
public class TestServer {
    public static void main(String[] args) throws IOException {
        Socket socket1 = new Socket("localhost",2000);
        Socket socket2 = new Socket("localhost",2000);
        Scanner scan1 = new Scanner(socket1.getInputStream());
        Scanner scan2 = new Scanner(socket2.getInputStream());

        System.out.println(scan1.nextLine());

        System.out.println(scan2.nextLine());
    }
}
