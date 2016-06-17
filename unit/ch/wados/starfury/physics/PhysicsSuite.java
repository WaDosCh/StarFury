package ch.wados.starfury.physics;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ch.wados.starfury.physics.api.APISuite;

/**
 * Suite combining all physics related suites
 */
@RunWith(Suite.class)
@SuiteClasses(APISuite.class)
public class PhysicsSuite {
}