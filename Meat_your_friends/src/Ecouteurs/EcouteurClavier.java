/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ecouteurs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author christophe.varnier
 */
public class EcouteurClavier implements KeyListener {

    @Override
    public void keyPressed(KeyEvent event) {
        System.out.println("La touche " + event.getKeyCode() + " a été pressée, le caractère correspondant est " + event.getKeyChar());
        System.out.flush();
    }

    @Override
    public void keyTyped(KeyEvent event) {
        System.out.println("La touche " + event.getKeyCode() + " a été appuyée, le caractère correspondant est " + event.getKeyChar());
        System.out.flush();
    }

    @Override
    public void keyReleased(KeyEvent event) {
        System.out.println("La touche " + event.getKeyCode() + " a été relachée, le caractère correspondant est " + event.getKeyChar());
        System.out.flush();
    }

}
