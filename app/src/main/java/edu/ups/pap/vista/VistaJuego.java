package edu.ups.pap.vista;

import java.util.ArrayList;
import edu.ups.pap.models.*;
import edu.ups.pap.models.abstractFactory.*;
import edu.ups.pap.models.decorator.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Random;
import java.util.prefs.Preferences;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VistaJuego extends JFrame {
    private static final Color BACK = new Color(8, 13, 24);
    private static final Color PANEL = new Color(16, 23, 39);
    private static final Color CARD = new Color(23, 33, 50);
    private static final Color LINE = new Color(43, 55, 75);
    private static final Color TEXT = new Color(245, 247, 255);
    private static final Color MUTED = new Color(160, 174, 195);
    private static final Color GREEN = new Color(54, 214, 170);
    private static final Color GOLD = new Color(255, 202, 67);
    private final String[] colorNames = { "Verde", "Azul", "Violeta", "Naranja", "Rosa" };
    private final Color[] colors = { GREEN, new Color(53, 191, 238), new Color(193, 122, 243), new Color(255, 173, 52),
            new Color(247, 115, 166) };
    private final String[] hats = { "Gorra", "De copa", "Fiesta", "Corona", "Vikingo" };
    private final String[] glasses = { "Clásicas", "Pixel 8-Bit", "Aviador", "Visor VR", "Monóculo" };
    private final String[] speeds = { "Tranqui", "Normal", "Rápido" };
    private final Preferences prefs = abrirPreferencias();
    private final JButton[][] optionButtons = new JButton[4][];
    private final JLabel[] selectedLabels = new JLabel[4];
    private final JLabel outfitLabel = label("", 15, GREEN, true);
    private final JLabel savedLabel = label("Guardado automático del estilo", 13, MUTED, false);
    private final Preview preview = new Preview();
    private final int[] selected = { 0, 3, 1, 1 };
    private final JLabel[] datosPartida = new JLabel[3];

    public VistaJuego() {

        super("SnakeLab | Vestidor Arcade");
        loadPreferences();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 740));
        setSize(1370, 960);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACK);
        setLayout(new BorderLayout());
        add(createHeader(), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(24, 24));
        content.setBackground(BACK);
        content.setBorder(new EmptyBorder(24, 30, 26, 30));
        content.add(createStats(), BorderLayout.NORTH);

        JPanel columns = new JPanel(new BorderLayout(24, 0));
        columns.setOpaque(false);
        columns.add(createCustomization(), BorderLayout.WEST);
        columns.add(createArena(), BorderLayout.CENTER);
        content.add(columns, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.getViewport().setBackground(BACK);
        add(scroll, BorderLayout.CENTER);
        JLabel footer = label("SnakeLab Arcade    •    Personaliza tu serpiente y prepara tu próxima partida", 13,
                MUTED, false);
        footer.setBorder(new EmptyBorder(14, 30, 14, 30));
        add(footer, BorderLayout.SOUTH);
        refresh();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(20, 0));
        header.setBackground(PANEL);
        header.setBorder(new EmptyBorder(20, 30, 20, 30));
        JPanel brand = vertical();
        brand.add(label("SnakeLab   /   V2.0 ARCADE", 28, GREEN, true));
        brand.add(label("¡El clásico juego sin fin reinventado!", 14, MUTED, false));
        header.add(brand, BorderLayout.WEST);
        JButton rules = button("Reglas");
        rules.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Mueve la serpiente con WASD o las flechas.\nRecoge manzanas y evita chocar con tu cola o los bordes.\nEspacio: pausa.\n\nComida roja: +10. Dorada: +20. Azul: +10 y aumenta la velocidad en modo rápido.",
                "Reglas de SnakeLab", JOptionPane.INFORMATION_MESSAGE));
        header.add(rules, BorderLayout.EAST);
        return header;
    }

    private JPanel createStats() { // CREA LOS RECUADROS DE PUNTAJE , MANZANAS Y NIVEL
        JPanel stats = new JPanel(new GridLayout(1, 3, 18, 0));
        stats.setOpaque(false);
        String[] titles = { "ULTIMO PUNTAJE", "MANZANITAS COMIDAS", "LEVEL" };
        int puntajeAnterior = 0;
        int manzanasAnteriores = 0;
        int nivelAnterior = 1;

        try {
            Preferences datos = Preferences.userNodeForPackage(JuegoPanel.class);

            puntajeAnterior = datos.getInt("ultimoPuntaje", 0);
            manzanasAnteriores = datos.getInt("ultimasManzanas", 0);
            nivelAnterior = datos.getInt("ultimoNivel", 1);
        } catch (SecurityException ignored) {
        }

        String[] values = {
                String.valueOf(puntajeAnterior),
                String.valueOf(manzanasAnteriores),
                String.valueOf(nivelAnterior)
        };
        Color[] accents = { TEXT, GOLD, colors[1] };
        for (int i = 0; i < titles.length; i++) {
            JPanel card = panel();
            card.setLayout(new BorderLayout(0, 5));
            card.add(label(titles[i], 13, MUTED, true), BorderLayout.NORTH);
            datosPartida[i] = label(values[i], 26, accents[i], true);
            card.add(datosPartida[i], BorderLayout.CENTER);
            stats.add(card);
        }
        return stats;
    }

    private void mostrarUltimaPartida() {
        try {
            Preferences datos = Preferences.userNodeForPackage(JuegoPanel.class);

            datosPartida[0].setText(
                    String.valueOf(datos.getInt("ultimoPuntaje", 0)));

            datosPartida[1].setText(
                    String.valueOf(datos.getInt("ultimasManzanas", 0)));

            datosPartida[2].setText(
                    String.valueOf(datos.getInt("ultimoNivel", 1)));
        } catch (SecurityException ignored) {
        }
    }

    private JPanel createCustomization() {// Crea las opciones para personalizar la serpiente
        JPanel left = vertical();
        left.setPreferredSize(new Dimension(430, 770));
        JPanel wardrobe = panel();
        wardrobe.setLayout(new BoxLayout(wardrobe, BoxLayout.Y_AXIS));
        wardrobe.add(label(" ARMARIO ", 13, GREEN, true));
        wardrobe.add(Box.createVerticalStrut(14));
        wardrobe.add(label("Personaliza tu serpiente", 26, TEXT, true));
        wardrobe.add(Box.createVerticalStrut(8));
        wardrobe.add(label("Crea tu outfit se original !.", 15, MUTED, false));
        wardrobe.add(createOptions(0, "COLOR SERPIENTE", colorNames));
        wardrobe.add(createOptions(1, "SOMBREROS", hats));
        wardrobe.add(createOptions(2, "GAFAS", glasses));
        wardrobe.add(Box.createVerticalStrut(20));
        JButton random = button("Sorpréndeme");
        random.setAlignmentX(Component.LEFT_ALIGNMENT);
        random.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        random.addActionListener(e -> {
            Random rng = new Random();
            for (int i = 0; i < 3; i++)
                selected[i] = rng.nextInt(5);
            savePreferences();
            refresh();
        });
        wardrobe.add(random);
        wardrobe.setAlignmentX(Component.LEFT_ALIGNMENT);
        left.add(wardrobe);
        left.add(Box.createVerticalStrut(18));
        JPanel speedPanel = panel();
        speedPanel.setLayout(new BoxLayout(speedPanel, BoxLayout.Y_AXIS));
        speedPanel.add(createOptions(3, "VELOCIDAD DE JUEGO", speeds));
        speedPanel.add(Box.createVerticalStrut(14));
        speedPanel.add(label("Moverse: WASD o flechas    |    Espacio: pausa", 13, MUTED, false));
        speedPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        left.add(speedPanel);
        return left;
    }

    private JPanel createOptions(int group, String title, String[] names) {
        JPanel section = vertical();
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.setBorder(new EmptyBorder(22, 0, 0, 0));
        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);
        heading.add(label(title, 12, MUTED, true), BorderLayout.WEST);
        selectedLabels[group] = label("", 12, group == 3 ? GOLD : GREEN, true);
        heading.add(selectedLabels[group], BorderLayout.EAST);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.add(heading);
        section.add(Box.createVerticalStrut(12));
        JPanel options = new JPanel(new GridLayout(1, names.length, 7, 0));
        options.setOpaque(false);
        options.setAlignmentX(Component.LEFT_ALIGNMENT);
        optionButtons[group] = new JButton[names.length];
        for (int i = 0; i < names.length; i++) {
            final int index = i;
            JButton item = button(names[i]);
            item.setFont(new Font("SansSerif", Font.PLAIN, 11));
            item.setMargin(new Insets(8, 2, 8, 2));
            item.setPreferredSize(new Dimension(70, group == 0 ? 86 : 66));
            if (group == 0) {
                item.setIcon(new ColorIcon(colors[i]));
                item.setHorizontalTextPosition(SwingConstants.CENTER);
                item.setVerticalTextPosition(SwingConstants.BOTTOM);
            }
            item.getAccessibleContext().setAccessibleName(title + ": " + names[i]);
            item.addActionListener(e -> {
                selected[group] = index;
                savePreferences();
                refresh();
            });
            optionButtons[group][i] = item;
            options.add(item);
        }
        section.add(options);
        return section;
    }

    private JPanel createArena() { // crea la vista de la serpiente y el boton para jugar
        JPanel area = new JPanel(new BorderLayout(0, 16));
        area.setOpaque(false);
        JPanel heading = panel();
        heading.setLayout(new BorderLayout());
        heading.add(label(" Armario", 14, TEXT, true), BorderLayout.WEST);
        heading.add(label("20 × 15", 13, GREEN, false), BorderLayout.EAST);
        area.add(heading, BorderLayout.NORTH);
        JPanel arena = new GridPanel();
        arena.setLayout(new GridBagLayout());
        arena.setBorder(BorderFactory.createLineBorder(LINE, 2, true));
        JPanel center = vertical();
        center.setBorder(new EmptyBorder(20, 14, 20, 14));
        preview.setPreferredSize(new Dimension(310, 280));
        preview.setMaximumSize(new Dimension(310, 280));
        preview.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(preview);
        outfitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(outfitLabel);
        center.add(Box.createVerticalStrut(24));
        JLabel title = label("Aoutfit listo para la accion 0_o", 27, TEXT, true);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(title);
        center.add(Box.createVerticalStrut(12));
        JLabel subtitle = label("Guarda tu combinación antes de entrar al tablero de juego.", 15, MUTED, false);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(subtitle);
        center.add(Box.createVerticalStrut(26));
        JButton play = button("GUARDAR Y CONTINUAR");
        play.setBackground(GREEN);
        play.setForeground(BACK);
        play.setFont(new Font("SansSerif", Font.BOLD, 20));
        play.setBorder(new EmptyBorder(20, 30, 20, 30));
        play.setAlignmentX(Component.CENTER_ALIGNMENT);
        play.addActionListener(e -> onPlay());
        center.add(play);
        center.add(Box.createVerticalStrut(22));
        savedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(savedLabel);
        arena.add(center);
        area.add(arena, BorderLayout.CENTER);
        return area;
    }

    private void refresh() { // muestra lo q elejimos y como esta la serpiente
        String[][] names = { colorNames, hats, glasses, speeds };
        for (int group = 0; group < optionButtons.length; group++) {
            selectedLabels[group].setText(names[group][selected[group]]);
            for (int i = 0; i < optionButtons[group].length; i++) {
                JButton item = optionButtons[group][i];
                boolean active = i == selected[group];
                item.setBackground(active ? new Color(32, 57, 63) : CARD);
                item.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(active ? (group == 3 ? GOLD : GREEN) : LINE, 2, true),
                        new EmptyBorder(7, 2, 7, 2)));
                item.getAccessibleContext().setAccessibleDescription(active ? "Seleccionado" : "Sin seleccionar");
            }
        }
        outfitLabel.setText(colorNames[selected[0]] + "   /   " + hats[selected[1]] + "   /   " + glasses[selected[2]]);
        preview.repaint();
    }

    protected void onPlay() { // Empieza la partida.
        savePreferences();
        Container inicio = getContentPane();

        JuegoPanel juego = new JuegoPanel(
                colors[selected[0]], selected[1], selected[2], selected[3],
                () -> {
                    mostrarUltimaPartida(); // Actualiza los puntos del vestidor.
                    setContentPane(inicio);
                    revalidate();
                    repaint();
                });

        setContentPane(juego);
        revalidate();
        repaint();
        juego.iniciar();
    }

    private void savePreferences() {// Guarda el estilo q se escojio para la serpiente
        if (prefs == null) {
            savedLabel.setText("Estilo seleccionado para esta sesión");
            return;
        }

        try {
            for (int i = 0; i < selected.length; i++) {
                prefs.putInt("opcion" + i, selected[i]);
            }

            savedLabel.setText("Tu estilo se guarda automáticamente");
        } catch (SecurityException ex) {
            savedLabel.setText("Estilo seleccionado para esta sesión");
        }
    }

    private void loadPreferences() {
        if (prefs == null)
            return;
        try {
            for (int i = 0; i < selected.length; i++) {
                int value = prefs.getInt("opcion" + i, selected[i]);
                if (value >= 0 && value < (i == 3 ? 3 : 5))
                    selected[i] = value;
            }
        } catch (SecurityException ignored) {
            /* La ventana funciona sin persistencia. */ }
    }

    private static Preferences abrirPreferencias() {// Abre las preferencias del usuario para SnakeLab y devuelve null
                                                    // si no tiene permisos de acceso.
        try {
            return Preferences.userNodeForPackage(VistaJuego.class).node("SnakeLab");
        } catch (SecurityException ex) {
            return null;
        }
    }

    private static JPanel vertical() { // Crea un panel transparente para agrupar y ordenar los elementos de la
                                       // interfaz uno debajo del otro.
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        return p;
    }

    private static JPanel panel() { // Crea un panel con fondo, borde y margen interior.
        JPanel p = new JPanel();
        p.setBackground(PANEL);
        p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(LINE, 1, true),
                new EmptyBorder(18, 18, 18, 18)));
        return p;
    }

    private static JLabel label(String text, int size, Color color, boolean bold) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", bold ? Font.BOLD : Font.PLAIN, size));
        l.setForeground(color);
        return l;
    }

    private static JButton button(String title) {
        JButton b = new JButton(title);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setForeground(TEXT);
        b.setBackground(CARD);
        b.setFocusPainted(true);
        b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(LINE, 1, true),
                new EmptyBorder(10, 14, 10, 14)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private static class ColorIcon implements Icon { // crea todas las opciones de colores q se muestran en el vestidor
        private final Color color;

        ColorIcon(Color color) {
            this.color = color;
        }

        public int getIconWidth() {
            return 32;
        }

        public int getIconHeight() {
            return 32;
        }

        public void paintIcon(Component c, Graphics graphics, int x, int y) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(color);
            g.fillOval(x, y, 31, 31);
            g.dispose();
        }
    }

    private static class GridPanel extends JPanel { // apariencia del tablero de ropa
        GridPanel() {
            setBackground(new Color(55, 25, 80));
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.setColor(new Color(85, 45, 115));
            for (int x = 0; x < getWidth(); x += 36)
                graphics.drawLine(x, 0, x, getHeight());
            for (int y = 0; y < getHeight(); y += 36)
                graphics.drawLine(0, y, getWidth(), y);
        }
    }

    private class Preview extends JPanel { // Dibuja la vista previa de la serpiente personalizada.
        Preview() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {

            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.translate((getWidth() - 260) / 2.0, (getHeight() - 260) / 2.0);
            g.setColor(new Color(9, 19, 29));
            g.fillOval(9, 9, 242, 242);
            g.setColor(new Color(38, 98, 85));
            g.setStroke(new BasicStroke(2));
            g.drawOval(9, 9, 242, 242);
            g.setColor(colors[selected[0]]);
            g.setStroke(new BasicStroke(30, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Path2D snake = new Path2D.Double();
            snake.moveTo(84, 181);
            snake.curveTo(55, 225, 190, 229, 174, 164);
            snake.curveTo(171, 142, 134, 151, 135, 118);
            g.draw(snake);
            g.fillOval(103, 87, 62, 58);
            g.setColor(new Color(255, 255, 255, 55));
            g.setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawArc(111, 164, 55, 41, 235, 115);
            g.setColor(TEXT);
            g.fillOval(142, 102, 14, 15);
            g.setColor(BACK);
            g.fillOval(149, 106, 6, 8);
            g.setColor(new Color(255, 103, 142));
            g.setStroke(new BasicStroke(3));
            g.drawLine(162, 129, 182, 133);
            g.drawLine(182, 133, 190, 127);
            g.drawLine(182, 133, 189, 139);
            drawHat(g);
            drawGlasses(g);
            g.dispose();
        }

        private void drawHat(Graphics2D g) {
            g.setStroke(new BasicStroke(3));
            switch (selected[1]) {
                case 0:
                    g.setColor(colors[1]);
                    g.fillArc(104, 65, 61, 44, 0, 180);
                    g.fillRoundRect(98, 84, 81, 9, 5, 5);
                    break;
                case 1:
                    g.setColor(new Color(38, 41, 52));
                    g.fillRect(112, 49, 43, 39);
                    g.fillRoundRect(101, 84, 66, 9, 5, 5);
                    g.setColor(new Color(203, 66, 83));
                    g.fillRect(112, 75, 43, 8);
                    break;
                case 2:
                    g.setColor(colors[2]);
                    g.fillPolygon(new int[] { 107, 133, 160 }, new int[] { 90, 41, 90 }, 3);
                    g.setColor(GOLD);
                    g.fillOval(128, 41, 10, 10);
                    g.fillOval(125, 67, 7, 7);
                    g.fillOval(141, 80, 6, 6);
                    break;
                case 3:
                    g.setColor(GOLD);
                    g.fillPolygon(new int[] { 105, 110, 124, 134, 145, 158, 163 },
                            new int[] { 88, 59, 72, 52, 72, 59, 88 }, 7);
                    g.setColor(new Color(217, 142, 30));
                    g.drawLine(106, 88, 163, 88);
                    g.setColor(colors[1]);
                    g.fillOval(131, 76, 6, 7);
                    break;
                case 4:
                    g.setColor(new Color(130, 143, 164));
                    g.fillArc(106, 60, 56, 61, 0, 180);
                    g.setColor(GOLD);
                    g.drawArc(91, 50, 27, 34, 150, 160);
                    g.drawArc(151, 50, 27, 34, 230, 160);
                    break;
                default:
                    break;
            }
        }

        private void drawGlasses(Graphics2D g) {
            g.setStroke(new BasicStroke(3));
            if (selected[2] == 4) {
                g.setColor(GOLD);
                g.drawOval(141, 100, 22, 23);
                g.drawLine(161, 119, 165, 149);
                return;
            }
            g.setColor(
                    selected[2] == 1 ? colors[1] : selected[2] == 2 ? new Color(148, 209, 224) : new Color(39, 43, 58));
            if (selected[2] == 3) {
                g.fillRoundRect(109, 98, 58, 25, 9, 9);
                g.setColor(colors[1]);
                g.drawLine(118, 106, 156, 106);
                return;
            }
            g.fillRoundRect(111, 100, 23, 20, selected[2] == 1 ? 1 : 8, 8);
            g.fillRoundRect(140, 100, 23, 20, selected[2] == 1 ? 1 : 8, 8);
            g.drawLine(132, 106, 143, 106);
            g.setColor(BACK);
            g.fillRect(116, 104, 13, 10);
            g.fillRect(145, 104, 13, 10);
            g.setColor(TEXT);
            g.fillRect(118, 105, 4, 3);
            g.fillRect(147, 105, 4, 3);
        }
    }

    public static void main(String[] args) { // Inicia la interfaz y muestra la ventana del juego.
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {
                /* Se conserva el aspecto disponible. */ }
            new VistaJuego().setVisible(true);
        });
    }

    public static class JuegoPanel extends JPanel {
        private static final Color BACK = new Color(8, 14, 22);
        private static final Color PANEL = new Color(17, 28, 40);
        private static final Color LINE = new Color(36, 52, 67);
        private static final Color TEXT = new Color(241, 247, 255);
        private static final Color MUTED = new Color(151, 170, 192);
        private static final Color GREEN = new Color(35, 225, 151);
        private static final Color GOLD = new Color(255, 199, 59);
        private final Partida partida;
        private final Timer timer;
        private final Runnable volver;
        private final Apariencia cuerpo, cabeza;
        private final Arena arena = new Arena();
        private final JLabel puntos = text("0", 55, GREEN, true);
        private final JLabel recordLabel = text("0", 20, GOLD, true);
        private final JLabel estado = text("PARTIDA EN MARCHA", 13, GREEN, true);
        private final JLabel longitud = text("", 14, GREEN, true);
        private final JLabel ritmo = text("", 14, GOLD, true);
        private final JLabel nivel = text("", 19, TEXT, true);
        private final JLabel comidas = text("", 14, MUTED, false);
        private final JLabel tipo = text("", 15, GOLD, true);
        private final JLabel efecto = text("", 13, MUTED, false);
        private final JButton pausa = button("Pausar", GREEN, BACK);
        private final Preferences prefs = abrirPreferencias();
        private int record;
        private Window ventana;
        private final WindowFocusListener foco = new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                if (partida.estaJugando()) {
                    partida.pausar();
                    actualizarVista();
                }
            }
        };

        public JuegoPanel(Color color, int sombrero, int gafas, int velocidad, Runnable volver) {
            this.volver = volver;
            int[] intervalos = { 280, 200, 120 };

            if (velocidad < 0 || velocidad >= intervalos.length)
                throw new IllegalArgumentException("Velocidad inválida");
            partida = new Partida(velocidad == 2 ? new FabricaComidaRapida() : new FabricaComidaClasica(),
                    intervalos[velocidad]);
            cuerpo = new DecoradorColor(new AparienciaBase(), color);
            cabeza = new DecoradorSombrero(new DecoradorGafas(new DecoradorOjos(cuerpo)));
            try {
                if (prefs != null)
                    record = prefs.getInt("record", 0);
            } catch (SecurityException ignored) {
            }
            setBackground(BACK);
            setLayout(new BorderLayout(20, 20));
            setBorder(new EmptyBorder(20, 24, 20, 24));
            add(crearCabecera(), BorderLayout.NORTH);
            JPanel centro = new JPanel(new BorderLayout(20, 0));
            centro.setOpaque(false);
            centro.add(crearTablero(), BorderLayout.CENTER);
            centro.add(crearLateral(), BorderLayout.EAST);
            add(centro, BorderLayout.CENTER);
            instalarTeclas();
            timer = new Timer(partida.getVelocidad(), e -> {
                partida.actualizar();
                timerDelay();
                actualizarVista();
                if (partida.estaTerminada())
                    detener();
            });
            timer.setCoalesce(true);
            actualizarVista();
        }

        public void iniciar() {
            partida.iniciar();
            timer.restart();
            actualizarVista();
        }

        public void detener() {
            timer.stop();
        }

        private void timerDelay() {
            timer.setDelay(partida.getVelocidad());
        }

        @Override
        public void addNotify() {
            super.addNotify();
            ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana != null)
                ventana.addWindowFocusListener(foco);
        }

        @Override
        public void removeNotify() {
            detener();
            if (ventana != null)
                ventana.removeWindowFocusListener(foco);
            super.removeNotify();
        }

        private JPanel crearCabecera() {
            JPanel p = new JPanel(new BorderLayout(20, 0));
            p.setOpaque(false);
            JPanel marca = vertical();
            marca.add(text("SnakeLab   /   MODO AVENTURA", 26, GREEN, true));
            marca.add(text("¡Conduce, devora y rompe tu marca!", 14, MUTED, false));
            p.add(marca, BorderLayout.WEST);
            JPanel opciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 8));
            opciones.setOpaque(false);
            opciones.add(estado);
            JButton salir = button("Volver al vestidor", PANEL, TEXT);
            salir.addActionListener(e -> {
                detener();
                volver.run();
            });
            opciones.add(salir);
            p.add(opciones, BorderLayout.EAST);
            return p;
        }

        private JPanel crearTablero() {
            JPanel p = new JPanel(new BorderLayout(0, 15));
            p.setOpaque(false);
            JPanel banner = card();
            banner.setLayout(new BorderLayout(10, 0));
            JPanel nombre = vertical();
            nombre.add(nivel);
            nombre.add(text("Atrapa las frutas sin tocar las paredes", 13, MUTED, false));
            banner.add(nombre, BorderLayout.CENTER);
            JPanel datos = vertical();
            datos.add(longitud);
            datos.add(ritmo);
            banner.add(datos, BorderLayout.EAST);
            p.add(banner, BorderLayout.NORTH);
            JPanel contenedor = card();
            contenedor.setLayout(new BorderLayout(0, 12));
            arena.setPreferredSize(new Dimension(720, 540));
            arena.setMinimumSize(new Dimension(400, 300));
            contenedor.add(arena, BorderLayout.CENTER);
            JPanel controles = new JPanel(new BorderLayout());
            controles.setOpaque(false);
            controles.add(text("WASD / Flechas: mover   |   Espacio: pausa   |   R: reiniciar", 13, MUTED, false),
                    BorderLayout.CENTER);
            JPanel pad = new JPanel(new GridLayout(2, 3, 3, 3));
            pad.setOpaque(false);
            pad.add(new JLabel());
            pad.add(tecla("↑", Direccion.ARRIBA));
            pad.add(new JLabel());
            pad.add(tecla("←", Direccion.IZQUIERDA));
            pad.add(tecla("↓", Direccion.ABAJO));
            pad.add(tecla("→", Direccion.DERECHA));
            controles.add(pad, BorderLayout.EAST);
            contenedor.add(controles, BorderLayout.SOUTH);
            p.add(contenedor, BorderLayout.CENTER);
            return p;
        }

        private JButton tecla(String titulo, Direccion d) {
            JButton b = button(titulo, new Color(26, 40, 57), TEXT);
            b.setBorder(new EmptyBorder(5, 10, 5, 10));
            b.addActionListener(e -> partida.manejarEntrada(d));
            return b;
        }

        private JPanel crearLateral() {
            JPanel p = vertical();
            p.setPreferredSize(new Dimension(265, 550));
            JPanel score = card();
            score.setLayout(new BoxLayout(score, BoxLayout.Y_AXIS));
            score.add(text("PUNTAJE ACTUAL", 13, MUTED, true));
            score.add(puntos);
            score.add(text("RÉCORD PERSONAL", 12, MUTED, true));
            score.add(recordLabel);
            score.add(Box.createVerticalStrut(8));
            score.add(comidas);
            score.add(Box.createVerticalStrut(17));
            JPanel botones = new JPanel(new GridLayout(1, 2, 8, 0));
            botones.setOpaque(false);
            pausa.addActionListener(e -> alternarPausa());
            botones.add(pausa);
            JButton reiniciar = button("Reiniciar", new Color(30, 43, 60), TEXT);
            reiniciar.addActionListener(e -> reiniciar());
            botones.add(reiniciar);
            score.add(botones);
            score.setAlignmentX(LEFT_ALIGNMENT);
            p.add(score);
            p.add(Box.createVerticalStrut(18));
            JPanel leyenda = card();
            leyenda.setLayout(new BoxLayout(leyenda, BoxLayout.Y_AXIS));
            leyenda.add(text("Frutas de la partida", 19, TEXT, true));
            leyenda.add(Box.createVerticalStrut(10));
            leyenda.add(text("Nueva fruta al recoger la anterior.", 13, MUTED, false));
            leyenda.add(Box.createVerticalStrut(18));
            leyenda.add(text("●  Manzana   +10 puntos", 15, new Color(255, 102, 128), true));
            leyenda.add(Box.createVerticalStrut(12));
            boolean rapido = partida.getVelocidad() == 120;
            leyenda.add(text(rapido ? "●  Azul   +10 y más velocidad" : "●  Dorada   +20 puntos", 15,
                    rapido ? Color.CYAN : GOLD, true));
            leyenda.add(Box.createVerticalStrut(18));
            leyenda.add(text("80% normal · 20% especial", 13, MUTED, false));
            leyenda.add(Box.createVerticalStrut(20));
            leyenda.add(text("EN EL TABLERO", 12, MUTED, true));
            leyenda.add(Box.createVerticalStrut(8));
            leyenda.add(tipo);
            leyenda.add(Box.createVerticalStrut(8));
            leyenda.add(efecto);
            leyenda.setAlignmentX(LEFT_ALIGNMENT);
            p.add(leyenda);
            p.add(Box.createVerticalStrut(18));
            JPanel ayuda = card();
            ayuda.setLayout(new BoxLayout(ayuda, BoxLayout.Y_AXIS));
            ayuda.add(text("Tu próxima mejor partida", 16, TEXT, true));
            ayuda.add(Box.createVerticalStrut(10));
            ayuda.add(text("Planea tus giros con tiempo.", 13, MUTED, false));
            ayuda.add(text("Cada fruta añade un bloque.", 13, MUTED, false));
            ayuda.add(text("Llena las 300 casillas para ganar.", 13, MUTED, false));
            ayuda.setAlignmentX(LEFT_ALIGNMENT);
            p.add(ayuda);
            p.add(Box.createVerticalGlue());
            return p;
        }

        private void alternarPausa() {
            if (partida.estaJugando())
                partida.pausar();
            else if (partida.estaPausada())
                partida.reanudar();
            actualizarVista();
        }

        private void reiniciar() {
            partida.reiniciar();

            timer.setDelay(partida.getVelocidad());
            timer.setInitialDelay(partida.getVelocidad());
            timer.restart();

            actualizarVista();

        }

        private void instalarTeclas() {
            bind("UP", "arriba", () -> partida.manejarEntrada(Direccion.ARRIBA));
            bind("W", "arribaW", () -> partida.manejarEntrada(Direccion.ARRIBA));
            bind("DOWN", "abajo", () -> partida.manejarEntrada(Direccion.ABAJO));
            bind("S", "abajoS", () -> partida.manejarEntrada(Direccion.ABAJO));
            bind("LEFT", "izquierda", () -> partida.manejarEntrada(Direccion.IZQUIERDA));
            bind("A", "izquierdaA", () -> partida.manejarEntrada(Direccion.IZQUIERDA));
            bind("RIGHT", "derecha", () -> partida.manejarEntrada(Direccion.DERECHA));
            bind("D", "derechaD", () -> partida.manejarEntrada(Direccion.DERECHA));
            bind("SPACE", "pausa", this::alternarPausa);
            bind("R", "reiniciar", this::reiniciar);
            bind("ENTER", "continuar", () -> {
                if (partida.estaTerminada())
                    reiniciar();
                else if (partida.estaPausada())
                    alternarPausa();
            });
            bind("ESCAPE", "salir", () -> {
                detener();
                volver.run();
            });
        }

        private void bind(String tecla, String nombre, Runnable accion) {
            getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(tecla), nombre);
            getActionMap().put(nombre, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    accion.run();
                }
            });
        }

        private void actualizarVista() {
            puntos.setText(String.valueOf(partida.getPuntaje()));
            if (partida.getPuntaje() > record) {
                record = partida.getPuntaje();
                try {
                    if (prefs != null)
                        prefs.putInt("record", record);
                } catch (SecurityException ignored) {
                }
            }

            if (partida.estaTerminada() && prefs != null) {
                try {
                    prefs.putInt("ultimoPuntaje", partida.getPuntaje());
                    prefs.putInt("ultimasManzanas", partida.getManzanas());
                    prefs.putInt("ultimoNivel", 1 + partida.getManzanas() / 5);
                } catch (SecurityException ignored) {
                }
            }
            recordLabel.setText(String.valueOf(record));
            longitud.setText("Longitud: " + partida.getSerpiente().getLongitud() + " bloques");
            ritmo.setText("Ritmo: " + partida.getVelocidad() + " ms / paso");
            nivel.setText("Mundo Salvaje · Nivel " + (1 + partida.getManzanas() / 5));
            comidas.setText("Frutas recogidas: " + partida.getManzanas());
            estado.setText(partida.estaTerminada() ? (partida.getGano() ? "¡TABLERO COMPLETADO!" : "PARTIDA TERMINADA")
                    : partida.estaPausada() ? "EN PAUSA" : "PARTIDA EN MARCHA");
            pausa.setText(partida.estaPausada() ? "Reanudar" : "Pausar");
            pausa.setEnabled(!partida.estaTerminada());
            Comida c = partida.getComida();
            tipo.setText(c == null ? "Tablero completo"
                    : c instanceof ComidaDoble ? "Manzana dorada · +20"
                            : c instanceof ComidaVelocidad ? "Fruta azul · +10" : "Manzana · +10");
            efecto.setText(
                    c instanceof ComidaVelocidad ? "Acelera 30 ms, mínimo 60 ms." : "Añade un bloque a tu serpiente.");
            arena.repaint();
        }

        private static Preferences abrirPreferencias() {
            try {
                return Preferences.userNodeForPackage(JuegoPanel.class);
            } catch (SecurityException ex) {
                return null;
            }
        }

        private static JPanel vertical() {
            JPanel p = new JPanel();
            p.setOpaque(false);
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            return p;
        }

        private static JPanel card() {
            JPanel p = new JPanel();
            p.setBackground(PANEL);
            p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(LINE, 1, true),
                    new EmptyBorder(17, 17, 17, 17)));
            return p;
        }

        private static JLabel text(
                String texto,
                int size,
                Color color,
                boolean bold) {

            return VistaJuego.label(texto, size, color, bold);
        }

        private static JButton button(String s, Color bg, Color fg) {
            JButton b = new JButton(s);
            b.setFont(new Font("SansSerif", Font.BOLD, 13));
            b.setBackground(bg);
            b.setForeground(fg);
            b.setBorder(new EmptyBorder(12, 10, 12, 10));
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            // Todas las teclas del juego llegan a WHEN_IN_FOCUSED_WINDOW.
            b.setFocusable(false);
            return b;
        }

        private class Arena extends JPanel {
            Arena() {
                setBackground(new Color(11, 20, 29));
            }

            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                Graphics2D g = (Graphics2D) graphics.create();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                double escala = Math.min(getWidth() / 400.0, getHeight() / 300.0);
                double ox = (getWidth() - 400 * escala) / 2, oy = (getHeight() - 300 * escala) / 2;
                g.translate(ox, oy);
                g.scale(escala, escala);
                g.setColor(new Color(13, 23, 33));
                g.fillRect(0, 0, 400, 300);
                g.setColor(new Color(25, 39, 51));
                g.setStroke(new BasicStroke(.5f));
                for (int x = 0; x <= 400; x += 20)
                    g.drawLine(x, 0, x, 300);
                for (int y = 0; y <= 300; y += 20)
                    g.drawLine(0, y, 400, y);
                g.setColor(new Color(29, 46, 61));
                g.setFont(new Font("SansSerif", Font.BOLD, 31));
                g.drawString("SNAKELAB", 107, 160);
                Comida c = partida.getComida();
                if (c != null) {
                    int x = c.getPosicion().getX() * 20, y = c.getPosicion().getY() * 20;
                    Color fruta = c instanceof ComidaDoble ? GOLD
                            : c instanceof ComidaVelocidad ? new Color(66, 207, 255) : new Color(255, 85, 117);
                    g.setColor(new Color(fruta.getRed(), fruta.getGreen(), fruta.getBlue(), 35));
                    g.fillOval(x - 3, y - 3, 26, 26);
                    g.setColor(fruta);
                    g.fillOval(x + 3, y + 3, 14, 14);
                    g.setColor(new Color(255, 255, 255, 170));
                    g.fillOval(x + 6, y + 5, 4, 4);
                    g.setColor(GREEN);
                    g.setStroke(new BasicStroke(1.5f));
                    g.drawLine(x + 10, y + 3, x + 13, y);
                }
                ArrayList<Posicion> segmentos = partida.getSerpiente().getSegmentos();
                for (int i = segmentos.size() - 1; i > 0; i--)
                    cuerpo.dibujar(g, segmentos.get(i));
                cabeza.dibujar(g, segmentos.get(0));
                g.setColor(new Color(50, 76, 91));
                g.setStroke(new BasicStroke(1));
                g.drawRect(0, 0, 400, 300);
                g.dispose();
                if (partida.estaPausada() || partida.estaTerminada()) {
                    Graphics2D overlay = (Graphics2D) graphics.create();
                    overlay.setColor(new Color(4, 10, 18, 215));
                    overlay.fillRect(0, 0, getWidth(), getHeight());
                    String title = partida.estaPausada() ? "EN PAUSA"
                            : partida.getGano() ? "¡GANASTE!" : "FIN DE LA PARTIDA";
                    String detail = partida.estaPausada() ? "Espacio o Reanudar para continuar"
                            : "Puntaje: " + partida.getPuntaje() + "   ·   Enter o Reiniciar para jugar";
                    overlay.setColor(partida.estaTerminada() && !partida.getGano() ? GOLD : GREEN);
                    overlay.setFont(new Font("SansSerif", Font.BOLD, 28));
                    overlay.drawString(title, (getWidth() - overlay.getFontMetrics().stringWidth(title)) / 2,
                            getHeight() / 2 - 8);
                    overlay.setColor(TEXT);
                    overlay.setFont(new Font("SansSerif", Font.PLAIN, 14));
                    overlay.drawString(detail, (getWidth() - overlay.getFontMetrics().stringWidth(detail)) / 2,
                            getHeight() / 2 + 25);
                    overlay.dispose();
                }
            }
        }
    }
}
