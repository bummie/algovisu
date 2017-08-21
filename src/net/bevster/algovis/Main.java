package net.bevster.algovis;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;


public class Main extends JFrame
{
	private boolean isRunning = true;
	private final int FRAMES_PER_SECOND = 30;
	private int windowWidth = 720;
	private int windowHeight = 480;
	private long time;

	private int[] arrayVerdier = {10, 1, 5, 4, 9, 3,7,2,6};

	BufferedImage backBuffer;
	Insets insets;

	public static void main(String[] args)
	{
		Main app = new Main();
		app.run();
		System.exit(0);
	}

	private void init()
	{
		setTitle("Algoritmevisualisering");
		setSize(windowWidth, windowHeight);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		insets = getInsets();
		setSize(insets.left + windowWidth + insets.right,
				insets.top + windowHeight + insets.bottom);

		backBuffer = new BufferedImage(windowWidth, windowHeight,   BufferedImage.TYPE_INT_RGB);
	}

	private void  run()
	{
		init();

		while(isRunning)
		{
			time = System.currentTimeMillis();
			update();
			draw();
			time = (1000 / FRAMES_PER_SECOND) - (System.currentTimeMillis() - time);

			if (time > 0)
			{
				try
				{
					Thread.sleep(time);
				}
				catch(Exception e){}
			}
		}

		setVisible(false);
	}

	private void update()
	{
		// Oppdater grafer her
	}

	private void draw()
	{
		Graphics g = getGraphics();

		Graphics bbg = backBuffer.getGraphics();

		bbg.setColor(Color.WHITE);
		bbg.fillRect(0, 0, windowWidth, windowHeight);

		// Tegn søyler
		drawGraphs(bbg);

		g.drawImage(backBuffer, insets.left, insets.top, this);
	}

	private void drawGraphs(Graphics g)
	{
		g.setColor(Color.BLACK);

		int barWidth = windowWidth / arrayVerdier.length;
		for(int i = 0; i < arrayVerdier.length; i++)
		{
			int barHeight = arrayVerdier[i]*10;
			int y = windowHeight - barHeight;
			g.fillRect(i*barWidth, y, barWidth, barHeight);
		}

	}
}