package ch.wados.starfury.physics.api;

/**
 * Whenever a collision is detected through CCD a {@link TimeOfImpact} event is
 * created. All registered TimeOfImpactListeners will be invoked. Since the
 * invocation takes place during the physics calculations, the world must not be
 * altered. It is recommended to buffer any required changes and to perform them
 * through an {@link UpdateListener}.
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/13
 * @since StarFury 0.0.1
 * 
 * @see UpdateListener
 * @see TimeOfImpact
 */
@FunctionalInterface
public interface TimeOfImpactListener {

	/**
	 * Called whenever an impact is detected through CCD.
	 * 
	 * @param toi
	 *            the {@link TimeOfImpact} data
	 */
	public abstract void collision(TimeOfImpact toi);

}
