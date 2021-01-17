/**
 * GameGrame.java
 * a class where the game run
 * By Vincent Zhang
 * 2018/1/18
 * Teacher: Mr.Mangat
 * */
import javax.swing.JPanel;
import java.awt.Point;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Queue;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import java.io.File;
class GameFrame extends JPanel{
  
  //create varible
  Boolean moved;
  Boolean[][] visited;
  int round;
  int winner;
  int mapSizeX;
  int mapSizeY;
  int winSizeY=FinnalGame.winy;
  int winSizeX=FinnalGame.winx;
  Character c;
  Character[][] map2;
  Character currentDisplayCharacter;
  MapObject[][] map;
  MapObject currentDisplayLand;
  static Queue<String>q;
  
  
  
  //constructor
  GameFrame(Character[] player1, Character[] player2, MapObject[][]map, int mapx, int mapy) {
    
    //set the varibles
    moved=false;
    winner=-1;
    round=0;
    q=new LinkedList<String>();
    visited=new Boolean[mapx][mapy];
    
    //set the map
    mapSizeX=mapx;
    mapSizeY=mapy;
    map2=new Character[mapSizeX][mapSizeY];
    for(int i=0;i<mapSizeX;i++){
      for(int e=0;e<mapSizeY;e++){
        map2[i][e]=new TempCharacter();
      }
    }
    this.map=map;
    
    //loop the character list and added it to the map
    for(int i=1;i<=player1.length;i++){
      player1[i-1].setXPosition(i);
      player1[i-1].setYPosition(1);
      map2[i][1]=player1[i-1];
      if(map[i][1] instanceof Wall){
        map[i][1]=new Grass();
      }
    }
    for(int i=1;i<=player2.length;i++){
      player2[i-1].setXPosition(mapSizeX-i);
      player2[i-1].setYPosition(mapSizeY-2);
      map2[mapSizeX-i][mapSizeY-2]=player2[i-1];
      if(map[mapSizeX-i][mapSizeY-2] instanceof Wall){
        map[mapSizeX-i][mapSizeY-2]=new Grass();
      }
    }
  }//end
  
  
  /* *
   * showMovingRange method
   *a method that draw the color of a box, depend on if the character can path through it
   * by Vincent Zhang
   * 2018/1/18
   * Teacher: Mr.Mangat
   * */
  public void showMovingRange(int x,int y,Graphics g){
    
    //if not drawed. then draw. after draw, turn the visit to true, so it will not draw again
    if(!visited[x][y]){
      visited[x][y]=true;
      g.setColor(new Color(0,0,255, 100));
      try{
        if(c.getXPosition()==x&&c.getYPosition()==y){
          g.setColor(new Color(0,0,255, 50));
        }
      }catch(Exception e){}
      
      
      
      //draw
      g. fillRect(winSizeX/mapSizeX*x,winSizeY/mapSizeY*y,winSizeX/mapSizeX,winSizeY/mapSizeY); 
      g.setColor(Color.red);
      g. drawRect(winSizeX/mapSizeX*x,winSizeY/mapSizeY*y,winSizeX/mapSizeX,winSizeY/mapSizeY); 
    }
  }
  
  
  /* *
   *drawMovingRange method
   *a method that recursed through the map, finding the moveing range of a character, and call showMovingRange method
   * by Vincent Zhang
   * 2018/1/18
   * Teacher: Mr.Mangat
   * */
  public void drawMoveRange(Point currentPoint, int round, Graphics g){
    
    //getting the current point
    int x=(int)currentPoint.getX(); 
    int y=(int)currentPoint.getY();
    
    //if reach the edge of the map, return
    if(x<0||x>=FinnalGame.mapx||y<0||y>=FinnalGame.mapy){
      return;
    }
    
    
    //if reach enemy, return 
    if(!(map2[x][y] instanceof TempCharacter)){
      if(map2[x][y].getTeam() !=c.getTeam()){
        return;
      }
    }
    
    //if reach wall, return 
    if((map[x][y] instanceof Wall)){
      return;
    }
    
    //if it goes bigger then the moving range of a character, then return
    if(round>c.getMove()){
      return;
    }
    //draw it
    showMovingRange(x, y, g);
    
    //calculate the cost of moving a box
    int addRound=c.checkMoveCost(map[x][y]);
    
    //recurse
    drawMoveRange(new Point(x+1, y),  round+addRound, g);
    drawMoveRange(new Point(x-1, y),  round+addRound,g);
    drawMoveRange(new Point(x, y+1), round+addRound,g);
    drawMoveRange(new Point(x, y-1), round+addRound,g);
    
  }
  
  
  
  
  //overwrite
  public void paintComponent(Graphics g) {
    super.paintComponent(g); //required

  
      //if the winner is someone, then display if the user win or lose, else draw the game
      if(winner!=-1){
        g.setFont(new Font("TimesRoman", Font.PLAIN, 200));
        if(winner==FinnalGame.playerNumber){
          g.setColor(new Color(255,210,0,50));
          g.fillRect(0, 0, winSizeX+300, winSizeY);
          g.setColor(Color.black);
          g.drawString("You Lose", 100, winSizeY-300);
        }else{
          g.setColor(new Color(255,0,0,50));
          g.fillRect(0, 0, winSizeX+300, winSizeY);
          g.setColor(Color.black);
          g.drawString("You Win",100,winSizeY-300);
        }
      }else{
        
        //reset the visited varible
        for(int i=0;i<mapSizeX;i++){
          for(int e =0;e<mapSizeY;e++){
            visited[i][e]=false;
          }
        }
        
        
        //a for loop to draw types of land
        for(int i=0;i<mapSizeX;i++){
          for(int e=0;e<mapSizeY;e++){
            drawRecObject(i, e, g); //call method that draw different type of land
          }
        }
        
        
        g.setColor(Color.black);  //set color to black
        
        ////draw the black box in the map to tell the different between different part of land
        for(int i=0;i<mapSizeX;i++){
          for(int e=0;e<mapSizeY;e++){
            g. drawRect(winSizeX/mapSizeX*i,winSizeY/mapSizeY*e,winSizeX/mapSizeX,winSizeY/mapSizeY);
          }
        }
        
        //if moved, then draw attack range, if not moved, then draw moveing range
        if(moved){
          drawAttackingRange(g);
        }
        if(!moved){
          drawMoveRange(new Point(c.getXPosition(), c.getYPosition()), 0,  g);
        }
        
        
        //draw characters in to the map
        for(int i=0;i<mapSizeX;i++){
          for(int e=0;e<mapSizeY;e++){
            if(!(map2[i][e] instanceof TempCharacter)){
              drawCharacter(i, e, g);
            }
          }
        }
        
        
        //draw the box to show character's attack, health, and type
        g.setColor(Color.black);
        g.drawRect(0, 0, winSizeX+300-1, winSizeY-1);
        g.drawLine(winSizeX+300-1, (winSizeY-1)/4,winSizeX-1, (winSizeY-1)/4);
        g.drawLine(winSizeX+300-1, (winSizeY-1)/2,winSizeX-1, (winSizeY-1)/2);
        g.drawLine(winSizeX+300-1, (winSizeY-1)/4*3,winSizeX-1, (winSizeY-1)/4*3);
        g.drawString("Round pass:"+round, winSizeX+50, 160);
        // if it is temp character, then show nothing there
        if(currentDisplayCharacter instanceof TempCharacter){
          g.drawString("Nothings there", winSizeX+75, winSizeY/4+25);
        }else{
          // show character's attack, health, and type...
          g.drawImage(currentDisplayCharacter.getImageDis(), winSizeX, 1,  150,150,this);
          g.drawString("Class: "+currentDisplayCharacter.getClass()+", move "+currentDisplayCharacter.getMove(), winSizeX+25, winSizeY/4+25);
          g.drawString("Health: "+currentDisplayCharacter.getHealth()+"/"+currentDisplayCharacter.getMaxHealth(), winSizeX+75, winSizeY/4+40);
          g.drawString("Attack: "+currentDisplayCharacter.getAttack(),winSizeX+77, winSizeY/4+55);
          g.drawString("Defence: "+currentDisplayCharacter.getDefence(),winSizeX+73, winSizeY/4+75);
          g.drawString("Speed: "+currentDisplayCharacter.getSpeed(), winSizeX+73, winSizeY/4+90);
          g.drawString("Position: "+currentDisplayCharacter.getXPosition()+":"+currentDisplayCharacter.getYPosition(), winSizeX+73, winSizeY/4+110);
        }
        
        //show the type of land, is nothing, then put nothings there
        g.drawImage(currentDisplayLand.getImage(), winSizeX+10, winSizeY/2+7, 150, 150,this);
        if(currentDisplayLand instanceof Grass){
          g.drawString("Grass", winSizeX+75, winSizeY/4+ winSizeY/2+25);
        }else{
          g.drawString("Type: "+currentDisplayLand.getClass(),winSizeX+75, winSizeY/4+ winSizeY/2+25);
          g.drawString("Position: "+currentDisplayLand.getX()+":"+currentDisplayLand.getY(),winSizeX+75, winSizeY/4+ winSizeY/2+45);
          if(currentDisplayLand instanceof Wall){
            g.drawString("Health "+((Wall)(currentDisplayLand)).getHealth(),winSizeX+75, winSizeY/4+ winSizeY/2+65);
          }
        }
        
        //draw event happened in the game
        //poll and display everything in a q untail it is "", and put it to another q, at the end, put it back
        String s="";
        Queue<String> q2=new LinkedList<String> ();
        int i=0;
        try{
          while(!q.isEmpty()){
            s=q.poll();
            g.drawString(s, winSizeX+5,winSizeY-10*i-5 );
            q2.add(s);
            i++;
          }
          while(!q2.isEmpty()){
            s=q2.poll();
            q.add(s);
            i++;
          }
          
        }catch(Exception e){}
      } 

    
  }
  
