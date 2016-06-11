package ch.wados.starfury.physics.api;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Common base interface for all entities involved in physics calculations.
 * <p>
 * The way an entity is treated by the {@link PhysicsManager} is mainly
 * depending on the entity type provided through {@link #getEntityType()}, but
 * also on the specific implementation of the manager used.
 * </p>
 * <p>
 * An entity represents a single rigid object that may or may not interact with
 * other objects and thereby form the smallest simulated unit in the physics
 * engine. (The only smaller unit is a convex collision hull. A single entity
 * may consist of any number of these shapes.)
 * </p>
 *
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/11
 * @since StarFury 0.0.1
 * 
 * @see EntityType
 * @see PhysicsManager
 */
public interface PhysicsEntity {

	/**
	 * Provides the entity type this entity should be treated as.
	 * 
	 * This method should be consistent and always return the same object for a
	 * single entity. Otherwise the behaviour may be unpredictable.
	 * 
	 * @return The entity type. May not be {@code null}.
	 * @see EntityType
	 */
	public abstract @NonNull EntityType getEntityType();

}
