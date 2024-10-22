package ca;

import hello.IProcessingApp;
import processing.core.PApplet;

public class Testeca implements IProcessingApp {

    private int nrows = 50;
    private int ncols = 50;
    private CellularAutomata ca;

    @Override
    public void setup(PApplet p) {
        ca = new CellularAutomata(p, nrows, ncols);
        ca.initRandom();
//      ca.initFromFile("C:\\Users\\belar\\Desktop\\AAA\\src\\ca\\teste.txt");
    }

    @Override
    public void draw(PApplet p, float dt) {
        ca.display(p);
        ca.update(); // Atualiza o estado das células
    }

    @Override
    public void mousePressed(PApplet p, float x, float y) {
        Cell cell = ca.pixel2Cell(p.mouseX, p.mouseY);
        cell.setState(1);
    }

    @Override
    public void keyPressed(PApplet p) {
        ca.initRandom();
    }
}
