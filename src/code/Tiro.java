package code;

import jplay.Sound;
import jplay.Sprite;

public class Tiro extends Sprite {
    private boolean visivel;
    // private Double velocidade = 2.7d;
    private Double velocidade = 1d;
    private boolean disparo = false;

    public Tiro(String fileName, int numFrames) {
        super(fileName, numFrames);
        this.visivel = false;
    }

    public boolean isVisivel() {
        return visivel;
    }

    public void setVelocidade() {
        if (this.velocidade < 1.2)
            this.velocidade += 0.2;
    }

    public boolean isDisparo() {
        return this.disparo;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public void disparar(Double x, Double y) {
        if (!visivel) {
            this.x = x;
            this.y = y;
            this.visivel = true;
            this.disparo = true;
            Sound som = new Sound("src/resource/music/laser1.wav");
            som.decreaseVolume(20);
            som.play();
            som = null;

        }
    }

    public void mover() {
        if (this.visivel) {
            this.x += velocidade;
            this.disparo = false;
            if (this.x > 800) {
                this.visivel = false;
                this.disparo = false;
            }

        }
    }

    public void atingiu() {
        this.visivel = false;
        this.disparo = false;
    }

}
