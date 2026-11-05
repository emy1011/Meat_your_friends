/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ecouteurs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author christophe.varnier
 */
public class EcouteurSouris implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("clique y " + e.getY()+"x " + e.getX());
        System.out.flush();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("presse y" + e.getY()+"x " + e.getX());
        System.out.flush();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(" relache y" + e.getY()+"x " + e.getX());
        System.out.flush();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("entree y" + e.getYOnScreen()+"x " + e.getXOnScreen());
        System.out.flush();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("sortie y" +e.getYOnScreen()+"x " + e.getXOnScreen());
        System.out.flush();
    }

}
