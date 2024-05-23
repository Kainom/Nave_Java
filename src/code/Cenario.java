package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jplay.GameImage;
import jplay.Keyboard;
import jplay.Scene;
import jplay.Sound;
import jplay.Sprite;
import jplay.Window;

public class Cenario {
    private Window janela;
    private GameImage cena;
    private Jogador jogador;
    private Tiro tiro;
    private Treasure treasure;
    private Sprite heart[] = new Sprite[4];
    private Font font;
    private List<Nave> naves;
    private static final Integer MAXENEMIE = 2;
    private Integer fistGame = 0;
    private Integer morePoints = 50;
    private Integer moreEnemies = 0;
    private Integer bufferPoint = 0;
    private Double moreFast = 0d;
    private boolean dead = false;

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

    private void gerarInimigos() {
        naves = null;
        naves = new ArrayList<Nave>();
        for (int i = 0; i < MAXENEMIE + 7; i++) {
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
        treasure = new Treasure(700, this.random(520, 10), 0);
        this.fistGame = 1;
        this.bufferPoint = 0;
        this.moreEnemies = 0;
        this.moreFast = 0d;
        this.morePoints = 50;
        this.gerarInimigos();
        System.out.println(this.moreFast);

    }

    private void run() {
        this.iniciar();
        while (true) {
            sair();
            cena.draw();
            janela.drawText("Pontos: " + jogador.getPontuacao(), 20, 25, Color.white, this.font);
            // janela.drawText("Life: " + jogador.getLife(), 600, 25, Color.white,
            // this.font);
            for (int i = 0; i < this.jogador.getLife(); i++)
                heart[i].draw();

            if (jogador.getLife() == 0) { // verifica se o jogador perdeu
                new Sound("/home/kainom/meusprojetos/game_nave/src/resource/music/explosionBit.wav").play();
                this.dead = true;

                this.iniciar();
            }
            jogador.mover(janela, 0d);

            if (this.bufferPoint == 10) {
                new Sound("/home/kainom/meusprojetos/game_nave/src/resource/music/coinUp.wav").play();
                this.bufferPoint = 0;
            }
            if (this.morePoints <= 450)
                if (jogador.getPontuacao() >= this.morePoints) {
                    this.morePoints = this.morePoints + 50;
                    if (this.moreFast <= 0.4)
                        this.moreFast += 0.1d;
                    if (this.moreEnemies < 9)
                        this.moreEnemies = this.moreEnemies + 1;
                    this.treasure.respawnTreasure();
                }

            if (jogador.disparou(janela)) {
                tiro.disparar(jogador.x + 90d, jogador.y + 25d);
                if (tiro.isDisparo())
                    new Sound("/home/kainom/meusprojetos/game_nave/src/resource/music/laser1.wav").play();
            }

            if (treasure.isVisible())
                treasure.mover();

            if (tiro.isVisivel())
                tiro.mover();

            if (this.treasure.isVisible() && this.jogador.collided(treasure)) {
                treasure.getTeasure();
                this.jogador.SetPontuacao(30);
                new Sound("/home/kainom/meusprojetos/game_nave/src/resource/music/collect.wav").play();
            }

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
                        naves.get(i).atingido();
                        naves.get(i).x = 700;
                        this.jogador.setLife(1);
                        new Sound("/home/kainom/meusprojetos/game_nave/src/resource/music/damage.wav").play();

                    }
                }
            } // Atualizar inimigos

            for (int i = 0; i < (MAXENEMIE + this.moreEnemies); i++) { // verificar morte de inimigos
                if (naves.get(i).isVisible())
                    if (tiro.isVisivel() && naves.get(i).collided(tiro)) {
                        tiro.atingiu();
                        naves.get(i).atingido();
                        jogador.SetPontuacao(5);
                        bufferPoint += 5;
                        new Sound("//home/kainom/meusprojetos/game_nave/src/resource/music/explosionBit.wav").play();
                    }
            }

            jogador.draw(); // printar na tela

            if (treasure.isVisible())
                treasure.draw();
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
