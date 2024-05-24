package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import jplay.GameImage;
import jplay.Keyboard;
import jplay.Sound;
import jplay.Sprite;
import jplay.Window;

public class Cenario {
    private Window janela;
    private GameImage cena;
    private Sound musicBack;
    private Jogador jogador;
    private Tiro tiro;
    private Treasure treasure;
    private List<Treasure> treasures;
    private Sprite heart[] = new Sprite[4];
    private Font font;
    private List<Nave> naves;
    private Random gerador = new Random();
    private static final Integer MAXENEMIE = 2;
    private Integer fistGame = 0;
    private Integer morePoints = 50;
    private Integer moreEnemies = 0;
    private Integer bufferPoint = 0;
    private Double moreFast = 0d;
    private boolean dead = false;
    Sound som;

    public Cenario(Window janela) {
        this.janela = janela;
        this.font = font();
        run();
    }

    private Font font() {
        try {
            Font customFont = Font
                    .createFont(Font.TRUETYPE_FONT,
                            new File(
                                    "/home/kainom/meusprojetos/game_nave/src/resource/emulogic-font/Emulogic-zrEw.ttf"))
                    .deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont;
        } catch (IOException | FontFormatException e) {
            return null;
        }
    }

    public Integer random(Integer max, Integer min) {
        Integer range = max - min + 1;
        return (int) (Math.random() * range) + min;
    }

    public void musicback() {
        this.musicBack = new Sound("src/resource/music/musicBackALien.wav");
        this.musicBack.play();
        this.musicBack.setRepeat(true);
        this.musicBack.decreaseVolume(20);
    }

    private void gerarInimigos() {
        naves = null;
        naves = new ArrayList<Nave>();
        for (int i = 0; i < (MAXENEMIE + 3); i++) {
            Integer y = this.random(520, 10);
            Integer enemie = this.random(1, 0);
            Integer tipo = this.random(2, 0);
            naves.add(new Nave(700, y, enemie, tipo));
            if (i < 4) {
                heart[i] = new Sprite("src/resource/img/heart.png", 1);
                heart[i].x = 600 + (i * 43);
                heart[i].y = 0;
            }

        }
    }

    private void sair() {
        if (janela.getKeyboard().keyDown(Keyboard.ESCAPE_KEY))
            System.exit(0);
    }

    public void iniciar() {
        if (this.dead) {
            while (this.dead) {
                sair();
                janela.drawText("GAME OVER", 290, 300, Color.white, this.font);
                janela.update();
                if (janela.getKeyboard().keyDown(Keyboard.ENTER_KEY))
                    this.dead = false;

            }
        }

        cena = new GameImage("/home/kainom/meusprojetos/game_nave/src/resource/img/starNave/fundoBlackh.jpg");
        jogador = new Jogador(0, 250);
        tiro = new Tiro("/home/kainom/meusprojetos/game_nave/src/resource/img/starNave/Fighter/Charge_1.png", 1);
        treasure = new Treasure(700, this.random(520, 10), 0, jogador);
        treasures = Arrays.asList(new Treasure(700, this.random(520, 10), 0, jogador),
                new Life(700, this.random(520, 10), 1, jogador));
        this.fistGame = 1;
        this.bufferPoint = 0;
        this.moreEnemies = 0;
        this.moreFast = 0d;
        this.morePoints = 50;
        this.gerarInimigos();
        this.musicback();

    }

    private void run() {
        this.iniciar();
        while (true) {
            sair();
            cena.draw();
            janela.drawText("Pontos: " + jogador.getPontuacao(), 20, 25, Color.white, this.font);
            for (int i = 0; i < this.jogador.getLife(); i++)
                heart[i].draw();

            if (jogador.getLife() == 0) { // verifica se o jogador perdeu
                this.dead = true;
                som = new Sound("src/resource/music/game-over.wav");
                som.increaseVolume(6);
                som.play();
                this.musicBack.stop();
                this.musicBack = null;
                this.iniciar();
            }
            jogador.mover(janela, 0d);

            if (this.bufferPoint == 20) {
                Sound som = new Sound("/home/kainom/meusprojetos/game_nave/src/resource/music/coinUp.wav");
                som.decreaseVolume(8);
                som.play();
                som = null;
                this.bufferPoint = 0;
            }

            if (jogador.getPontuacao() >= this.morePoints) { // aumenta a dificuldade
                this.morePoints = this.morePoints + 50;
                if (this.moreFast <= 0.3)
                    this.moreFast += 0.05d;
                if (this.moreEnemies != 3) {
                    this.moreEnemies++;
                    // this.tiro.setVelocidade();
                }
                this.treasures.forEach(e -> e.respawnTreasure());
            }

            if (jogador.disparou(janela)) {
                tiro.disparar(jogador.x + 90d, jogador.y + 25d);
                // if (tiro.isDisparo())

            }

            this.treasures.forEach((e) -> {
                if (e.isVisible())
                    e.mover();
            });

            if (tiro.isVisivel())
                tiro.mover();

            this.treasures.forEach((e) -> {
                if (e.isVisible() && e.getPlayer().collided(e)) {
                    e.getTeasure();
                    e.bonus();

                }
            });

            for (int i = 0; i < (MAXENEMIE + this.moreEnemies); i++) { // Atualizar inimigos
                if (naves.get(i).isVisible())
                    naves.get(i).mover(janela, moreFast);
                else {
                    naves.get(i).setVisible(true);
                    naves.get(i).posiciona(fistGame);
                    fistGame++;
                }
            }
            for (int i = 0; i < (MAXENEMIE + this.moreEnemies); i++) {
                if (naves.get(i).isVisible()) {
                    if (jogador.collided(naves.get(i))) { // jogador leva dano
                        naves.get(i).atingido(gi);
                        naves.get(i).x = 700;
                        this.jogador.setDano(1);

                    }
                }
            } // Atualizar inimigos

            for (int i = 0; i < (MAXENEMIE + this.moreEnemies); i++) { // verificar morte de inimigos
                if (naves.get(i).isVisible())
                    if (tiro.isVisivel() && naves.get(i).collided(tiro)) {
                        tiro.atingiu();
                        naves.get(i).atingido();
                        jogador.SetPontuacao(5);
                        som = new Sound("/home/kainom/meusprojetos/game_nave/src/resource/music/explosionBit.wav");
                        som.decreaseVolume(15);
                        som.play();
                        som = null;
                        bufferPoint += 5;
                    }
            }

            jogador.draw(); // printar na tela

            treasures.forEach((e) -> {
                if (e.isVisible())
                    e.draw();
            });

            if (tiro.isVisivel())
                tiro.draw();

            for (int i = 0; i < (MAXENEMIE + this.moreEnemies); i++) {
                if (naves.get(i).isVisible())
                    naves.get(i).draw();
            }
            janela.update();

        }
    }
}