  /* *
   * drawRecObject method
   * a method draw the different types of map land 
   * by Vincent Zhang
   * 2018/1/18
   * Teacher: Mr.Mangat
   * */
  public void drawRecObject(int i, int e, Graphics g){
    g.drawImage(map[i][e].getImage(),winSizeX/mapSizeX*i,winSizeY/mapSizeY*e,winSizeX/mapSizeX,winSizeY/mapSizeY, this);
  }
  
  /* *
   * drawAttackingRange  method
   * a method draw the attack range of current character
   * by Vincent Zhang
   * 2018/1/18
   * Teacher: Mr.Mangat
   * */
  public void drawAttackingRange (Graphics g){
    
    //loop through map
    for(int i=0;i<mapSizeX;i++){
      for(int e=0;e<mapSizeY;e++){
        
        //set color depand on different thing on the map
        g.setColor(new Color(Color.orange.getRed(),Color.orange.getGreen(),Color.orange.getBlue(),100 ));
        
        if(!(map2[i][e] instanceof TempCharacter)){
          if(map2[i][e].getTeam()!=c.getTeam()){
            g.setColor(new Color(255,0,0,150));
          }else{
            
            g.setColor(new Color(0, 0, 0, 0));
          }
          
          if(c instanceof Caster){
            g.setColor(new Color(0, 255, 0, 150));
          }
          
          
        }
        
        
        if(map[i][e] instanceof Wall){
          g.setColor(new Color(255,0,0,150));
        }
        
        // if it is in the attack range, then draw it
        if((Math.abs(i-c.getXPosition())+Math.abs(e-c.getYPosition())>=c.getMinRange())&&(Math.abs(i-c.getXPosition())+Math.abs(e-c.getYPosition())<c.getMaxRange())){
          g.fillRect(winSizeX/mapSizeX*i,winSizeY/mapSizeY*e,winSizeX/mapSizeX,winSizeY/mapSizeY);
        }
      }
    }
    g.setColor(Color.black );
  }
  
