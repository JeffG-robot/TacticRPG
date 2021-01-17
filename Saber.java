/**
 * Saber.java
 * a object of a type of unit
 * By Vincent Zhang
 * 2018/1/18
 * Teacher: Mr.Mangat
 * */
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
public class Saber extends Character {
  //constructor
  Saber(String name, int health, int attack, int defence, int speed, int team){
    super.setTeam(team);
    super.setName(name);
    super.setMaxRange(2);
    super.setMinRange(1);
    super.setSpeed(speed);
    super.setAttack(attack);
    super.setMaxHealth(health);
    super.setHealth(health);
    super.setDefence(defence);
    super.setMove(3);
    BufferedImage image;
    
    
    //read image, and set it to main class
    try{
      if(team==1){
        image = ImageIO.read(new File("RedTeam(1).png"));
      }else{
        image = ImageIO.read(new File("BlueTeam(1).png"));
      }
      super.setImage(image.getSubimage(0, 0, 46, 46), image.getSubimage(0, 46, 46, 46));
    }catch(Exception e){}
    super.upDateSpeed();
  }
  
}
