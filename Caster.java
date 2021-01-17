/**
 * Caster.java
 * a object of a type of unit
 * By Vincent Zhang
 * 2018/1/18
 * Teacher: Mr.Mangat
 * */
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
public class Caster extends Character {
  //constructor
  public Caster(String name, int health, int attack, int defence, int speed, int team) { 
    super.setName(name);
    super.setTeam(team);
    super.setMaxRange(3);//can shoot range of 2 and 3
    super.setMinRange(1);
    super.setSpeed(speed);
    super.setAttack(attack);
    super.setHealth(health);
    super.setMaxHealth(health);
    super.setDefence(defence);
    super.setMove(2);
    BufferedImage image;
    
    
    //read image, and set it to main class
    try{
      if(team==1){
        image = ImageIO.read(new File("RedTeam(1).png"));
      }else{
        image = ImageIO.read(new File("BlueTeam(1).png"));
      }
      super.setImage(image.getSubimage(276, 0, 46, 46), image.getSubimage(276, 46, 46, 46));
    }catch(Exception e){}
    super.upDateSpeed();
  }
  
  //overwrite
  public Character[][] attack(Character[][]map, int x, int y,double enemyDefenceTimes){
    
    //if it is within the attackrange, then heal the unit with the attack state, until it is full health
    
    if(map[x][y].getMaxHealth()-map[x][y].getHealth()<=super.getAttack()){  //if it is almose full health
      GameFrame.q.add(this.getClass()+" Heal "+(map[x][y].getMaxHealth()-map[x][y].getHealth())+" Health to "+map[x][y].getClass());
      if( GameFrame.q.size()>10){
        GameFrame.q.poll();
      }
      map[x][y].setHealth(map[x][y].getMaxHealth());
      return map;
    }else{           //if it is not almost full health
      GameFrame.q.add(this.getClass()+" Heal "+super.getAttack()+" Health to "+map[x][y].getClass());
      if( GameFrame.q.size()>10){
        GameFrame.q.poll();
      }
      map[x][y].setHealth(map[x][y].getHealth()+super.getAttack());
      return map;
      
      
    }
  }
  
}
