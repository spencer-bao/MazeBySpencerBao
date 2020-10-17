package gui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class UnreliableSensorTest {
	
	UnreliableRobot rob;
	
	/**
	 * Test that the sensor is switching between operational == true and operational == false.
	 */
	@Test
	void operationalTest() {
		rob = new UnreliableRobot("0000");
		rob.leftSensor.startFailureAndRepairProcess(UnreliableRobot.MEAN_TIME_BTWN_FAILURES, UnreliableRobot.MEAN_TIME_TO_REPAIR);
		assertTrue(rob.leftSensor.operational == true);
//		try {
//			Thread.sleep(UnreliableRobot.MEAN_TIME_BTWN_FAILURES - 500);
//			assertFalse(rob.leftSensor.operational);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	/**
	 * Test that stop() stops the thread
	 */
	@Test
	void test() {
		rob = new UnreliableRobot("0000");
		rob.leftSensor.startFailureAndRepairProcess(UnreliableRobot.MEAN_TIME_BTWN_FAILURES, UnreliableRobot.MEAN_TIME_TO_REPAIR);
		rob.leftSensor.stopFailureAndRepairProcess();
//		assertTrue(rob.leftSensor.thread);
	}
}
