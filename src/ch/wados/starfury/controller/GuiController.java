package ch.wados.starfury.controller;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class GuiController {

	private RenderingManager renderingManager;
	private long window;

	public GuiController() {
	}

	public void setRenderingManager(RenderingManager renderingManager) {
		this.renderingManager = renderingManager;
	}

	public void startLoop() {
		// Set the clear color
		glClearColor(0.2f, 0.2f, 0.2f, 0.0f);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while (!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the
																// framebuffer
			this.renderingManager.draw();

			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}

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

		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure our window
		// optional, the current window hints are already the default
		glfwDefaultWindowHints();
		// the window will stay hidden after creation
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		// Create the window
		this.window = glfwCreateWindow(vidmode.width(), vidmode.height(),
				"StarFury v.0", NULL, NULL);
		if (this.window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}

}
