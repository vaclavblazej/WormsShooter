/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.menu;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import objects.items.ComponentTableModel;

/**
 *
 * @author Pajcak
 */
public class GameWindowItemBar extends JToolBar {
    private static GameWindowItemBar instance;
    public static GameWindowItemBar getInstance() {
        if (instance == null) {
            instance = new GameWindowItemBar();
        }
        return instance;
    }
    
    public GameWindowItemBar() {
        setPreferredSize(new Dimension(800,26));
        setFloatable(false);
    }
    public void clearBar() {
        removeAll();
        this.repaint();
    }
    public void refreshBar(ComponentTableModel ctm) {
        clearBar();
        for (int i = 0; i < ctm.getRowCount(); i++) {
            JButton btn = new JButton(ctm.getValueAt(i,0).toString()+": "+ctm.getValueAt(i,1).toString());
            btn.setEnabled(false);UIManager.put("Button.disabledText",Color.BLACK); 
            add(btn);
        }
    }
}
