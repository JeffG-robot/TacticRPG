/**
 * Walls.java
 * a object of Wall in the map
 * By Vincent Zhang
 * 2018/1/18
 * Teacher: Mr.Mangat
 * */
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
public class Wall extends MapObject {
//create health varible
  private int health;
  
  //constructor
  public Wall(int x, int y, int health) { 
        //set the x and y and image of the objecy
    super.setX(x);
    super.setY(y);
    this.health=health;
    try{
      
      image = ImageIO.read(new File("wall.png"));
      super.setImage(image);
    }catch(Exception e){}
  }
    
  //getter
  public int getHealth(){
    return health;
  }
  
  //setter
  public void setHealth(int health){
    this.health=health;
  }
  
}
