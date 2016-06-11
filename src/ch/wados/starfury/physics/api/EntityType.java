package ch.wados.starfury.physics.api;

/**
 * Collection of supported {@link PhysicsEntity} types.
 * <p>
 * Depending on the used {@link PhysicsManager} implementation this may control
 * the physics core(s) the entity is simulated in or other simulation
 * configurations. See the individual members and the used PhysicsEntity
 * implementation for specific information on the internal interpretation of a
 * member.
 * </p>
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/11
 * @since StarFury 0.0.1
 * 
 * @see PhysicsEntityDefinition
 * @see PhysicsManager
 */
public enum EntityType {

	/**
	 * The default entity type.
	 * <p>
	 * These entities have finite positive mass and are subject to linear and
	 * angular acceleration. The default implementation is for there entities to
	 * use traditional collision detection. See the {@link PhysicsManager}
	 * implementation for specific information.
	 * </p>
	 */
	DEFAULT,

	/**
	 * The default entity type for fast-moving objects.
	 * <p>
	 * These entities have finite positive mass and are subject to linear and
	 * angular acceleration. This entity type is targeted at fast-moving objects
	 * that require special treatment for collision detection. The default
	 * implementation is for these entities to use CCD (Continuous Collision
	 * Detection). See the {@link PhysicsManager} implementation for specific
	 * information.
	 * </p>
	 */
	BULLET,

	/**
	 * The default entity type for anchored immovable objects.
	 * <p>
	 * These entities are not subject to linear or angular acceleration. They
	 * only interact through collisions with movable objects. This type is meant
	 * for use as obstacles.
	 * </p>
	 */
	STATIC;

}
