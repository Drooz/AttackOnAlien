# AttackOnAlien

CMP 2004 Term Project Report 

(Attack On Alien) 

 

Görkem Yağız ÖZCAN ID : 1409859 

Fares Saleh ID : 1408885 

Yunus Emre Vural ID : 1318199 

 

The Project Purpose  

Attack On Alien project is a game which simulate the well known game Angry Birds. The game is implemented on eclipse using Java programming language. The aim of this project is to accomplish what we have been learning in the past 12 weeks. Additionally we have been trying to utilize every programming concept such as OOP,  frame and panel usage, GUIs etc. 

Project Explanation " Minimum Criteria " 

The game frame is divided into two sections using the border layout, which is the default layout, in the south region we implemented the control buttons like start, velocity, angle and next. In addition we also added some indicators such as time, score, level and wind. However in the center we did add the GUI or Graphical User Interface class, which is inherited from JPanel, which actually draws the objects in the game. The GUI class is also responsible for the game animation like the ball movement, explosion, etc. Keep in mind that the game class contains the game engine from calculating the velocity, changing the x and y coordinate, increasing the score and level. The game class consists of two functions which are responsible of the math going which are called "shoot" and "velocity". In the shoot function there are some if statements which determine if the ball hits the ground, wall or the alien. It also changes the X and Y coordinates constantly. Wind and gravity effect the vx and vy components. We used the following formula : 

 

The Gravity: vy = vy - g; 

The X and Y Coordinate :  

Gui.setXball(Gui.getXball()+vx/10); **10** IS JUST TO DECREASE ITS RATE  

Gui.setYball(Gui.getYball()-vy/10); 

Wind: vx += Wind; 

 

 The score and level are also being changed there as well. The velocity function which calculates the Vx and Vy uses the following formula : 

vx = Velocity*Math.cos(Math.toRadians(Angle));  

vy = Velocity*Math.sin(Math.toRadians(Angle)); 

 

The GUI class will implement the paintComponent function which its job is to draw the objects in the game.  The game does include sound effects so don't forget to use a speaker while the game is running. 

The code is heavily commented and organized which will make it easier for the reviewer to read and understand the code. 

Game Rule  

Your aim is to hit the alien in one shot. Otherwise you will be forced to move to the next level for game balance purpose. 

Be careful of the wind and the arrow in the top left corner gives you the direction of the wind. 

Power up concept: if you hit the power up the ball will teleport to the target which means a guaranteed hit . The position of the power up is random so you can't memorize the exact velocity and angle. 

Screenshots 

 

 

 

 
