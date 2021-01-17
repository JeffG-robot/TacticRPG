/**
 * MapObject.java
 *the super class of all land type in the map
 * By Vincent Zhang
 * 2018/1/18
 * Teacher: Mr.Mangat
 * */
import java.awt.image.BufferedImage;
public abstract class MapObject {
  //create varible
  private int x;
  private int y;
  BufferedImage image;
  
//setter and getter
  public void setImage(BufferedImage image1){
    this.image=image;
  }
  public BufferedImage getImage(){
    return image;
  }
  public void setX(int x){
    this.x=x;
  }
  public void setY(int y){
    this.y=y;
  }
  public int getX(){
    return x;
  }
  public int getY(){
    return y;
  }
}
