/**
 * Forect.java
 * a object of forest in the map
 * By Vincent Zhang
 * 2018/1/18
 * Teacher: Mr.Mangat
 * */
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
public class Forest extends MapObject {
  //constructor
  public Forest(int x, int y) { 
    //set the x and y and image of the objecy
    super.setX(x);
    super.setY(y);
    try{
      
      image = ImageIO.read(new File("forest.png"));
      super.setImage(image);
    }catch(Exception e){}
  }
  /* ADD YOUR CODE HERE */
  
}
