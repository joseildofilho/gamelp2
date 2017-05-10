package com.game.Connection;

import java.io.IOException;

/**
 * Created by root on 07/05/17.
 */
public class TestConnection {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection c1 = new Connection("120");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection c2 = new Connection("1");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
//        c1.updateShip();
        //      c2.recive();
    }
}
