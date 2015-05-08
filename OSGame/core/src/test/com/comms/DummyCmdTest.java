package test.com.comms;

import com.comms.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class DummyCmdTest
{
    /**
     * Method: getData()
     */
    @Test
    public void testGetData() throws Exception
    {
        DummyCmd dummyCmd = new DummyCmd();
        assertEquals("com.comms.DummyCmd:test", new String(dummyCmd.getData()));
    }
} 
