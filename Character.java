/**
 * Character.java
 *the super class of all unit
 * By Vincent Zhang
 * 2018/1/18
 * Teacher: Mr.Mangat
 * */
import java.awt.image.BufferedImage;
import java.awt.Point;
public abstract class Character {
  
  //create varible
  private int health;
  private int defence;
  private int attack;
  BufferedImage image1;
  BufferedImage image2;
  private int move;
  private int currentSpeed;
  private int maxRange;
  private int minRange;
  private  int speed;
  private  int yPosition;
  private  int team;
  private   int xPosition;
  private   String name;
  private int maxHealth;
  
  
  /* *
   *attack method
   * a method which run the attacking
   * by Vincent Zhang
   *  2018/1/18
   * Teacher: Mr.Mangat
   * */
  public Character[][] attack(Character[][]map, int x, int y,double enemyDefenceTimes,int doubleDamage){
    
    //if it is double damage, then deal double damage, else do not
    if(doubleDamage==1){
      
      //calcalate the attack
      GameFrame.q.add(this.getClass()+" deal "+(int)(attack*1.5*(1-(map[x][y].getDefence()*enemyDefenceTimes/100)))+" damage to "+map[x][y].getClass());
      if( GameFrame.q.size()>10){
        GameFrame.q.poll();
      }
      map[x][y].setHealth(map[x][y].getHealth()-(int)(attack*1.5*(1-map[x][y].getDefence()*enemyDefenceTimes/100)));
    }else{
      GameFrame.q.add(this.getClass()+" deal "+(int)(attack*(1-(map[x][y].getDefence()*enemyDefenceTimes/100)))+" damage to "+map[x][y].getClass());
      if( GameFrame.q.size()>10){
        GameFrame.q.poll();
      }
      map[x][y].setHealth(map[x][y].getHealth()-(int)(attack*(1-map[x][y].getDefence()*enemyDefenceTimes/100)));
    }
    return map;
  }
  
  
  /* *
   *changePosition method
   * a method which change the position of this unit
   * by Vincent Zhang
   *  2018/1/18
   * Teacher: Mr.Mangat
   * */
  public Character[][] changePosition(Character[][] map,int moveToX,int moveToY){
    
    //set the x and y of this unit to moveToX, and movetoY
    map[xPosition][yPosition]=new TempCharacter();
    map[moveToX][moveToY]=this;
    xPosition=moveToX;
    yPosition=moveToY;
    return map;
  }
  
  /* *
   * callCheckMoveRange method
   * a method which call the checkMoveRange
   * by Vincent Zhang
   *  2018/1/18
   * Teacher: Mr.Mangat
   * */
  public Boolean callCheckMoveRange(int moveToX, int moveToY,  MapObject[][]map, Character[][] map2){
    return checkMoveRange(new Point(xPosition, yPosition),new Point(moveToX, moveToY),map, 0, map2 );
  }
  
  
  
  /* *
   * checkMoveRange method
   * a method which use recursion to check the move range of this unit
   * by Vincent Zhang
   *  2018/1/18
   * Teacher: Mr.Mangat
   * */
  public Boolean checkMoveRange(Point currentPoint, Point aim,  MapObject[][]map, int round, Character[][] map2){
    int x=(int)currentPoint.getX();
    int y=(int)currentPoint.getY();
    
    //if it go path the move limit, then return false
    if(round>move){
      return false;
    }
    
    //if it goes outside of map or reach wall, return false
    if(x<0||x>=FinnalGame.mapx||y<0||y>=FinnalGame.mapy||(map[x][y] instanceof Wall)){
      return false;
    }
    
    //if it goes to enemy, return false
    if(!(map2[x][y] instanceof TempCharacter)){
      if(map2[x][y].getTeam() !=team){
        return false;
      }
    }
    
    // if it reach the aim position, return true
    if(x==aim.getX()&&y==aim.getY()){
      return true;
      
    }
    
    //use checkMoveCost to check the cost of the move, and call it self
    int addRound=checkMoveCost(map[x][y]);
    
    //recurse through the surrounding
    if(checkMoveRange(new Point(x+1, y), aim, map, round+addRound, map2)||checkMoveRange(new Point(x-1, y), aim, map, round+addRound, map2)||checkMoveRange(new Point(x, y+1), aim, map, round+addRound, map2)||checkMoveRange(new Point(x, y-1), aim, map, round+addRound, map2)){
      return true;
    }
    return false;
  }
  
  /* *
   *checkMoveCost method
   * a method which check the move cost
   * by Vincent Zhang
   *  2018/1/18
   * Teacher: Mr.Mangat
   * */
  public int checkMoveCost(MapObject ob){
    int  moveCast;
    
    //if it is forect or desert, then cost2, else cost1
    if(ob instanceof Forest||ob instanceof Desert){
      moveCast= 2;
    }else{
      moveCast= 1;
    }
    return moveCast;
  }
  /* *
   *upDateSpeed method
   * a method which reset the speed of the unit
   * by Vincent Zhang
   *  2018/1/18
   * Teacher: Mr.Mangat
   * */
  public void upDateSpeed(){
    currentSpeed=speed;
  }
  
  //setter
  public void setImage(BufferedImage image1, BufferedImage image2){
    this.image1=image1;
    this.image2=image2;
  }
  public void setTeam(int team){
    this.team=team;
  }
  public void setName(String name){
    this.name=name;
  }
  public void setAttack(int attack){
    this.attack=attack;
  }
  public void setSpeed(int speed){
    this.speed=speed;
  }
  public void setDefence(int defence){
    this.defence=defence;
  }
  public void setHealth(int health){
    this.health=health;
  }
  public void setMaxHealth(int maxHealth){
    this. maxHealth=maxHealth;
  }
  public void setCurrentSpeed(int currentSpeed){
    this.currentSpeed=currentSpeed;
  }
  public void setXPosition(int x){
    xPosition=x;
  }
  public void setYPosition(int y){
    yPosition=y;
  }
  public void setMaxRange(int maxRange){
    this.maxRange=maxRange;
  }  
  public int getMinRange(){
    return minRange;
  }
  public void setMinRange(int minRange){
    this.minRange=minRange;
  }
  public void setMove(int move){
    this.move=move;
  }
  
  
  //getter
  public BufferedImage getImage(){
    return image1;
  }
  public BufferedImage getImageDis(){
    return image2;
  }
  public int getAttack(){
    return attack;
  }
  public int getTeam(){
    return team;
  }
  public int getDefence(){
    return defence;
  }
  public int getHealth(){
    return health;
  }
  public int getMove(){
    return move;
  }
  public int getCurrentSpeed(){
    return currentSpeed;
  }
  public int getSpeed(){
    return speed;
  }
  public int getMaxRange(){
    return maxRange;
  }
  public int getXPosition(){
    return xPosition;
  }
  public String getName(){
    return name;
  }
  public int getYPosition(){
    return yPosition;
  }
  public int getMaxHealth(){
    return maxHealth;
  }
  
}
