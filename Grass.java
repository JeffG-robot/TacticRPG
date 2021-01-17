/**
 * Grass.java
 * a object of grass in the map
 * By Vincent Zhang
 * 2018/1/18
 * Teacher: Mr.Mangat
 * */
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
public class Grass extends MapObject {
  //constructor
  public Grass() {
    //set the image
    try{
      image = ImageIO.read(new File("grass.png"));
      super.setImage(image);
    }catch(Exception e){}
  }
  
}
