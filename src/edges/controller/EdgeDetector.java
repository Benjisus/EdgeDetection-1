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
	private boolean mapToGradient;
	
	private BufferedImage image;
	private Component parentComponent;
	
	public EdgeDetector(Component parentComponent)
	{
		threshold = 300;
		mapToGradient = false;
		
		image = null;
		this.parentComponent = parentComponent;
	}
	
	public void start()
	{
		chooseImage();
		performDetection();
		saveImage();
	}
	
	private void chooseImage()
	{
		final JFileChooser fileChooser = new JFileChooser();
		int returnState = fileChooser.showOpenDialog(parentComponent);
		File file = fileChooser.getSelectedFile();
		
		try
		{
			image = ImageIO.read(file);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void performDetection()
	{
		for(int row = 0; row < image.getWidth(); row++)
		{
			for(int col = 0; col < image.getHeight(); col++)
			{
				int rgb = image.getRGB(row,col);
				Color c = new Color(rgb);
				int r = c.getRed();
				int b = c.getBlue();
				int g = c.getGreen();
				int grey = (r + b + g)/3;
				Color c2 = 	new Color(grey, b, grey);
				image.setRGB(row,col,c2.getRGB());
			}
		}
	}
	
	private void saveImage()
	{
		final JFileChooser fileChooser = new JFileChooser();
		int returnState = fileChooser.showSaveDialog(parentComponent);
		File file = fileChooser.getSelectedFile();
		try
		{
			ImageIO.write(image, "jpeg", file);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//Setters
	public void setThreshold(int threshold)
	{
		this.threshold = threshold;
	}
	
	public void setMapToGradient(boolean mapToGradient)
	{
		this.mapToGradient = mapToGradient;
	}
}
