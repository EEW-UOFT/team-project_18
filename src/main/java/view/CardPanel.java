package view;

import entity.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CardPanel extends JPanel {

    Image cardBackUnscaled = ImageIO.read(new File("src/main/resources/images/cardback.jpg"));
    Image cardBack =
            cardBackUnscaled.getScaledInstance(150, 200, Image.SCALE_DEFAULT);
    List<JLabel> cardImages = new ArrayList<>();

    public CardPanel(String entity) throws IOException {
        this.setLayout(new GridLayout(1, 1));
        this.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
        this.setPreferredSize(new Dimension(800, 250));
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(),entity));

        JLabel tempLabel = new JLabel(new ImageIcon(cardBack));
        tempLabel.setPreferredSize(new Dimension(150,200));
        this.cardImages.add(tempLabel);
        this.add(tempLabel);
    }

    public void drawCards(List<Card> cards) throws IOException {
        this.removeAll();
        for  (JLabel card : cardImages) {
            this.add(card);
        }
        for (Card card : cards) {
            try {
                Image temp_image = ImageIO.read(new URL(card.getImageUrl()));
                JLabel tempLabel = new JLabel(new ImageIcon(temp_image));
                resize(tempLabel);
                this.cardImages.add(tempLabel);
                this.add(tempLabel);
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            this.revalidate();
            this.repaint();
        }
    }

    private void resize(JLabel label) {
        label.setPreferredSize(new Dimension(150,200));
    }


}
