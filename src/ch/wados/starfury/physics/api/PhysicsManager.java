package ch.wados.starfury.physics.api;

import ch.wados.starfury.physics.exceptions.IllegalPhysicsEntityDefinitionException;

/**
 * A PhysicsManager handles all interaction between the physics engine and all
 * other modules. It serves as an abstraction layer to enable flexible physics
 * implementation changes.
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/11
 * @since StarFury 0.0.1
 */
public interface PhysicsManager {

	/**
	 * Creates a new entity based off the provided
	 * {@link PhysicsEntityDefinition} and returns the produced
	 * {@link PhysicsEntity} instance. Note that the returned entity must not be
	 * identical to the internally used entity, but must at least keep the
	 * feedback data it provides in sync with the internal object.
	 * 
	 * @param definition
	 *            the definition object the entity should be based off. May not
	 *            be {@code null}
	 * @return the entity. May not be {@code null}
	 * @throws IllegalPhysicsEntityDefinitionException
	 *             if the initialisation fails due to illegal contents of the
	 *             definition.
	 */
	public abstract PhysicsEntity addEntity(PhysicsEntityDefinition definition)
			throws IllegalPhysicsEntityDefinitionException;

	// TODO: add methods
}