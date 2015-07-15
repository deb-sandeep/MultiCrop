package com.sandy.multicrop.ui ;

import java.awt.BorderLayout ;
import java.awt.FileDialog ;
import java.awt.event.WindowAdapter ;
import java.awt.event.WindowEvent ;
import java.io.File ;

import javax.swing.JFrame ;
import javax.swing.JOptionPane ;

import org.apache.log4j.Logger ;

import com.sandy.multicrop.MultiCrop ;
import com.sandy.multicrop.util.UserPreferenceKey ;

public class MainFrame extends JFrame {

    private static final Logger logger = Logger.getLogger( MainFrame.class ) ;

    private static final long serialVersionUID = 5470525171405875394L ;
    
    private FileDialog fileDialog = null ;
    private ImageCropPanel imageCropPanel = null ;
    
    public MainFrame() {
        
        super( "MultiCrop" ) ;
        setUpUI() ;
        setUpWindowListeners() ;
    }

    private void setUpUI() {
        
        getContentPane().setLayout( new BorderLayout() ) ;
        setBounds( 100, 100, 400, 400 ) ;
        setJMenuBar( new MenuBar( this ) ) ;
        getContentPane().add( new ToolBar( this ), BorderLayout.NORTH ) ;
        
        fileDialog = new FileDialog( this ) ;
        String lastLoadDir = MultiCrop.PREFS.getString( 
                                           UserPreferenceKey.LAST_IMG_LOAD_DIR, 
                                           System.getProperty( "user.home" ) ) ;
        fileDialog.setDirectory( lastLoadDir ) ;
        fileDialog.setMode( FileDialog.LOAD ) ;
        fileDialog.setModal( true ) ;
        fileDialog.setMultipleMode( false ) ;
    }
    
    private void setUpWindowListeners() {
        
        this.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e ) {
                MainFrame.this.windowClosing() ;
            }
        } ) ;
    }
    
    void createNewDocument() {
        
        fileDialog.setVisible( true ) ;
        String selDirectory = fileDialog.getDirectory() ;
        String selFile      = fileDialog.getFile() ;
        
        if( selFile != null ) {
            File file = new File( selDirectory, selFile ) ;
            MultiCrop.PREFS.setProperty( UserPreferenceKey.LAST_IMG_LOAD_DIR, 
                                         file.getParent() );
            
            if( imageCropPanel != null ) {
                if( imageCropPanel.hasUnsavedChanges() ) {
                    if( userIsOkToDiscardChanges() ) {
                        getContentPane().remove( imageCropPanel ) ;
                        imageCropPanel = null ;
                    }
                }
                else {
                    getContentPane().remove( imageCropPanel ) ;
                    imageCropPanel = null ;
                }
            }
            
            if( imageCropPanel == null ) {
                imageCropPanel = new ImageCropPanel( file ) ;
                getContentPane().add( imageCropPanel, BorderLayout.CENTER ) ;
                revalidate() ;
            }
        }
    }
    
    private boolean userIsOkToDiscardChanges() {
        int choice = JOptionPane.showConfirmDialog( this, 
                   "There are unsaved changes. Do you want to discard them?" ) ;
        return choice == JOptionPane.OK_OPTION ;
    }
    
    void windowClosing() {
        if( imageCropPanel != null ) {
            if( imageCropPanel.hasUnsavedChanges() ) {
                if( !userIsOkToDiscardChanges() ) {
                    return ;
                }
            }
        }
        System.exit( 0 ) ;
    }
    
    void deleteSelectedRegion() {
        logger.debug( "deleteSelectedRegion" ) ;
    }
}
