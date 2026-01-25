package com.zachvoxwattz;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.junit.Test;

import com.zachvoxwattz.core.event_data_provider.EventDataProvider;
import com.zachvoxwattz.core.logging.LogSentry;

/**
 * Contains tests for {@code EventStringProvider} class.
 */
public class EventDataProviderTest {
    private final EventDataProvider eventStringProvider;

    public EventDataProviderTest() {
        LogSentry.initialize();
        this.eventStringProvider = new EventDataProvider();
    }

    /**
     * Tests to see if the event entries JSON file exists.
     */
    @Test
    public void JSONFileExists() {
        try {
            URL targetURL = this.getClass().getResource("/events/entries.json");
            File targetFile = new File(targetURL.getFile());

            assertTrue("JSON event registry found cannot be found!", targetFile.exists());
        }

        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Tests to see if entries are filled within {@code EventDataProvider} class maps.
     */
    @Test
    public void ShouldNotBeEmpty() {
        assertTrue("Event registry file has 0 entry!", this.eventStringProvider.getMapCount() > 0);
    }
}
