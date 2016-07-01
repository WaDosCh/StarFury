package ch.wados.starfury.physics.api;

import org.dyn4j.geometry.Convex;

/**
 * Definition object for fixtures. A fixture represents the bridge between
 * geometry and entities. A single instance of this class may be used in the
 * construction of multiple entities.
 * <p>
 * Definitions can be build by setter chaining and copy constructor. If one
 * wants to make sure an instance can no longer be modified, it should be
 * {@link #lock() locked}.
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
 * @version 1.4 - 2016/06/30
 * @since StarFury 0.0.1
 * 
 * @see Convex
 * @see PhysicsEntity
 * @see CollisionListener
 * @see Lockable
 */
public final class FixtureDefinition implements Lockable {

	private Convex shape;
	private double density;
	private double friction;
	private double restitution;
	private String id;
	private boolean isSensor;
	// locking flag
	private boolean isLocked;

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
	 * @deprecated since 0.0.1 - use constructor
	 *             {@link #FixtureDefinition(Convex)} and setter chaining
	 */
	@Deprecated
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
		this.isLocked = false;
	}

	/**
	 * Creates a new FixtureDefinition instance for a given shape with default
	 * configuration. Change configuration with setters. The default values are:
	 * <ul>
	 * <li>{@code density} = 1</li>
	 * <li>{@code restitution} = 0 (ideal non-elastic body)</li>
	 * <li>{@code friction} = 0.2</li>
	 * <li>{@code id} = {@code null}</li>
	 * <li>{@code isSensor} = {@code false}</li>
	 * </ul>
	 * 
	 * @param shape
	 *            the convex shape. May not be {@code null}.
	 * @throws NullPointerException
	 *             if the {@code shape} is {@code null}.
	 * @since 1.3 (StarFury 0.0.1)
	 */
	public FixtureDefinition(Convex shape) {
		// validate
		if (shape == null)
			throw new NullPointerException("shape may not be null");
		// initialise
		this.shape = shape;
		this.density = 1.0;
		this.friction = 0.2;
		this.restitution = 0.0;
		this.id = null;
		this.isSensor = false;
		this.isLocked = false;
	}

	/**
	 * Copy constructor. Creates a shallow copy of a given definition. The new
	 * instance will always be unlocked.
	 * 
	 * @param def
	 *            the original definition. May not be {@code null}.
	 * @throws NullPointerException
	 *             if the {@code def} is {@code null}.
	 * @since 1.3 (StarFury 0.0.1)
	 */
	public FixtureDefinition(FixtureDefinition def) {
		// validate
		if (def == null)
			throw new NullPointerException("original def may not be null");
		// copy
		this.shape = def.shape;
		this.density = def.density;
		this.friction = def.friction;
		this.restitution = def.restitution;
		this.id = def.id;
		this.isSensor = def.isSensor;
		this.isLocked = false;
	}

	/**
	 * Locks the instance. After a call to this method any attempt to modify the
	 * object through a setter will result in an {@link IllegalStateException}.
	 * 
	 * @since 1.3 (StarFury 0.0.1)
	 */
	public void lock() {
		this.isLocked = true;
	}

	/**
	 * sets the shape
	 * 
	 * @param shape
	 *            the convex shape. May not be {@code null}.
	 * @return itself
	 * @throws NullPointerException
	 *             if the {@code shape} is {@code null}.
	 * @throws IllegalStateException
	 *             if the instance is locked.
	 * @since 1.3 (StarFury 0.0.1)
	 */
	public FixtureDefinition setShape(Convex shape) {
		this.enforceLock();
		// validate
		if (shape == null)
			throw new NullPointerException("shape may not be null");
		// update
		this.shape = shape;
		return this;
	}

