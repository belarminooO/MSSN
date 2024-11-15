import processing.core.PApplet;

public interface IProcessingApp {
    void setup(PApplet p);

    void mousePressed(PApplet p);

    void keyPressed(PApplet p);

    void draw(PApplet p, float dt);
}