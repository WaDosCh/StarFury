//package ch.wados.starfury.physics.simple;
//
//import java.awt.Color;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//
//import org.dyn4j.dynamics.Body;
//import org.dyn4j.dynamics.BodyFixture;
//import org.dyn4j.dynamics.Force;
//import org.dyn4j.dynamics.World;
//import org.dyn4j.geometry.Convex;
//import org.dyn4j.geometry.MassType;
//import org.dyn4j.geometry.Transform;
//import org.dyn4j.geometry.Triangle;
//import org.dyn4j.geometry.Vector2;
//
//
///**
// * Simple Ship Controls
// * 
// * hold [AD] for lateral thrusters (attached on the nose pointing left and
// * right) hold [W] for main thruster press [R] for map reset
// */
//public class ShipDemo {
//
//	static Body ship;
//	static boolean left, right, thrust, reset;
//
//	public static void main(String[] args) {
//
//		System.out.println("SHIP CONTROL DEMO");
//		System.out.println("===============================");
//		System.out.println("hold [AD] for lateral thrusters attached on nose");
//		System.out.println("hold [W] for main thruster");
//		System.out.println("press [R] for map reset");
//
//		SandboxController ctrl = new SandboxController(800, 600, 10) {
//			@Override
//			protected void worldInit(World world) {
//				init(world);
//			}
//
//			@Override
//			protected void customUpdate(World world) {
//				update(world);
//			}
//		};
//
//		ctrl.start();
//
//		ctrl.getFrame().getCanvas().addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				keyChange(e, true);
//			}
//
//			@Override
//			public void keyReleased(KeyEvent e) {
//				keyChange(e, false);
//			}
//
//		});
//
//	}
//
//	private static void keyChange(KeyEvent event, boolean keyDown) {
//		switch (event.getKeyCode()) {
//		case KeyEvent.VK_A:
//			left = keyDown;
//			break;
//		case KeyEvent.VK_D:
//			right = keyDown;
//			break;
//		case KeyEvent.VK_W:
//			thrust = keyDown;
//			break;
//		case KeyEvent.VK_R:
//			reset = keyDown;
//			break;
//		case KeyEvent.VK_Q:
//			if (keyDown)
//				System.exit(0);
//			break;
//		default:
//			break;
//		}
//	}
//
//	private static void init(World world) {
//		world.setGravity(World.ZERO_GRAVITY);
//		// build ship
//		Convex outline = new Triangle(new Vector2(0, 5), new Vector2(-3, -2), new Vector2(3, -2));
//		BodyFixture fixture = new BodyFixture(outline);
//		//ship = new ColoredBody(Color.RED);
//		ship.addFixture(fixture);
//		ship.setMass(MassType.NORMAL);
//		world.addBody(ship);
//	}
//
//	private static void update(World world) {
//		ship.clearForce();
//		Transform T = ship.getTransform();
//		if (reset) {
//			ship.clearForce();
//			ship.setLinearVelocity(0, 0);
//			ship.setAngularVelocity(0);
//			ship.setTransform(Transform.IDENTITY);
//		}
//		if (left) {
//			ship.applyForce(T.getTransformedR(new Vector2(-20, 0)), T.getTransformed(new Vector2(0, 5)));
//		}
//		if (right) {
//			ship.applyForce(T.getTransformedR(new Vector2(20, 0)), T.getTransformed(new Vector2(0, 5)));
//		}
//		if (thrust) {
//			ship.applyForce(T.getTransformedR(new Vector2(0, 100)), T.getTransformed(new Vector2(0, -3)));
//		}
//	}
//
//	static class ContinuousForce extends Force {
//		ContinuousForce(Vector2 direction) {
//			super(direction);
//		}
//
//		@Override
//		public boolean isComplete(double elapsedTime) {
//			return false;
//		}
//
//	}
//
//}
