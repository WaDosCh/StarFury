package ch.wados.starfury.physics.api;

/**
 * UpdateListeners are invoked whenever a physics step has been completed. This
 * allows for processing of events that were recorded through the other
 * listeners during a physics step. The invocation order (or even thread-safety)
 * of multiple registered listeners is not guaranteed.
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/13
 * @since StarFury 0.0.1
 */
@FunctionalInterface
public interface UpdateListener {

	void update();

}
