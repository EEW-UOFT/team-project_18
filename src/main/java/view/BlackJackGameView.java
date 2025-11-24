package view;

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

public class BlackJackGameView extends JPanel implements ActionListener, PropertyChangeListener {

    BufferedImage cardBack = ImageIO.read(new File("src/images/back.png"));

    public BlackJackGameView() throws IOException {

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
