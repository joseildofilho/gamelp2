package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by root on 05/05/17.
 */
class Room implements Runnable {

    private Socket player1;
    private Socket player2;

    private DataOutputStream playerWriter1, playerWriter2;
    private DataInputStream playerReader1, playerReader2;

    private AtomicBoolean player1Connected, player2Connected;

    public Room(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
        try {
            playerWriter1 = new DataOutputStream(this.player1.getOutputStream());
            playerWriter2 = new DataOutputStream(this.player2.getOutputStream());
            playerReader1 = new DataInputStream(this.player1.getInputStream());
            playerReader2 = new DataInputStream(this.player2.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        player1Connected = new AtomicBoolean(true);
        player2Connected = new AtomicBoolean(true);
    }

    @Override
    public void run() {
        System.out.println("rodando Room");

        Thread t = new Thread(() -> {
            while (checkConnection()) {
                parser1To2();
            }
            if(!player1Connected.get()) System.out.println("Player 1 não esta mais conectado");
        });
        Thread t2 = new Thread(() -> {
            while (checkConnection()) {
                parser2To1();
            }
            if(!player2Connected.get()) System.out.println("Player 2 não esta mais conectado");
        });

        t.start();
        t2.start();
    }

    private void parser2To1() {
        String b = "";
        try {
            b = playerReader2.readUTF();
        } catch (IOException e) {
            player2Connected.set(false);
            b = "89999999";
        }
        try {
            playerWriter1.writeUTF(b);
            playerWriter1.flush();
        } catch (IOException e) {
            player1Connected.set(false);
        }
    }


    private void parser1To2() {
        String b = "";
        try {
            b = playerReader1.readUTF();
        } catch (IOException e) {
            player1Connected.set(false);
            b = "89999999";
        }
        try {
            playerWriter2.writeUTF(b);
            playerWriter2.flush();
        } catch (IOException e) {
            player2Connected.set(false);
        }
    }

    private boolean checkConnection() {
        return player1Connected.get() && player2Connected.get();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("terminando Room");
    }
}