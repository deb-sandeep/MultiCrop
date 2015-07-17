package com.sandy.multicrop.ui;

import java.awt.BorderLayout ;
import java.io.File ;

import javax.swing.JLabel ;
import javax.swing.JPanel ;
import javax.swing.JSlider ;
import javax.swing.event.ChangeEvent ;
import javax.swing.event.ChangeListener ;

import com.sandy.multicrop.App.Toolbar;

public class ImageCropPanel extends JPanel implements ChangeListener {
    
    private static final int SLIDER_MAX  = 1000 ;
    private static final int SLIDER_MIN  = 0 ;
    private static final int SLIDER_ZERO = 500 ;
    
    private static final float ZOOM_PER_SLIDER_UNIT = 0.01f ;
    
    private File          imgFile = null ;
    private JSlider       slider  = null ;
    private DrawingCanvas canvas  = null ;
    
    private float scaleFactor = 1.0f ;

    public ImageCropPanel( File newImgFile ) throws Exception {
        this.imgFile = newImgFile ;
        setUpUI() ;
    }
    
    private void setUpUI() throws Exception {
        setLayout( new BorderLayout() );

        slider = new JSlider( JSlider.VERTICAL ) ;
        slider.setMaximum( 1000 ) ;
        slider.setValue( 500 ) ;
        slider.addChangeListener( this ) ;
        
        canvas = new DrawingCanvas( new Toolbar(), imgFile ) ;
        
        add( slider, BorderLayout.WEST ) ;
        add( canvas, BorderLayout.CENTER ) ;
    }
    
    public boolean hasUnsavedChanges() {
        return false ;
    }

    @Override 
    public void stateChanged( ChangeEvent ce ) {
        this.scaleFactor = computeScaleFactor( slider.getValue() ) ;
        this.canvas.scaleImage( this.scaleFactor ) ;
    }
    
    private float computeScaleFactor( int sliderValue ) {
        
        int deltaVal = sliderValue - SLIDER_ZERO ;
        int absDelta = Math.abs( deltaVal ) ;
        
        float factor = 1.0f + absDelta*ZOOM_PER_SLIDER_UNIT ;
        if( deltaVal < 0 ) { factor = 1/factor ; }
        
        return factor ;
    }
}
