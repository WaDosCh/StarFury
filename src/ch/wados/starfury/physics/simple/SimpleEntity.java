package ch.wados.starfury.physics.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.dyn4j.Epsilon;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.Force;
import org.dyn4j.dynamics.Torque;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import ch.wados.starfury.physics.api.CollisionFilter;
import ch.wados.starfury.physics.api.CollisionListener;
import ch.wados.starfury.physics.api.CollisionPoint;
import ch.wados.starfury.physics.api.EntityType;
import ch.wados.starfury.physics.api.FixtureDefinition;
import ch.wados.starfury.physics.api.PhysicsEntity;
import ch.wados.starfury.physics.api.PhysicsEntityDefinition;
import ch.wados.starfury.physics.api.ThrustPointDefinition;
import ch.wados.starfury.physics.api.TimeOfImpactListener;
import ch.wados.starfury.physics.api.UpdateListener;

/**
 * Simple Entity
 * 
 * @see PhysicsEntity
 */
class SimpleEntity implements PhysicsEntity {

	private final List<CollisionFilter> collisionFilters = new ArrayList<>();
	private final List<CollisionListener> collisionListeners = new ArrayList<>();
	private final List<TimeOfImpactListener> toiListeners = new ArrayList<>();
	private final List<UpdateListener> updateListeners = new ArrayList<>();

	@Override
	public void addCollisionFilter(CollisionFilter filter) {
		Objects.requireNonNull(filter);
		if (!this.collisionFilters.contains(filter))
			this.collisionFilters.add(filter);
	}

	@Override
	public void addCollisionListener(CollisionListener listener) {
		Objects.requireNonNull(listener);
		if (!this.collisionListeners.contains(listener))
			this.collisionListeners.add(listener);
	}

	@Override
	public void addTimeOfImpactListener(TimeOfImpactListener listener) {
		Objects.requireNonNull(listener);
		if (!this.toiListeners.contains(listener))
			this.toiListeners.add(listener);
	}

	@Override
	public void addUpdateListener(UpdateListener listener) {
		Objects.requireNonNull(listener);
		if (!this.updateListeners.contains(listener))
			this.updateListeners.add(listener);
	}

	@Override
	public void removeCollisionFilter(CollisionFilter filter) {
		Objects.requireNonNull(filter);
		this.collisionFilters.remove(filter);
	}

	@Override
	public void removeCollisionListener(CollisionListener listener) {
		Objects.requireNonNull(listener);
		this.collisionListeners.remove(listener);
	}

	@Override
	public void removeTimeOfImpactListener(TimeOfImpactListener listener) {
		Objects.requireNonNull(listener);
		this.toiListeners.remove(listener);
	}

	@Override
	public void removeUpdateListener(UpdateListener listener) {
		Objects.requireNonNull(listener);
		this.updateListeners.remove(listener);
	}

	@Override
	public boolean filterCollision(PhysicsEntity entity0, String fixture0,
			PhysicsEntity entity1, String fixture1) {
		if (entity0 != this && entity1 != this)
			return true;
		boolean result = true;
		for (CollisionFilter f : this.collisionFilters)
			if (!f.filterCollision(entity0, fixture0, entity1, fixture1))
				result = false;
		return result;
	}

	@Override
	public void collision(CollisionPoint collision) {
		if (collision.entity0 != this && collision.entity1 != this)
			return;
		for (CollisionListener l : this.collisionListeners)
			l.collision(collision);
	}

	@Override
	public boolean collision(PhysicsEntity entity0, String fixture0,
			PhysicsEntity entity1, String fixture1, double toi) {
		if (entity0 != this && entity1 != this)
			return true;
		boolean result = true;
		for (TimeOfImpactListener l : this.toiListeners)
			if (!l.collision(entity0, fixture0, entity1, fixture1, toi))
				result = false;
		return result;
	}

	@Override
	public void update() {
		for (UpdateListener l : this.updateListeners)
			l.update();
	}

