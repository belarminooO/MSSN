package dla;
import processing.core.PApplet;
import processing.core.PVector;

import java.nio.file.WatchKey;
import java.util.List;

public class Walker {
    public enum State{
        STOPPED,
        WANDER
    }

    private PVector pos;
    private State state;
    private int colour;
    private static int radius = 4;
    public static int num_wanders=0;
    public static int num_stopped = 0;

    public Walker(PApplet p) {
        //pos = new PVector(p.random(p.width), p.random(p.height));
        pos = new PVector(p.width/2, p.height/2);
        PVector r = PVector.random2D();
        r.mult(p.width/2);
        pos.add(r);

        setState(p,State.WANDER);
    }//metedo para espalhar as particulas a uma certa distancia do centro,formando um circulo

    public Walker(PApplet p, PVector pos) {
        this.pos = pos;
        setState(p,State.STOPPED);

    }

    public State getState() {
        return state;
    }

    private void setState(PApplet p, State state) {
        this.state = state;
        if(state == State.STOPPED) {
            colour = p.color(0);
            num_wanders++;
        }else{
            colour = p.color(255);
//            colour = p.color(p.random(255),p.random(255),p.random(255));
            num_stopped++;
        }
    }
    public void updateState(PApplet p, List<Walker>walkers) {
        if(state == State.STOPPED)
            return;

        for(Walker w : walkers) {
            if(w.state == State.STOPPED){
                float dist = PVector.dist(pos,w.pos);
                if(dist < 2*radius){
                    setState(p,State.STOPPED);
                    num_wanders--;
                    break;
                }
            }
        }
    }

    /**
     *  optimizar de modo a que o algoritmo nao cresca de forma quadrada
     *  uma das formas E usar uma grelha, para que se possa calc a distancia
     *  de uma forma mais eficaz
     *
     *  2.podemos fazer uma squatriz que E uma grelha dinamica...
     *
     */

    public void wander(PApplet p) {
        PVector step = PVector.random2D();
        pos.add(step);
        pos.lerp(new PVector(p.width/2, p.height/2),0.0002f);
        pos.x = PApplet.constrain(pos.x,0,p.width);
        pos.y = PApplet.constrain(pos.y,0,p.height);
    }//para fazer as particulas moverem-se de forma aleatoria

    public void display(PApplet p){
        p.fill(colour);
        p.circle(pos.x, pos.y, 2*radius);
    }

}
