package code;

import java.util.Random;

import jplay.Keyboard;
import jplay.Sprite;
import jplay.Window;

public abstract class Ator extends Sprite {
    protected static final Double VELOCIDADE = 0.3;
    protected Integer direcao = 3;
    protected boolean movendo = false;
    protected Random gerador = new Random();

    public Ator(String file, int i) {
        super(file, 1);
    }

    public abstract void mover(Window janela, Double moreFast);

    public void posiciona() {
        this.y = this.random(520).intValue();
    }

    public Double random(Integer max) {
    return  gerador.nextDouble() * max;
    }

}
