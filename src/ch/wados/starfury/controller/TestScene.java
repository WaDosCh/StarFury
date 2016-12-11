package ch.wados.starfury.controller;

import static org.lwjgl.demo.opengl.util.DemoUtils.createShader;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import ch.wados.starfury.view.Window;

public class TestScene implements Scene {

	private Window window;

	@Override
	public void enter(SceneManager manager) {
		this.window = manager.getWindow();

		try {
			setup();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		loop();
	}

	Matrix4f viewMatrix = new Matrix4f();
	Matrix4f projMatrix = new Matrix4f();
	FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

	private void setup() throws IOException {
		glClearColor(0.55f, 0.75f, 0.95f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);

		/* Create all needed GL resources */
		createVao();
		createRasterProgram();
		initProgram();
	}

	int width = 1920;
	int height = 1080;

	float angle = 0.0f;
	long lastTime = System.nanoTime();

	void update() {
		projMatrix.setPerspective((float) Math.toRadians(30), (float) width
				/ height, 0.01f, 50.0f);
		long thisTime = System.nanoTime();
		float diff = (thisTime - lastTime) / 1E9f;
		angle += diff;
		viewMatrix.setLookAt(0.0f, 2.0f, 5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f,
				0.0f).rotateY(angle);
		lastTime = thisTime;
	}

	void render() {
		glUseProgram(this.program);

		glUniformMatrix4fv(viewMatrixUniform, false,
				viewMatrix.get(matrixBuffer));
		glUniformMatrix4fv(projMatrixUniform, false,
				projMatrix.get(matrixBuffer));
		glUniform2f(viewportSizeUniform, width, height);

		glBindVertexArray(vao);
		glDrawArrays(GL_TRIANGLES, 0, 3 * 2 * 6);
		glBindVertexArray(0);

		glUseProgram(0);
	}

	void loop() {
		while (!window.isClosing()) {
			glfwPollEvents();
			glViewport(0, 0, width, height);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			update();
			render();

			window.update();
		}
	}

	static void quadPattern(IntBuffer vb) {
		vb.put(1).put(0).put(1).put(1).put(0).put(1);
	}

	static void quadWithDiagonalPattern(IntBuffer vb) {
		vb.put(1).put(1).put(1).put(1).put(1).put(1);
	}

	int viewMatrixUniform;
	int projMatrixUniform;
	int viewportSizeUniform;

	void initProgram() {
		glUseProgram(this.program);
		viewMatrixUniform = glGetUniformLocation(this.program, "viewMatrix");
		projMatrixUniform = glGetUniformLocation(this.program, "projMatrix");
		viewportSizeUniform = glGetUniformLocation(this.program, "viewportSize");
		glUseProgram(0);
	}

	void createVao() {
		this.vao = glGenVertexArrays();
		glBindVertexArray(vao);
		IntBuffer vb = BufferUtils.createIntBuffer(6 * 6);
		FloatBuffer pb = BufferUtils.createFloatBuffer(3 * 6 * 6);
		quadPattern(vb);
		pb.put(0.5f).put(0.5f).put(-0.5f);
		pb.put(0.5f).put(-0.5f).put(-0.5f);
		pb.put(-0.5f).put(-0.5f).put(-0.5f);
		pb.put(-0.5f).put(-0.5f).put(-0.5f);
		pb.put(-0.5f).put(0.5f).put(-0.5f);
		pb.put(0.5f).put(0.5f).put(-0.5f);
		quadWithDiagonalPattern(vb);
		pb.put(0.5f).put(-0.5f).put(0.5f);
		pb.put(0.5f).put(0.5f).put(0.5f);
		pb.put(-0.5f).put(0.5f).put(0.5f);
		pb.put(-0.5f).put(0.5f).put(0.5f);
		pb.put(-0.5f).put(-0.5f).put(0.5f);
		pb.put(0.5f).put(-0.5f).put(0.5f);
		quadPattern(vb);
		pb.put(0.5f).put(-0.5f).put(-0.5f);
		pb.put(0.5f).put(0.5f).put(-0.5f);
		pb.put(0.5f).put(0.5f).put(0.5f);
		pb.put(0.5f).put(0.5f).put(0.5f);
		pb.put(0.5f).put(-0.5f).put(0.5f);
		pb.put(0.5f).put(-0.5f).put(-0.5f);
		quadWithDiagonalPattern(vb);
		pb.put(-0.5f).put(-0.5f).put(0.5f);
		pb.put(-0.5f).put(0.5f).put(0.5f);
		pb.put(-0.5f).put(0.5f).put(-0.5f);
		pb.put(-0.5f).put(0.5f).put(-0.5f);
		pb.put(-0.5f).put(-0.5f).put(-0.5f);
		pb.put(-0.5f).put(-0.5f).put(0.5f);
		quadPattern(vb);
		pb.put(0.5f).put(0.5f).put(0.5f);
		pb.put(0.5f).put(0.5f).put(-0.5f);
		pb.put(-0.5f).put(0.5f).put(-0.5f);
		pb.put(-0.5f).put(0.5f).put(-0.5f);
		pb.put(-0.5f).put(0.5f).put(0.5f);
		pb.put(0.5f).put(0.5f).put(0.5f);
		quadWithDiagonalPattern(vb);
		pb.put(0.5f).put(-0.5f).put(-0.5f);
		pb.put(0.5f).put(-0.5f).put(0.5f);
		pb.put(-0.5f).put(-0.5f).put(0.5f);
		pb.put(-0.5f).put(-0.5f).put(0.5f);
		pb.put(-0.5f).put(-0.5f).put(-0.5f);
		pb.put(0.5f).put(-0.5f).put(-0.5f);
		pb.flip();
		vb.flip();
		// setup vertex positions buffer
		int posVbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, posVbo);
		glBufferData(GL_ARRAY_BUFFER, pb, GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0L);
		// setup vertex visibility buffer
		int visVbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, visVbo);
		glBufferData(GL_ARRAY_BUFFER, vb, GL_STATIC_DRAW);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1, 1, GL_UNSIGNED_INT, false, 0, 0L);
		glBindVertexArray(0);
	}

	void createRasterProgram() throws IOException {
		int program = glCreateProgram();
		int vshader = createShader("org/lwjgl/demo/opengl/geometry/vs.glsl",
				GL_VERTEX_SHADER);
		int fshader = createShader("org/lwjgl/demo/opengl/geometry/fs.glsl",
				GL_FRAGMENT_SHADER);
		int gshader = createShader("org/lwjgl/demo/opengl/geometry/gs.glsl",
				GL_GEOMETRY_SHADER);
		glAttachShader(program, vshader);
		glAttachShader(program, fshader);
		glAttachShader(program, gshader);
		glBindAttribLocation(program, 0, "position");
		glBindAttribLocation(program, 1, "visible");
		glBindFragDataLocation(program, 0, "color");
		glLinkProgram(program);
		int linked = glGetProgrami(program, GL_LINK_STATUS);
		String programLog = glGetProgramInfoLog(program);
		if (programLog.trim().length() > 0) {
			System.err.println(programLog);
		}
		if (linked == 0) {
			throw new AssertionError("Could not link program");
		}
		this.program = program;
	}

	int vao;
	int program;

	@Override
	public void exit() {

	}

}
