package ch.wados.starfury.view;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

public class Window {

	private long window;

	public Window() {
	}

	// public void startLoop() {
	// try {
	// // Set the clear color
	// glClearColor(0.2f, 0.2f, 0.2f, 0.0f);
	//
	// // Run the rendering loop until the user has attempted to close
	// // the window or has pressed the ESCAPE key.
	// while (!glfwWindowShouldClose(window)) {
	// glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the
	// // framebuffer
	// //draw();
	//
	// glfwSwapBuffers(window); // swap the color buffers
	//
	// // Poll for window events. The key callback above will only be
	// // invoked during this call.
	// glfwPollEvents();
	// }
	// } finally {
	// glfwTerminate();
	// }
	// }

	public boolean isOpened() {
		return this.window != NULL;
	}

	public void dispose() {
		try {
			// Free the window callbacks and destroy the window
			glfwFreeCallbacks(window);
			glfwDestroyWindow(window);
		} finally {
			// Terminate GLFW and free the error callback
			glfwTerminate();
			glfwSetErrorCallback(null).free();
		}
	}

	public void openWindow() {

		if (isOpened())
			throw new IllegalStateException("Window is already opened");

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Configure our window
		// optional, the current window hints are already the default
		glfwDefaultWindowHints();
		// mac compatibility.
		glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
		glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		// the window will stay hidden after creation
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_SAMPLES, 4);

		// Get the resolution of the primary monitor
		long monitor = glfwGetPrimaryMonitor();
		GLFWVidMode vidmode = glfwGetVideoMode(monitor);

		// Create the window
		this.window = glfwCreateWindow(vidmode.width(), vidmode.height(),
				"StarFury v.0", monitor, NULL);
		if (this.window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Make the OpenGL context current
		glfwMakeContextCurrent(this.window);

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(this.window);

		GLCapabilities caps = GL.createCapabilities();
		if (!caps.OpenGL30) {
			throw new IllegalStateException(
					"This game requires openGl 3.0 to run");
		}

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LESS);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public boolean isClosing() {
		return glfwWindowShouldClose(this.window);
	}

	public void update() {
		glfwSwapBuffers(this.window);
		glfwPollEvents();
	}

	public boolean isVSyncEnabled() {
		return true;
	}

	public void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

}
