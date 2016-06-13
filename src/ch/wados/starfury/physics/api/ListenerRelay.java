package ch.wados.starfury.physics.api;

/**
 * A ListenerRelay combines a listener with a {@link Listenable} to create a
 * single listener that other listeners can register to. A relay may be
 * implemented such to filter events or to provide concurrent listener
 * invocation.
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/14
 * @since StarFury 0.0.1
 */
public interface ListenerRelay extends Listenable, CollisionListener, TimeOfImpactListener, UpdateListener {
}