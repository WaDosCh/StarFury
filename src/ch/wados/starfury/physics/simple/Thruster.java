package ch.wados.starfury.physics.simple;

import org.dyn4j.geometry.Vector2;

import ch.wados.starfury.physics.api.ThrustPointDefinition;

/**
 * ThrustPoint representation
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/14
 * @since StarFury 0.0.1
 * 
 * @see ThrustPointDefinition
 */
class Thruster {

	final String identifier;
	private final Vector2 position;
	private final Vector2 direction;
	private double torqueFactor;
	private double thrust;

	private Vector2 force;
	private double torque;

	Thruster(ThrustPointDefinition def) {
		this.identifier = def.getIdentifier();
		this.position = def.getPosition();
		this.direction = def.getDirection().getNormalized();
		this.torqueFactor = 0;
		this.thrust = 0;
		this.force = new Vector2(0, 0);
		this.torque = 0;
	}

	/**
	 * updates the torque value
	 * 
	 * @param CoM
	 *            the centre of mass of the entity.
	 */
	void update(Vector2 CoM) {
		this.torqueFactor = CoM.to(this.position).cross(this.direction);
		this.torque = this.torqueFactor * this.thrust;
	}

	void setThrust(double thrust) {
		this.thrust = thrust;
		this.force.set(this.direction);
		this.force.multiply(thrust);
		this.torque = this.torqueFactor * thrust;
	}

	Vector2 getForce() {
		return this.force;
	}

	double getTorque() {
		return this.torque;
	}

}
