package view;

import data.Access.Deck;
import data.Access.DeckApiInterface;
import entity.Card;
import entity.CurrentGame;
import interfaceadapter.startnewgame.StartNewGameViewModel;
import interfaceadapter.viewgameresult.ViewGameResultController;
import interfaceadapter.stand.StandController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlackJackGameView extends JPanel implements ActionListener, PropertyChangeListener {

    public static final int HGAP = 5;
    public static final int VGAP = 5;
    private final CardPanel dealerPanel = new CardPanel("Dealer");
    private final CardPanel playerPanel = new CardPanel("Player");
    BufferedImage cardBack = ImageIO.read(
            new File("src/main/resources/images/cardback.jpg"));
    private CurrentGame currentGame;
    private final ViewGameResultController viewGameResultController;
    private final StartNewGameViewModel startNewGameViewModel;
    private final StandController standController;

    public BlackJackGameView(ViewGameResultController viewGameResultController,
                             StartNewGameViewModel startNewGameViewModel,
                             StandController standController) throws IOException {

        this.viewGameResultController = viewGameResultController;
        this.startNewGameViewModel = startNewGameViewModel;
        this.standController = standController;

        this.setLayout(new BorderLayout(HGAP, VGAP));

        // TEMP: Hit still just draws a fake card to the player panel.
        final JButton hitButton = new JButton("Hit");
        hitButton.addActionListener(
                evt -> {
                    final Card card1 = new Card("HEARTS", "ACE");
                    final List<Card> test = new ArrayList<>();
                    test.add(card1);
                    try {
                        playerPanel.drawCards(test);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );

        final JButton standButton = new JButton("Stand");
        standButton.addActionListener(evt -> {
            // Use the current game to compute player total, then call Stand use case.
            CurrentGame game = startNewGameViewModel.getCurrentGame();
            if (game == null) {
                JOptionPane.showMessageDialog(this,
                        "No active game found.",
                        "Stand Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int playerTotal = game.calculateScore(game.getPlayerHand());

        });

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(800, 50));
        buttonPanel.setLayout(new GridLayout(1, 1));
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);

        // Invisible view game result button that appears when the game ends
        // TODO: toggle the visibility when the game ends, currently always visible for testing
        final JButton viewGameResultButton = new JButton("View Game Result");
//        viewGameResultButton.setVisible(false);
        buttonPanel.add(viewGameResultButton, BorderLayout.EAST);

        viewGameResultButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        viewGameResultController.execute(
                                startNewGameViewModel.getCurrentGame());
                    }
                }
        );

        this.add(dealerPanel, BorderLayout.NORTH);
        this.add(playerPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public void setCurrentGame(CurrentGame currentGame) {
        this.currentGame = currentGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // currently not used; buttons have their own listeners
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // hook this up later if your view model fires property changes
    }
}
