package com.sandy.common.utils;

import java.io.File ;

import org.apache.commons.configuration.ConfigurationException ;
import org.apache.commons.configuration.PropertiesConfiguration ;

public class UserPreferences extends PropertiesConfiguration {
    
    private File prefFile = null ;

    public UserPreferences( String prefFileName ) throws ConfigurationException {
        
        prefFile = new File( System.getProperty( "user.home" ), prefFileName ) ;
        super.setFile( prefFile ) ;
        if( prefFile.exists() ) {
            super.load( prefFile ) ;
        }
        super.setAutoSave( true ) ;
    }
}
