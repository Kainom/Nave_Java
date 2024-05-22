package code;

import jplay.Keyboard;
import jplay.Sprite;
import jplay.Window;

public abstract class Ator extends Sprite{
    protected static final Double VELOCIDADE = 0.5;
    protected Integer direcao = 3;
    protected boolean movendo = false;

    public Ator(String file,int i) {
        super(file,1);
    }

     public abstract void mover(Window janela);
    


    
}
