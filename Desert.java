/**
 * desert.java
 * a object of desert in the map
 * By Vincent Zhang
 * 2018/1/18
 * Teacher: Mr.Mangat
 * */
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
public class Desert extends MapObject{
  
  //constructor
  public Desert(int x, int y) { 
    //set the x and y and image of the objecy
    super.setX(x);
    super.setY(y);
    try{
      
      image = ImageIO.read(new File("desert.png"));
      super.setImage(image);
    }catch(Exception e){}
  }
  
}
