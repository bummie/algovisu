package net.bevster.algovis;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JFrame;


public class Main extends JFrame
{
	private boolean isRunning = true;
	private final int FRAMES_PER_SECOND = 100;
	private int windowWidth = 720;
	private int windowHeight = 480;
	private long time;

	private Font textFont = new Font ("Courier New", Font.BOLD, 17);


	private final int ARRAY_SIZE = 150;
	private int arrayPos = 0, lastSortedOrigin = 0;
	private boolean swappedInt = true, isSorted = false;
	private int[] arrayVerdier;

	private int statSwaps = 0;

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
		hentTilfeldigeArrayVerdier(ARRAY_SIZE);

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
		if(!isSorted)
		{

			if(arrayPos >= arrayVerdier.length-1)
			{
				arrayPos = 0;
				if(swappedInt = false)
					isSorted = true;
				swappedInt = false;
			}

			if(arrayVerdier[arrayPos] > arrayVerdier[arrayPos+1])
			{
				int buffer = arrayVerdier[arrayPos];
				arrayVerdier[arrayPos] = arrayVerdier[arrayPos+1];
				arrayVerdier[arrayPos+1] = buffer;
				swappedInt = true;
				statSwaps++;
			}
			arrayPos++;
		}
	}

	private void draw()
	{
		Graphics g = getGraphics();

		Graphics bbg = backBuffer.getGraphics();

		bbg.setColor(Color.WHITE);
		bbg.fillRect(0, 0, windowWidth, windowHeight);

		// Tegn søyler
		drawGraphs(bbg);

		// Statestikktekst
		g.setColor(Color.black);
		g.setFont(textFont);
		g.drawString("Swaps: " + statSwaps , 100, 100);

		g.drawImage(backBuffer, insets.left, insets.top, this);
	}

	private void drawGraphs(Graphics g)
	{

		int barWidth = windowWidth / arrayVerdier.length;
		int barHeightMultiplier = (windowHeight/arrayVerdier.length);
		for(int i = 0; i < arrayVerdier.length; i++)
		{
			if(i%2==0)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.CYAN);

			int barHeight = arrayVerdier[i]*barHeightMultiplier;
			int y = windowHeight - barHeight;
			g.fillRect(i*barWidth, y, barWidth, barHeight);
		}

		g.setColor(Color.RED);
		g.fillRect(arrayPos*barWidth, 0, barWidth, windowHeight);
	}

	private void hentTilfeldigeArrayVerdier(int size)
	{
		arrayVerdier = new int[size];
		for(int i = 0; i < size; i++)
		{
			arrayVerdier[i] = new Random().nextInt(size);
		}
	}
}