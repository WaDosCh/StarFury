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
 * 
 * @see Convex
 * @see PhysicsEntity
 * @see CollisionListener
 */
public final class FixtureDefinition {

	private final Convex shape;
	private final double density;
	private final double friction;
	private final double restitution;
	private final String id;
	private final boolean isSensor;

	/**
	 * Creates a new FixtureDefinition instance for a given shape, density
	 * coefficient and identifier.
	 * 
	 * @param shape
	 *            the convex shape. May not be {@code null}.
	 * @param density
	 *            the density coefficient. Must be positive and finite.
	 * @param friction
	 *            the friction coefficient. Must be positive or zero and finite.
	 * @param restitution
	 *            the restitution coefficient. Must be in the range [0,1].
	 * @param isSensor
	 *            indicates if the fixture is a sensor.
	 * @param id
	 *            the identifier string. May be {@code null} if no identifier
	 *            should be used. May not be an empty String.
	 * @throws NullPointerException
	 *             if the {@code shape} parameter is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@code density} is negative or not finite or the
	 *             {@code id} is an empty String or the {@code friction} is
	 *             negative or not finite or the {@code restitution} is not in
	 *             the range [0,1].
	 */
	public FixtureDefinition(Convex shape, double density, double friction, double restitution, boolean isSensor,
			String id) {
		// validate
		if (shape == null)
			throw new NullPointerException("shape may not be null");
		if (density < 0 || !Double.isFinite(density))
			throw new IllegalArgumentException("density coefficient must be strictly positive. Was " + density);
		if (friction < 0 || !Double.isFinite(friction))
			throw new IllegalArgumentException("friction coefficient must be positive. Was " + density);
		if (restitution < 0 || restitution > 1)
			throw new IllegalArgumentException("restitution coefficient must be in the range [0,1]. Was " + density);
		if (id != null && id.isEmpty())
			throw new IllegalArgumentException("id may not be an empty String. use null instead");
		// apply values
		this.shape = shape;
		this.density = density;
		this.restitution = restitution;
		this.friction = friction;
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

	public double getFrictionCoefficient() {
		return this.friction;
	}

	public double getRestitutionCoefficient() {
		return this.restitution;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(density);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(friction);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isSensor ? 1231 : 1237);
		temp = Double.doubleToLongBits(restitution);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((shape == null) ? 0 : shape.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FixtureDefinition other = (FixtureDefinition) obj;
		if (Double.doubleToLongBits(density) != Double.doubleToLongBits(other.density))
			return false;
		if (Double.doubleToLongBits(friction) != Double.doubleToLongBits(other.friction))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isSensor != other.isSensor)
			return false;
		if (Double.doubleToLongBits(restitution) != Double.doubleToLongBits(other.restitution))
			return false;
		if (shape == null) {
			if (other.shape != null)
				return false;
		} else if (!shape.equals(other.shape))
			return false;
		return true;
	}

}
