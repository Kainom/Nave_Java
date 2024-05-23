package code;

import java.util.Random;

import jplay.Sound;
import jplay.Window;

public class Nave extends Ator {
    private boolean visible = true;
    private Integer tipo;
    private static final String vet[] = { "src/resource/img/nave.png",
            "/home/kainom/meusprojetos/game_nave/src/resource/img/inimigo.png" };

    public Nave(int x, int y, int enemie, Integer tipo) {
        super(vet[enemie], 1);
        this.x = x;
        this.y = y;
        this.visible = false;
        this.tipo = tipo;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visivel) {
        this.visible = visivel;

    }

    public void atingido() {
        this.visible = false;
        tipo = random(2).intValue();
    }

    public void posiciona(Integer firtsGame) {
        if (firtsGame == 1)
            this.x = 1400;
        else
            this.x = 700;
        super.posiciona();
    }

    @Override
    public void mover(Window janela, Double moreFast) {

        if (tipo == 1) {
            if (this.y >= 10)
                this.y -= moreFast; // 0.5
            else
                tipo = 2;
        } else if (tipo == 2) {
            if (this.y <= 520)
                this.y += moreFast;
            else
                tipo = 1;
        }
        // this.x -= VELOCIDADE + 1;
        // this.x -= VELOCIDADE + 0.3;
        this.x -= (VELOCIDADE + moreFast);

        update();

        if (this.x <= -200) {
            this.visible = false;
        }
    }

}
