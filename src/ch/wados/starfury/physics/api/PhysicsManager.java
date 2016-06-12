package ch.wados.starfury.physics.api;

/**
 * A PhysicsManager handles all interaction between the physics engine and all
 * other modules. It serves as an abstraction layer to enable flexible physics
 * implementation changes.
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/12
 * @since StarFury 0.0.1
 */
public interface PhysicsManager {

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
	public abstract PhysicsEntity createEntity(PhysicsEntityDefinition definition);

	/**
	 * Spawns the given {@link PhysicsEntity} in the world.
	 * 
	 * @param entity
	 *            the entity to spawn. May not be {@code null} or an already
	 *            spawned entity.
	 * @throws NullPointerException
	 *             if the {@code entity} parameter is {@code null}
	 * @throws IllegalStateException
	 *             if the {@code entity} is already present in the world
	 * @throws IllegalArgumentException
	 *             if the {@code entity} is illegal or unsupported in any other
	 *             way.
	 */
	public abstract void spawnEntity(PhysicsEntity entity);

	/**
	 * Despawns the given {@link PhysicsEntity} from the world. The entity
	 * remains valid and can be respawned at any point.
	 * 
	 * @param entity
	 *            the entity to despawn. May not be {@code null} and the entity
	 *            must be currently spawned.
	 * @throws NullPointerException
	 *             if the {@code entity} parameter is {@code null}
	 * @throws IllegalStateException
	 *             if the {@code entity} is not present in the world
	 */
	public abstract void despawnEntity(PhysicsEntity entity);

	// TODO: add methods
}