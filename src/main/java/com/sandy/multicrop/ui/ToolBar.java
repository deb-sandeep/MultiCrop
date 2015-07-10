package com.sandy.multicrop.ui;

import java.awt.Insets ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.net.URL ;

import javax.swing.ButtonGroup ;
import javax.swing.ImageIcon ;
import javax.swing.JToggleButton ;
import javax.swing.JToolBar ;

import org.apache.log4j.Logger ;

import com.sandy.multicrop.App ;

public class ToolBar extends JToolBar implements ActionListener {

    private static final long serialVersionUID = 2912847362555482858L ;
    
    private static final Logger logger = Logger.getLogger(  ToolBar.class ) ;
    
    public static final String AC_SELECT = "SELECT" ;
    public static final String AC_MARK   = "MARK" ;
    
    ToolBar( MainFrame frame ) {
        setUpUI() ;
    }
    
    private void setUpUI() {
        
        ButtonGroup group = new ButtonGroup() ;
        add( createToggleButton( group, "select", AC_SELECT ) ) ;
        add( createToggleButton( group, "rect",   AC_MARK ) ) ;
    }
    
    private JToggleButton createToggleButton( ButtonGroup group, 
                                              String imgName, String actCmd ) {
        
        URL imgURL = App.class.getResource( 
                              "/com/sandy/multicrop/res/" + imgName + ".gif" ) ;
        
        JToggleButton tglBtn = new JToggleButton( new ImageIcon( imgURL ) ) ;
        tglBtn.setActionCommand( actCmd ) ;
        tglBtn.addActionListener( this ) ;
        tglBtn.setMargin( new Insets( 0, 0, 0, 0 ) ) ;
        group.add( tglBtn );
        
        return tglBtn ;
    }

    @Override
    public void actionPerformed( ActionEvent ae ) {
        
        String actCmd = ae.getActionCommand() ;
        logger.debug( "Tool " + actCmd + " selected." ) ;
    }
}
