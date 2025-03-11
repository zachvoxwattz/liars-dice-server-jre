package com.zachvoxwattz;

import java.util.NoSuchElementException;

import org.junit.Test;

import com.zachvoxwattz.core.event_manager.EventNameManager;
import com.zachvoxwattz.core.event_manager.type.RequestEventType;
import com.zachvoxwattz.core.event_manager.type.ResponseEventType;

// @SuppressWarnings(value="static-access")
public class EventNameManagerTest {
    public EventNameManagerTest() {
        EventNameManager.initialize();
    }
    /**
     * Tests to throw {@code NoSuchElementException} for client request whenever an invalid type is used or unhandled.
     */
    @Test(expected = NoSuchElementException.class)
    public void NullOrInvalidCheckingClient() {
        EventNameManager.getCLEvent(RequestEventType.NON_EXIST_TEST);
    }

    /**
     * Tests to throw {@code NoSuchElementException} for server response whenever an invalid type is used or unhandled.
     */
    @Test(expected = NoSuchElementException.class)
    public void NullOrInvalidCheckingServer() {
        EventNameManager.getSVEvent(ResponseEventType.NON_EXIST_TEST);
    }
}
