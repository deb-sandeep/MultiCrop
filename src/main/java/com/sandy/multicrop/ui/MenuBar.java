package com.sandy.multicrop.ui;

import java.awt.Toolkit ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.awt.event.KeyEvent ;

import javax.swing.JMenu ;
import javax.swing.JMenuBar ;
import javax.swing.JMenuItem ;
import javax.swing.KeyStroke ;

import com.sandy.common.bus.Event ;
import com.sandy.common.bus.EventSubscriber ;
import com.sandy.multicrop.EventType ;
import com.sandy.multicrop.MultiCrop ;

public class MenuBar extends JMenuBar implements EventSubscriber {
    
    private static final long serialVersionUID = 9062303552990293475L ;
    
    private MainFrame mainFrame = null ;
    
    private JMenuItem deleteMI = null ;
    private JMenuItem quitMI = null ;
    
    MenuBar( MainFrame frame ) {
        this.mainFrame = frame ;
        setUpUI() ;
        MultiCrop.BUS.addSubscriberForEventTypes( this, true, 
                                                 EventType.REGION_DESELECTED, 
                                                 EventType.REGION_SELECTED );
    }
    
    private void setUpUI() {
        
        createFileMenu() ;
        createEditMenu() ;
    }
    
    private void createFileMenu() {
        
        JMenu m = new JMenu( "File" ) ;
        int menuMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() ;
        
        quitMI = createMenuItem( "Quit", KeyEvent.VK_Q, menuMask, 
                                 new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                MenuBar.this.mainFrame.windowClosing() ;
            }
        } ) ;
        
        m.add( quitMI ) ;
        add( m ) ;
    }
    
    private void createEditMenu() {
        
        JMenu m = new JMenu( "Edit" ) ;
        
        deleteMI = createMenuItem( "Delete", KeyEvent.VK_DELETE, 0, 
                                   new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                MenuBar.this.mainFrame.deleteSelectedRegion() ;
            }
        } ) ;
        deleteMI.setEnabled( false ) ;
        
        m.add( deleteMI ) ;
        add( m ) ;
    }
    
    private JMenuItem createMenuItem( String name, 
                                      int keyStroke, int modifier,
                                      ActionListener listener ) {
        
        JMenuItem mi = new JMenuItem( name ) ;
        mi.setAccelerator( KeyStroke.getKeyStroke( keyStroke, modifier ) ) ;
        mi.addActionListener( listener ) ;
        return mi ;
    }

    @Override
    public void handleEvent( Event event ) {
        
        switch( event.getEventType() ) {
            case EventType.REGION_DESELECTED:
                deleteMI.setEnabled( false ) ;
                break ;
            case EventType.REGION_SELECTED:
                deleteMI.setEnabled( true ) ;
                break ;
        }
    }
}
