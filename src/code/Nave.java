package code;

import java.util.Random;

import jplay.Window;

public class Nave extends Ator {
    private Double position = 0d;
    private Random gerador = new Random();
    private boolean visivel = true;
    private Integer tipo;
    private static final String vet[] = { "src/resource/img/nave.png",
            "/home/kainom/meusprojetos/game_nave/src/resource/img/inimigo.png" };

    public Nave(int x, int y, int enemie, Integer tipo) {
        super(vet[enemie], 1);
        this.x = x;
        this.y = y;
        this.visivel = false;
        this.tipo = tipo;
    }

    public boolean isVisible() {
        return visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public void atingido() {
        this.visivel = false;
    }

    public void posiciona(Integer firtsGame) {
        if (firtsGame == 1)
            this.x = 1400;
        this.x = 700;
        this.y = this.random(520).intValue();
    }

    public Double random(Integer max) {
        return position = gerador.nextDouble() * max;
    }

    @Override
    public void mover(Window janela) {
        if (tipo == 1) {
            if (this.y >= 10)
                this.y -= 0.5;
        } else if (tipo == 2) {
            if (this.y <= 520)
                this.y += 0.5;
        }
        // this.x -= VELOCIDADE + 1;
        this.x -= VELOCIDADE + 0.3;

        update();

        if (this.x <= -200)
            this.visivel = false;
    }

}

// if (tipo == 1) {
// if (this.y >= 10)
// this.y -= VELOCIDADE;
// else
// this.y += 1;
// } else {
// if (this.y <= 520)
// this.y += VELOCIDADE;
// else
// this.y -= 2;
// }