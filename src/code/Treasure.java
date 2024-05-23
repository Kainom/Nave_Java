package code;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import jplay.Sprite;
import jplay.Window;

public class Treasure extends Sprite {
    private static final Double VELOCIDADE = 0.2;
    private static final List<String> teasures = Arrays
            .asList("/home/kainom/meusprojetos/game_nave/src/resource/img/astronaut.png");
    private boolean visible;
    private Random gerador;

    public Treasure(int x, int y, int teasure) {
        super(teasures.get(0), 1);
        this.x = x;
        this.y = y;
        this.visible = false;
        this.gerador = new Random();
    }

    public boolean isVisible() {
        return visible;
    }

    public void respawnTreasure() {
        this.visible = true;
        this.posiciona();

    }

    public void getTeasure() {
        this.visible = false;
    }

    public Double random(Integer max) {
        return gerador.nextDouble() * max;
    }

    private void posiciona() {
        this.y = this.random(520).intValue();
        this.x = 700;

    }

    public void mover() {
        this.x -= VELOCIDADE;
        if (x <= -200) {
            this.visible = false;
        }
    }

}
