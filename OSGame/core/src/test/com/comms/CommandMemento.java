package test.com.comms;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.comms.*;
import com.map.*;
/**
* DummyCmd Tester.
*
* @author <Authors name>
* @since <pre>Apr 15, 2015</pre>
* @version 1.0
*/
public class CommandMemento {

@Before
public void before() throws Exception {
}

@After
public void after() throws Exception {
}

@Test
public void testMoveCommandPacketize() throws Exception {
    GameID testID = new GameID();
    Direction testDir = Direction.NORTH;
    Command orig = new MoveCmd(testID, testDir);

    char[] data = orig.packetize();

    Command result = Command.parse(data);

    assertTrue("Packetization class test", result.getClass()==orig.getClass());
    MoveCmd mres = (MoveCmd) result;
    assertTrue("Direction test", mres.getDirection().equals(testDir));
    assertTrue("Actor ID test" , mres.getActorID().equals(testID));
}
@Test
public void testStopCommandPacketize() throws Exception {
    GameID testID = new GameID();
    Direction testDir = Direction.NORTH;
    Command orig = new StopCmd(testID, testDir);

    char[] data = orig.packetize();
    String transmitted = String.valueOf(data);
    char[] received = transmitted.toCharArray();

    Command result = Command.parse(received);

    assertTrue("Packetization class test", result.getClass()==orig.getClass());
    StopCmd mres = (StopCmd) result;
    assertTrue("Direction test", mres.getDirection().equals(testDir));
    assertTrue("Actor ID test" , mres.getActorID().equals(testID));
}


}
