package PARTE_C;

import Physics.CelestialBody;
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

    float[] viewport = {0.0f, 0.0f, 1.0f, 1.0f}; // Ocupa toda a tela
    private double[] window = {-5 * distEarthSun, 5 * distEarthSun, -5 * distEarthSun, 5 * distEarthSun};

    private SubPlot plt;
    private CelestialBody sun, earth;
    private float speedUp = 60 * 60 * 24 * 30;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);

        // Sol e Terra inicializados
        sun = new CelestialBody(new PVector(), new PVector(), sunMass, distEarthSun / 10, p.color(255, 128, 0));
        earth = new CelestialBody(
                new PVector(0, distEarthSun),
                new PVector(earthSpeed, 0),
                earthMass,
                distEarthSun / 20,
                p.color(0, 180, 120)
        );

        bodies = new ArrayList<>();
    }

    @Override
    public void draw(PApplet p, float dt) {
        float[] pp = plt.getBoundingBox();

        // Fundo transparente para simular rastros
        p.fill(255, 16);
        p.rect(pp[0], pp[1], pp[2], pp[3]);

        // Desenhar Sol
        sun.display(p, plt);

        // Calcular e aplicar força gravitacional do Sol na Terra
        PVector f = sun.attraction(earth);
        earth.applyForce(f);

        // Mover e desenhar Terra
        earth.move(dt * speedUp);
        earth.display(p, plt);

        // Atualizar e desenhar outros corpos celestes
        for (CelestialBody body : bodies) {
            PVector attraction = sun.attraction(body);
            body.applyForce(attraction);
            body.move(dt * speedUp);
            body.display(p, plt);
        }
    }

    @Override
    public void mousePressed(PApplet p) {
        // Obter coordenadas do mundo a partir do mouse
        double[] worldCoord = plt.getWorldCoord(p.mouseX, p.mouseY);
        PVector pos = new PVector((float) worldCoord[0], (float) worldCoord[1]);

        // Calcular distância ao Sol
        PVector directionToSun = PVector.sub(pos, sun.getPos());
        float distanceToSun = directionToSun.mag();

        // Gerar atributos aleatórios para o novo CelestialBody
        float randomMass = p.random(1e20f, 1e25f); // Massa aleatória
        float randomRadius = p.random(1e9f, 5e9f); // Raio aleatório
        int randomColor = p.color(p.random(255), p.random(255), p.random(255)); // Cor aleatória

        // Calcular velocidade orbital inicial
        float orbitalSpeed = (float) Math.sqrt(CelestialBody.getG() * sunMass / distanceToSun);

        // Determinar direção perpendicular ao vetor direção ao Sol
        PVector velocity = directionToSun.copy().rotate(-PApplet.HALF_PI).normalize().mult(orbitalSpeed);

        // Criar e adicionar novo corpo celeste
        CelestialBody newBody = new CelestialBody(
                pos, // Posição
                velocity, // Velocidade tangencial
                randomMass, // Massa
                randomRadius, // Raio
                randomColor // Cor
        );

        bodies.add(newBody);
    }

    @Override
    public void keyPressed(PApplet p) {
        // Exemplo de controle de simulação
        if (p.key == 'p') {
            // Pausar ou continuar simulação
            speedUp = (speedUp == 0) ? 60 * 60 * 24 * 30 : 0;
        }
    }
}