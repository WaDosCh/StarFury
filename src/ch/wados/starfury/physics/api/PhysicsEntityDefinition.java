package ch.wados.starfury.physics.api;

import java.util.ArrayList;
import java.util.List;

import org.dyn4j.geometry.Vector2;

/**
 * Definition object for physics entities. A physics entity is a single body in
 * the physics engine. A definition object contains all relevant information for
 * constructing an entity from scratch. A definition object can be used for
 * construction of multiple entities.
 * 
 * @author Andreas Wälchli
 * @version 1.2 - 2016/06/17
 * @since StarFury 0.0.1
 */
public final class PhysicsEntityDefinition {

	private final EntityType type;
	private final double mass;
	private final List<FixtureDefinition> fixtures;
	private final List<ThrustPointDefinition> thrustpoints;
	private final Vector2 position;
	private final double orientation;

	/**
	 * Creates a new PhysicsEntityDefinition instance from a given type, mass
	 * value, position and orientation.
	 * <p>
	 * Note that such an instance is not valid for entity creation. At least one
	 * {@link FixtureDefinition} must be added.
	 * </p>
	 * 
	 * @param type
	 *            the entity type. May not be {@code null}.
	 * @param mass
	 *            the entity mass. May not be negative and must be finite.
	 * @param position
	 *            the initial position of the entity. May not be {@code null}.
	 * @param orientation
	 *            the initial orientation of the entity. Measured in radians.
	 * @throws NullPointerException
	 *             if {@code type} or {@code position} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if {@code mass} is negative.
	 * @throws IllegalArgumentException
	 *             if {@code orientation} is non-finite.
	 */
	public PhysicsEntityDefinition(EntityType type, double mass, Vector2 position, double orientation) {
		// validate input
		if (type == null)
			throw new NullPointerException("type may not be null");
		if (mass < 0 || !Double.isFinite(mass))
			throw new IllegalArgumentException("mass must be non-negative and finite");
		if (!Double.isFinite(orientation))
			throw new IllegalArgumentException("orientation must be finite");
		if (position == null)
			throw new NullPointerException("position may not be null");
		// copy finals
		this.type = type;
		this.mass = mass;
		this.orientation = orientation;
		this.position = position;
		// initialise lists
		this.fixtures = new ArrayList<>();
		this.thrustpoints = new ArrayList<>();
	}

	/**
	 * Adds a fixture.
	 * 
	 * @param fixture
	 *            the fixture to add. May not be {@code null}.
	 * @return itself (for chaining).
	 * @throws NullPointerException
	 *             if {@code fixture} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@code fixture} has an identifier that is equal to
	 *             that of an already existing fixture.
	 */
	public PhysicsEntityDefinition addFixture(FixtureDefinition fixture) {
		// validate input
		if (fixture == null)
			throw new NullPointerException("fixture may not be null");
		if (fixture.getIdentifier() != null && this.fixtures.stream().map(FixtureDefinition::getIdentifier)
				.filter(f -> f.equals(fixture.getIdentifier())).count() > 0)
			throw new IllegalArgumentException("fixture identifier already in use");
		// add fixture
		this.fixtures.add(fixture);
		return this;
	}

	/**
	 * Adds a thrust point.
	 * 
	 * @param thrustpoint
	 *            the thrust point to add. May not be {@code null}.
	 * @return itself (for chaining).
	 * @throws NullPointerException
	 *             if {@code fixture} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@code thrustpoint} has an identifier that is equal to
	 *             that of an already existing thrust point.
	 */
	public PhysicsEntityDefinition addThrustPoint(ThrustPointDefinition thrustpoint) {
		// validate input
		if (thrustpoint == null)
			throw new NullPointerException("thrustpoint may not be null");
		if (thrustpoint.getIdentifier() != null && this.thrustpoints.stream().map(ThrustPointDefinition::getIdentifier)
				.filter(t -> t.equals(thrustpoint.getIdentifier())).count() > 0)
			throw new IllegalArgumentException("thrustpoint identifier already in use");
		// add thrustpoint
		this.thrustpoints.add(thrustpoint);
		return this;
	}

	public EntityType getEntityType() {
		return this.type;
	}

	public double getInitialMass() {
		return this.mass;
	}

	public List<FixtureDefinition> getFixtures() {
		return this.fixtures;
	}

	public Vector2 getInitialPosition() {
		return this.position;
	}

	public double getInitialOrientation() {
		return this.orientation;
	}

	public List<ThrustPointDefinition> getThrustPoints() {
		return this.thrustpoints;
	}

	/**
	 * The {@code hashCode} is not depending on the fixture and thrustPoint
	 * lists. Therefore if 2 {@code PhysicsEntityDefinitions} have the same
	 * parameters except for the fixture and thrustPoint lists, they will have
	 * the same hash. This also implies that adding fixtures or thrustPoints to
	 * an existing {@code PhysicsEntityDefinition} does not change its hash -
	 * useful if they are managed in hash maps.
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
		temp = Double.doubleToLongBits(mass);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(orientation);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
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
		PhysicsEntityDefinition other = (PhysicsEntityDefinition) obj;
		if (fixtures == null) {
			if (other.fixtures != null)
				return false;
		} else if (!fixtures.equals(other.fixtures))
			return false;
		if (Double.doubleToLongBits(mass) != Double.doubleToLongBits(other.mass))
			return false;
		if (Double.doubleToLongBits(orientation) != Double.doubleToLongBits(other.orientation))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (thrustpoints == null) {
			if (other.thrustpoints != null)
				return false;
		} else if (!thrustpoints.equals(other.thrustpoints))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}