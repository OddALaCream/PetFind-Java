package VETERINARIA;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class CampoPasswordPersonalizado extends JPasswordField {
    
    private String placeholder;
    private boolean mostrarPlaceholder = true;
    
    public CampoPasswordPersonalizado(String placeholder) {
        this.placeholder = placeholder;
        
        // Configuración básica
        setMaximumSize(new Dimension(300, 35));
        setPreferredSize(new Dimension(300, 35));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setFont(new Font("Arial", Font.PLAIN, 16));
        setEchoChar('•');
        
        // Colores - tema oscuro
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setCaretColor(Color.WHITE);
        
        // Borde azul
        setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(0, 0, 255), 2, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Listeners para manejar el placeholder
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                mostrarPlaceholder = false;
                repaint();
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (getPassword().length == 0) {
                    mostrarPlaceholder = true;
                    repaint();
                }
            }
        });
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                mostrarPlaceholder = false;
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Dibujar placeholder si está vacío y no tiene foco
        if (mostrarPlaceholder && getPassword().length == 0) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(150, 150, 150));
            g2.setFont(getFont());
            
            FontMetrics fm = g2.getFontMetrics();
            int x = getInsets().left;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            
            g2.drawString(placeholder, x, y);
            g2.dispose();
        }
    }
    
    @Override
    public void setText(String t) {
        super.setText(t);
        mostrarPlaceholder = (t == null || t.isEmpty());
        repaint();
    }
}