  /* *
   * drawCharacter method
   * a method draw image of characters
   * by Vincent Zhang
   * 2018/1/18
   * Teacher: Mr.Mangat
   * */
  public void drawCharacter(int i, int e, Graphics g){
    
    //if the character drawing is taking turn, draw a different image
    if(!map2[i][e].equals(c)){
      g.drawImage(map2[i][e].getImageDis(),winSizeX/mapSizeX*i+(int)(winSizeX/mapSizeX*0.1),winSizeY/mapSizeY*e+(int)(winSizeY/mapSizeY*0.1),winSizeX/mapSizeX-(int)(winSizeX/mapSizeX*0.2),winSizeY/mapSizeY-(int)(winSizeY/mapSizeY*0.2), this);
    }else{
      g.drawImage(c.getImage(), winSizeX/mapSizeX*i,winSizeY/mapSizeY*e,winSizeX/mapSizeX,winSizeY/mapSizeY, this);
    }
  }
  
  
  
  /* *
   *animate  method
   * the looping method of the game, everything other then graphics of running game
   * by Vincent Zhang
   * 2018/1/18
   * Teacher: Mr.Mangat
   * */
  public void animate(){
    
    while(winner==-1){  //if there is no winner, the game continu
      //count round
      round++;
      
      //set the displaying object
      currentDisplayLand=new Grass();
      currentDisplayCharacter=new TempCharacter();
      
      //check whose turn is it
      c=getMinMove();
      //repaint, and wait for imput
      this.repaint();
      int moveToX=0;
      int moveToY=0;
      moved=false;
      Boolean attack=false;
      
      
      while(!moved){
        
        
        if(c.getTeam()==FinnalGame.team){
          //make user choose a place, untail it is within the movement range of a character
          do{
            checkMouse();
            FinnalGame.mousePressed=false;
            try{
              Thread.sleep(1);
            }catch(Exception e){};
            
            moveToX=(int)((mapSizeX*1.0/winSizeX)* FinnalGame.p.getX());
            moveToY=(int)((mapSizeY*1.0/winSizeX)* FinnalGame.p.getY());
          }while(moveToX<0||moveToX>=FinnalGame.mapx||moveToY<0||moveToY>=FinnalGame.mapy);
          FinnalGame.outPutCommands(moveToX);
          FinnalGame.outPutCommands(moveToY);
          
        }else{
          checkMouse2();
          try{
            moveToX=Integer.valueOf(FinnalGame.input.readLine());
            moveToY=Integer.valueOf(FinnalGame.input.readLine());
          }catch(Exception e){};
        }
        //if click on it self, skip the move
        if(moveToX==c.getXPosition()&&moveToY==c.getYPosition()){
          moved=true;
          
          //put in event to queue
          if(c.getTeam()==1){
            q.add("Character from team red stay");
          }else{
            q.add("Character from team blue stay");
          }
          if(q.size()>8){
            q.poll();
          }
          
        }
        //check moving range
        
        
        if(c.callCheckMoveRange(moveToX,moveToY, map, map2)){
          //if it is not character
          if((map2[moveToX][moveToY] instanceof TempCharacter)){
            //move
            map2=c.changePosition(map2, moveToX, moveToY);
            moved=true;
            //added it to event
            if(c.getTeam()==1){
              q.add("Character from team red moved");
            }else{
              q.add("Character from team blue moved");
            }
            if(q.size()>8){
              q.poll();
            }
          }    
        }  
      }
      
      //repaint, and goes to attack
      this.repaint();
      currentDisplayLand=new Grass();
      currentDisplayCharacter=new TempCharacter();
      
      //loop until the user make a successful attack or skip the round
      while(!attack){
        
        int attackToX=-1;
        int attackToY=-1;
        int doubleDamage=0;
        //if the character is on the player's team, choose a position, or else, wail for input
        if(c.getTeam()==FinnalGame.team){
          
          do{
            
            checkMouse();
            
            FinnalGame.mousePressed=false;
            attackToX=(int)((mapSizeX*1.0/winSizeX)* FinnalGame.p.getX());
            attackToY=(int)((mapSizeY*1.0/winSizeX)* FinnalGame.p.getY());
            
          } while((attackToX<0||attackToX>=FinnalGame.mapx||attackToY<0||attackToY>=FinnalGame.mapy));
          //10% of double damage
          if(Math.random()*100>=90){
            doubleDamage=1;
          }
          FinnalGame.outPutCommands( attackToX);
          FinnalGame.outPutCommands( attackToY);
          FinnalGame.outPutCommands(doubleDamage);
        }else{
          checkMouse2();
          try{
            attackToX=Integer.valueOf(FinnalGame.input.readLine());
            attackToY=Integer.valueOf(FinnalGame.input.readLine());
            doubleDamage=Integer.valueOf(FinnalGame.input.readLine());
          }catch(Exception e){};
        }
        
        
        //if there are characters in the place, then attack
        if(Math.abs(attackToX-c.getXPosition())+Math.abs(attackToY-c.getYPosition())>=c.getMinRange()&&Math.abs(attackToX-c.getXPosition())+  Math.abs(attackToY-c.getYPosition())<c.getMaxRange()){
          
          if(!(map2[attackToX][attackToY] instanceof TempCharacter))  {
            
            if(map[attackToX][attackToY] instanceof Forest){
              c.attack(map2,attackToX,attackToY, 1.3, doubleDamage );
            }else{
              c.attack(map2,attackToX,attackToY, 1.0, doubleDamage);
            }
            
            //see is it dead
            if(map2[attackToX][attackToY].getHealth()<=0){
              q.add("Character "+map2[attackToX][attackToY].getClass()+" dead");
              if(q.size()>8){
                q.poll();
              }
              map2[attackToX][attackToY]=new TempCharacter();
            }
            attack=true;
          }else if(map[attackToX][attackToY] instanceof Wall){
            //attack walls
            ((Wall)map[attackToX][attackToY]).setHealth(((Wall)map[attackToX][attackToY]).getHealth()-c.getAttack());
            
            q.add(this.getClass()+" deal "+c.getAttack()+" damage to Wall");
            if(q.size()>8){
              q.poll();
            }
            if(((Wall)map[attackToX][attackToY]).getHealth()<=0){
              map[attackToX][attackToY]=new Grass();
              q.add(this.getClass()+" distory Wall");
              if(q.size()>8){
                q.poll();
              }
            }
            attack=true;
          }
        }
        
        //if click on it self, cancle the attack
        if(attackToX==c.getXPosition()&&attackToY==c.getYPosition()){
          attack=true;
          if(c.getTeam()==1){
            q.add("Character from team red cancle attack");
          }else{
            q.add("Character from team blue cancle attack");
          }
          if(q.size()>8){
            q.poll();
          }
        } 
      }
      FinnalGame.mousePressed=false;
      
      //reset the character's speed
      c.upDateSpeed();
      
      //check win
      winner=checkWin();
    }
    
    this.repaint();
    
    
  }
  
