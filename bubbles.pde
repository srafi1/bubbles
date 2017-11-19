ArrayList<Bubble> bubbles;

void setup() {
  size(480, 600);
  frameRate(120);
  bubbles = new ArrayList<Bubble>();
}

void draw() {
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

void mousePressed() {
  bubbles.add(new Bubble(mouseX, mouseY));
}

void keyPressed() {
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
  
  void move() {
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
  
  float getX() {
    return x;
  }
  
  float getY() {
    return y;
  }
  
  float getDX() {
    return dx;
  }
  
  float getDY() {
    return dy;
  }
  
  void crash(Bubble b) {
    if (dist(x, y, b.getX(), b.getY()) <= r*2) {
      float deltax = b.getX() - x;
      float deltay = b.getY() - y;
      float denom = (float) Math.sqrt(deltax*deltax + deltay*deltay);
      
      dx = -deltax/denom;
      dy = -deltay/denom;
    }
  }
  
  boolean crashVert() {
    return y >= height - r || y <= r;
  }
  
  boolean crashHorz() {
    return x >= width - r || x <= r;
  }
  
  void paint() {
    fill(red, green, blue);
    ellipse(x, y, r*2, r*2);
  }
}
