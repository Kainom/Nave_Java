package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import jplay.GameImage;
import jplay.Keyboard;
import jplay.Scene;
import jplay.Sound;
import jplay.Window;

public class Cenario {
    private Window janela;
    private GameImage cena;
    private Jogador jogador;
    private List<Nave> naves;
    private static final Integer MAXENEMIE = 4;
    private Integer fistGame = 0;
    private Tiro tiro;
    private Integer bufferPoint = 0;
    private boolean morre = false;
    private Font font;

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
        for (int i = 0; i < MAXENEMIE; i++) {
            Integer y = this.random(520, 10);
            Integer enemie = this.random(1, 0);
            Integer tipo = this.random(2, 1);
            naves.add(new Nave(700, y, enemie, tipo));
        }
    }

    private void sair() {
        if (janela.getKeyboard().keyDown(Keyboard.ESCAPE_KEY))
            System.exit(0);
    }

    public void iniciar() {
        if (morre) {
            while (morre) {
                sair();
                janela.drawText("GAME OVER", 290, 300, Color.white, this.font);
                janela.update();
                if (janela.getKeyboard().keyDown(Keyboard.ENTER_KEY))
                    morre = false;
            }
        }
        cena = new GameImage("/home/kainom/meusprojetos/game_nave/src/resource/img/starNave/fundoBlackh.jpg");
        jogador = new Jogador(0, 250);
        tiro = new Tiro("/home/kainom/meusprojetos/game_nave/src/resource/img/starNave/Fighter/Charge_1.png", 1);
        this.fistGame = 1;
        this.bufferPoint = 0;
        this.gerarInimigos();
    }

    private void run() {
        this.iniciar();
        while (true) {
            sair();
            cena.draw();
            janela.drawText("Pontos: " + jogador.getPontuacao(), 20, 20, Color.white, this.font);

            jogador.mover(janela);

            if (bufferPoint == 10) {
                new Sound("/home/kainom/meusprojetos/game_nave/src/resource/music/coinUp.wav").play();
                bufferPoint = 0;
            }

            if (jogador.disparou(janela)) {
                tiro.disparar(jogador.x + 90d, jogador.y + 25d);
                if (tiro.isDisparo())
                    new Sound("/home/kainom/meusprojetos/game_nave/src/resource/music/laser1.wav").play();
            }
            if (tiro.isVisivel())
                tiro.mover();
            for (int i = 0; i < MAXENEMIE; i++) { // Atualizar inimigos
                if (naves.get(i).isVisible())
                    naves.get(i).mover(janela);
                else {
                    naves.get(i).setVisivel(true);
                    naves.get(i).posiciona(fistGame);
                    fistGame++;
                }
            }
            for (int i = 0; i < MAXENEMIE; i++) {
                if (naves.get(i).isVisible()) {
                    if (jogador.collided(naves.get(i))) { // jogador morre
                        new Sound("/home/kainom/meusprojetos/game_nave/src/resource/music/explosionBit.wav").play();
                        morre = true;
                        this.iniciar();
                    }
                }
            } // Atualizar inimigos

            for (int i = 0; i < MAXENEMIE; i++) { // verificar morte de inimigos
                if (naves.get(i).isVisible())
                    if (tiro.isVisivel() && naves.get(i).collided(tiro)) {
                        tiro.atingiu();
                        naves.get(i).atingido();
                        jogador.SetPontuacao();
                        bufferPoint += 5;
                        new Sound("//home/kainom/meusprojetos/game_nave/src/resource/music/explosionBit.wav").play();
                    }
            }

            jogador.draw(); // printar na tela
            if (tiro.isVisivel())
                tiro.draw();
            for (int i = 0; i < MAXENEMIE; i++) {
                if (naves.get(i).isVisible())
                    naves.get(i).draw();
            }
            janela.update();

        }
    }
}
