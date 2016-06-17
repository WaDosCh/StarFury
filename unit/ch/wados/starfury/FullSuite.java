package ch.wados.starfury;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ch.wados.starfury.physics.PhysicsSuite;

/**
 * Global master suite combining all tests from all suites.
 * 
 * Running this suite allows for full coverage of all test cases in one step.
 */
@RunWith(Suite.class)
@SuiteClasses(PhysicsSuite.class)
public class FullSuite {
}
