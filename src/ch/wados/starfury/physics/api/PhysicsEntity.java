package ch.wados.starfury.physics.api;

import java.util.List;

import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

/**
 * Common base interface for all entity definitions involved in physics
 * calculations.
 * <p>
 * <strong> This interface should not be implemented outside the physics
 * module! </strong>
 * </p>
 * <p>
 * An entity represents a single rigid object that may or may not interact with
 * other objects and thereby form the smallest simulated unit in the physics
 * engine. (The only smaller unit is a convex collision hull. A single entity
 * may consist of any number of these shapes.). Entities can carry arbitrary
 * data that can be accessed through {@link #setUserData(Object)} and
 * {@link #getUserData()}. This may be used for any purpose, it is however
 * recommended to be consistent. An example use would be linking an entity to an
 * external controller class that controls the entity.
 * </p>
 *
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/14
 * @since StarFury 0.0.1
 * 
 * @see EntityType
 * @see PhysicsEntityDefinition
 * @see PhysicsManager
 */
public interface PhysicsEntity extends ListenerRelay {

	/**
	 * Adds a new fixture to the entity. Adding fixtures to entities produces
	 * quite some overhead since the mass distribution and all thrust points
	 * have to be recalculated. Use as little as possible.
	 * 
	 * @param fixture
	 *            the definition object for the new fixture. May not be
	 *            {@code null} or have an {@code id} that is already present in
	 *            this entity.
	 * @throws NullPointerException
	 *             if the {@code fixture} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@code fixture} contains invalid data like an already
	 *             present {@code id}.
	 */
	void addFixture(FixtureDefinition fixture);

	/**
	 * adds a new thrust point to the entity.
	 * 
	 * @param thruster
	 *            the definition object for the new thrust point. May not be
	 *            {@code null} or have an {@code id} that is already present in
	 *            this entity.
	 * @throws NullPointerException
	 *             if the {@code thruster} is {@code null}
	 * @throws IllegalArgumentException
	 *             if the {@code thruster} has an {@code id} that is already
	 *             present.
	 */
	void addThrustPoint(ThrustPointDefinition thruster);

	/**
	 * Applies a given force at a given position for given amount of time. Both
	 * the force and the position are given in the world frame of reference.
	 * 
	 * @param position
	 *            the position vector of the point the force acts on. May not be
	 *            {@code null}.
	 * @param force
	 *            the force vector that is applied. May not be {@code null} or
	 *            the zero vector.
	 * @param duration
	 *            the duration (in seconds) the force should be applied for. If
	 *            this is zero, the force is only applied for one physics step.
	 *            May not be negative or non-finite.
	 * @throws NullPointerException
	 *             if the {@code position} or {@code force} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@code force} is the zero vector or the
	 *             {@code duration} is negative or non-finite.
	 */
	void applyForce(Vector2 position, Vector2 force, double duration);

	/**
	 * Instantly applies an impulse (in Newton-seconds) to a given position.
	 * Both the impulse and the position are given in the world frame of
	 * reference.
	 * 
	 * @param position
	 *            the position vector of the point the impulse is applied to.
	 *            May not be {@code null}.
	 * @param impulse
	 *            the impulse vector that is applied. May not be {@code null} or
	 *            the zero vector.
	 * @throws NullPointerException
	 *             if the {@code position} or {@code force} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@code impulse} is the zero vector.
	 */
	void applyImpulse(Vector2 position, Vector2 impulse);

	/**
	 * @return the angular velocity in {@code rad/s}.
	 */
	double getAngularVelocity();

	/**
	 * @return the centre of mass in local coordinates.
	 */
	Vector2 getCenterOfMass();

	EntityType getEntityType();

	/**
	 * @return the number of fixtures currently present in the entity.
	 */
	int getFixtureCount();

	/**
	 * @return the linear velocity in {@code m/s}.
	 */
	Vector2 getLinearVelocity();

	/**
	 * Converts a point in world coordinates into local coordinates.
	 * 
	 * @param world
	 *            the world point.
	 * @return the point in local coordinates.
	 * @throws NullPointerException
	 *             if {@code local} is {@code null}.
	 */
	default Vector2 getLocalPoint(Vector2 world) {
		return this.getTransform().getInverseTransformed(world);
	}

	/**
	 * Converts a vector in world coordinates into local coordinates. Only the
	 * rotation is performed.
	 * 
	 * @param world
	 *            the world vector.
	 * @return the vector in local coordinates.
	 * @throws NullPointerException
	 *             if {@code world} is {@code null}.
	 */
	default Vector2 getLocalVector(Vector2 world) {
		return this.getTransform().getInverseTransformedR(world);
	}

	/**
	 * @return the mass of the entity. For {@link EntityType#STATIC STATIC}
	 *          entities zero is returned.
	 */
	double getMass();

	/**
	 * @return the orientation in radians.
	 */
	double getOrientation();

	/**
	 * @return the position of the centre of mass in world coordinates.
	 */
	Vector2 getPosition();

	/**
	 * @return a list with all shapes (including sensors) of the entity.
	 */
	List<Convex> getShapes();

