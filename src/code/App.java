package code;

import jplay.Keyboard;
import jplay.Sound;
import jplay.Window;

public class App {

    public static void main(String[] args) {
        Sound music;

        Window window = new Window(800, 600);

        while (true) {
            music = new Sound("src/resource/music/starWars.wav");
            // music.play();
            // music.setRepeat(true);
            // music.decreaseVolume(15);
            window.update();
            new Cenario(window);
        }

    }

}
