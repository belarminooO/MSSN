package fisica;

import Pro.IProcessingApp;
import processing.core.PApplet;
import processing.core.PVector;

public class TesteAPP implements IProcessingApp{
    private Body b;
    private float mass = 10f;
    private float radius = 10;
    private float g  = 9.8f;
    private Water water;
    private Air air;
    private float sppedUp = 2;

    @Override
    public void setup(PApplet p) {
        b = new Body(new PVector(p.width/2,0), new PVector(),mass, radius, p.color(100,200,100));
        water = new Water (100, p.color(0,255,255));
        air = new Air();
    }

    @Override
    public void draw(PApplet p, float dt) {

        float weight = mass * g;
        b.applyForce(new PVector(0, weight));


        PVector drag =  water.drag(b);
        b.applyForce(drag);// o aplay force esta mal

        b.move(sppedUp*dt);

        water.display(p);
        b.display(p);
    }

    @Override
    public void mousePressed(PApplet p) {

    }

    @Override
    public void keyPressed(PApplet p) {

    }
}
