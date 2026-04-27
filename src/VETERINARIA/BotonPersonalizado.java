package VETERINARIA;

import javax.swing.*;
import java.awt.*;

public class BotonPersonalizado {

    public static JButton crearBoton(String texto, String rutaImagen) {
        ImageIcon icono = new ImageIcon(rutaImagen);

        JButton boton = new JButton(texto, icono);

        boton.setForeground(new Color(0, 0, 255));
        boton.setFont(new Font("Arial", Font.BOLD, 18));

        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);

        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);

        return boton;
    }

    public static JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);


        boton.setForeground(Color.WHITE);


        boton.setFont(new Font("Arial", Font.BOLD, 18));


        boton.setBackground(new Color(0, 139, 139));
        boton.setOpaque(true); 
        boton.setContentAreaFilled(true);

     
        boton.setFocusPainted(false);

        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.CENTER);

        return boton;
    }

}
