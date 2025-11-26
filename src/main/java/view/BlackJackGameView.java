package view;

import entity.CurrentGame;
import interface_adapter.StartNewGame.StartNewGameController;
import interface_adapter.StartNewGame.StartNewGameViewModel;
import interface_adapter.ViewGameResult.ViewGameResultController;
import use_case.hit.HitOutputData;

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

public class BlackJackGameView extends JPanel implements ActionListener, PropertyChangeListener {

    BufferedImage cardBack = ImageIO.read(new File("src/main/resources/images/cardback.jpg"));
    private ViewGameResultController viewGameResultController;
    private StartNewGameViewModel startNewGameViewModel;  // For the current game state, maybe not great design

    public BlackJackGameView(ViewGameResultController viewGameResultController,
                             StartNewGameViewModel startNewGameViewModel) throws IOException {

        this.startNewGameViewModel = startNewGameViewModel;
        this.viewGameResultController = viewGameResultController;

        this.setLayout(new BorderLayout(5,5));

        final JButton hitButton = new JButton("Hit");
        hitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                }
        );
        final JButton standButton = new JButton("Stand");

        final JPanel dealerPanel = new JPanel();
        dealerPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
        dealerPanel.setPreferredSize(new Dimension(800, 250));
        dealerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(),"Dealer"));
        final JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
        playerPanel.setPreferredSize(new Dimension(800, 250));
        playerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(),"Player"));
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(800, 50));
        buttonPanel.setLayout(new BorderLayout(5,5));
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);

        // Invisible view game result button that appears when the game ends
        final JButton viewGameResultButton = new JButton("View Game Result");
//        viewGameResultButton.setVisible(false);
        buttonPanel.add(viewGameResultButton, BorderLayout.EAST);

        viewGameResultButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Trigger the view game result use case
                        System.out.println("BlackJackGameView viewGameResultController hash in viewGameResultButton:");
                        System.out.println(System.identityHashCode(viewGameResultController));
                        viewGameResultController.execute(startNewGameViewModel.getCurrentGame());
                    }
                }
        );

        this.add(dealerPanel, BorderLayout.NORTH);
        this.add(playerPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
