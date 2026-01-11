package vista;

import javax.swing.*;
import java.awt.*;

public class PanelDegradado extends JPanel {

    private Color colorInicio;
    private Color colorFin;

    public PanelDegradado(Color colorInicio, Color colorFin) {
        this.colorInicio = colorInicio;
        this.colorFin = colorFin;
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(
                0, 0, colorInicio,
                0, getHeight(), colorFin
        );

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
