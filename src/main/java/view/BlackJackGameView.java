package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import entity.Card;
import entity.CurrentGame;
import interfaceadapter.GameModel;
import interfaceadapter.hit.HitController;
import interfaceadapter.restartgame.RestartGameViewModel;
import interfaceadapter.stand.StandController;
import interfaceadapter.startnewgame.StartNewGameViewModel;
import interfaceadapter.viewgameresult.ViewGameResultController;
import static javax.swing.JOptionPane.*;



public class BlackJackGameView extends JPanel implements ActionListener, PropertyChangeListener {

    public static final int HGAP = 5;
    public static final int VGAP = 5;
    private final CardPanel dealerPanel = new CardPanel("Dealer");
    private final CardPanel playerPanel = new CardPanel("Player");
    private CurrentGame currentGame;
    private ViewGameResultController viewGameResultController;
    private StartNewGameViewModel startNewGameViewModel;
    private RestartGameViewModel restartGameViewModel;
    private HitController hitController;
    private StandController standController;
    private GameModel gameModel;
    private final JButton hitButton;
    private final JButton standButton;
    private final JButton viewGameResultButton;

    private BufferedImage cardBack = ImageIO.read(new File("src/main/resources/images/cardback.jpg"));

    public BlackJackGameView(ViewGameResultController viewGameResultController,
                             StartNewGameViewModel startNewGameViewModel,
                             RestartGameViewModel restartGameViewModel,
                             HitController hitController, GameModel gameModel,
                             StandController standController) throws IOException {

        this.viewGameResultController = viewGameResultController;
        this.startNewGameViewModel = startNewGameViewModel;
        this.restartGameViewModel = restartGameViewModel;
        this.hitController = hitController;
        this.standController = standController;
        this.gameModel = gameModel;
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        viewGameResultButton = new JButton("View Game Result");
        enableGameButtons(true);


        this.setLayout(new BorderLayout(HGAP, VGAP));


        JPanel cardsContainer = new JPanel(new GridLayout(2, 1, 0, 10)); // 2行1列，10像素间距
        cardsContainer.add(dealerPanel);
        cardsContainer.add(playerPanel);

        hitButton.addActionListener(
                evt -> {
                    try {
                        hitController.execute(currentGame);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    List<Card> playerHand = currentGame.getPlayerHand();
                    if (currentGame.calculateScore(playerHand) > 21) {
                        currentGame.gameLost();
                        currentGame.dealerReveal();
                        updateGameDisplay(currentGame);
                        enableGameButtons(false);
                        showMessageDialog(null,"Bust");
                    }
                    else if (currentGame.calculateScore(playerHand) == 21) {
                        currentGame.dealerReveal();
                        standController.execute(currentGame);
                        updateGameDisplay(currentGame);
                        enableGameButtons(false);
                        if (currentGame.calculateScore(currentGame.getDealerHand()) == 21) {
                            showMessageDialog(null,"BlackJack, Tie");
                        }
                        else {
                            showMessageDialog(null,"BlackJack, Win!");
                        }
                    }
                }
        );

        standButton.addActionListener(evt -> {
            try {
                standController.execute(currentGame);

                currentGame.dealerReveal();
                updateGameDisplay(currentGame);
                enableGameButtons(false);

                switch(currentGame.getGameState()) {
                    case WIN:
                        showMessageDialog(null,"You Win");
                        break;
                    case LOST:
                        showMessageDialog(null,"You Lose");
                        break;
                    case DRAW:
                        showMessageDialog(null,"Draw");
                        break;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        final JPanel buttonPanel = new JPanel();

        buttonPanel.setPreferredSize(new Dimension(1200, 50));
        buttonPanel.setLayout(new GridLayout(1, 1));

        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);


        // Invisible view game result button that appears when the game ends

        viewGameResultButton.setEnabled(false);
        buttonPanel.add(viewGameResultButton, BorderLayout.EAST);

        viewGameResultButton.addActionListener(
                e -> viewGameResultController.execute(startNewGameViewModel.getCurrentGame())
        );

        this.add(cardsContainer, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setVisible(true);

        startNewGameViewModel.addPropertyChangeListener(this);
        restartGameViewModel.addPropertyChangeListener(this);

        if (gameModel != null) {
            gameModel.addPropertyChangeListener(this);
        }
    }

    private void enableGameButtons(boolean enabled) {
        hitButton.setEnabled(enabled);
        standButton.setEnabled(enabled);
        viewGameResultButton.setEnabled(!enabled);
    }

    public void setCurrentGame(CurrentGame currentGame) {
        this.currentGame = currentGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if ("currentGame".equals(evt.getPropertyName())) {
            CurrentGame newGame = (CurrentGame) evt.getNewValue();
            this.currentGame = newGame;
            updateGameDisplay(newGame);
            enableGameButtons(true);
        }else if ("playerHand".equals(evt.getPropertyName())) {
            if (currentGame != null) {
                updateGameDisplay(currentGame);
            }
        }
    }

    private void updateGameDisplay(CurrentGame game) {
        if (game == null) {
            return;
        }

        try {
            if (game.getPlayerHand() != null) {
                playerPanel.drawCards(game.getPlayerHand());
            }
            if (game.getDealerHand() != null) {
                dealerPanel.drawCards(game.getDealerHand());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
