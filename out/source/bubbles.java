import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class bubbles extends PApplet {

ArrayList<Bubble> bubbles;

public void setup() {
  
  frameRate(120);
  bubbles = new ArrayList<Bubble>();
}

public void draw() {
  background(255);
  fill(0, 0, 200);
  noStroke();
  
  for (Bubble b : bubbles) {
    b.move();
    b.paint();
    
    for (Bubble b2 : bubbles) {
      if (b == b2) continue;
      b.crash(b2);
    }
  }
}

public void mousePressed() {
  bubbles.add(new Bubble(mouseX, mouseY));
}

public void keyPressed() {
  bubbles.add(new Bubble(random(width - 60) + 30, random(height - 60) + 30));
}

class Bubble {
  float x, y, r, dx, dy;
  int red, green, blue;
  Bubble(float nx, float ny) {
    x = nx;
    y = ny;
    dx = random(2f) - 1;
    dy = random(2f) - 1;
    r = 25;
    
    red = (int) random(255);
    green = (int) random(255);
    blue = (int) random(255);
  }
  
  public void move() {
    x += dx;
    y += dy;
    
    if (crashHorz()) {
      dx *= -1;
      x += dx;
    }
    if (crashVert()) {
      dy *= -1;
      y += dy;
    }
  }
  
  public float getX() {
    return x;
  }
  
  public float getY() {
    return y;
  }
  
  public float getDX() {
    return dx;
  }
  
  public float getDY() {
    return dy;
  }
  
  public void crash(Bubble b) {
    if (dist(x, y, b.getX(), b.getY()) <= r*2) {
      float deltax = b.getX() - x;
      float deltay = b.getY() - y;
      float denom = (float) Math.sqrt(deltax*deltax + deltay*deltay);
      
      dx = -deltax/denom;
      dy = -deltay/denom;
    }
  }
  
  public boolean crashVert() {
    return y >= height - r || y <= r;
  }
  
  public boolean crashHorz() {
    return x >= width - r || x <= r;
  }
  
  public void paint() {
    fill(red, green, blue);
    ellipse(x, y, r*2, r*2);
  }
}
  public void settings() {  size(480, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "bubbles" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
