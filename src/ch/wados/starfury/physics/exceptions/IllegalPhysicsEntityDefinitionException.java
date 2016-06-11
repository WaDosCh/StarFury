package ch.wados.starfury.physics.exceptions;

import ch.wados.starfury.physics.api.PhysicsEntityDefinition;
import ch.wados.starfury.physics.api.PhysicsManager;

/**
 * Exception signalling that a {@link PhysicsEntityDefinition} could not be
 * processed by a {@link PhysicsManager} due to invalid contents or contents
 * leading to failure during entity initialisation.
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/11
 * @since StarFury 0.0.1
 */
public class IllegalPhysicsEntityDefinitionException extends Exception {

	private static final long serialVersionUID = -1774875980439413266L;

	public IllegalPhysicsEntityDefinitionException(String message) {
		super(message);
	}

}
