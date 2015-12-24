package application;

import control.Command;
import control.NextImageCommand;
import control.PrevImageCommand;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import view.ImageDisplay;

public class Application extends JFrame{
    
    private final Map<String, Command> commands = new HashMap<>();
    private ImageDisplay imageDisplay;
    
    public static void main(String[] args) {
        new Application().setVisible(true);
    }

    public Application(){
        deployUI();
        addCommands();
    }

    private void deployUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.getContentPane().add(elements());
        this.add(toolBar(),BorderLayout.SOUTH);
    }

    private void addCommands() {
        commands.put("Prev", new PrevImageCommand(imageDisplay));
        commands.put("Next", new NextImageCommand(imageDisplay));
    }

    private JPanel elements() {
        ImagePanel panel = new ImagePanel(new FileImageReader("C:\\Users\\Public\\Pictures\\Sample Pictures").read());
        setTitle("Image Viewer"+" - "+panel.image().name());
        imageDisplay = panel;
        return panel;
    }

    private JMenuBar toolBar() {
        JMenuBar menu = new JMenuBar();
        menu.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu.add(prevButton());
        menu.add(nextButton());
        return menu;
    }

    private JButton prevButton() {
        JButton prevButton = new JButton("<");
        prevButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get("Prev").execute();
                setTitle("Image Viewer - "+imageDisplay.image().name());
                repaint();
            }
        });
        return prevButton;
    }

    private JButton nextButton() {
        JButton nextButton = new JButton(">");
        nextButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get("Next").execute();
                setTitle("Image Viewer - "+imageDisplay.image().name());
                repaint();
            }
        });
        return nextButton;
    }
}
