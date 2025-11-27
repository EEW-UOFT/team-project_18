package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import interface_adapter.restartgame.RestartGameController;
import interface_adapter.restartgame.RestartGameViewModel;


public class BlackJackGameView extends JPanel implements ActionListener, PropertyChangeListener {

    BufferedImage cardBack = ImageIO.read(new File("src/main/resources/images/cardback.jpg"));
    private final RestartGameController restartGameController;
    private final RestartGameViewModel restartGameViewModel;

    public BlackJackGameView(RestartGameController restartGameController,
                             RestartGameViewModel restartGameViewModel) throws IOException {

        this.restartGameController = restartGameController;
        this.restartGameViewModel = restartGameViewModel;

        this.viewGameResultController = viewGameResultController;
        this.startNewGameViewModel = startNewGameViewModel;

        this.setLayout(new BorderLayout(HGAP, VGAP));

        final JButton hitButton = new JButton("Hit");
        hitButton.addActionListener(
                evt -> {
                    final Card card1 = new Card("HEARTs", "ACE");
                    final List<Card> test = new ArrayList<>();
                    test.add(card1);
                    try {
                        playerPanel.drawCards(test);
                    }
                    catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
        final JButton standButton = new JButton("Stand");

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(800, 50));
        buttonPanel.setLayout(new GridLayout(1, 1));
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(restartButton);


        // Invisible view game result button that appears when the game ends
        // TODO: toggle the visibility when the game ends, currently always visible for testing
        final JButton viewGameResultButton = new JButton("View Game Result");
//        viewGameResultButton.setVisible(false);
        buttonPanel.add(viewGameResultButton, BorderLayout.EAST);

        viewGameResultButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        viewGameResultController.execute(startNewGameViewModel.getCurrentGame());
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

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