	/**
	 * @return the number of thrust points currently present in the entity.
	 */
	int getThrustPointCount();

	/**
	 * @return the local-space-to-world-space {@link Transform} of this entity.
	 */
	Transform getTransform();

	/**
	 * @return the currently stored user data.
	 */
	Object getUserData();

	/**
	 * Converts a point in local coordinates into world coordinates.
	 * 
	 * @param local
	 *            the local point.
	 * @return the point in world coordinates.
	 * @throws NullPointerException
	 *             if {@code local} is {@code null}.
	 */
	default Vector2 getWorldPoint(Vector2 local) {
		return this.getTransform().getTransformed(local);
	}

	/**
	 * Converts a vector in local coordinates into world coordinates. Only the
	 * rotation is performed.
	 * 
	 * @param local
	 *            the local vector.
	 * @return the vector in world coordinates.
	 * @throws NullPointerException
	 *             if {@code local} is {@code null}.
	 */
	default Vector2 getWorldVector(Vector2 local) {
		return this.getTransform().getTransformedR(local);
	}

	/**
	 * Indicates if a fixture is a sensor.
	 * 
	 * @param id
	 *            the fixture to check. May not be {@code null} or an empty
	 *            String.
	 * @return {@code true} if it is a sensor, {@code false} otherwise.
	 * @throws NullPointerException
	 *             if the {@code id} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if no fixture exists with id {@code id} or {@code id} is an
	 *             empty String.
	 */
	boolean isSensor(String id);

	/**
	 * Removes a fixture from the entity. Removing fixtures from entities
	 * produces quite some overhead since the mass distribution and all thrust
	 * points have to be recalculated. Use as little as possible. If a fixture
	 * should only be removed temporarily and will be added soon after again
	 * (e.g. repairing damagable sections), consider using
	 * {@link #setSensor(String, boolean)}.
	 * 
	 * @param id
	 *            the id of the fixture to remove. If no matching fixture is
	 *            found, this is ignored. May not be {@code null} or an empty
	 *            String.
	 * @throws NullPointerException
	 *             if the {@code id} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@code id} is an empty String.
	 */
	void removeFixture(String id);

	/**
	 * Removes a thrust point from the entity.
	 * 
	 * @param id
	 *            the id of the thrust point to remove. If no matching thrust
	 *            point is found, this is ignored. May not be {@code null} or an
	 *            empty String.
	 * @throws NullPointerException
	 *             if the {@code id} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@code id} is an empty String.
	 */
	void removeThrustPoint(String id);

	/**
	 * Sets the angular velocity to a given value.
	 * 
	 * @param velocity
	 *            the angular velocity in {@code rad/s}. Must be finite.
	 * @throws IllegalArgumentException
	 *             if the {@code velocity} is not finite.
	 */
	void setAngularVelocity(double velocity);

	/**
	 * Sets the linear velocity to a given value.
	 * 
	 * @param velocity
	 *            the linear velocity in {@code m/s}. May not be {@code null}.
	 * @throws NullPointerException
	 *             if the {@code velocity} is {@code null}.
	 */
	void setLinearVelocity(Vector2 velocity);

	/**
	 * Sets the mass of the entity. The moment of inertia is scaled linearly
	 * with the mass.
	 * 
	 * @param mass
	 *            the new mass value. May not be negative or non-finite.
	 *            (Usually strictly positive).
	 * @throws IllegalArgumentException
	 *             if the mass value is invalid for the type of the entity.
	 */
	void setMass(double mass);

	/**
	 * Sets the orientation to a given value.
	 * 
	 * @param orientation
	 *            the orientation in radians.
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code orientation} is non-finite.
	 */
	void setOrientation(double orientation);

	/**
	 * Sets the position to a given value in world coordinates.
	 * 
	 * @param position
	 *            the position in world coordinates. May not be {@code null}.
	 * @throws NullPointerException
	 *             if the {@code position} is {@code null}.
	 */
	void setPosition(Vector2 position);

	/**
	 * Updates the sensor flag of a given fixture.
	 * 
	 * @param id
	 *            the fixture to update. May not be {@code null} or an empty
	 *            string.
	 * @param isSensor
	 *            {@code true} if the fixture should be a sensor, {@code false}
	 *            otherwise.
	 * @throws NullPointerException
	 *             if the {@code id} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@code id} is an empty String or no matching fixture
	 *             exists.
	 */
	void setSensor(String id, boolean isSensor);

	/**
	 * Sets the force applied at a thrust point. This force will continuously be
	 * applied until a new force value is set.
	 * 
	 * @param id
	 *            the identifier of the thrust point. May not be {@code null} or
	 *            an empty String.
	 * @param force
	 *            the force (in Newtons) to apply. If it is zero, the call is
	 *            ignored. May not be non-finite.
	 * @throws NullPointerException
	 *             if the {@code id} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@code id} is not known or an empty String. or the
	 *             {@code force} is non-finite.
	 */
	void setThrust(String id, double force);

	/**
	 * Sets the user data field.
	 * 
	 * @param data
	 *            the data to store.
	 */
	void setUserData(Object data);

}
