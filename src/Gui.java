import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;


public class Gui extends JPanel {
	
	public static int level = 1 ;
	
	
	/* START Line Coordinate*/
	private double xLine; 
	private double yLine;
	private int xint,yint;
	/* END Line Coordinate */
	
	/* START Ball Initial  Coordinate */
	private static double xball = 30;
	private static double yball = 430;
	/* END Ball Initial  Coordinate */

	  private BufferedImage bg;
	  private BufferedImage wall;
	  private BufferedImage mon1;
	  private BufferedImage mon2;
	  private BufferedImage mon3;
	  private BufferedImage mon4;
	  private BufferedImage explosion;
	  private BufferedImage left;
	  private BufferedImage right;
	  private BufferedImage powerup;
	  private BufferedImage trophy;
	  
	  public static boolean powerhit = false; //a boolean variable for the power up initially set to false which is a tool to be used in the Gui class 
	  Random Xpower = new Random();
	  Random Ypower = new Random();
	  int xpower1 = Xpower.nextInt(270)+480; //level 1 power up x coordinate
	  int ypower1 = Ypower.nextInt(320); //level 1 power up y coordinate
	  int xpower2 = Xpower.nextInt(270)+480; //level 2 power up x coordinate
	  int ypower2 = Ypower.nextInt(320); //level 2 power up y coordinate
	  int xpower3 = Xpower.nextInt(270)+480; //level 3 power up x coordinate
	  int ypower3 = Ypower.nextInt(320); //level 3 power up y coordinate
	  int xpower,ypower; // will take the power up coordinates depending on the level
	  
	public Gui(){
		 super(); 
		    try /* Try To Catch IO Exception */
		    {    
		    	/* START Reading images */
		      bg = ImageIO.read(new File("src/bg.jpg")); 
		      wall = ImageIO.read(new File("src/wall.png"));
		      mon1 = ImageIO.read(new File("src/mon.png"));	
		      mon2 = ImageIO.read(new File("src/mon2.png"));	
		      mon3 = ImageIO.read(new File("src/mon3.png"));	
		      mon4 = ImageIO.read(new File("src/mon4.png"));	
		      explosion = ImageIO.read(new File("src/explosion.png"));
		      left = ImageIO.read(new File("src/left.png"));
		      right = ImageIO.read(new File("src/right.png"));
		      powerup = ImageIO.read(new File("src/powerup.png"));
		      trophy = ImageIO.read(new File("src/trophy.png"));
		      	/* END Reading images */
		      repaint();
		    } 
		    catch (IOException e) 
		    { 
		    	System.out.println("Some image/s is/are missing");
		    } 
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);//
		
		/* START Drawing BG, Wall, Monster, Power up and The wind direction */
		g.drawImage(bg, 0, 0,800,510,null);
		g.drawImage(wall,220,320,50,150,null);
		
		
		if(level == 1){
			g.drawImage(mon1,547,320,150,150,null);
			g.drawImage(powerup, xpower1, ypower1, 50, 50,null);
			xpower = xpower1;
			ypower = ypower1;
		}else if (level == 2) {
			g.drawImage(mon2,547,360,110,110,null);
			g.drawImage(powerup, xpower2, ypower2, 50, 50,null);
			xpower = xpower2;
			ypower = ypower2;
		}else if (level == 3) {
			g.drawImage(mon3,547,390,80,80,null);
			g.drawImage(powerup, xpower3, ypower3, 50, 50,null);
			xpower = xpower3;
			ypower = ypower3;
		} else if(level == 4 && Game.score == 600){
			g.drawImage(mon4,547,390,80,80,null);
		}
	    repaint();
	    if(Game.Wind > 0){
	    	g.drawImage(right, 0, 0, 30, 30, null);
	    }
		else if (Game.Wind < 0){
			g.drawImage(left, 0, 0, 30, 30, null);
		}
	    /* END Drawing BG, Wall, Monster, Power up and The wind direction */
	    
		/* START Drawing The Line for Aiming */
		g.setColor(Color.RED);// Setting up color
		xLine = 50+100*Math.cos(Math.toRadians(Game.Angle));// Calculating X coordinate 
		yLine = 450-100*Math.sin(Math.toRadians(Game.Angle));// Calculating Y coordinate 
		xint = (int) xLine; // Convert  it to INT
		yint = (int) yLine;	// Convert  it to INT
		g.setColor(Color.RED);// Setting up color
		g.drawLine(50, 450, xint ,yint); // Calling Draw Function
		
		/* END Drawing The Line for Aiming */
	
		/* START Drawing The Ball */
		Graphics2D g2dball = (Graphics2D) g;// creation of the ball 
		Ellipse2D.Double ball = new Ellipse2D.Double(xball,yball,40,40);
		g2dball.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2dball.fill(ball);
		Graphics2D g2dtracer = (Graphics2D) g;//creation of the tracer in case the ball goes over the upper boundary
		g2dtracer.setColor(Color.orange);
		if(xball < 0 || xball > 760 || yball > 440 || (xball > 180 && xball < 230 && yball < 430 && yball > 280) || (xball > 507 && xball < 587 && yball < 430 && yball > 350&& Gui.level == 3) ||(xball > 507 && xball < 587 && yball < 430 && yball > 350&& Gui.level == 3)|| (xball > 507 && xball < 617 && yball < 430 && yball > 320 && Gui.level == 2) || (xball > 507 && xball < 657 && yball < 430 && yball > 280 && Gui.level == 1))
		{
			g.drawImage(explosion,(int)xball-80,(int)yball-70,200,200,null);// drawing the explosion if the ball has hit anything
			
		} else if(yball<0) {
			
			g2dtracer.fill3DRect((int) xball+10, 5, 20, 15, true); //drawing the tracer 
			
		} else if(xball > xpower-40 && xball < xpower && yball < ypower && yball > ypower-40) //checking to see if the the power up has been hit
		{
			powerhit = true; 
		} else if(Game.score == 600) {
			g.drawImage(trophy,275,125,250,250,null);
		}
		
		/* END Drawing The Ball and the Tracer */
		
	
	}// END paintComponent
	
	/* START Getters and Setters For the Private Var For Ball*/
	public static double getXball() {
		return xball;
	}

	public static void setXball(double xball) {
		Gui.xball = xball;
	}

	public static double getYball() {
		return yball;
	}

	public static void setYball(double yball) {
		Gui.yball = yball;
	}
	/* START Getters and Setters For the Private Var For Ball*/
	
	
	
}// END Class