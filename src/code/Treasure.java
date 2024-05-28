package code;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import jplay.Sound;
import jplay.Sprite;
import jplay.Window;

public class Treasure extends Sprite {
    protected  static final Double VELOCIDADE = 0.2;
    private static final List<String> teasures = Arrays
            .asList("src/resource/img/astronaut.png","src/resource/img/heart.png");
    protected boolean visible;
    private Random gerador;
    protected Jogador jogador;

    public Treasure(int x, int y, int teasure,Jogador jogador) {
        super(teasures.get(teasure), 1);
        this.x = x;
        this.y = y;
        this.visible = false;
        this.gerador = new Random();
        this.jogador = jogador;
    }

    public boolean isVisible() {
        return visible;
    }

    public Jogador getPlayer(){
        return this.jogador;
    }

public void bonus(){
    this.jogador.SetPontuacao(this.random(50).intValue());
    new Sound("src/resource/music/collect.wav").play();

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
