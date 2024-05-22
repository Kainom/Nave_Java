package code;

import jplay.Sprite;

public class Tiro extends Sprite {
    private boolean visivel;
    private Double velocidade = 1.7d;
    private boolean disparo = false;

    public Tiro(String fileName, int numFrames) {
        super(fileName, numFrames);
        this.visivel = false;
    }

    public boolean isVisivel() {
        return visivel;
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
