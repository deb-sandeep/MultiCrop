package com.sandy.multicrop ;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class App {

    static public void main(String[] args) {
        
        JFrame frame = new JFrame("MultiCrop") ;
        Toolbar toolbar = new Toolbar() ;
        DrawingCanvas canvas = new DrawingCanvas(toolbar, 350, 350) ;
        frame.getContentPane().setLayout(new BorderLayout(4, 4)) ;
        frame.getContentPane().add(toolbar, BorderLayout.NORTH) ;
        frame.getContentPane().add(canvas, BorderLayout.CENTER) ;
        frame.setJMenuBar(createMenus(canvas)) ;

        frame.pack() ; 
        frame.setVisible(true) ; 

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0) ;
            }
        }) ;
    }

    static protected JMenuBar createMenus( final DrawingCanvas canvas ) {
        
        JMenuBar mb = new JMenuBar() ;
        JMenuItem mi ;
        int menuMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() ;

        JMenu m = new JMenu("File") ;

        m.add( mi = new JMenuItem("Quit") ) ;
        mi.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Q, menuMask) ) ;
        mi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0) ;
            }
        }) ;
        mb.add(m) ;

        m = new JMenu("Edit") ;
        m.add( mi = new JMenuItem("Delete") ) ;
        mi.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0) ) ;
        mi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                canvas.delete() ;
            }
        }) ;
        mb.add(m) ;

        return mb ;
    }
    
    static public class Toolbar extends JToolBar {
        
        private static final long serialVersionUID = 5449414159351731196L ;

        public static final int SELECT = 0, RECT = 1 ;

        protected JToggleButton[] toolButtons ;

        protected static final String[] toolNames = { "select", "rect" } ;

        public int getCurrentTool() {
            for (int i = 0 ; i < toolButtons.length ; i++)
                if (toolButtons[i].isSelected())
                    return i ;
            return -1 ;
        }

        public void setCurrentTool(int toolNum) {
            toolButtons[toolNum].setSelected(true) ;
        }

        public Toolbar() {
            
            ButtonGroup group = new ButtonGroup() ;
            toolButtons = new JToggleButton[toolNames.length] ;
            for( int i = 0 ; i < toolNames.length ; i++) {
                
                URL imgURL = App.class.getResource( 
                        "/com/sandy/multicrop/res/" + toolNames[i] + ".gif" ) ;
                
                toolButtons[i] = new JToggleButton(new ImageIcon( imgURL ), i == 0) ;
                toolButtons[i].setMargin( new Insets( 0, 0, 0, 0 ) ) ;
                group.add(toolButtons[i]) ;
                add(toolButtons[i]) ;
            }
        }
    }
}
