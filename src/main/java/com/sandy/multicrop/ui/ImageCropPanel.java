package com.sandy.multicrop.ui;

import java.awt.BorderLayout ;
import java.io.File ;

import javax.swing.JPanel ;

public class ImageCropPanel extends JPanel {
    
    private File imgFile = null ;

    public ImageCropPanel( File newImgFile ) {
        this.imgFile = newImgFile ;
        setLayout( new BorderLayout() );
    }
    
    public boolean hasUnsavedChanges() {
        return true ;
    }
}
