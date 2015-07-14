package com.sandy.multicrop.ui;

import java.awt.Color ;
import java.awt.Dimension ;
import java.awt.Graphics ;
import java.awt.Point ;
import java.awt.event.MouseAdapter ;
import java.awt.event.MouseEvent ;
import java.awt.event.MouseMotionListener ;
import java.util.ArrayList ;

import javax.swing.JPanel ;

import com.sandy.multicrop.App.Toolbar ;

public class DrawingCanvas extends JPanel {
    
    private static final long serialVersionUID = -5864412396440506797L;
    
    protected ArrayList<Rect> allShapes ; 
    protected Rect selectedShape ; 
    protected Toolbar toolbar ; 
    
    public DrawingCanvas(Toolbar tb, int width, int height) {
        
        this.toolbar       = tb ;
        this.allShapes     = new ArrayList<Rect>() ;
        this.selectedShape = null ;
        
        setPreferredSize( new Dimension(width, height) ) ;
        setBackground( Color.white ) ;
        
        CanvasMouseHandler handler = new CanvasMouseHandler() ;
        addMouseListener( handler ) ;
        addMouseMotionListener( handler ) ;
    }

    public void paintComponent( Graphics g ){
        
        super.paintComponent( g ) ;
        for( Rect rect : allShapes ) {
            rect.draw( g, g.getClipBounds() ) ;
        }
    }

    protected void setSelectedShape( Rect shapeToSelect ) {
        
        if( selectedShape != shapeToSelect ){ 
            if( selectedShape != null ) {
                selectedShape.setSelected( false ) ;
            }
            selectedShape = shapeToSelect ; 
            if( selectedShape != null ){
                shapeToSelect.setSelected( true ) ;
            }
        }
    }

    protected Rect shapeContainingPoint( Point pt ) {
        for( Rect r : allShapes ) {
            if( r.inside( pt ) ) {
                return r ;
            }
        }
        return null ;
    }

    public void delete() {
        
        if( selectedShape != null ) {
            for( int i = allShapes.size() - 1 ; i >= 0 ; i--) {
                Rect r = (Rect) allShapes.get(i) ;
                if( r == selectedShape) {
                    allShapes.remove(i) ;
                }
            }
            
            selectedShape = null ;
            super.repaint() ;
        }
    }
    
    // -------------------------------------------------------------------------
    protected class CanvasMouseHandler 
        extends MouseAdapter implements MouseMotionListener {
        
        static final int DRAG_NONE   = 0 ;
        static final int DRAG_CREATE = 1 ;
        static final int DRAG_RESIZE = 2 ;
        static final int DRAG_MOVE   = 3 ;
        
        Point dragAnchor ; 
        int dragStatus ;

        public void mousePressed( MouseEvent event ) {
            
            Rect clicked = null ;
            Point curPt = event.getPoint() ;

            if( toolbar.getCurrentTool() == Toolbar.SELECT ) {
                
                if( selectedShape != null && 
                    ( dragAnchor = selectedShape.getAnchorForResize( curPt ) ) != null) {
                    dragStatus = DRAG_RESIZE ; // drag will resize this shape
                } 
                else if( (clicked = shapeContainingPoint(curPt)) != null) { 
                    setSelectedShape( clicked ) ;
                    dragStatus = DRAG_MOVE ; 
                    dragAnchor = curPt ;
                } 
                else { 
                    setSelectedShape( null ) ;
                    dragStatus = DRAG_NONE ;
                }
            } 
            else {
                Rect newShape = new Rect( curPt, DrawingCanvas.this ) ; 
                allShapes.add( newShape ) ;
                setSelectedShape( newShape ) ;
                dragStatus = DRAG_CREATE ; 
                dragAnchor = curPt ;
            }
        }

        public void mouseDragged( MouseEvent event ) {
            
            Point curPt = event.getPoint() ;

            switch (dragStatus) {
                case DRAG_MOVE:
                    selectedShape.translate( curPt.x - dragAnchor.x, 
                                             curPt.y - dragAnchor.y) ;
                    dragAnchor = curPt ; 
                    break ;
                    
                case DRAG_CREATE:
                case DRAG_RESIZE:
                    selectedShape.resize( dragAnchor, curPt ) ;
                    break ;
            }
        }
    }
}
