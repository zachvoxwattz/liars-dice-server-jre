package com.zachvoxwattz;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.zachvoxwattz.core.event_string.EventStringProvider;
import com.zachvoxwattz.core.event_string.type.ReqVar;
import com.zachvoxwattz.core.event_string.type.ResVar;

public class EventStringProviderTest {
    private EventStringProvider eventStringProvider;

    public EventStringProviderTest() {
        this.eventStringProvider = new EventStringProvider();
    }
    
    /**
     * Tests to see if null value is observed.
     */
    @Test
    public void NullOrInvalidCheckingClient() {
        assertNull(this.eventStringProvider.getCLEvent(ReqVar.NON_EXIST));
    }

    /**
     * Tests to see if null value is observed.
     */
    @Test
    public void NullOrInvalidCheckingServer() {
        assertNull(this.eventStringProvider.getSVEvent(ResVar.NON_EXIST));
    }
}
