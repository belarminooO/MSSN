package hello;


import processing.core.PApplet;

public interface IProcessingApp {

    public void setup(PApplet p);
    public void draw(PApplet p , float dt);
    public void mousePressed(PApplet p, float x, float y);
    public void keyPressed(PApplet p);
}
