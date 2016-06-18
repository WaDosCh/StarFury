package ch.wados.starfury.physics;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ch.wados.starfury.physics.api.APISuite;
import ch.wados.starfury.physics.simple.SimpleSuite;

/**
 * Suite combining all physics related suites
 */
@RunWith(Suite.class)
@SuiteClasses({ APISuite.class, SimpleSuite.class })
public class PhysicsSuite {
}