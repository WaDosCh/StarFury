package ch.wados.starfury.physics.api;

import java.util.List;

import org.dyn4j.geometry.Convex;

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
 * @version 1.1 - 2016/06/12
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
	 * @return The entity type.
	 * @see EntityType
	 */
	public abstract EntityType getEntityType();

	/**
	 * Provides the initial mass of the entity in [kg]. For
	 * {@link EntityType#STATIC STATIC} entities this value is ignored. For
	 * normal entities this must be strictly positive.
	 * 
	 * @return the initial mass.
	 * @see EntityType
	 */
	public abstract double getInitialMass();

	/**
	 * Provides a set of convex shapes that represent the entities collision
	 * hull. These shapes are also used to calculate the moment of inertia.
	 * 
	 * @return the shapes the entity is constructed from.
	 */
	public abstract List<Convex> getShapes();
}