	/**
	 * sets the density coefficient
	 * 
	 * @param density
	 *            the density coefficient. Must be positive or zero and finite.
	 * @return itself
	 * @throws IllegalArgumentException
	 *             if the density coefficient is negative or not finite.
	 * @throws IllegalStateException
	 *             if the instance is locked;
	 * @since 1.3 (StarFury 0.0.1)
	 */
	public FixtureDefinition setDensityCoefficient(double density) {
		this.enforceLock();
		// validate
		if (density < 0 || !Double.isFinite(density))
			throw new IllegalArgumentException("density coefficient must be positive. Was " + density);
		// update
		this.density = density;
		return this;
	}

	/**
	 * 
	 * @param friction
	 *            the friction coefficient. Must be positive or zero and finite.
	 * @return itself
	 * @throws IllegalArgumentException
	 *             if the {@code friction} is negative or not finite.
	 * @throws IllegalStateException
	 *             if the instance is locked.
	 * @since 1.3 (StarFury 0.0.1)
	 */
	public FixtureDefinition setFrictionCoefficient(double friction) {
		this.enforceLock();
		// validate
		if (friction < 0 || !Double.isFinite(friction))
			throw new IllegalArgumentException("friction coefficient must be positive. Was " + density);
		// update
		this.friction = friction;
		return this;
	}

	/**
	 * sets the restitution coefficient.
	 * 
	 * @param restitution
	 *            the restitution coefficient. Must be in the range [0,1].
	 * @return itself
	 * @throws IllegalArgumentException
	 *             if the restitution coefficient is no in range [0,1].
	 * @throws IllegalStateException
	 *             if the instance is locked.
	 * @since 1.3 (StarFury 0.0.1)
	 */
	public FixtureDefinition setRestitutionCoefficient(double restitution) {
		this.enforceLock();
		// validate
		if (restitution < 0 || restitution > 1)
			throw new IllegalArgumentException("restitution coefficient must be in the range [0,1]. Was " + density);
		// update
		this.restitution = restitution;
		return this;
	}

	/**
	 * sets the identifier.
	 * 
	 * @param id
	 *            the identifier string. May be {@code null} if no identifier
	 *            should be used. May not be an empty String.
	 * @return itself
	 * @throws IllegalArgumentException
	 *             if the {@code id} is an empty string.
	 * @throws IllegalStateException
	 *             if the instance is locked.
	 * @since 1.3 (StarFury 0.0.1)
	 */
	public FixtureDefinition setIdentifier(String id) {
		this.enforceLock();
		// validate
		if (id != null && id.isEmpty())
			throw new IllegalArgumentException("id may not be an empty String. use null instead");
		// update
		this.id = id;
		return this;
	}

	/**
	 * sets the sensor flag
	 * 
	 * @param isSensor
	 *            the sensor flag.
	 * @return itself
	 * @throws IllegalStateException
	 *             if the instance is locked.
	 * @since 1.3 (StarFury 0.0.1)
	 */
	public FixtureDefinition setSensor(boolean isSensor) {
		this.enforceLock();
		this.isSensor = isSensor;
		return this;
	}

	/**
	 * indicates the locking state of the instance.
	 * 
	 * @return {@code true} if the instance is locked.
	 * @since 1.3 (StarFury 0.0.1)
	 */
	public boolean isLocked() {
		return this.isLocked;
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

	/**
	 * Provides a hash value for this definition.
	 * 
	 * <p>
	 * Note that the hash is depending on internal data, therefore it may change
	 * when changing internal data. For that reason care must be taken when
	 * using hash maps. When in doubt consider using the copy constructor or
	 * locking the instance with {@link #lock()}.
	 * </p>
	 * 
	 * {@inheritDoc}
	 * 
	 * @since 1.2 (StarFury 0.0.1)
	 */
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

	/**
	 * Tests object equality.
	 * 
	 * <p>
	 * Does not check the locking state of instances. Therefore a locked
	 * instance holding the same data as an unlocked one is considered equal to
	 * it.
	 * </p>
	 * 
	 * {@inheritDoc}
	 * 
	 * @since 1.2 (StarFury 0.0.1)
	 */
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