	@Override
	public void addFixture(FixtureDefinition fixture) {
		String id = fixture.getIdentifier();
		if (fixture.getIdentifier() != null) {
			for (BodyFixture f : this.body.getFixtures())
				if (id.equals(f.getUserData()))
					throw new IllegalArgumentException(
							"fixture id [" + id + "] already in use");
		}
		this.buildFixture(fixture);
		this.recalculateCoM();
		this.callback.run();
	}

	@Override
	public void addThrustPoint(ThrustPointDefinition thruster) {
		// duplicate keys handled by thruster system
		this.thrusters.addThruster(thruster);
	}

	@Override
	public void applyForce(Vector2 position, Vector2 force, double duration) {
		Objects.requireNonNull(position);
		Objects.requireNonNull(force);
		if (force.isZero())
			throw new IllegalArgumentException(
					"force may not be the zero vector");
		if (duration < 0 || !Double.isFinite(duration))
			throw new IllegalArgumentException("duration must be positive");
		this.body.applyTorque(new TimedTorque(
				this.body.getWorldCenter().to(position).cross(force),
				duration));
	}

	@Override
	public void applyImpulse(Vector2 position, Vector2 impulse) {
		Objects.requireNonNull(position);
		Objects.requireNonNull(impulse);
		if (impulse.isZero())
			throw new IllegalArgumentException(
					"impulse may not be the zero vector");
		this.body.applyImpulse(impulse, position);
	}

	@Override
	public double getAngularVelocity() {
		return this.body.getAngularVelocity();
	}

	@Override
	public Vector2 getCenterOfMass() {
		return this.body.getMass().getCenter();
	}

	@Override
	public EntityType getEntityType() {
		return this.type;
	}

	@Override
	public int getFixtureCount() {
		return this.body.getFixtureCount();
	}

	@Override
	public Vector2 getLinearVelocity() {
		return this.body.getLinearVelocity();
	}

	@Override
	public double getMass() {
		return this.body.getMass().getMass();
	}

	@Override
	public double getOrientation() {
		return this.getWorldVector(new Vector2(1, 0)).getDirection();
	}

	@Override
	public Vector2 getPosition() {
		return this.getWorldPoint(new Vector2(0, 0));
	}

	@Override
	public List<Convex> getShapes() {
		return this.body.getFixtures().stream().map(BodyFixture::getShape)
				.collect(Collectors.toList());
	}

	@Override
	public int getThrustPointCount() {
		return this.thrusters.getThrusterCount();
	}

	@Override
	public Transform getTransform() {
		return this.body.getTransform();
	}

	private Object user_data = null;

	@Override
	public Object getUserData() {
		return this.user_data;
	}

	@Override
	public boolean isSensor(String id) {
		Objects.requireNonNull(id);
		if (id.isEmpty())
			throw new IllegalArgumentException("id may not be empty");
		for (BodyFixture f : this.body.getFixtures())
			if (id.equals(f.getUserData()))
				return f.isSensor();
		throw new IllegalArgumentException(
				"fixture not found for id [" + id + "]");
	}

	@Override
	public void removeFixture(String id) {
		Objects.requireNonNull(id);
		if (id.isEmpty())
			throw new IllegalArgumentException("id may not be empty");
		BodyFixture fx = null;
		for (BodyFixture f : this.body.getFixtures())
			if (id.equals(f.getUserData()))
				fx = f;
		if (fx == null)
			throw new IllegalArgumentException(
					"fixture not found for id [" + id + "]");
		this.body.removeFixture(fx);
		this.callback.run();
	}

	@Override
	public void removeThrustPoint(String id) {
		this.thrusters.removeThruster(id);
	}

	@Override
	public void setAngularVelocity(double velocity) {
		this.body.setAngularVelocity(velocity);
	}

	@Override
	public void setLinearVelocity(Vector2 velocity) {
		this.body.setLinearVelocity(velocity);
	}

	@Override
	public void setMass(double mass) {
		if (this.type == EntityType.STATIC)
			return;
		Mass oldMass = this.body.getMass();
		this.body.setMass(new Mass(oldMass.getCenter(), mass,
				oldMass.getInertia() * mass / oldMass.getMass()));
		this.thrusters.update(oldMass.getCenter());
	}

