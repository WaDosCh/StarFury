package ch.wados.starfury.physics.simple;

import static org.junit.Assert.*;

import org.dyn4j.Epsilon;
import org.dyn4j.geometry.Vector2;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ch.wados.starfury.physics.api.ThrustPointDefinition;

public class ThrusterTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void nullDefinition() {
		thrown.expect(NullPointerException.class);
		new Thruster(null);
	}

	@Test
	public void linearConsistancy() {
		Thruster t = new Thruster(new ThrustPointDefinition(new Vector2(0, 0), new Vector2(1, 0), "t"));
		assertEquals(new Vector2(0, 0), t.getForce());
		// apply thrust
		t.setThrust(1);
		assertEquals(new Vector2(1, 0), t.getForce());
		// apply thrust
		t.setThrust(-1);
		assertEquals(new Vector2(-1, 0), t.getForce());
	}

	@Test
	public void linearForceIndependantFromOffset() {
		Thruster t = new Thruster(new ThrustPointDefinition(new Vector2(0, 0), new Vector2(1, 0), "t"));
		t.setThrust(1);
		assertEquals(new Vector2(1, 0), t.getForce());
		// update CoM
		t.update(new Vector2(1, 1));
		assertEquals(new Vector2(1, 0), t.getForce());
		// update again
		t.update(new Vector2(-1, 19));
		assertEquals(new Vector2(1, 0), t.getForce());
	}

	@Test
	public void noTorqueByDefault() {
		Thruster t = new Thruster(new ThrustPointDefinition(new Vector2(0, 1), new Vector2(1, 0), "t"));
		t.setThrust(1);
		assertEquals(0, t.getTorque(), Epsilon.E);
	}

	@Test
	public void angularConsistancy() {
		Thruster t = new Thruster(new ThrustPointDefinition(new Vector2(1, 0), new Vector2(0, 1), "t"));
		t.update(new Vector2(0, 0));
		t.setThrust(1);
		assertEquals(1, t.getTorque(), Epsilon.E);
		t.setThrust(-1);
		assertEquals(-1, t.getTorque(), Epsilon.E);
	}
	
	@Test
	public void torqueAdjustedToOffset() {
		Thruster t = new Thruster(new ThrustPointDefinition(new Vector2(1, 0), new Vector2(0, 1), "t"));
		t.update(new Vector2(0, 0));
		t.setThrust(1);
		assertEquals(1, t.getTorque(), Epsilon.E);
		// update CoM
		t.update(new Vector2(-1, 0));
		assertEquals(2, t.getTorque(), Epsilon.E);
		// update CoM
		t.update(new Vector2(1, 0));
		assertEquals(0, t.getTorque(), Epsilon.E);
		// update CoM
		t.update(new Vector2(2, 0));
		assertEquals(-1, t.getTorque(), Epsilon.E);
		// update CoM
		t.update(new Vector2(0, 10));
		assertEquals(1, t.getTorque(), Epsilon.E);
		// update CoM
		t.update(new Vector2(1, 10));
		assertEquals(0, t.getTorque(), Epsilon.E);
	}

}
