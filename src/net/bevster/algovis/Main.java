package net.bevster.algovis;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JFrame;


public class Main extends JFrame
{
	private boolean isRunning = true;
	private final int FRAMES_PER_SECOND = 500;
	private int windowWidth = 720;
	private int windowHeight = 480;
	private long time;
	private Font textFont;

	// Colors
	private Color colBG = new Color(195, 200, 205);
	private Color colPair = new Color(22, 160, 133);
	private Color colOdd = new Color(44, 62, 80);

	private final int ARRAY_SIZE = 300;
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
		textFont = new Font ("Courier New", Font.BOLD, 25);
		hentTilfeldigeArrayVerdier(ARRAY_SIZE);

		setTitle("Algoritmevisualisering");
		setSize(windowWidth, windowHeight);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		insets = getInsets();
		setSize(insets.left + windowWidth + insets.right,
				insets.top + windowHeight + insets.bottom);

		backBuffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
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
		bubbleSort();
		//quickSort();
	}

	private void draw()
	{
		Graphics g = getGraphics();

		Graphics bbg = backBuffer.getGraphics();

		bbg.setColor(colBG);
		bbg.fillRect(0, 0, windowWidth, windowHeight);

		// Tegn søyler
		drawGraphs(bbg);

		// Statestikktekst
		bbg.setColor(Color.BLACK);
		bbg.setFont(textFont);
		bbg.drawString("Swaps: " + statSwaps + " Sorted: " + isSorted , 0, 20);
		bbg.drawString("Size: " + arrayVerdier.length + " Swap: " + swappedInt , 0, 40);


		g.drawImage(backBuffer, insets.left, insets.top, this);
	}

	private void drawGraphs(Graphics g)
	{

		int barWidth = windowWidth / arrayVerdier.length;
		int barHeightMultiplier = (windowHeight/arrayVerdier.length);
		for(int i = 0; i < arrayVerdier.length; i++)
		{
			if(arrayVerdier[i]%2==0 && !isSorted)
				g.setColor(colOdd);
			else
				g.setColor(colPair);

			int barHeight = arrayVerdier[i]*barHeightMultiplier;
			int y = windowHeight - barHeight;
			g.fillRect(i*barWidth, y, barWidth, barHeight);
		}

		if(!isSorted)
		{
			g.setColor(Color.RED);
			g.fillRect(arrayPos*barWidth, 0, barWidth, windowHeight);
		}
	}

	private void hentTilfeldigeArrayVerdier(int size)
	{
		arrayVerdier = new int[size];
		for(int i = 0; i < size; i++)
		{
			arrayVerdier[i] = i;
		}
		shuffleArray(arrayVerdier);
	}


	// Fisher-Yates shuffle
	// https://stackoverflow.com/a/1520212/6826158
	static void shuffleArray(int[] ar)
	{
		// If running on Java 6 or older, use `new Random()` on RHS here
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--)
		{
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	// Sorteringsalgoritmer

	private void bubbleSort()
	{
		if(!isSorted)
		{
			if(arrayPos >= arrayVerdier.length-1)
			{
				arrayPos = 0;
				if(swappedInt == false)
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

	private void quickSort()
	{

	}
}