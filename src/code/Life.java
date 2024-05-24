package code;

public class Life extends Treasure {

    public Life(int x, int y, int teasure, Jogador jogador) {
        super(x, y, teasure, jogador);

    }

    public void bonus() {
        this.jogador.heal();
    }

}
