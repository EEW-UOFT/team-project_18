package view;

import entity.Card;
import entity.CurrentGame;
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
import java.util.ArrayList;
import java.util.List;

public class BlackJackGameView extends JPanel implements ActionListener, PropertyChangeListener {

    final CardPanel dealerPanel = new CardPanel("Dealer");
    final CardPanel playerPanel = new CardPanel("Player");
    private CurrentGame currentGame;

    public BlackJackGameView() throws IOException {

        this.setLayout(new BorderLayout(5,5));

        final JButton hitButton = new JButton("Hit");
        hitButton.addActionListener(
                e -> {
                    Card card1 = new Card("HEARTs", "ACE");
                    List<Card> test = new ArrayList<>();
                    test.add(card1);
                    try {
                        playerPanel.drawCards(test);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
        final JButton standButton = new JButton("Stand");

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(800, 50));
        buttonPanel.setLayout(new GridLayout(1,1));
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);

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
