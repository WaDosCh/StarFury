package ch.wados.starfury.physics.simple;

import static org.junit.Assert.*;

import org.dyn4j.Epsilon;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ch.wados.starfury.physics.api.ThrustPointDefinition;

public class ThrusterSystemTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void negativeCapacity() {
		thrown.expect(IllegalArgumentException.class);
		new ThrusterSystem(-1);
	}

	@Test
	public void zeroResultsWhenEmpty() {
		ThrusterSystem s = new ThrusterSystem(10);
		assertEquals(0, s.getThrusterCount());
		assertEquals(0, s.getTorque().getTorque(), Epsilon.E);
		assertEquals(new Vector2(0, 0), s.getForce(Transform.IDENTITY).getForce());
	}

	@Test
	public void nullThruster() {
		thrown.expect(NullPointerException.class);
		new ThrusterSystem(10).addThruster(null);
	}

	@Test
	public void duplicateThrusterId() {
		ThrusterSystem s = new ThrusterSystem(10);
		s.addThruster(new ThrustPointDefinition(new Vector2(0, 0), new Vector2(0, 1), "t"));
		thrown.expect(IllegalArgumentException.class);
		s.addThruster(new ThrustPointDefinition(new Vector2(0, 0), new Vector2(0, 1), "t"));
	}

	@Test
	public void memberManagementUpdatesOutputs() {
		ThrusterSystem s = new ThrusterSystem(10);
		s.addThruster(new ThrustPointDefinition(new Vector2(1, 0), new Vector2(0, 1), "t0"));
		assertEquals(new Vector2(0, 0), s.getForce(Transform.IDENTITY).getForce());
		assertEquals(0, s.getTorque().getTorque(), Epsilon.E);
		s.setThrust("t0", 1);
		assertEquals(new Vector2(0, 1), s.getForce(Transform.IDENTITY).getForce());
		assertEquals(1, s.getTorque().getTorque(), Epsilon.E);
		s.addThruster(new ThrustPointDefinition(new Vector2(-1, 0), new Vector2(0, 1), "t1"));
		assertEquals(new Vector2(0, 1), s.getForce(Transform.IDENTITY).getForce());
		assertEquals(1, s.getTorque().getTorque(), Epsilon.E);
		s.setThrust("t1", 1);
		assertEquals(new Vector2(0, 2), s.getForce(Transform.IDENTITY).getForce());
		assertEquals(0, s.getTorque().getTorque(), Epsilon.E);
		s.removeThruster("t0");
		assertEquals(new Vector2(0, 1), s.getForce(Transform.IDENTITY).getForce());
		assertEquals(-1, s.getTorque().getTorque(), Epsilon.E);
		s.removeThruster("t1");
		assertEquals(new Vector2(0, 0), s.getForce(Transform.IDENTITY).getForce());
		assertEquals(0, s.getTorque().getTorque(), Epsilon.E);
	}

	@Test
	public void removingNull() {
		thrown.expect(NullPointerException.class);
		new ThrusterSystem(10).removeThruster(null);
	}

	@Test
	public void removingEmptyString() {
		thrown.expect(IllegalArgumentException.class);
		new ThrusterSystem(10).removeThruster("");
	}

	@Test
	public void removingKnownAndUnknownString() {
		ThrusterSystem s = new ThrusterSystem(10);
		s.addThruster(new ThrustPointDefinition(new Vector2(1, 0), new Vector2(0, 1), "t0"));
		// removing an unknown must not throw an exception!
		s.removeThruster("duba");
		assertEquals(1, s.getThrusterCount());
		// removing a known thruster must work
		s.removeThruster("t0");
		assertEquals(0, s.getThrusterCount());
	}

	@Test
	public void changing_CoM_works() {
		ThrusterSystem s = new ThrusterSystem(10);
		s.addThruster(new ThrustPointDefinition(new Vector2(1, 0), new Vector2(0, 1), "t0"));
		s.setThrust("t0", 1);
		assertEquals(new Vector2(0, 1), s.getForce(Transform.IDENTITY).getForce());
		assertEquals(1, s.getTorque().getTorque(), Epsilon.E);
		// change CoM
		s.update(new Vector2(1, 0));
		assertEquals(new Vector2(0, 1), s.getForce(Transform.IDENTITY).getForce());
		assertEquals(0, s.getTorque().getTorque(), Epsilon.E);
		// change CoM
		s.update(new Vector2(-1, 0));
		assertEquals(new Vector2(0, 1), s.getForce(Transform.IDENTITY).getForce());
		assertEquals(2, s.getTorque().getTorque(), Epsilon.E);
		// change CoM
		s.update(new Vector2(2, 0));
		assertEquals(new Vector2(0, 1), s.getForce(Transform.IDENTITY).getForce());
		assertEquals(-1, s.getTorque().getTorque(), Epsilon.E);
	}

	@Test
	public void settingNullThruster() {
		thrown.expect(NullPointerException.class);
		new ThrusterSystem(10).setThrust(null, 1);
	}

	@Test
	public void settingEmptyThruster() {
		thrown.expect(IllegalArgumentException.class);
		new ThrusterSystem(10).setThrust("", 1);
	}

	@Test
	public void settingKnownAndUnknownThruster() {
		ThrusterSystem s = new ThrusterSystem(10);
		s.addThruster(new ThrustPointDefinition(new Vector2(1, 0), new Vector2(0, 1), "t0"));
		s.setThrust("t0", 1);
		// throw exception here
		thrown.expect(IllegalArgumentException.class);
		s.setThrust("duba", 1);
	}

	@Test
	public void settingInfiniteThrust() {
		ThrusterSystem s = new ThrusterSystem(10);
		s.addThruster(new ThrustPointDefinition(new Vector2(1, 0), new Vector2(0, 1), "t0"));
		thrown.expect(IllegalArgumentException.class);
		s.setThrust("t0", Double.POSITIVE_INFINITY);
	}

}
