package VETERINARIA;

import javax.swing.*;
import java.awt.*;

public class CampoTextoPersonalizado extends JTextField {

    public CampoTextoPersonalizado(String placeholder) {
        super();
        setMaximumSize(new Dimension(300, 30));
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.PLAIN, 18));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 2));
        setAlignmentX(Component.LEFT_ALIGNMENT);


        if (placeholder != null && !placeholder.isEmpty()) {
            setToolTipText(placeholder); 
        }
    }
}