  /**
   * checkwin method
   * check the winner of the game
   * By Vincent Zhang
   * 2018/1/18
   * Teacher: Mr.Mangat
   * */
  public int checkWin(){
    
    //loop over the map, and if 
    int count1=0;
    int count2=0;
    for(int i=0;i<mapSizeX;i++){
      for(int e=0;e<mapSizeY;e++){
        if(!(map2[i][e] instanceof TempCharacter)){
          if(map2[i][e].getTeam()==1){
            count1++;
          }else{
            count2++;
          }
        }
      }
    }
    if(count1>0&&count2>0){
      return -1;
    }else if(count1==0){
      return 1;
    }
    return 2;
  }
  
  /* *
   * checkMove method
   * a method wait for input of mouse, and see what the user click
   * by Vincent Zhang
   *  2018/1/18
   * Teacher: Mr.Mangat
   * */
  public void checkMouse(){
    
    //wait for input
    while((!FinnalGame.mousePressed)){
      
      //this.repaint();
      try{
        Thread.sleep(1);
      }catch(Exception e){}
      
      
      //if right mouse clicked, then display it using repaint(edit the currentDisplaying character/land) 
      if(FinnalGame.mousePressed2){
        int movex=(int)((mapSizeX*1.0/winSizeX)*FinnalGame.displayPoint.getX());
        int movey=(int)((mapSizeX*1.0/winSizeX)*FinnalGame.displayPoint.getY());
        if( movex>=0&& movex<FinnalGame.mapx&& movey>=0&& movey<FinnalGame.mapy){
          
          currentDisplayCharacter=map2[movex][movey];
          currentDisplayLand=map[movex][movey];
          this.repaint();
          FinnalGame.mousePressed2=false;
        }
      }
      
    }
    FinnalGame.mousePressed=false;
  }
  /* *
   * checkMove method
   * a method wait for the input of another user, and see what the user click while waiting
   * by Vincent Zhang
   *  2018/1/18
   * Teacher: Mr.Mangat
   * */
  public void checkMouse2(){
    
    //wait for input of another user
    try{
      while((!FinnalGame.input.ready())){
        
        this.repaint();
        try{
          Thread.sleep(1);
        }catch(Exception e){}
        
        
        //if right mouse clicked, then display it using repaint(edit the currentDisplaying character/land) 
        if(FinnalGame.mousePressed2){
          int movex=(int)((mapSizeX*1.0/winSizeX)*FinnalGame.displayPoint.getX());
          int movey=(int)((mapSizeX*1.0/winSizeX)*FinnalGame.displayPoint.getY());
          if( movex>=0&& movex<FinnalGame.mapx&& movey>=0&& movey<FinnalGame.mapy){
            
            currentDisplayCharacter=map2[movex][movey];
            currentDisplayLand=map[movex][movey];
            this.repaint();
            FinnalGame.mousePressed2=false;
          }
        }
        
      }
      FinnalGame.mousePressed=false;
    }catch(Exception e){}
  }
  
  /* *
   * getMinMove method
   * a method get which character's turn is it
   * by Vincent Zhang
   *  2018/1/18
   * Teacher: Mr.Mangat
   * */
  public Character getMinMove(){
    int minMove=3000;
    Character min=null;
    //loop over and see the character who has minimum speed, and return it
    for(int i=0;i<mapSizeX;i++){
      for(int e=0;e<mapSizeY;e++){
        if(!(map2[i][e] instanceof TempCharacter)){
          if(map2[i][e].getCurrentSpeed()<minMove){
            min=map2[i][e];
            minMove=map2[i][e].getCurrentSpeed();
          }
        }else{
        }
      }
    }
    //change the move of all the character, so make them moved
    for(int i=0;i<mapSizeX;i++){
      for(int e=0;e<mapSizeY;e++){
        if(!(map2[i][e] instanceof TempCharacter)){
          map2[i][e].setCurrentSpeed(map2[i][e].getCurrentSpeed()-minMove);
        }
      }
    }
    return min;
    
  }
  
}
