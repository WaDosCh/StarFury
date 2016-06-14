package ch.wados.starfury.physics.api;

import org.dyn4j.geometry.Convex;

/**
 * Definition object for fixtures. A fixture represents the bridge between
 * geometry and entities. A single instance of this class may be used in the
 * construction of multiple entities.
 * <p>
 * A fixture definition consists of a {@link Convex} shape, an identifier and a
 * relative density coefficient. The identifier and the density coefficient are
 * both optional. An identifier allows later interactions with a fixture of a
 * {@link PhysicsEntity}. The identifier must be unique within an entity. The
 * density coefficient allows adjustment of the fixture density. The real
 * density is calculated from the entity mass, but the density coefficients of
 * the fixtures determine the mass distribution. If no custom mass distribution
 * is required the default density coefficient of 1 should be used. A fixture
 * can also be defined as a sensor. Collisions with sensors are not resolved,
 * but detected.
 * </p>
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/14
 * @since StarFury 0.0.1
 */
public final class FixtureDefinition {

	private final Convex shape;
	private final double density;
	private final String id;
	private final boolean isSensor;

	/**
	 * Creates a new FixtureDefinition instance for a given shape, density
	 * coefficient and identifier.
	 * 
	 * @param shape
	 *            the convex shape. May not be {@code null}
	 * @param density
	 *            the density coefficient. Must be strictly positive and finite.
	 * @param isSensor
	 *            indicates if the fixture is a sensor.
	 * @param id
	 *            the identifier string. May be {@code null} if no identifier
	 *            should be used.
	 * @throws NullPointerException
	 *             if the {@code shape} parameter is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@code density} is negative or not finite.
	 */
	public FixtureDefinition(Convex shape, double density, boolean isSensor, String id) {
		if (shape == null)
			throw new NullPointerException("shape may not be null");
		if (density <= 0 || !Double.isFinite(density))
			throw new IllegalArgumentException("density coefficient must be strictly positive. Was " + density);
		this.shape = shape;
		this.density = density;
		this.id = id;
		this.isSensor = isSensor;
	}

	public Convex getShape() {
		return this.shape;
	}

	public String getIdentifier() {
		return this.id;
	}

	public double getDensityCoefficient() {
		return this.density;
	}

	public boolean isSensor() {
		return this.isSensor;
	}
}
