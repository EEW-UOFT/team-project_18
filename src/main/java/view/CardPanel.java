package view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entity.Card;

public class CardPanel extends JPanel {

    final Image cardBackUnscaled = ImageIO.read(new File("src/main/resources/images/cardback.jpg"));
    final JLabel cardBackJLabel = toJLabel(cardBackUnscaled);


    public CardPanel(String entity) throws IOException {
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        this.setPreferredSize(new Dimension(1200, 250));

        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), entity));
    }

    public void drawCards(List<Card> cards) throws IOException {
        this.removeAll();

        for (Card card : cards) {
            try {
                if (!card.isFaceUp()) {
                    this.add(cardBackJLabel);
                }
                else {
                    final Image tempImage = ImageIO.read(new URL(card.getImageUrl()));
                    this.add(toJLabel(tempImage));
                }
            }
            catch (MalformedURLException evt) {
                evt.printStackTrace();
            }
            this.revalidate();
            this.repaint();
        }
    }

    private static JLabel toJLabel(Image image) {

        Image resized = image.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(resized));
        label.setPreferredSize(new Dimension(150, 200));
        return label;
    }

    public void clearCards() {
        this.removeAll();
        this.add(cardBackJLabel);
        this.revalidate();
        this.repaint();
    }
}
