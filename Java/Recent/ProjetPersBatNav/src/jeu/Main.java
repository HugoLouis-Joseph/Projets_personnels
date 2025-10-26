package jeu;
// Imports //
import java.util.*;
import javax.swing.*;

import actions.*;
import actions.Action;
import game.*;
import board.*;

import java.awt.event.*;
// ------- //

/**
 * Public class for Main
 */
public class Main {
    public static void main(String[] args) {
        Game game = new Game(10, 10);

        Board B1 = game.getBoard1();
        Board B2 = game.getBoard2();


        int player = 0;
        ArrayList<Action> actionsJ1 = new ArrayList<Action>();
        actionsJ1.add(new Attack(B2));
        actionsJ1.add(new AttackPlus(B2,B1));
        actionsJ1.add(new MoveShip(B1));
        actionsJ1.add(new RotateShip(B1));
        actionsJ1.add(new Jam(B1));
        actionsJ1.add(new SendPlane(B1,B2));
        actionsJ1.add(new AttackAir(B1));

        ArrayList<Action> actionsJ2 = new ArrayList<Action>();
        actionsJ2.add(new Attack(B1));
        actionsJ2.add(new AttackPlus(B1,B2));
        actionsJ2.add(new MoveShip(B2));
        actionsJ2.add(new RotateShip(B2));
        actionsJ2.add(new Jam(B2));
        actionsJ2.add(new SendPlane(B2,B1));
        actionsJ2.add(new AttackAir(B2));

        game.placeRandom(0);
        game.placeRandom(1);

        JFrame frame = new JFrame("Jeu");
       // la fenêtre doit se fermer quand on clique sur la croix rouge
       frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       
       // On crée un nouvel affichage
       Board boards[] = {B1,B2};
       
       // on demande d'attribuer une taille minimale à la fenêtre
       //  (juste assez pour voir tous les composants)
       frame.setSize(916,439);

       /** */

       // on centre la fenêtre
       frame.setLocationRelativeTo(null);


       JFrame frame2 = new JFrame("Boutons d'action");
       frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       frame2.setLocationRelativeTo(frame);
       frame2.setLayout(null);

        Handler handler = new Handler();

        while (!game.gameOver()) {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new Panel(boards,player));
            frame.setVisible(true);

            frame2.getContentPane().removeAll();
            
            ArrayList<Action> possibleActions = new ArrayList<Action>();
            if (player == 0) {
                B1.changeSubStatus();
                B2.changeSubStatus();
                B1.movePlanes(B2);
                B2.movePlanes(B1);
                game.display(player);
                for (Action act : actionsJ1) {
                    if (act.canAct()) {
                        possibleActions.add(act);
                    }
                }
            } else {
                game.display(player);
                for (Action act : actionsJ2) {
                    if (act.canAct()) {
                        possibleActions.add(act);
                    }
                }
            }

            int offset = 0;
            for (Action act : possibleActions) {
                Bouton bouton = new Bouton(act.getName(),offset);
                bouton.setBounds(75*(offset++) , 0, 75, 75);
                bouton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handler.setChosen(bouton.getValue());
                    }
                }
                );
                frame2.add(bouton);
            }

            frame2.setSize(75*offset+15,134);
            frame2.setVisible(true);
            
            while (handler.getChosen() == -1) {
                System.out.print("");
            }

            possibleActions.get(handler.getChosen()).act(frame,frame2);

            handler.setChosen(-1);
            player = (player+1)%2;
        }

        game.display(player);
        System.out.println(game.gameOver());
    }

    public static int choose(int len , JFrame f) {
        JTextField field = new JTextField("0");
        field.setBounds(0, 75, 20, 20);
        Bouton incr = new Bouton("^",0);
        incr.setBounds(20, 75, 10, 10);
        Bouton decr = new Bouton("v",0);
        decr.setBounds(20, 85, 10, 10);
        Bouton valid = new Bouton("*",0);
        valid.setBounds(30,75,20,20);

        incr.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                    field.setText(Math.min(Math.max((Integer.parseInt(field.getText())+1),0),len) + "");
                    } catch (Exception ex) {
                        field.setText("0");
                    }
                }
            });

        decr.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                    field.setText(Math.max(Math.min((Integer.parseInt(field.getText())-1),len),0) + "");
                    } catch (Exception ex) {
                        field.setText("0");
                    }
                }
            });

        valid.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                valid.setValue(1);
            }
        });

        f.add(field);
        f.add(incr);
        f.add(decr);
        f.add(valid);
        f.repaint();

        while (valid.getValue() == 0) {
            System.out.print("");
        }

        f.remove(field);
        f.remove(incr);
        f.remove(decr);
        f.remove(valid);
        f.repaint();

        try {
        return Math.max(0,Math.min(Integer.parseInt(field.getText()),len));
        }
        catch (Exception e) {
            return 0;
        }
    }
}