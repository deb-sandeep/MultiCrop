package com.sandy.multicrop.util;

import java.io.File;
import java.net.URL;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

/**
 * The configuration manager for MultiCrop app. All the configuration entities 
 * are accessible by getter methods.
 * 
 * @author Sandeep
 */
public class ConfigManager{

    private static Logger log = Logger.getLogger(ConfigManager.class);
    
    private boolean showUsage            = false ;
    
    public boolean isShowUsage()           { return this.showUsage; }
    
    // ------------------------------------------------------------------------
    private Options clOptions = null ;
    private boolean logCLP    = false ;
    
    public ConfigManager( String[] args ) throws Exception {
        
        this.clOptions = prepareOptions() ;
        parseCLP( args ) ;
        if( this.showUsage )return ;
        
        PropertiesConfiguration propCfg = new PropertiesConfiguration() ;
        URL cfgURL = ConfigManager.class.getResource( "/config.properties" ) ;
        if( cfgURL == null ) {
            throw new Exception( "config.properties not found in classpath." ) ;
        }
        propCfg.load( cfgURL );
        parseConfig( propCfg ) ;
    }
    
    private void parseConfig( PropertiesConfiguration config ) 
        throws Exception {
        
    }
    
    protected File getMandatoryDirFromConfig( String key, 
                                            PropertiesConfiguration config ) 
        throws Exception {
        
        String path = getMandatoryConfig( key, config ) ;
        File file = new File( path ) ;
        if( !file.exists() ) {
            throw new Exception( "Folder referred to by " + key + 
                    " configuration does not exist." ) ;
        }
        return file ;
    }
    
    protected String getMandatoryConfig( String key, PropertiesConfiguration config ) 
        throws Exception {
        
        String value = config.getString( key ) ;
        if( StringUtil.isEmptyOrNull( value ) ) {
            throw new Exception( key + " configuration is missing." ) ;
        }
        return value ;
    }
    
    public void printUsage() {
        
        String usageStr = "MultiCrop [h]";
        
        HelpFormatter helpFormatter = new HelpFormatter() ;
        helpFormatter.printHelp( 80, usageStr, null, this.clOptions, null ) ;
    }

    private Options prepareOptions() {

        final Options options = new Options() ;
        options.addOption( "h", "Print this usage and exit." ) ;
        return options ;
    }
    
    private void parseCLP( String[] args ) throws Exception {

        if( this.logCLP ) {
            StringBuilder str = new StringBuilder() ;
            for( String arg : args ) {
                str.append( arg ).append( " " ) ;
            }
            log.debug( "Parsing CL args = " + str ) ;
        }
        
        try {
            CommandLine cmdLine = new DefaultParser().parse( this.clOptions, args ) ;
            
            if( cmdLine.hasOption( 'h' ) ) { this.showUsage = true ; }
        }
        catch ( Exception e ) {
            log.error( "Error parsing command line arguments.", e ) ;
            printUsage() ;
            throw e ;
        }
    }
}
