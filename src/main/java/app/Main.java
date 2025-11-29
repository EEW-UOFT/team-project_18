package app;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        final JFrame app = new AppBuilder()
                .addStartNewGameUseCase()
                .addRestartGameUseCase()
                .addViewGameResultUseCase()
                .addGameRuleUseCase()
                .addHomePageView()
                .addBlackJackGameView()
                .addStatisticsUseCase()
                .addGameResultView()
                .setupViewManager()
                .build();

        app.setVisible(true);
    }

}
