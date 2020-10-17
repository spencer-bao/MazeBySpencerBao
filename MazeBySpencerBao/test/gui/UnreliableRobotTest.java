package gui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UnreliableRobotTest {
	
	/**
	 * Test constructor with reliable sensor parameters
	 */
	@Test
	void contructorReliableSensorTest() {
		UnreliableRobot rob = new UnreliableRobot("1111");
		assertTrue(rob.leftSensor.getClass().equals(ReliableSensor.class));
		assertTrue(rob.rightSensor.getClass().equals(ReliableSensor.class));
		assertTrue(rob.forwardSensor.getClass().equals(ReliableSensor.class));
		assertTrue(rob.backwardSensor.getClass().equals(ReliableSensor.class));
	}
	
	/**
	 * Test constructor with unreliable sensor parameters
	 */
	@Test
	void contructorUnreliableSensorTest() {
		UnreliableRobot rob = new UnreliableRobot("0000");
		assertTrue(rob.leftSensor.getClass().equals(UnreliableSensor.class));
		assertTrue(rob.rightSensor.getClass().equals(UnreliableSensor.class));
		assertTrue(rob.forwardSensor.getClass().equals(UnreliableSensor.class));
		assertTrue(rob.backwardSensor.getClass().equals(UnreliableSensor.class));
	}
	
	/**
	 * Test constructor with unreliable and reliable sensor parameters
	 */
	@Test
	void contructorMixedSensorTest() {
		UnreliableRobot rob = new UnreliableRobot("1010");
		assertTrue(rob.forwardSensor.getClass().equals(ReliableSensor.class));
		assertTrue(rob.leftSensor.getClass().equals(UnreliableSensor.class));
		assertTrue(rob.rightSensor.getClass().equals(ReliableSensor.class));
		assertTrue(rob.backwardSensor.getClass().equals(UnreliableSensor.class));
	}
	
	/**
	 * Test constructor with incorrect sensor parameters
	 */
	@Test
	void contructorIncorrectSensorTest() {
		try {
			UnreliableRobot rob = new UnreliableRobot("S9U6");
			fail("Expected UnsupportedOperationException to be thrown.");
		} catch (UnsupportedOperationException e) {
		}
	}
	
	/**
	 * Test constructor with null parameter
	 */
	@Test
	void constructorNullParamTest() {
		UnreliableRobot rob = new UnreliableRobot(null);
		assertTrue(rob.leftSensor.getClass().equals(ReliableSensor.class));
		assertTrue(rob.rightSensor.getClass().equals(ReliableSensor.class));
		assertTrue(rob.forwardSensor.getClass().equals(ReliableSensor.class));
		assertTrue(rob.backwardSensor.getClass().equals(ReliableSensor.class));
	}
	
	
}

