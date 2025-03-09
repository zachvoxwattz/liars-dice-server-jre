package com.zachvoxwattz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.zachvoxwattz.core.event_manager.EventNameManager;
import com.zachvoxwattz.core.event_manager.type.RequestEventType;
import com.zachvoxwattz.core.event_manager.type.ResponseEventType;

@SuppressWarnings(value="static-access")
public class EventNameManagerTest {

    // Registers the EventNameManager extension
    @RegisterExtension
    static EventNameManager testEventNameManager = new EventNameManager();

    /**
     * Tests to throw {@code NoSuchElementException} for client request whenever an invalid type is used or unhandled.
     */
    @Test
    void NullOrInvalidCheckingClient() {
        var eventType = RequestEventType.NON_EXIST_TEST;
        Exception resultException = assertThrows(NoSuchElementException.class, () -> {
            testEventNameManager.getCLEvent(eventType);
        });

        var reportMessage = String.format("No event name found for request type '%s'", eventType.toString());
        assertEquals(resultException.getMessage(), reportMessage);
    }

    /**
     * Tests to throw {@code NoSuchElementException} for server response whenever an invalid type is used or unhandled.
     */
    @Test
    void NullOrInvalidCheckingServer() {
        var eventType = ResponseEventType.NON_EXIST_TEST;
        Exception resultException = assertThrows(NoSuchElementException.class, () -> {
            testEventNameManager.getSVEvent(eventType);
        });

        var reportMessage = String.format("No event name found for response type '%s'", eventType.toString());
        assertEquals(resultException.getMessage(), reportMessage);
    }
}
