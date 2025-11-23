package view;

import javax.swing.*;
import java.awt.*;

public class BlackJackGameView extends JPanel {

    private final JFrame frame = new JFrame("BlackJack Game");

    public void BlackJackViewFrame() {

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setSize(new Dimension(800, 600));
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setLayout(new BorderLayout(5, 5));
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

        this.frame.add(dealerPanel, BorderLayout.NORTH);
        this.frame.add(playerPanel, BorderLayout.CENTER);
        this.frame.add(buttonPanel,  BorderLayout.SOUTH);
        this.frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BlackJackGameView bjframe = new BlackJackGameView();
                bjframe.BlackJackViewFrame();
            }
        });
    }
}
