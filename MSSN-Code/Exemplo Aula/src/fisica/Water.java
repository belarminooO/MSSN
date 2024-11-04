package fisica;

import processing.core.PApplet;

public class Water extends Fluid{

    private float h;
    private int color;
    public Water(float height, int color) {
        super (1000f);
        h= height;
        this.color = color;
    }

    public void display(PApplet p){
        p.pushStyle();
        p.noStroke();
        p.fill(color);
        p.rect(0, p.height-h , p.width, h);
        p.popStyle();
    }

}
