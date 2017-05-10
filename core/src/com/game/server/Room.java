package com.game.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by root on 05/05/17.
 */
class Room implements Runnable {

    private Socket player1;
    private Socket player2;

    public Room(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        System.out.println("rodando Room");
        try {
            PrintStream ps = new PrintStream(player1.getOutputStream());
            Scanner s = new Scanner(player2.getInputStream());
            ps.println(player2.getRemoteSocketAddress());
            ps.flush();
            ps.println(s.nextLine());
            ps.flush();
            ps = new PrintStream(player2.getOutputStream());
            s = new Scanner(player1.getInputStream());
            ps.println(player1.getRemoteSocketAddress());
            ps.flush();
            ps.println(s.nextLine());
            ps.flush();
            ps.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (player1.isConnected() && player2.isConnected()) {

        }
    }
}
