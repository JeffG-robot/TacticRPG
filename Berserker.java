/**
 * Berserker.java
 * a object of a type of unit
 * By Vincent Zhang
 * 2018/1/18
 * Teacher: Mr.Mangat
 * */
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
public class Berserker extends Character{
  
  public Berserker(String name, int health, int attack, int defence, int speed, int team) { 
    super.setTeam(team);
    super.setName(name);
    super.setMaxRange(2);//can shoot range of 2 and 3
    super.setMinRange(1);
    super.setSpeed(speed);
    super.setAttack(attack);
    super.setHealth(health);
    super.setMaxHealth(health);
    super.setDefence(0);
    super.setMove(3);
    BufferedImage image;
    
    //read image, and set it to main class
    try{
      if(team==1){
        image = ImageIO.read(new File("RedTeam(1).png"));
      }else{
        image = ImageIO.read(new File("BlueTeam(1).png"));
      }
      super.setImage(image.getSubimage(92, 0, 46, 46), image.getSubimage(92, 46, 46, 46));
    }catch(Exception e){}
    super.upDateSpeed();
  }
  
}
