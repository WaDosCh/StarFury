package ch.wados.starfury.physics.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.dyn4j.geometry.Vector2;

import ch.wados.starfury.util.Math;

/**
 * Definition object for physics entities. A physics entity is a single body in
 * the physics engine. A definition object contains all relevant information for
 * constructing an entity from scratch. A definition object can be used for
 * construction of multiple entities.
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
		this.linearDampening = 0;
		this.angularDampening = 0.01;
	}

	public PhysicsEntityDefinition(PhysicsEntityDefinition original) {
		Objects.requireNonNull(original);
		this.type = original.type;
		this.mass = original.mass;
		this.position = new Vector2(original.getPosition());
		this.orientation = original.orientation;
		this.fixtures = new ArrayList<>(original.fixtures);
		this.ro_fixtures = Collections.unmodifiableList(this.fixtures);
		this.thrustpoints = new ArrayList<>(original.thrustpoints);
		this.ro_thrust = Collections.unmodifiableList(this.thrustpoints);
		this.linearDampening = original.linearDampening;
		this.angularDampening = original.angularDampening;
	}

	// LOCK MANAGEMENT

	@Override
	public void lock() {
		this.isLocked = true;
	}

	@Override
	public boolean isLocked() {
		return this.isLocked;
	}

	// ACCESSORS

	public EntityType getType() {
		return type;
	}

	public double getMass() {
		return mass;
	}

	public List<FixtureDefinition> getFixtures() {
		return ro_fixtures;
	}

	public List<ThrustPointDefinition> getThrustPoints() {
		return ro_thrust;
	}

	public Vector2 getPosition() {
		return position;
	}

	public double getOrientation() {
		return orientation;
	}

	public double getLinearDampening() {
		return linearDampening;
	}

	public double getAngularDampening() {
		return angularDampening;
	}

	// SETTERS

	public PhysicsEntityDefinition setType(EntityType type) {
		this.enforceLock();
		Objects.requireNonNull(type);
		this.type = type;
		return this;
	}

	public PhysicsEntityDefinition setMass(double mass) {
		this.enforceLock();
		if (mass < 0)
			throw new IllegalArgumentException();
		this.mass = mass;
		return this;
	}

	public PhysicsEntityDefinition setPosition(Vector2 position) {
		this.enforceLock();
		Objects.requireNonNull(position);
		this.position = new Vector2(position);
		return this;
	}

	public PhysicsEntityDefinition setOrientation(double orientation) {
		this.enforceLock();
		this.orientation = Math.normalizedAngle(orientation);
		return this;
	}

	public PhysicsEntityDefinition setLinearDampening(double linearDampening) {
		this.enforceLock();
		this.linearDampening = linearDampening;
		return this;
	}

	public PhysicsEntityDefinition setAngularDampening(double angularDampening) {
		this.enforceLock();
		this.angularDampening = angularDampening;
		return this;
	}

	public PhysicsEntityDefinition setLocked(boolean isLocked) {
		this.enforceLock();
		this.isLocked = isLocked;
		return this;
	}

	public PhysicsEntityDefinition clearFixtures() {
		this.enforceLock();
		this.fixtures.clear();
		return this;
	}

	public PhysicsEntityDefinition clearThrusters() {
		this.enforceLock();
		this.thrustpoints.clear();
		return this;
	}

	private final Object THRUST_LOCK = new Object();
	private final Object FIXTURE_LOCK = new Object();

	public PhysicsEntityDefinition addThrustPoint(ThrustPointDefinition def) {
		this.enforceLock();
		Objects.requireNonNull(def);
		synchronized (THRUST_LOCK) {
			if (this.thrustpoints.stream().filter(t -> def.getIdentifier().equals(t.getIdentifier())).count() > 0)
				throw new IllegalArgumentException("duplicate id");
			this.thrustpoints.add(def);
		}
		return this;
	}

	public PhysicsEntityDefinition addFixture(FixtureDefinition def) {
		this.enforceLock();
		Objects.requireNonNull(def);
		synchronized (FIXTURE_LOCK) {
			if (def.getIdentifier() != null
					&& this.fixtures.stream().filter(f -> def.getIdentifier().equals(f.getIdentifier())).count() > 0)
				throw new IllegalArgumentException("duplicate id");
			this.fixtures.add(def);
		}
		return this;
	}

}