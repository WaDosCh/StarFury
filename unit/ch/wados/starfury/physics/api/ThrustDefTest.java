package ch.wados.starfury.physics.api;

import static org.junit.Assert.*;

import org.dyn4j.geometry.Vector2;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ThrustDefTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void consitancy() {
		Vector2 position = new Vector2(1, 2);
		Vector2 direction = new Vector2(3, 4);
		String id = "hi there";
		ThrustPointDefinition def = new ThrustPointDefinition(position, direction, id);
		assertEquals(position, def.getPosition());
		assertEquals(direction.getNormalized(), def.getDirection());
		assertEquals(id, def.getIdentifier());
	}

	@Test
	public void nullPosition() {
		thrown.expect(NullPointerException.class);
		new ThrustPointDefinition(null, new Vector2(1, 0), "hi");
	}

	@Test
	public void nullDirection() {
		thrown.expect(NullPointerException.class);
		new ThrustPointDefinition(new Vector2(2, 0), null, "hi");
	}

	@Test
	public void nullIdentifier() {
		thrown.expect(NullPointerException.class);
		new ThrustPointDefinition(new Vector2(2, 0), new Vector2(1, 2), null);
	}

	@Test
	public void zeroDirection() {
		thrown.expect(IllegalArgumentException.class);
		new ThrustPointDefinition(new Vector2(0, 0), new Vector2(0, 0), "hi");
	}

	@Test
	public void empyIdentifier() {
		thrown.expect(IllegalArgumentException.class);
		new ThrustPointDefinition(new Vector2(0, 0), new Vector2(1, 2), "");
	}

}
