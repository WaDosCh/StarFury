package ch.wados.starfury.physics.api;

import static org.junit.Assert.*;

import org.dyn4j.geometry.Vector2;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link ThrustPointDefinition}
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016-06-17
 */
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

	@Test
	public void contentEquality() {
		ThrustPointDefinition point_0 = new ThrustPointDefinition(new Vector2(0, 1), new Vector2(2, 3), "point");
		ThrustPointDefinition point_1 = new ThrustPointDefinition(new Vector2(0, 1), new Vector2(4, 6), "point");
		ThrustPointDefinition point_2 = new ThrustPointDefinition(new Vector2(0, 2), new Vector2(2, 3), "point");
		ThrustPointDefinition point_3 = new ThrustPointDefinition(new Vector2(0, 2), new Vector2(2, 4), "point");
		ThrustPointDefinition point_4 = new ThrustPointDefinition(new Vector2(0, 2), new Vector2(2, 4), "another");
		// check equalities. stretching the direction should still equal.
		// All others should not.
		assertEquals(point_0, point_1);
		assertNotEquals(point_1, point_2);
		assertNotEquals(point_2, point_3);
		assertNotEquals(point_3, point_4);
	}

	@Test
	public void hashEquality() {
		ThrustPointDefinition point_0 = new ThrustPointDefinition(new Vector2(0, 1), new Vector2(2, 3), "point");
		ThrustPointDefinition point_1 = new ThrustPointDefinition(new Vector2(0, 1), new Vector2(4, 6), "point");
		ThrustPointDefinition point_2 = new ThrustPointDefinition(new Vector2(0, 2), new Vector2(2, 3), "point");
		// 0 and 1 should have same hash, 1 and 2 not
		assertEquals(point_0.hashCode(), point_1.hashCode());
		assertNotEquals(point_1.hashCode(), point_2.hashCode());
	}

}
