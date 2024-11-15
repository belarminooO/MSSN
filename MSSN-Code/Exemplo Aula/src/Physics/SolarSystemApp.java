package Physics;

import Pro.IProcessingApp;

import Tools.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;

public class SolarSystemApp implements IProcessingApp {

    private float sunMass = 1.989e30f;
    private float earthMass = 5.97e24f;
    private float distEarthSun = 1.496e11f;
    private float earthSpeed = 3e4f;
    private ArrayList<CelestialBody> planetas;
    private ArrayList<ParticleSystem> pss;


//        private float [] viewport = {0.25f,0.25f,0.5f,0.5f};
    float[] viewport = {0.0f, 0.0f, 1.0f, 1.0f};  // Ocupa toda a tela



    private double[] window = {-1.28*distEarthSun, 1.28*distEarthSun, -1.28*distEarthSun, 1.28*distEarthSun};

    private SubPlot plt;
    private CelestialBody sun, earth;

    private float speedUp = 60 * 60 * 24 * 30;



    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window,  viewport, p.width, p.height);
        sun = new CelestialBody(new PVector(), new PVector(), sunMass, distEarthSun/10, p.color(255, 128,0));
        earth = new CelestialBody(new PVector(0,distEarthSun), new PVector(earthSpeed,0), earthMass,
                distEarthSun/20, p.color(0,180,120));
        planetas = new ArrayList<>();
        pss = new ArrayList<ParticleSystem>();

    }

    @Override
    public void draw(PApplet p, float dt) {
        float[] pp = plt.getBoundingBox();

        p.fill(255,16);
        p.rect(pp[0], pp[1], pp[2], pp[3]);


        sun.display(p,plt);

        PVector f = sun.attraction(earth);
        earth.applyForce(f);


        earth.move(dt*speedUp);
        earth.display(p,plt);

        for(int i =0; i< pss.size(); i++){
            PVector fCom = sun.attraction(pss.get(i));
            pss.get(i).applyForce(fCom.mult(1.5e12f));
            pss.get(i).move(dt*3);
            pss.get(i).display(p,plt);
        }

//        for(ParticleSystem ps : pss) {
//            ps.applyForce(new PVector(0,-1));
//        }


//        for(ParticleSystem ps : pss) {
//            ps.move(dt);
//            ps.display(p,plt);
//        }
        }



    @Override
    public void mousePressed(PApplet p) {





//        CelestialBody(PVector pos, PVector vel, float mass, float radius, int color)


    }


    @Override
    public void keyPressed(PApplet p) {
        if (p.key == 'a'){
            Random rr = new Random();

            double [] ww = plt.getWorldCoord(p.mouseX,p.mouseY);

            int cor = p.color(p.random(255), p.random(255), p.random(255));
            float vx = rr.nextFloat(4,10);
            float vy = rr.nextFloat(4,10);
            float vvx = rr.nextFloat(-10,10);
            float vvy = rr.nextFloat(-10,10);

            float lifespan = rr.nextFloat(1,5);

            ParticleSystem ps= new ParticleSystem(new PVector((float)ww[0], (float)ww[1]), new PVector(2e9f*vvx, 8e9f*vvy), 5e14f,distEarthSun/60, cor, lifespan, new PVector(2e9f*vx, 2e9f*vy));

            pss.add(ps);


        }

    }
}
