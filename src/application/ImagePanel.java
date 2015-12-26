package application;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import model.Image;
import view.ImageDisplay;

public class ImagePanel extends JPanel implements ImageDisplay {

    private Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }
    
    @Override
    public Image image() {
        return image;
    }

    @Override
    public void show(Image image) {
        this.image = image;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage((BufferedImage) image.bitMap(), 0, 0, this);
    }
    
}
