package code;

import jplay.Window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import jplay.GameObject;
import jplay.Keyboard;
import jplay.Sound;
import jplay.Sprite;

public class Jogador extends Ator {
    private static final Double VELOCIDADE = 0.9;
    private Integer pontuacao = 0;
    private Keyboard teclado;
    private Integer life;

    public Jogador(int x, int y) {
        super("src/resource/img/starNave/Fighter/Idle.png", 1);
        this.x = x;
        this.y = y;
        this.life = 4;
    }

    private void gravidade() {
        if ((this.direcao != 2 || !(this.movendo)) && x > 0)
            this.x -= 0.2;
    }

    public boolean disparou(Window janela) {
        if (teclado == null)
            teclado = janela.getKeyboard();
        return teclado.keyDown(Keyboard.SPACE_KEY);
    }

    public Integer getLife() {
        return this.life;
    }

    public void setDano(Integer dano) {
        this.life -= dano;
        Sound som = new Sound("/home/kainom/meusprojetos/game_nave/src/resource/music/damage.wav");
        som.decreaseVolume(13);
        som.play();
        som = null;

    }

    public void heal() {
        if (this.life > 0 && this.life < 4) {
            this.life++;
            Sound som = new Sound("src/resource/music/heal.wav");
            som.decreaseVolume(13);
            som.play();
            som = null;
        }

    }

    public Integer getPontuacao() {
        return this.pontuacao;
    }

    public void SetPontuacao(Integer valor) {
        this.pontuacao += valor;
    }

    public void movimentaAdjunta(Keyboard teclado, Window janela) {
        if (teclado.keyDown(Keyboard.UP_KEY)) {
            if (this.y > 0)
                this.y -= VELOCIDADE;
        } else if (teclado.keyDown(Keyboard.DOWN_KEY)) {
            if (this.y <= ((janela.getY() * 5)))
                this.y += VELOCIDADE;
        }
    }

    public void mover(Window janela, Double moreFast) {
        if (teclado == null) {
            teclado = janela.getKeyboard();
        }

        gravidade();

        if (teclado.keyDown(Keyboard.LEFT_KEY)) {
            if (this.x > 0) {
                this.x -= VELOCIDADE;
                this.movimentaAdjunta(teclado, janela);
            }
            if (this.direcao != 1) {
                this.direcao = 1;
            }
            this.movendo = true;

        } else if (teclado.keyDown(Keyboard.RIGHT_KEY)) {
            if (this.x <= (janela.getX() * 2.47)) {
                this.x += VELOCIDADE;
                this.movimentaAdjunta(teclado, janela);
            }
            if (this.direcao != 2) {
                this.direcao = 2;
            }
            this.movendo = true;

        } else if (teclado.keyDown(Keyboard.UP_KEY)) {
            if (this.y > 0)
                this.y -= VELOCIDADE;
            if (this.direcao != 3) {
                this.direcao = 3;
            }
            this.movendo = true;

        } else if (teclado.keyDown(Keyboard.DOWN_KEY)) {
            if (this.y <= ((janela.getY() * 5)))
                this.y += VELOCIDADE;
            if (this.direcao != 4) {
                this.direcao = 4;
            }
            this.movendo = true;

        }

        if (this.movendo) {
            movendo = false;
        }

        // janela.update();

    }

}
