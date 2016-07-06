package ch.wados.starfury.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VertexArray {

	private final int id;
	private final int count;
	private final int mode;
	private final int[] buffers;
	private final int[] widths;

	/**
	 * Creates a new vertex array object. The object is binded.
	 * 
	 * @param usage
	 *            the usage. e.g. {@code GL_STATIC_DRAW}
	 * @param mode
	 *            the draw mode. e.g. {@code GL_TRIANGLES}
	 * @param size
	 *            the number of vertices.
	 * @param buffers
	 *            the buffers for the object.
	 */
	public VertexArray(int usage, int mode, int size, float[]... buffers) {
		this.id = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(this.id);

		this.count = size;
		this.mode = mode;
		this.buffers = new int[buffers.length];
		this.widths = new int[buffers.length];

		for (int i = 0; i < buffers.length; i++) {
			this.buffers[i] = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.buffers[i]);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffers[i], usage);
			this.widths[i] = buffers[i].length / size;
		}
	}

	/**
	 * Binds the Vertex Array
	 */
	public void bind() {
		GL30.glBindVertexArray(this.id);
	}

	/**
	 * Binds the Vertex Array, enables the Vertex Attribute Arrays and binds the
	 * buffers.
	 */
	public void bindAndEnable() {
		this.bind();
		this.enable();
	}

	/**
	 * Enables the Vertex Attribute Arrays and binds the buffers
	 */
	public void enable() {
		for (int i = 0; i < this.buffers.length; i++) {
			GL20.glEnableVertexAttribArray(i);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.buffers[i]);
			GL20.glVertexAttribPointer(i, this.widths[i], GL11.GL_FLOAT, false,
					0, 0);
		}
	}

	/**
	 * Disables the Vertex Attribute Arrays
	 */
	public void disable() {
		for (int i = 0; i < this.buffers.length; i++)
			GL20.glDisableVertexAttribArray(i);
	}

	/**
	 * Draws the vertices
	 */
	public void draw() {
		GL11.glDrawArrays(this.mode, 0, this.count);
	}

}
