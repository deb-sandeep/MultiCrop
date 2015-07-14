package com.sandy.multicrop.ui ;

import java.awt.BorderLayout ;
import java.awt.event.WindowAdapter ;
import java.awt.event.WindowEvent ;

import javax.swing.JFrame ;

import org.apache.log4j.Logger ;

public class MainFrame extends JFrame {

    private static final Logger logger = Logger.getLogger( MainFrame.class ) ;

    private static final long   serialVersionUID = 5470525171405875394L ;
    
    public MainFrame() {
        
        super( "MultiCrop" ) ;
        setUpUI() ;
        setUpWindowListeners() ;
    }

    private void setUpUI() {
        
        setBounds( 100, 100, 400, 400 ) ;
        setJMenuBar( new MenuBar( this ) ) ;
        getContentPane().add( new ToolBar( this ), BorderLayout.NORTH ) ;
    }
    
    private void setUpWindowListeners() {
        
        this.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e ) {
                MainFrame.this.windowClosing() ;
            }
        } ) ;
    }
    
    void windowClosing() {
        
        logger.debug( "windowClosing" ) ;
        System.exit( 0 ) ;
    }
    
    void deleteSelectedRegion() {
        
        logger.debug( "deleteSelectedRegion" ) ;
    }
    
}
