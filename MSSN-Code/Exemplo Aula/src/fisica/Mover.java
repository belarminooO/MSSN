package fisica;

import processing.core.PVector;

//tem que ser abstrata
public class Mover {

        protected PVector pos;
        protected PVector vel;
        private PVector acc;
        private float mass;


    public Mover (PVector pos, PVector vel, float mass){
            this.pos = pos;
            this.vel = vel;
            this.mass = mass;
            acc = new PVector();

        }
        public void applyForce(PVector f){
            acc.add(PVector.div(f,mass));
        }
        public void move(float dt){
            vel.add(acc.mult(dt));


            PVector aux = vel.copy(); //1 forma de fazer
//          PVector aux1 = new PVector(vel.x, vel.y);   outra forma de fazer
//          pos.add(aux.mult(dt)); // para 1 e 2
            pos.add(PVector.mult(vel, dt)); // 3 forma de fazer, estao nao destroi a velocidade por nao ser um metedo de instancia e sim de classe
            acc.mult(0);
        }

}

