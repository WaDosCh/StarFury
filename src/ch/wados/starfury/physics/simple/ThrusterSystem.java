package ch.wados.starfury.physics.simple;

import java.util.ArrayList;
import java.util.List;

import org.dyn4j.dynamics.Force;
import org.dyn4j.dynamics.Torque;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import ch.wados.starfury.physics.api.ThrustPointDefinition;

/**
 * The set of all {@link Thruster Thrusters} of an entity. Includes force/torque
 * caching and update management.
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/14
 * @since StarFury 0.0.1
 */
class ThrusterSystem {

	private final List<Thruster> thrusters;
	private Vector2 net_force;
	private Vector2 force_vector;
	private Vector2 CoM;
	private Force force;
	private Torque torque;

	ThrusterSystem(int capacity) {
		this.thrusters = new ArrayList<>(capacity);
		this.net_force = new Vector2(0, 0);
		this.force_vector = new Vector2(0, 0);
		this.force = new Force(this.force_vector);
		this.torque = new Torque(0);
		this.CoM = new Vector2(0, 0);
	}

	Torque getTorque() {
		return this.torque;
	}

	Force getForce(Transform T) {
		this.force_vector.set(this.net_force);
		T.transformR(this.force_vector);
		return this.force;
	}

	void update(Vector2 CoM) {
		this.CoM.set(CoM);
		for (Thruster t : this.thrusters)
			t.update(CoM);
		this.refresh();
	}

	private void refresh() {
		this.net_force.set(0, 0);
		double net_torque = 0;
		for (Thruster t : this.thrusters) {
			this.net_force.add(t.getForce());
			net_torque += t.getTorque();
		}
		this.torque.set(net_torque);
	}

	/**
	 * Sets the force applied at a thrust point. This force will continuously be
	 * applied until a new force value is set.
	 * 
	 * @param id
	 *            the identifier of the thrust point. May not be {@code null} or
	 *            an empty String.
	 * @param thrust
	 *            the force (in Newtons) to apply. If it is zero, the call is
	 *            ignored. May not be non-finite.
	 * @throws NullPointerException
	 *             if the {@code id} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@code id} is not known or an empty String. or the
	 *             {@code thrust} is non-finite.
	 */
	void setThrust(String id, double thrust) {
		// validate input
		if (id == null)
			throw new NullPointerException("id may not be null");
		if (id.isEmpty())
			throw new IllegalArgumentException("id may not be empty");
		if (!Double.isFinite(thrust))
			throw new IllegalArgumentException("thrust must be finite");
		// search thruster and perform update
		for (Thruster t : this.thrusters)
			if (t.identifier.equals(id)) {
				t.setThrust(thrust);
				this.refresh();
				return;
			}
		// thruster not found!
		throw new IllegalArgumentException("thruster [" + id + "] not found");
	}

	int getThrusterCount() {
		return this.thrusters.size();
	}

	/**
	 * adds a new thrust point.
	 * 
	 * @param thruster
	 *            the definition object for the new thrust point. May not be
	 *            {@code null} or have an {@code id} that is already present in
	 *            this entity.
	 * @throws NullPointerException
	 *             if the {@code def} is {@code null}
	 * @throws IllegalArgumentException
	 *             if the {@code def} has an {@code id} that is already present.
	 */
	void addThruster(ThrustPointDefinition def) {
		// validate
		if (def == null)
			throw new NullPointerException("def may not be null");
		String id = def.getIdentifier();
		for (Thruster t : this.thrusters)
			if (t.identifier.equals(id))
				throw new IllegalArgumentException("thruster with id [" + id + "] already exists");
		// create thruster
		Thruster t = new Thruster(def);
		t.update(this.CoM);
		this.thrusters.add(t);
		// refresh not required since new thrusters are turned off by default
	}

	/**
	 * Removes a thrust point.
	 * 
	 * @param id
	 *            the id of the thrust point to remove. If no matching thrust
	 *            point is found, this is ignored. May not be {@code null} or an
	 *            empty String.
	 * @throws NullPointerException
	 *             if the {@code id} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@code id} is an empty String.
	 */
	void removeThruster(String id) {
		// validate
		if (id == null)
			throw new NullPointerException("id may not be null");
		if (id.isEmpty())
			throw new IllegalArgumentException("id may not be empty");
		// remove thruster if it exists
		for (int i = 0; i < this.thrusters.size(); i++)
			if (this.thrusters.get(i).identifier.equals(id)) {
				this.thrusters.remove(i);
				return;
			}
	}

}
