package com.sandy.multicrop;

import com.sandy.common.bus.EventBus ;
import com.sandy.multicrop.ui.MainFrame ;

public class MultiCrop {

    public static EventBus BUS = new EventBus() ;
    
    public static void main( String[] args ) {
        
        MainFrame mf = new MainFrame() ;
        mf.setVisible( true ) ;
    }
}
