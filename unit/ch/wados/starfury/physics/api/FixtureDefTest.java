package ch.wados.starfury.physics.api;

import static org.junit.Assert.*;

import org.dyn4j.Epsilon;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Vector2;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
		FixtureDefinition def = new FixtureDefinition(poly, 1, 0.5, 0.3, true, "hi there");
		// assert contents
		assertEquals(poly, def.getShape());
		assertEquals(1, def.getDensityCoefficient(), Epsilon.E);
		assertEquals(0.5, def.getFrictionCoefficient(), Epsilon.E);
		assertEquals(0.3, def.getRestitutionCoefficient(), Epsilon.E);
		assertTrue(def.isSensor());
		assertEquals("hi there", def.getIdentifier());
	}

	@Test
	public void nullConvex() {
		thrown.expect(NullPointerException.class);
		new FixtureDefinition(null, 1, 0.5, 0.4, true, "hi");
	}

	@Test
	public void negativeDensity() {
		thrown.expect(IllegalArgumentException.class);
		new FixtureDefinition(getPoly(), -1, 0.5, 0.4, true, "hi");
	}

	@Test
	public void zeroDensity() {
		assertEquals(0, new FixtureDefinition(getPoly(), 0, 1, 1, true, "hi").getDensityCoefficient(), Epsilon.E);
	}

	@Test
	public void infiniteDensity() {
		thrown.expect(IllegalArgumentException.class);
		new FixtureDefinition(getPoly(), Double.POSITIVE_INFINITY, 1, 1, true, "hi");
	}

	@Test
	public void negativeFriction() {
		thrown.expect(IllegalArgumentException.class);
		new FixtureDefinition(getPoly(), 2, -1, 0.4, true, "hi");
	}

	@Test
	public void zeroFriction() {
		assertEquals(0, new FixtureDefinition(getPoly(), 2, 0, 1, true, "hi").getFrictionCoefficient(), Epsilon.E);
	}

	@Test
	public void infiniteFriction() {
		thrown.expect(IllegalArgumentException.class);
		new FixtureDefinition(getPoly(), 1, Double.POSITIVE_INFINITY, 1, true, "hi");
	}

	@Test
	public void negativeRestitution() {
		thrown.expect(IllegalArgumentException.class);
		new FixtureDefinition(getPoly(), 2, 1, -2, true, "hi");
	}

	@Test
	public void largeRestitution() {
		thrown.expect(IllegalArgumentException.class);
		new FixtureDefinition(getPoly(), 2, 2, 2, true, "hi");
	}

	@Test
	public void nullIdentifier() {
		assertNull(new FixtureDefinition(getPoly(), 2, 0.5, 0.5, false, null).getIdentifier());
	}

	@Test
	public void emptyIdentifier() {
		thrown.expect(IllegalArgumentException.class);
		new FixtureDefinition(getPoly(), 2, 0.5, 0.5, false, "");
	}

}