package view;

import javax.swing.*;
import java.awt.*;

public class BlackJackGameView extends JPanel {

    public BlackJackGameView() {

        this.setLayout(new BorderLayout(5,5));

        final JButton hitButton = new JButton("Hit");
        final JButton standButton = new JButton("Stand");

        final JPanel dealerPanel = new JPanel();
        dealerPanel.setLayout(new BoxLayout(dealerPanel, BoxLayout.X_AXIS));
        dealerPanel.setPreferredSize(new Dimension(800, 250));
        dealerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(),"Dealer"));
        final JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.X_AXIS));
        playerPanel.setPreferredSize(new Dimension(800, 250));
        playerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(),"Player"));
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(800, 50));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);

        this.add(dealerPanel, BorderLayout.NORTH);
        this.add(playerPanel, BorderLayout.CENTER);
        this.add(buttonPanel,  BorderLayout.SOUTH);
        this.setVisible(true);
    }
}
