package app;

import java.io.IOException;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws IOException {
        final JFrame app = new AppBuilder()
                .addStartNewGameUseCase()
                .addHomePageView()
                .addViewGameResultUseCase()
                .addBlackJackGameView()
                .setupViewManager()
                .build();

        app.setVisible(true);
    }

}
