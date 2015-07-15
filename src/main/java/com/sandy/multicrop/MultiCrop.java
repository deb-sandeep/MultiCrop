package com.sandy.multicrop;

import com.sandy.common.bus.EventBus ;
import com.sandy.common.utils.UserPreferences ;
import com.sandy.multicrop.ui.MainFrame ;

public class MultiCrop {

    public static EventBus BUS = new EventBus() ;
    public static UserPreferences PREFS = null ;
    
    public static void main( String[] args ) throws Exception {
        
        PREFS = new UserPreferences( ".multicrop" ) ;
        
        MainFrame mf = new MainFrame() ;
        mf.setVisible( true ) ;
    }
}
