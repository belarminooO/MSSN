package Physics;

import Pro.IProcessingApp;

import Tools.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class SolarSystemApp implements IProcessingApp {

    private float sunMass = 1.989e30f;
    private float earthMass = 5.97e24f;
    private float distEarthSun = 1.496e11f;
    private float earthSpeed = 3e4f;
    private ArrayList<CelestialBody> bodies;

//    private float [] viewport = {0.25f,0.25f,0.5f,0.5f};
float[] viewport = {0.0f, 0.0f, 1.0f, 1.0f};  // Ocupa toda a tela



    private double[] window = {-1.2*distEarthSun, 1.2*distEarthSun, -1.2*distEarthSun, 1.2*distEarthSun};

    private SubPlot plt;
    private CelestialBody sun, earth;

    private float speedUp = 60 * 60 * 24 * 30;



    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window,  viewport, p.width, p.height);
        sun = new CelestialBody(new PVector(), new PVector(), sunMass, distEarthSun/10, p.color(255, 128,0));
        earth = new CelestialBody(new PVector(0,distEarthSun), new PVector(earthSpeed,0), earthMass,
                distEarthSun/20, p.color(0,180,120));
        bodies = new ArrayList<>();

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


        for (CelestialBody body : bodies) {
            PVector attraction = sun.attraction(body);
            body.applyForce(attraction);
            body.move(dt * speedUp);
            body.display(p, plt);
        }


    }

    @Override
    public void mousePressed(PApplet p) {
        // Obtém a posição do mouse e converte para coordenadas do mundo
        float[] mousePos = {p.mouseX, p.mouseY};
        double[] worldPos = plt.getWorldCoord(mousePos[0], mousePos[1]);
        PVector pos = new PVector((float) worldPos[0], (float) worldPos[1]);

        // Calcula a distância entre o novo corpo e o Sol
        PVector toSun = PVector.sub(pos, sun.getPos());
        float distance = toSun.mag();

        // Calcula a magnitude da velocidade orbital necessária
        float orbitalSpeed = (float) Math.sqrt(CelestialBody.getG() *sunMass / distance);

        // Define a direção da velocidade como perpendicular ao vetor que aponta para o Sol
        PVector vel = toSun.copy().normalize().rotate(PApplet.HALF_PI).mult(orbitalSpeed);

        // Gera massa, raio e cor aleatórios usando p.random()
        float mass = p.random(1e23f, 5e24f); // Massa aleatória
        float radius = distEarthSun / 30 * p.random(0.5f, 1.0f); // Raio aleatório
        int color = p.color(p.random(256), p.random(256), p.random(256)); // Cor aleatória

        // Cria o novo corpo com a velocidade ajustada para uma órbita e o adiciona à lista
        CelestialBody newBody = new CelestialBody(pos, vel, mass, radius, color);
        bodies.add(newBody);
    }


    @Override
    public void keyPressed(PApplet p) {

    }
}
