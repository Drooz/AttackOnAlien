import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Game extends JPanel  {
	
	
	public static ScheduledExecutorService exec1 = Executors.newSingleThreadScheduledExecutor(); // A timer for level 1
	public static ScheduledExecutorService exec2 = Executors.newSingleThreadScheduledExecutor(); // A timer for level 2
	public static ScheduledExecutorService exec3 = Executors.newSingleThreadScheduledExecutor(); // A timer for level 3
	
	/* START Variables */
	static int Velocity = 0; //This variable keeps the velocity from the velocity slider
	double vx,vy; //The x and y components of the velocity
	public static int Angle = 0; //This variable keeps the angle from the angle slider
	double g = 0.9; //gravity
	private static Random rand1 = new Random();
	private static Random rand2 = new Random();
	public static double Wind = rand1.nextDouble()-rand2.nextDouble();
	double n = Wind;
	boolean right = true;
	boolean up = true;
	boolean touch = false;
	int time = 0; //It will be increased every second 
	int count = 0;//used to increase time each second
	public static int score = 0;
	String id = ""; // A string variable which is used to write the scores down 
	boolean flag = true;
	
	/* START Initiating 2 Panels p1 and p2 */
	JPanel p1 = new Gui();// For the GUI
	JPanel p2 = new JPanel();// For the controls button
	/* END Initiating 2 Panels p1 and p2 */
	
	//JPanel container = new JPanel();  ***** TO BE DELETED 
	
	/* START Creating Control Button, Label and Slider */
	JButton start = new JButton("Start"); 
	JButton next = new JButton("Next");
	JSlider velocity = new JSlider(10,100,10);
	JLabel v = new JLabel("Velocity: ");
	JSlider angle = new JSlider(0,90,0);
	JLabel a = new JLabel("Angle: ");
	JLabel timelabel = new JLabel("Time : ");
	JLabel wind = new JLabel("Wind : ");
	JLabel level = new JLabel("Level: ");
	JLabel scorelabel = new JLabel("Score: ");
	JLabel Value_of_vel = new JLabel("0 m/s");
	JLabel Value_of_ang = new JLabel("0 degrees");
	/* END Creating Control Button, Label and Slider */
	/* END Variables*/
	
	public Game() {
		/* START Initiating The Frame */
		JFrame f = new JFrame("Attack On Alien");
		f.setSize(800,600);
		f.setLayout(new BorderLayout());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Close The Threat
		f.setResizable(false);// Fixed Window Size
		f.setVisible(true);
		/*END Initiating The Frame */
		
		
		/* START Adding The Objects */
		p2.setLayout(new GridLayout(2,6));
		p2.add(a);
		p2.add(angle);
		p2.add(Value_of_ang);
		p2.add(v);
		p2.add(velocity);
		p2.add(Value_of_vel);
		p2.add(start); 
		p2.add(timelabel);
		p2.add(wind);
		p2.add(level);
		p2.add(scorelabel);
		p2.add(next);
		p2.setVisible(true);
		f.add(p2,BorderLayout.SOUTH);
		/* END Adding The Objects */
		
		/* START Creating p1 Layout */
		p1.setSize(800,500);
		p1.setLayout(null);
		p1.setVisible(true);
		f.add(p1,BorderLayout.CENTER);
		/* END Creating p1 Layout */
	
		/* START Setting Up Wind Indicator */
		wind.setText("Wind : "+Wind*10);
		/* END Setting Up Wind Indicator */
		
		/* START Setting Up Velocity Live Preview Indicator */
		velocity.addChangeListener(new ChangeListener() {
			 public void stateChanged(ChangeEvent e) {
				 Velocity = velocity.getValue(); 
				 Value_of_vel.setText(""+Velocity+" m/s");
			 }
		});
		/* END Setting Up Velocity Live Preview Indicator */
		
		/* START Setting Up Angle Live Preview Indicator */
		angle.addChangeListener(new ChangeListener() {
			 public void stateChanged(ChangeEvent e) {
				 Angle = angle.getValue();
				 Value_of_ang.setText(""+Angle+" degrees");
				 p1.repaint();
			 }
		});
		/* END Setting Up Angle Live Preview Indicator */
		try {
			playSound("src/monster.wav");
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (LineUnavailableException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedAudioFileException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		/* START ActionListener For Start Button */
		start.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					playSound("src/blop.wav");
					playSound("src/fall.wav");
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println(1);
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println(2);
				} catch (UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println(3);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println(4);
				}
				velocity();
				if(Gui.level == 1) {
					exec1.scheduleAtFixedRate(new Runnable() {
						@Override
						public void run() {
							
							level.setText("Level : 1");
							shoot();
							p1.repaint();
							count++;
							if(count%100 == 0) {
								timelabel.setText("Time: "+(time++));
							}
						}
					}, 0, 10, TimeUnit.MILLISECONDS);
				} else if(Gui.level == 2){
					exec2.scheduleAtFixedRate(new Runnable() {
						@Override
						public void run() {
							level.setText("Level : 2");
							shoot();
							p1.repaint();
							count++;
							if(count%100 == 0) {
								timelabel.setText("Time: "+(time++));
							}
						}
					}, 0, 10, TimeUnit.MILLISECONDS);
				} else if(Gui.level == 3){
					exec3.scheduleAtFixedRate(new Runnable() {
						@Override
						public void run() {
							level.setText("Level : 3");
							shoot();
							p1.repaint();
							count++;
							if(count%100 == 0) {
								timelabel.setText("Time: "+(time++));
							}
						}
					}, 0, 10, TimeUnit.MILLISECONDS);
				}
			}
			
		});	
		/* END ActionListener For Start Button */
		
		/* NEXT ActionListener For Start Button */
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Gui.setXball(30);
				Gui.setYball(430);
				Gui.level++;
				velocity();
				Gui.powerhit = false;
				try {
					if(Gui.level == 1 || Gui.level == 2 || Gui.level == 3) playSound("src/monster.wav");
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
	}// END Constructor
	public void velocity(){
		vx = Velocity*Math.cos(Math.toRadians(Angle));	
		vy = Velocity*Math.sin(Math.toRadians(Angle));
		System.out.println("VEL FUN");
	}
	public void shoot(){
		vx += Wind; //wind in effect 
		vy = vy - g; //gravity in effect
		if(Gui.getXball() < 0 || Gui.getXball() > 760 || Gui.getYball() > 440 || (Gui.getXball() > 180 && Gui.getXball() < 230 && Gui.getYball() < 430 && Gui.getYball() > 280) ) {
			vx = 0;
			vy = 0;
			try {
				playSound("src/explosion.wav"); //explosion sound
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(Gui.level == 1) exec1.shutdownNow();
			else if(Gui.level == 2) exec2.shutdownNow();
			else if(Gui.level == 3) exec3.shutdownNow();
		} else if(Gui.getXball() > 507 && Gui.getXball() < 587 && Gui.getYball() < 430 && Gui.getYball() > 350 && Gui.level == 3) {
			try {
				playSound("src/explosion.wav"); //explosion sound
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			vx = 0;
			vy = 0;
			score = score + 300;
			exec3.shutdownNow();
			scorelabel.setText("Score : " + score);
			
		} else if(Gui.getXball() > 507 && Gui.getXball() < 617 && Gui.getYball() < 430 && Gui.getYball() > 320 && Gui.level == 2) {
			try {
				Game.playSound("src/explosion.wav"); //explosion sound
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}vx = 0;
			vy = 0;
			score = score + 200;
			exec2.shutdownNow();
			scorelabel.setText("Score : " + score);
			
		} else if(Gui.getXball() > 507 && Gui.getXball() < 657 && Gui.getYball() < 430 && Gui.getYball() > 280 && Gui.level == 1) {
			try {
				Game.playSound("src/explosion.wav"); //explosion sound
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			vx = 0;
			vy = 0;
			score = score + 100;
			exec1.shutdownNow();
			scorelabel.setText("Score : " + score);
		} else if(Gui.powerhit == true) //hitting the power up
		{
			Gui.setXball(508); 
			Gui.setYball(351); 
		}
		Gui.setXball(Gui.getXball()+vx/10);
		Gui.setYball(Gui.getYball()-vy/10);
		System.out.println(vy+"   " +vx);
	}// END Shoot Function

	public static void main(String[] args) {
		Game g = new Game();
	}
	
	public static void playSound(String filename) throws MalformedURLException, LineUnavailableException, UnsupportedAudioFileException, IOException{
	    
		File url = new File(filename);
	    Clip clip = AudioSystem.getClip();

	    AudioInputStream ais = AudioSystem.
	        getAudioInputStream( url );
	    clip.open(ais);
	    clip.start();
	}
	
}// END Class


