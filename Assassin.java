/**
 * Assissin.java
 * a object of a type of unit
 * By Vincent Zhang
 * 2018/1/18
 * Teacher: Mr.Mangat
 * */
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
public class Assassin extends Character{
  
  //constructor
  public Assassin(String name,int health, int attack, int defence, int speed, int team) { 
    super.setTeam(team);
    super.setName(name);
    super.setMaxRange(2);
    super.setMinRange(1);
    super.setSpeed(speed);
    super.setAttack(attack);
    super.setHealth(health);
    super.setMaxHealth(health);
    super.setDefence(defence);
    super.setMove(4);
    BufferedImage image;
    
    //read image, and set it to main class
    try{
      if(team==1){
        image = ImageIO.read(new File("RedTeam(1).png"));
      }else{
        image = ImageIO.read(new File("BlueTeam(1).png"));
      }
      super.setImage(image.getSubimage(230, 0, 46, 46), image.getSubimage(230, 46, 46, 46));
    }catch(Exception e){}
    super.upDateSpeed();
  }
  
  
//overwrite attack method  
  public Character[][] attack(Character[][]map, int x, int y,double enemyDefenceTimes, int insKill){
    
    GameFrame.q.add(this.getClass()+" deal "+(int)(super.getAttack()*(1-map[x][y].getDefence()*enemyDefenceTimes/100))+" damage to "+map[x][y].getClass());
    if( GameFrame.q.size()>10){
      GameFrame.q.poll();
    }
    map[x][y].setHealth(map[x][y].getHealth()-(int)(super.getAttack()*(1-map[x][y].getDefence()*enemyDefenceTimes/100)));
    
    //if double damage, then just kill the enemy
    if(insKill==1){
      map[x][y].setHealth(-1);
    }
    return map;
  }
}
