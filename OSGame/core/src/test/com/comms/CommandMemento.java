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

/**
*
* Method: getData()
*
*/
@Test
public void testGetData() throws Exception {
//TODO: Test goes here...
}

/**
*
* Method: execute()
*
*/
@Test
public void testExecute() throws Exception {
//TODO: Test goes here...
}

@Test
public void testPacketize() throws Exception {
    GameID testID = new GameID();
    Direction testDir = Direction.NORTH;
    Command orig = new MoveCmd(testID, testDir);

    char[] data = orig.packetize();

    Command result = Command.parse(data);

    assertTrue("Packetization class test", result.getClass()==orig.getClass());
    System.out.println("Class type good");
    MoveCmd mres = (MoveCmd) result;
    assertTrue("Direction test", mres.getDirection().equals(testDir));
    System.out.println("Direction good");
    assertTrue("Actor ID test" , mres.getActorID().equals(testID));
    System.out.println("ID good");
}

}
