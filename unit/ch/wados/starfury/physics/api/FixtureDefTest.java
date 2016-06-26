package ch.wados.starfury.physics.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.dyn4j.Epsilon;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Vector2;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link FixtureDefinition}
 * 
 * @author Andreas Wälchli
 * @version 1.2 - 2016/06/26
 */
public class FixtureDefTest {

	private Convex getPoly() {
		return new Polygon(new Vector2(0, 0), new Vector2(1, 0), new Vector2(0, 1));
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void consitancy() {
		Convex poly = getPoly();
		// test valid object creation
		FixtureDefinition def = new FixtureDefinition(poly).setDensityCoefficient(1).setFrictionCoefficient(0.5)
				.setRestitutionCoefficient(0.3).setSensor(true).setIdentifier("hi there");
		// assert contents
		assertEquals(poly, def.getShape());
		assertEquals(1, def.getDensityCoefficient(), Epsilon.E);
		assertEquals(0.5, def.getFrictionCoefficient(), Epsilon.E);
		assertEquals(0.3, def.getRestitutionCoefficient(), Epsilon.E);
		assertTrue(def.isSensor());
		assertEquals("hi there", def.getIdentifier());
	}

	@Test
	public void nullConvexConstructor() {
		thrown.expect(NullPointerException.class);
		new FixtureDefinition((Convex) null);
	}

	@Test
	public void nullConvexSetter() {
		thrown.expect(NullPointerException.class);
		new FixtureDefinition(getPoly()).setShape(null);
	}

	@Test
	public void negativeDensity() {
		thrown.expect(IllegalArgumentException.class);
		new FixtureDefinition(getPoly()).setDensityCoefficient(-1);
	}

	@Test
	public void zeroDensity() {
		assertEquals(0, new FixtureDefinition(getPoly()).setDensityCoefficient(0).getDensityCoefficient(), Epsilon.E);
	}

	@Test
	public void infiniteDensity() {
		thrown.expect(IllegalArgumentException.class);
		new FixtureDefinition(getPoly()).setDensityCoefficient(Double.POSITIVE_INFINITY);
	}

	@Test
	public void negativeFriction() {
		thrown.expect(IllegalArgumentException.class);
		new FixtureDefinition(getPoly()).setFrictionCoefficient(-1);
	}

	@Test
	public void zeroFriction() {
		assertEquals(0, new FixtureDefinition(getPoly()).setFrictionCoefficient(0).getFrictionCoefficient(), Epsilon.E);
	}

	@Test
	public void infiniteFriction() {
		thrown.expect(IllegalArgumentException.class);
		new FixtureDefinition(getPoly()).setFrictionCoefficient(Double.POSITIVE_INFINITY);
	}

	@Test
	public void negativeRestitution() {
		thrown.expect(IllegalArgumentException.class);
		new FixtureDefinition(getPoly()).setRestitutionCoefficient(-2);
	}

	@Test
	public void largeRestitution() {
		thrown.expect(IllegalArgumentException.class);
		new FixtureDefinition(getPoly()).setRestitutionCoefficient(2);
	}

	@Test
	public void nullIdentifier() {
		assertNull(new FixtureDefinition(getPoly()).setIdentifier(null).getIdentifier());
	}

	@Test
	public void emptyIdentifier() {
		thrown.expect(IllegalArgumentException.class);
		new FixtureDefinition(getPoly()).setIdentifier("");
	}

	@Test
	public void contentEquality() {
		Convex poly = getPoly();
		FixtureDefinition fix_0 = new FixtureDefinition(poly).setDensityCoefficient(2).setFrictionCoefficient(0.5)
				.setRestitutionCoefficient(0.4).setSensor(false).setIdentifier("hi");
		FixtureDefinition fix_1 = new FixtureDefinition(fix_0);
		FixtureDefinition fix_2 = new FixtureDefinition(fix_1)
				.setShape(new Polygon(new Vector2(0, 0), new Vector2(1, 0), new Vector2(0, 1)));
		FixtureDefinition fix_3 = new FixtureDefinition(fix_2).setDensityCoefficient(1);
		FixtureDefinition fix_4 = new FixtureDefinition(fix_3).setFrictionCoefficient(0.4);
		FixtureDefinition fix_5 = new FixtureDefinition(fix_4).setRestitutionCoefficient(0.3);
		FixtureDefinition fix_6 = new FixtureDefinition(fix_5).setSensor(true);
		FixtureDefinition fix_7 = new FixtureDefinition(fix_6).setIdentifier("test");
		fix_1.lock();
		// check equalities fix_0 and fix_1 are equal, all others not
		assertEquals(fix_0, fix_1);
		assertNotEquals(fix_1, fix_2);
		assertNotEquals(fix_2, fix_3);
		assertNotEquals(fix_3, fix_4);
		assertNotEquals(fix_4, fix_5);
		assertNotEquals(fix_5, fix_6);
		assertNotEquals(fix_6, fix_7);
	}

	@Test
	public void hashEquality() {
		Convex poly = getPoly();
		FixtureDefinition fix_0 = new FixtureDefinition(poly);
		FixtureDefinition fix_1 = new FixtureDefinition(fix_0);
		FixtureDefinition fix_2 = new FixtureDefinition(fix_0).setSensor(true);
		// 0 == 1, 0 != 1
		assertEquals(fix_0.hashCode(), fix_1.hashCode());
		assertNotEquals(fix_1.hashCode(), fix_2.hashCode());
	}

	@Test
	public void lockedIdentifier() {
		FixtureDefinition fix = new FixtureDefinition(getPoly());
		fix.lock();
		thrown.expect(IllegalStateException.class);
		fix.setIdentifier("hi");
	}

	@Test
	public void lockedShape() {
		FixtureDefinition fix = new FixtureDefinition(getPoly());
		fix.lock();
		thrown.expect(IllegalStateException.class);
		fix.setShape(getPoly());
	}

	@Test
	public void lockedSensor() {
		FixtureDefinition fix = new FixtureDefinition(getPoly());
		fix.lock();
		thrown.expect(IllegalStateException.class);
		fix.setSensor(true);
	}

	@Test
	public void lockedFriction() {
		FixtureDefinition fix = new FixtureDefinition(getPoly());
		fix.lock();
		thrown.expect(IllegalStateException.class);
		fix.setFrictionCoefficient(0.4);
	}

	@Test
	public void lockedRestitution() {
		FixtureDefinition fix = new FixtureDefinition(getPoly());
		fix.lock();
		thrown.expect(IllegalStateException.class);
		fix.setRestitutionCoefficient(0.4);
	}

	@Test
	public void lockedDensity() {
		FixtureDefinition fix = new FixtureDefinition(getPoly());
		fix.lock();
		thrown.expect(IllegalStateException.class);
		fix.setDensityCoefficient(0.4);
	}

	@Test
	public void nullCopyConstructor() {
		thrown.expect(NullPointerException.class);
		new FixtureDefinition((FixtureDefinition) null);
	}

}