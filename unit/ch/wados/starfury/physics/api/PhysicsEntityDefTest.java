package ch.wados.starfury.physics.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.dyn4j.Epsilon;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Vector2;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PhysicsEntityDefTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void consistancy() {
		Vector2 pos = new Vector2(1, 2);
		PhysicsEntityDefinition def = new PhysicsEntityDefinition(EntityType.BULLET, 1, pos, 1.3);
		// check raw data validity
		assertEquals(EntityType.BULLET, def.getEntityType());
		assertEquals(pos, def.getInitialPosition());
		assertEquals(1.0, def.getInitialMass(), Epsilon.E);
		assertEquals(1.3, def.getInitialOrientation(), Epsilon.E);
		assertTrue(def.getFixtures().isEmpty());
		assertTrue(def.getThrustPoints().isEmpty());
		// fixtures
		FixtureDefinition fix_0 = new FixtureDefinition(
				new Polygon(new Vector2(0, 0), new Vector2(1, 0), new Vector2(0, 1)), 0.5, 0.3, 0.6, true, "hi");
		FixtureDefinition fix_1 = new FixtureDefinition(
				new Polygon(new Vector2(0, 0), new Vector2(1, 1), new Vector2(0, 1)), 0.5, 0.3, 0.6, true, "hi there");
		def.addFixture(fix_0);
		def.addFixture(fix_1);
		List<FixtureDefinition> fixtures = def.getFixtures();
		assertEquals(2, fixtures.size());
		assertEquals(fix_0, fixtures.get(0));
		assertEquals(fix_1, fixtures.get(1));
		// thrust points
		ThrustPointDefinition thr_0 = new ThrustPointDefinition(new Vector2(1, 2), new Vector2(3, 4), "no_1");
		ThrustPointDefinition thr_1 = new ThrustPointDefinition(new Vector2(2, 3), new Vector2(3, 4), "no_2");
		def.addThrustPoint(thr_0);
		def.addThrustPoint(thr_1);
		List<ThrustPointDefinition> thrust = def.getThrustPoints();
		assertEquals(2, thrust.size());
		assertEquals(thr_0, thrust.get(0));
		assertEquals(thr_1, thrust.get(1));
	}

	@Test
	public void nullEntity() {
		thrown.expect(NullPointerException.class);
		new PhysicsEntityDefinition(null, 1, new Vector2(0, 0), 0);
	}

	@Test
	public void negativeMass() {
		thrown.expect(IllegalArgumentException.class);
		new PhysicsEntityDefinition(EntityType.BULLET, -1, new Vector2(0, 0), 0);
	}

	@Test
	public void infiniteMass() {
		thrown.expect(IllegalArgumentException.class);
		new PhysicsEntityDefinition(EntityType.BULLET, Double.POSITIVE_INFINITY, new Vector2(2, 0), 0);
	}

	@Test
	public void nullPosition() {
		thrown.expect(NullPointerException.class);
		new PhysicsEntityDefinition(EntityType.BULLET, 1, null, 1);
	}

	@Test
	public void infiniteOrientation() {
		thrown.expect(IllegalArgumentException.class);
		new PhysicsEntityDefinition(EntityType.BULLET, 1, null, Double.POSITIVE_INFINITY);
	}

	@Test
	public void shallowContentEquality() {
		PhysicsEntityDefinition def_0 = new PhysicsEntityDefinition(EntityType.BULLET, 1, new Vector2(0, 0), 0);
		PhysicsEntityDefinition def_1 = new PhysicsEntityDefinition(EntityType.BULLET, 1, new Vector2(0, 0), 0);
		PhysicsEntityDefinition def_2 = new PhysicsEntityDefinition(EntityType.DEFAULT, 1, new Vector2(0, 0), 0);
		PhysicsEntityDefinition def_3 = new PhysicsEntityDefinition(EntityType.DEFAULT, 2, new Vector2(0, 0), 0);
		PhysicsEntityDefinition def_4 = new PhysicsEntityDefinition(EntityType.DEFAULT, 2, new Vector2(1, 0), 0);
		PhysicsEntityDefinition def_5 = new PhysicsEntityDefinition(EntityType.DEFAULT, 2, new Vector2(1, 0), 1);
		assertEquals(def_0, def_1);
		assertNotEquals(def_1, def_2);
		assertNotEquals(def_2, def_3);
		assertNotEquals(def_3, def_4);
		assertNotEquals(def_4, def_5);
	}

	@Test
	public void deepFixtureEquality() {
		PhysicsEntityDefinition def_0 = new PhysicsEntityDefinition(EntityType.BULLET, 1, new Vector2(0, 0), 0);
		PhysicsEntityDefinition def_1 = new PhysicsEntityDefinition(EntityType.BULLET, 1, new Vector2(0, 0), 0);
		FixtureDefinition fix_0 = new FixtureDefinition(
				new Polygon(new Vector2(0, 0), new Vector2(1, 0), new Vector2(0, 1)), 1, 0.5, 0.5, true, "fix_0");
		FixtureDefinition fix_1 = new FixtureDefinition(
				new Polygon(new Vector2(0.5, 0), new Vector2(1, 0), new Vector2(0, 1)), 1, 0.5, 0.5, true, "fix_0");
		assertEquals(def_0, def_1);
		assertNotEquals(fix_0, fix_1);
		// add fix_0 to def_0
		def_0.addFixture(fix_0);
		assertNotEquals(def_0, def_1);
		// add fix_0 to def_1
		def_1.addFixture(fix_0);
		assertEquals(def_0, def_1);
		// add fix_1 to def_0
		def_0.addFixture(fix_1);
		assertNotEquals(def_0, def_1);
		// add fix_1 to def_1
		def_1.addFixture(fix_1);
		assertEquals(def_0, def_1);
	}

	@Test
	public void deepThrustEquality() {
		PhysicsEntityDefinition def_0 = new PhysicsEntityDefinition(EntityType.BULLET, 1, new Vector2(0, 0), 0);
		PhysicsEntityDefinition def_1 = new PhysicsEntityDefinition(EntityType.BULLET, 1, new Vector2(0, 0), 0);
		ThrustPointDefinition thr_0 = new ThrustPointDefinition(new Vector2(0, 0), new Vector2(1, 2), "thr_0");
		ThrustPointDefinition thr_1 = new ThrustPointDefinition(new Vector2(0, 0), new Vector2(1, 2), "thr_1");
		assertEquals(def_0, def_1);
		assertNotEquals(thr_0, thr_1);
		// add fix_0 to def_0
		def_0.addThrustPoint(thr_0);
		assertNotEquals(def_0, def_1);
		// add fix_0 to def_1
		def_1.addThrustPoint(thr_0);
		assertEquals(def_0, def_1);
		// add fix_1 to def_0
		def_0.addThrustPoint(thr_1);
		assertNotEquals(def_0, def_1);
		// add fix_1 to def_1
		def_1.addThrustPoint(thr_1);
		assertEquals(def_0, def_1);
	}

	@Test
	public void hashEquality() {
		PhysicsEntityDefinition def_0 = new PhysicsEntityDefinition(EntityType.BULLET, 1, new Vector2(0, 0), 0);
		PhysicsEntityDefinition def_1 = new PhysicsEntityDefinition(EntityType.BULLET, 1, new Vector2(0, 0), 0);
		FixtureDefinition fix_0 = new FixtureDefinition(
				new Polygon(new Vector2(0, 0), new Vector2(1, 0), new Vector2(0, 1)), 1, 0.5, 0.5, true, "fix_0");
		ThrustPointDefinition thr_0 = new ThrustPointDefinition(new Vector2(0, 0), new Vector2(1, 2), "hi");
		assertEquals(def_0.hashCode(), def_1.hashCode());
		def_0.addFixture(fix_0);
		assertEquals(def_0.hashCode(), def_1.hashCode());
		def_0.addThrustPoint(thr_0);
		assertEquals(def_0.hashCode(), def_1.hashCode());
	}

}