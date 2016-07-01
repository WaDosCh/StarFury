package ch.wados.starfury.physics.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dyn4j.geometry.Vector2;

/**
 * Definition object for physics entities. A physics entity is a single body in
 * the physics engine. A definition object contains all relevant information for
 * constructing an entity from scratch. A definition object can be used for
 * construction of multiple entities.
 * 
 * @author Andreas Wälchli
 * @version 1.3 - 2016/07/01
 * @since StarFury 0.0.1
 * 
 * @see Lockable
 */
public final class PhysicsEntityDefinition implements Lockable {

	private EntityType type;
	private double mass;
	private List<FixtureDefinition> fixtures;
	private List<ThrustPointDefinition> thrustpoints;
	private Vector2 position;
	private double orientation;
	private double linearDampening;
	private double angularDampening;
	// read-only list access
	private final List<FixtureDefinition> ro_fixtures;
	private final List<ThrustPointDefinition> ro_thrust;
	// locking flat
	private boolean isLocked = false;

	public PhysicsEntityDefinition(EntityType type) {
		// validate
		if (type == null)
			throw new NullPointerException("type may not be null");
		// construct
		this.type = type;
		this.mass = 1;
		this.position = new Vector2(0, 0);
		this.orientation = 0;
		this.fixtures = new ArrayList<>();
		this.thrustpoints = new ArrayList<>();
		this.ro_fixtures = Collections.unmodifiableList(this.fixtures);
		this.ro_thrust = Collections.unmodifiableList(this.thrustpoints);
	}

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
	 * @deprecated since 0.0.1 - use constructor
	 *             {@link #PhysicsEntityDefinition(EntityType)} instead.
	 */
	@Deprecated
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
		this.ro_fixtures = Collections.unmodifiableList(this.fixtures);
		this.ro_thrust = Collections.unmodifiableList(this.thrustpoints);
		this.angularDampening = 0.02;
		this.linearDampening = 0;
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
		this.enforceLock();
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
		this.enforceLock();
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
		return this.ro_fixtures;
	}

	public Vector2 getInitialPosition() {
		return this.position;
	}

	public double getInitialOrientation() {
		return this.orientation;
	}

	public List<ThrustPointDefinition> getThrustPoints() {
		return this.ro_thrust;
	}

	@Override
	public void lock() {
		this.isLocked = true;
	}

	@Override
	public boolean isLocked() {
		return this.isLocked;
	}

}