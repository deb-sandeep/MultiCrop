package com.sandy.multicrop.ui;

import java.awt.Toolkit ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.awt.event.KeyEvent ;

import javax.swing.JMenu ;
import javax.swing.JMenuBar ;
import javax.swing.JMenuItem ;
import javax.swing.KeyStroke ;

public class MenuBar extends JMenuBar {
    
    private static final long serialVersionUID = 9062303552990293475L ;
    
    private MainFrame mainFrame = null ;
    
    MenuBar( MainFrame frame ) {
        this.mainFrame = frame ;
        setUpUI() ;
    }
    
    private void setUpUI() {
        
        createFileMenu() ;
        createEditMenu() ;
    }
    
    private void createFileMenu() {
        
        JMenu m = new JMenu( "File" ) ;
        int menuMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() ;
        
        addMenuItem( m, "Quit", KeyEvent.VK_Q, menuMask, new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                MenuBar.this.mainFrame.windowClosing() ;
            }
        } ) ;
        add( m ) ;
    }
    
    private void createEditMenu() {
        
        JMenu m = new JMenu( "Edit" ) ;
        
        addMenuItem( m, "Delete", KeyEvent.VK_DELETE, 0, new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                MenuBar.this.mainFrame.deleteSelectedRegion() ;
            }
        } ) ;
        add( m ) ;
    }
    
    private void addMenuItem( JMenu menu, String name, 
                              int keyStroke, int modifier,
                              ActionListener listener ) {
        
        JMenuItem mi = new JMenuItem( name ) ;
        mi.setAccelerator( KeyStroke.getKeyStroke( keyStroke, modifier ) ) ;
        mi.addActionListener( listener ) ;
        menu.add( mi ) ;
    }
}
