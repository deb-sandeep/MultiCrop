package com.sandy.common.bus.tests;

import static org.hamcrest.CoreMatchers.is ;
import static org.hamcrest.CoreMatchers.not ;
import static org.hamcrest.CoreMatchers.notNullValue ;
import static org.junit.Assert.assertThat ;
import static org.junit.matchers.JUnitMatchers.hasItem ;
import static org.junit.matchers.JUnitMatchers.hasItems ;

import java.util.ArrayList ;
import java.util.List ;

import org.junit.After ;
import org.junit.Before ;
import org.junit.Test ;

import com.sandy.common.bus.Event ;
import com.sandy.common.bus.EventBus ;
import com.sandy.common.bus.EventSubscriber ;

public class EventBusTest {
    
    private class BaseSubscriber implements EventSubscriber {
        
        public List<Integer> eventValues = new ArrayList<Integer>() ;
        
        @Override public void handleEvent( Event event ) {
            eventValues.add( (Integer)event.getValue() ) ;
        }
    }
    
    private class EventSubscriberA extends BaseSubscriber {}
    private class EventSubscriberB extends BaseSubscriber {}
    
    private static int EVT_1 = 1 ;
    private static int EVT_2 = 2 ;
    
    private EventBus        bus = null ;
    private EventSubscriber esA = null ;
    private EventSubscriber esB = null ;
    
    private List<Integer> regEvents = null ;
    private List<EventSubscriber> regSubscribers = null ;
    
    @Before public void setUp() {
        bus = new EventBus() ;
        esA = new EventSubscriberA() ;
        esB = new EventSubscriberB() ;
        
        regEvents = null ;
        regSubscribers = null ;
    }
    
    @After public void tearDown() {
        bus.clear() ;
    }

    @Test public void addSubscriber() {
        
        bus.addSubscriberForEventTypes( esA, false, EVT_1, EVT_2 ) ;
        bus.addSubscriberForEventTypes( esA, false ) ;
        
        regEvents = bus.getRegisteredEventsForSubscriber( esA ) ;
        assertThat( regEvents, hasItems( EVT_1, EVT_2, EventBus.ALL_EVENTS ) ) ;
    }
    
    @Test public void addAsyncSubscriber() {
        
        bus.addSubscriberForEventTypes( esA, true, EVT_1 ) ;
        regEvents = bus.getRegisteredEventsForSubscriber( esA ) ;
        assertThat( regEvents, hasItems( EVT_1 ) ) ;
    }
    
    @Test public void addSubscriberMultipleTimesForSameEvents() {
        
        bus.addSubscriberForEventTypes( esA, false, EVT_1 ) ;
        bus.addSubscriberForEventTypes( esA, false, EVT_1 ) ;
        
        regSubscribers = bus.getSubscribersForEvent( EVT_1 ) ;
        
        assertThat( regSubscribers, is( notNullValue() ) ) ;
        assertThat( regSubscribers.size(), is( 1 ) ) ;
        assertThat( regSubscribers, hasItem( esA ) ) ;
    }
    
    @Test public void addSubscriberForEventRange() {
        
        bus.addSubscriberForEventRange( esA, false, 1, 10 ) ;
        bus.addSubscriberForEventTypes( esA, false, EVT_2 ) ;
        
        regSubscribers = bus.getSubscribersForEvent( 7 ) ;
        
        assertThat( regSubscribers, is( notNullValue() ) ) ;
        assertThat( regSubscribers.size(), is( 1 ) ) ;
        assertThat( regSubscribers, hasItem( esA ) ) ;
    }
    
    @Test public void getSubscribersForEvent() {
        
        bus.addSubscriberForEventTypes( esA, false, EVT_1 );
        bus.addSubscriberForEventTypes( esB, false );
        
        regSubscribers = bus.getSubscribersForEvent( EVT_1 ) ;
        assertThat( regSubscribers, hasItems( esA, esB ) ) ;
    }
    
    @Test public void removeSubscriber() {
        
        bus.addSubscriberForEventTypes( esA, false, EVT_1 );
        bus.addSubscriberForEventTypes( esB, false, EVT_1 );
        bus.addSubscriberForEventRange( esB, true, 3, 7 );
        
        bus.removeSubscriber( esB ) ;
        
        regSubscribers = bus.getSubscribersForEvent( EVT_1 ) ;
        assertThat( regSubscribers, is( notNullValue() ) ) ;
        assertThat( regSubscribers.size(), is( 1 ) ) ;
        assertThat( regSubscribers, not( hasItem( esB ) ) ) ;
    }
    
    @Test public void removeAsyncSubscriber() {
        
        bus.addSubscriberForEventTypes( esA, true, EVT_1 );
        bus.removeSubscriber( esA ) ;
        
        regSubscribers = bus.getSubscribersForEvent( EVT_1 ) ;
        assertThat( regSubscribers, is( notNullValue() ) ) ;
        assertThat( regSubscribers.size(), is( 0 ) ) ;
    }
    
    @Test public void removeAllSubscribers() {
        
        bus.addSubscriberForEventTypes( esA, true, EVT_1 );
        bus.addSubscriberForEventTypes( esB, false, EVT_2 );
        bus.addSubscriberForEventRange( esA, false, 0, 100 );
        
        bus.clear() ;
        
        regSubscribers = bus.getSubscribersForEvent( EVT_1 ) ;
        assertThat( regSubscribers.size(), is( 0 ) ) ;
        
        regSubscribers = bus.getSubscribersForEvent( EVT_2 ) ;
        assertThat( regSubscribers.size(), is( 0 ) ) ;
        
        regSubscribers = bus.getSubscribersForEvent( 100 ) ;
        assertThat( regSubscribers.size(), is( 0 ) ) ;
    }
    
    @Test public void publishAsynchronousEvents() {
        
        bus.addSubscriberForEventTypes( esA, true, EVT_1 );
        bus.addSubscriberForEventTypes( esA, false ) ;
        bus.publishEvent( EVT_1, 1 ) ;
        bus.publishEvent( EVT_1, 2 ) ;
        bus.publishEvent( EVT_2, 3 ) ;
        
        try { Thread.sleep( 500 ) ; } catch( InterruptedException e ){}
        
        assertThat( ((BaseSubscriber)esA).eventValues, hasItems( 1, 2, 3 ) );
    }
}
