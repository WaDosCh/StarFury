package ch.wados.starfury.physics.api;

import java.util.List;

import org.dyn4j.geometry.Vector2;

/**
 * A PhysicsManager handles all interaction between the physics engine and all
 * other modules. It serves as an abstraction layer to enable flexible physics
 * implementation changes.
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/14
 * @since StarFury 0.0.1
 */
public interface PhysicsManager extends Listenable {

	/**
	 * Creates a new entity based off the provided
	 * {@link PhysicsEntityDefinition} and returns the produced
	 * {@link PhysicsEntity} instance. Note that the returned entity must not be
	 * identical to the internally used entity, but must at least keep the
	 * feedback data it provides in sync with the internal object. The created
	 * entity is not directly added the world. Therefore (if memory is no
	 * concern) entities can be preinitialised through this method. This method
	 * should be implemented thread-safe, so that it can be used for concurrent
	 * entity initialisation in a parallelised environment.
	 * 
	 * @param definition
	 *            the definition object the entity should be based off. May not
	 *            be {@code null}.
	 * @return the entity
	 * @throws NullPointerException
	 *             if the {@code definition} parameter is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the initialisation fails due to illegal contents of the
	 *             definition.
	 */
	PhysicsEntity createEntity(PhysicsEntityDefinition definition);

	/**
	 * Despawns the given {@link PhysicsEntity} from the world. The entity
	 * remains valid and can be respawned at any point.
	 * 
	 * @param entity
	 *            the entity to despawn. May not be {@code null} and the entity
	 *            must be currently spawned.
	 * @throws NullPointerException
	 *             if the {@code entity} parameter is {@code null}.
	 * @throws IllegalStateException
	 *             if the {@code entity} is not present in the world.
	 * @throws IllegalStateException
	 *             if the world has not been initialised.
	 */
	void despawnEntity(PhysicsEntity entity);

	/**
	 * @return the world gravity.
	 * 
	 * @throws IllegalStateException
	 *             if the world is not initialised.
	 */
	Vector2 getGravity();

	/**
	 * Initialises the internal physics engine with a given gravity vector.
	 * 
	 * @param gravity
	 *            the gravity vector. May not be {@code null}. Use the zero
	 *            vector if no gravity is wanted.
	 * @throws NullPointerException
	 *             if the {@code gravity} is {@code null}.
	 * @throws IllegalStateException
	 *             if the world has already been initialised.
	 * 
	 * @see #initialiseWorld(Vector2, int)
	 */
	void initialiseWorld(Vector2 gravity);

	/**
	 * Sets the world gravity.
	 * 
	 * @param gravity
	 *            the gravity vector. May not be {@code null}. If no gravity is
	 *            wanted use the zero vector.
	 * @throws NullPointerException
	 *             if the {@code gravity} is {@code null}.
	 * @throws IllegalStateException
	 *             if the world is not initialised.
	 */
	void setGravity(Vector2 gravity);

	/**
	 * Spawns the given {@link PhysicsEntity} in the world.
	 * 
	 * @param entity
	 *            the entity to spawn. May not be {@code null} or an already
	 *            spawned entity.
	 * @throws NullPointerException
	 *             if the {@code entity} parameter is {@code null}.
	 * @throws IllegalStateException
	 *             if the {@code entity} is already present in the world.
	 * @throws IllegalArgumentException
	 *             if the {@code entity} is illegal or unsupported in any other
	 *             way.
	 * @throws IllegalStateException
	 *             if the world has not been initialised.
	 */
	void spawnEntity(PhysicsEntity entity);

	/**
	 * Performs a single physics step of a given length. Best results are
	 * achieved if steps of a small and constant size are used.
	 * 
	 * @param stepTime
	 *            the step duration in seconds. Must be strictly positive and
	 *            finite.
	 * @throws IllegalArgumentException
	 *             if the stepTime is not positive or non-finite.
	 * @throws IllegalStateException
	 *             if the world is not initialised.
	 */
	void stepWorld(double stepTime);

	/**
	 * Performs a series of physics steps. The {@code totalTime} is divided into
	 * a given number of {@code substeps} of equal size. In essence this is a
	 * convenience method for covering multiple steps in a single method call.
	 * The same result could be achieved by calling {@link #stepWorld(double)}
	 * {@code substeps} number of times with
	 * {@code stepTime = totalTime / substeps}.
	 * 
	 * @param totalTime
	 *            the total duration over all steps in seconds. Must be strictly
	 *            positive and finite.
	 * @param substeps
	 *            the number of substeps the {@code totalTime} should be divided
	 *            over.
	 * @throws IllegalArgumentException
	 *             if the {@totalTime} or {@code substeps} argument is not
	 *             strictly positive or non-finite.
	 * @throws IllegalStateException
	 *             if the world is not initialised.
	 */
	default void stepWorld(double totalTime, int substeps) {
		if (substeps <= 0)
			throw new IllegalArgumentException(
					"substep count must be positive");
		final double stepTime = totalTime / substeps;
		for (int i = 0; i < substeps; i++)
			stepWorld(stepTime);
	}

	/**
	 * Provides a full list of all currently spawned entities. The contents of
	 * this list may change over time, but it should be enough for some basic
	 * interaction.
	 * 
	 * @return a list of all entities currently spawned.
	 */
	List<PhysicsEntity> getSpawnedEntities();

}