	@Override
	public void setOrientation(double orientation) {
		this.body.rotateAboutCenter(orientation - this.getOrientation());
		this.callback.run();
	}

	@Override
	public void setPosition(Vector2 position) {
		this.body.translateToOrigin();
		this.body.translate(position);
		this.callback.run();
	}

	@Override
	public void setSensor(String id, boolean isSensor) {
		Objects.requireNonNull(id);
		if (id.isEmpty())
			throw new IllegalArgumentException("id may not be empty");
		for (BodyFixture f : this.body.getFixtures())
			if (id.equals(f.getUserData())) {
				f.setSensor(isSensor);
				this.callback.run();
				return;
			}
		throw new IllegalArgumentException(
				"fixture not found for id [" + id + "]");
	}

	@Override
	public void setThrust(String id, double force) {
		this.thrusters.setThrust(id, force);
	}

	private void recalculateCoM() {
		double mass = this.body.getMass().getMass();
		switch (this.type) {
		case BULLET:
			this.body.setBullet(true);
		case DEFAULT:
			this.body.setMass(MassType.NORMAL);
			break;
		case STATIC:
			this.body.setMass(new Mass());
			return;
		}
		this.setMass(mass);
	}

	void applyThrust() {
		Force f = this.thrusters.getForce(this.getTransform());
		Torque t = this.thrusters.getTorque();
		if (Math.abs(t.getTorque()) > Epsilon.E)
			this.body.applyTorque(t);
		if (!f.getForce().isZero())
			this.body.applyForce(f);
	}

	@Override
	public void setUserData(Object data) {
		this.user_data = data;
	}

	private void buildFixture(FixtureDefinition f) {
		BodyFixture fixture = new BodyFixture(f.getShape());
		fixture.setDensity(f.getDensityCoefficient());
		fixture.setFriction(f.getFrictionCoefficient());
		fixture.setRestitution(f.getRestitutionCoefficient());
		fixture.setSensor(f.isSensor());
		fixture.setUserData(f.getIdentifier());
		this.body.addFixture(fixture);
	}

	private final static Runnable NULL_RUNNABLE = () -> {
	};

	final Body body;
	private final ThrusterSystem thrusters;
	private final EntityType type;
	private Runnable callback = NULL_RUNNABLE;

	void setCallback(Runnable r) {
		this.callback = (r == null) ? NULL_RUNNABLE : r;
	}

	SimpleEntity(PhysicsEntityDefinition def) {
		Objects.requireNonNull(def);

		List<FixtureDefinition> fixtures = def.getFixtures();
		List<ThrustPointDefinition> thrusts = def.getThrustPoints();

		this.body = new Body(fixtures.size());
		this.body.setUserData(this);
		this.body.rotate(def.getOrientation());
		this.body.translate(def.getPosition());

		this.thrusters = new ThrusterSystem(thrusts.size());
		this.type = def.getType();

		for (FixtureDefinition f : fixtures)
			this.buildFixture(f);
		
		this.body.setMass(MassType.NORMAL);

		this.recalculateCoM();
		this.setMass(def.getMass());

		this.body.setLinearDamping(def.getLinearDampening());
		this.body.setAngularDamping(def.getAngularDampening());

		for (ThrustPointDefinition t : thrusts)
			this.thrusters.addThruster(t);
	}

	Body getBody() {
		return this.body;
	}

	static class TimedForce extends Force {

		private double duration;

		TimedForce(Vector2 dir, double duration) {
			super(dir);
			this.duration = duration;
		}

		@Override
		public boolean isComplete(double elapsedTime) {
			duration -= elapsedTime;
			return duration < Epsilon.E;
		}

	}

	static class TimedTorque extends Torque {
		private double duration;

		TimedTorque(double t, double duration) {
			super(t);
			this.duration = duration;
		}

		@Override
		public boolean isComplete(double elapsedTime) {
			duration -= elapsedTime;
			return duration < Epsilon.E;
		}
	}

	@Override
	public void wakeUp() {
		this.body.setAsleep(false);

	}

	@Override
	public boolean isAsleep() {
		return this.body.isAsleep();
	}

}
