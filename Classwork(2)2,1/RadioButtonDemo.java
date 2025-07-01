import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File; 

public class RadioButtonDemo extends JFrame {

    private JRadioButton birdButton, catButton, dogButton, rabbitButton, pigButton;
    private JLabel imageLabel;
    private ButtonGroup petGroup; 

    private static final String IMAGE_DIR = "images/"; 

    public RadioButtonDemo() {
        setTitle("RadioButtonDemo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400); 
        setLocationRelativeTo(null); 

        setLayout(new BorderLayout()); 

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(5, 1)); 
        radioPanel.setBorder(BorderFactory.createTitledBorder("Choose a Pet"));

        birdButton = new JRadioButton("Bird");
        catButton = new JRadioButton("Cat");
        dogButton = new JRadioButton("Dog");
        rabbitButton = new JRadioButton("Rabbit");
        pigButton = new JRadioButton("Pig");

        petGroup = new ButtonGroup();
        petGroup.add(birdButton);
        petGroup.add(catButton);
        petGroup.add(dogButton);
        petGroup.add(rabbitButton);
        petGroup.add(pigButton);

        ActionListener radioListener = new RadioButtonListener();
        birdButton.addActionListener(radioListener);
        catButton.addActionListener(radioListener);
        dogButton.addActionListener(radioListener);
        rabbitButton.addActionListener(radioListener);
        pigButton.addActionListener(radioListener);

        radioPanel.add(birdButton);
        radioPanel.add(catButton);
        radioPanel.add(dogButton);
        radioPanel.add(rabbitButton);
        radioPanel.add(pigButton);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createEtchedBorder()); 

        add(radioPanel, BorderLayout.WEST); 
        add(imageLabel, BorderLayout.CENTER); 

        pigButton.setSelected(true);
        updateImage("pig"); 

        setVisible(true);
    }

    private class RadioButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String petName = "";
            if (e.getSource() == birdButton) {
                petName = "bird";
            } else if (e.getSource() == catButton) {
                petName = "cat";
            } else if (e.getSource() == dogButton) {
                petName = "dog";
            } else if (e.getSource() == rabbitButton) {
                petName = "rabbit";
            } else if (e.getSource() == pigButton) {
                petName = "pig";
            }

            updateImage(petName);
            JOptionPane.showMessageDialog(RadioButtonDemo.this,
                    "You selected: " + petName.substring(0, 1).toUpperCase() + petName.substring(1),
                    "Pet Selection",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateImage(String petName) {
        String imagePath = IMAGE_DIR + petName + ".png"; 
        File imageFile = new File(imagePath);

        if (imageFile.exists()) {
            ImageIcon icon = new ImageIcon(imagePath);
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH); 
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            imageLabel.setIcon(null); 
            imageLabel.setText("Image not found: " + petName + ".png");
            System.err.println("Image not found: " + imagePath);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RadioButtonDemo());
    }
}
