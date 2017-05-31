package com.game.Connection;

import com.game.bullet.Bullets;
import com.game.game.Ship;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by root on 10/04/17.
 */
public class Connection {
    // ip referente ao servidor de lobby
    private static final String IP_SERVE = "localhost";
    private int port = 2000;
    private Socket socket;
    private String result;
    private DataInputStream dis;
    private DataOutputStream dos;

    /**
     * Protocolo:
     * posicao 1,4 é o numero da posição x, posicao 5,8 é o numero da posição y
     * posição 0,7 valor tudo 9 = confirmada conexão
     */
    public Connection(String name) throws IOException, InterruptedException {
        socket = new Socket(IP_SERVE, this.port);
        socket.setTcpNoDelay(true);

        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());

    }

    public synchronized void updateShip(Ship ship) {
        String s = null;
        try {
            s = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (s.startsWith("1"))
            Bullets.shoot(Integer.parseInt(s.substring(1, 4)), Integer.parseInt(s.substring(5, 8)), false);
        if (s.startsWith("8"))
            System.out.println("O Outro usuario saiu");
        if (s.startsWith("7"))
            System.out.println("Voce Ganhou");

        //é plausivel de erro visto que havera perca de informação com relação conversão dos float para int
        //todo o calculo para ajuste das naves em ambas as telas é feito aqui, claramente uma gambiarra, favor refatorar no futuro
        ship.setPosition(600 - ship.getWidth() - Integer.parseInt(s.substring(1, 4)), 700 - ship.getHeight() - Integer.parseInt(s.substring(5, 8)));

    }

    public synchronized void sendDataShip(Ship ship) {
        //todo jogar essa tralha para dentro da classe ship
        String result = (9000 + ((int) ship.getX())) + "" + (9000 + ((int) ship.getY()));
        try {
            dos.writeUTF(result);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendDataShoot(Ship ship) {
        String result = (1000 + ((int) ship.getX())) + "" + (9000 + ((int) ship.getY()));
        try {
            dos.writeUTF(result);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sayVictory() {
        try {
            dos.writeUTF("79999999");
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized String getResult() {
        return result;
    }

    private void setResult(String result) {
        this.result = result;
    }
}
