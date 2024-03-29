package edges.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class EdgeDetector
{
	private int threshold;
	private boolean useGradient;

	private BufferedImage image;
	private Component parentComponent;

	/**
	 * Creates a new edge detector. threshold is set to 300 by default.
	 * 
	 * @param parentComponent
	 *            the parent of the file selection dialog boxes.
	 */
	public EdgeDetector(Component parentComponent)
	{
		threshold = 300;
		useGradient = false;

		image = null;
		this.parentComponent = parentComponent;
	}

	/**
	 * User chooses an image, edge detection is performed, image is saved where
	 * the user wants.
	 */
	public void start() {
		chooseImage();
		performDetection();
		saveImage();
	}

	public void startCreative() {
		chooseImage();
		performCreative();
		saveImage();
	}

	/**
	 * Opens a new file selection window so the user can select an image to
	 * perform on.
	 */
	private void chooseImage() {
		final JFileChooser fileChooser = new JFileChooser();
		int returnState = fileChooser.showOpenDialog(parentComponent);
		File file = fileChooser.getSelectedFile();

		try {
			image = ImageIO.read(file);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Changes the image based on the edges detected.
	 */
	private void performDetection() {
		int values[][] = fillArray();

		if (!useGradient) {
			for (int x = 0; x < values.length; x++) {
				for (int y = 0; y < values[0].length; y++) {
					if (values[x][y] > threshold) {
						image.setRGB(x, y, Color.WHITE.getRGB());
					}
					else {
						image.setRGB(x, y, Color.BLACK.getRGB());
					}
				}
			}
		}
		else {
			for (int x = 0; x < values.length; x++) {
				for (int y = 0; y < values[0].length; y++) {
					double gradRatio = Math.sqrt(values[x][y] / 1530.0f);
					int gradient = (int) (255 * gradRatio);

					image.setRGB(x, y, new Color(gradient, gradient, gradient).getRGB());
				}
			}
		}

	}

	private int[][] fillArray() {
		int[][] values = new int[image.getWidth()][image.getHeight()];

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int grad = Math.abs(grad(x + 1, y) - grad(x - 1, y)) + Math.abs(grad(x, y + 1) - grad(x, y - 1));
				values[x][y] = grad;
			}
		}

		return values;
	}

	private void performCreative() 
	{

		int[][] values = fillArray();

		for (int x = 0; x < values.length; x++) 
		{
			for (int y = 0; y < values[0].length; y++) 
			{
				int value = values[x][y];

				if(value > 300) 
				{
					image.setRGB(x, y, Color.RED.getRGB());
				}
				else if(value > 250){

					image.setRGB(x, y, Color.ORANGE.getRGB());
				}
				else if(value > 200)
				{

					image.setRGB(x, y, Color.YELLOW.getRGB());
				}
				else if(value > 150) 
				{
					image.setRGB(x, y, Color.GREEN.getRGB());
				}
				else if(value > 100) 
				{
					image.setRGB(x, y, Color.PINK.getRGB());
				}
				else if(value > 50)
				{
					image.setRGB(x, y, Color.CYAN.getRGB());
				}
				else if(value > 25)
				{
					image.setRGB(x, y, Color.MAGENTA.getRGB());
				}
				else if(value > 5)
				{
					image.setRGB(x, y, Color.BLUE.getRGB());
				}
				else 
				{
					image.setRGB(x, y, Color.BLACK.getRGB());
				}
			}
		}
	}

	/**
	 * Opens a file saving window to the user can specify where to save the
	 * edited image.
	 */
	private void saveImage() {
		final JFileChooser fileChooser = new JFileChooser();
		int returnState = fileChooser.showSaveDialog(parentComponent);
		File file = fileChooser.getSelectedFile();
		try {
			ImageIO.write(image, "png", file);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * gets the sum of all color channels found in the pixel at the given
	 * coordinates.
	 * 
	 * @param x
	 *            x position of the pixel.
	 * @param y
	 *            y position of the pixel.
	 * @return sum of all color channels combined.
	 */
	private int grad(int x, int y) {
		if (x > 0 && x < image.getWidth()) // x-axis in bounds
		{
			if (y > 0 && y < image.getHeight()) // y-axis in bounds
			{
				Color color = new Color(image.getRGB(x, y));
				return (color.getRed() + color.getGreen() + color.getBlue());
			}
		}

		return 0; // out of bounds return 0
	}

	// Setters
	/**
	 * Sets the threshold to be used when detecting edges.
	 * 
	 * @param threshold
	 */
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	/**
	 * If true: a gray scale image will be produced based on the gradient
	 * values. If false: a binary decision is made producing a black and white
	 * image.
	 * 
	 * @param mapToGradient
	 */
	public void setUseGradient(boolean useGradient) {
		this.useGradient = useGradient;
	}
}
