package ch.wados.starfury.physics.api;

/**
 * Base interface for all entity definitions for {@link PhysicsEntity
 * PhysicsEntities} involved in physics calculations.
 * <p>
 * Each entity is backed by a {@link PhysicsEntityDefinition}. Usually such a
 * definition object is passed to a {@link PhysicsManager}, that internally
 * handles the entity creation and returns a {@link PhysicsEntity} instance for
 * that internal entity.
 * </p>
 * <p>
 * The way an entity is treated by the {@link PhysicsManager} is mainly
 * depending on the entity type provided through {@link #getEntityType()}, but
 * also on the specific implementation of the manager used.
 * </p>
 *
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/11
 * @since StarFury 0.0.1
 * 
 * @see EntityType
 * @see PhysicsEntity
 * @see PhysicsManager
 */
public interface PhysicsEntityDefinition {

	/**
	 * Provides the entity type this entity should be treated as.
	 * 
	 * This method should be consistent and always return the same object for a
	 * single entity. Otherwise the behaviour may be unpredictable.
	 * 
	 * @return The entity type. May not be {@code null}.
	 * @see EntityType
	 */
	public abstract EntityType getEntityType();

	// TODO: add methods

}
