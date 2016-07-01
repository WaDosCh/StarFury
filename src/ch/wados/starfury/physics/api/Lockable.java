package ch.wados.starfury.physics.api;

/**
 * Common base interface for all lockable objects.
 * 
 * <p>
 * Locking an object is a way to programmatically make a previously mutable
 * object immutable. The object implementation should make sure that once locked
 * no mutation of the object state is allowed. Also unlocking a locked object
 * must not be possible since that would allow future alteration of a locked
 * object, violating the immutability constraint.
 * </p>
 * <p>
 * It is recommended that modification attempts on a locked object yield an
 * exception - preferably an {@link IllegalStateException}. This is also the
 * default implementation for the {@link #enforceLock()} method.
 * </p>
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/30
 * @since StarFury 0.0.1
 */
public interface Lockable {

	/**
	 * Locks the instance.
	 * <p>
	 * After the first call of this method the instance should be considered
	 * locked according to {@link #isLocked()}. Any subsequent calls must be
	 * ignored.
	 * </p>
	 */
	void lock();

	/**
	 * Indicates the locking state of the instance.
	 * <p>
	 * For mutable instances (i.e. unlocked ones) this must return {@code false}
	 * . For locked instances this must return {@code true}. Once this method
	 * returns {@code true} for a given instance it must return {@code true} for
	 * any subsequent calls for said instance.
	 * </p>
	 * 
	 * @return {@code true} if the instance is locked, {@code false} if it is
	 *         unlocked.
	 */
	boolean isLocked();

	/**
	 * Enforces the locking state of the instance.
	 * <p>
	 * This method can be used at any point where a modification attempt is made
	 * to ensure immutability of locked instances. Calling this method on
	 * unlocked objects must have no effect while calling it on locked objects
	 * must result in an exception being thrown.
	 * </p>
	 * <p>
	 * The default implementation throws an {@link IllegalStateException} if the
	 * instance is locked. The locking state is extracted through the
	 * {@link #isLocked()} method.
	 * </p>
	 * 
	 * 
	 * @throws IllegalStateException
	 *             if the instance is locked according to {@link #isLocked()}.
	 */
	default void enforceLock() {
		if (this.isLocked())
			throw new IllegalStateException("instance locked");
	}

}
