package view;

import entity.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CardPanel extends JPanel {

    final Image cardBackUnscaled = ImageIO.read(new File("src/main/resources/images/cardback.jpg"));
    final JLabel cardBackJLabel = toJLabel(cardBackUnscaled);


    public CardPanel(String entity) throws IOException {
        this.setLayout(new GridLayout(1, 1));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        this.setPreferredSize(new Dimension(800, 250));
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), entity));
    }

    private static JLabel toJLabel(Image image) {

        JLabel label = new JLabel(new ImageIcon(image));
        label.setPreferredSize(new Dimension(150, 200));
        return label;
    }

    public void drawCards(List<Card> cards) throws IOException {
        this.removeAll();
        for (Card card : cards) {
            try {
                if (!card.isFaceUp()) {
                    this.add(cardBackJLabel);
                } else {
                    final Image tempImage = ImageIO.read(new URL(card.getImageUrl()));
                    this.add(toJLabel(tempImage));
                }
            } catch (MalformedURLException evt) {
                evt.printStackTrace();
            }
            this.revalidate();
            this.repaint();
        }
    }
}
