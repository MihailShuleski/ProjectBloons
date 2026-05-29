import javax.swing.*;
import java.awt.*;
/**
 * Represents the main menu window of the game.
 * Provides options to start the game, view instructions, or exit the game.
 */
public class MainMenu extends JFrame {
    /**
     * Constructs a MainMenu frame with the title "Ball TD 6".
     */
    public MainMenu(){
        super("Ball TD 6");
    }
    /**
     * Sets up and displays the main menu title screen, featuring a background panel,
     * a card container, title, subtitle, and buttons for options.
     */
    public void showTitleScreen() {
        this.setSize(500, 560);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new GridBagLayout());
        this.setContentPane(backgroundPanel);

        GlassPanel card = new GlassPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(380, 440));
        card.setMinimumSize(new Dimension(380, 440));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("BALL TD 6");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 38));
        titleLabel.setForeground(Color.WHITE);
        card.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 35, 0);
        JLabel subtitleLabel = new JLabel("Defend the Path!");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(189, 189, 220));
        card.add(subtitleLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        CustomButton startBtn = new CustomButton("START GAME",
                new Color(13, 43, 206),
                new Color(37, 76, 245),
                new Color(9, 29, 140));
        startBtn.setPreferredSize(new Dimension(260, 45));
        startBtn.addActionListener(e -> {
            this.dispose();
            new Game().showMenu();
    });
        card.add(startBtn, gbc);

        gbc.gridy = 3;
        CustomButton helpBtn = new CustomButton("HOW TO PLAY",
                new Color(58, 59, 76),
                new Color(80, 82, 105),
                new Color(40, 41, 53));
        helpBtn.setPreferredSize(new Dimension(260, 45));
        helpBtn.addActionListener(e -> showHelpDialog());
        card.add(helpBtn, gbc);

        gbc.gridy=4;
        CustomButton exitBtn = new CustomButton("EXIT GAME",
                new Color(176, 42, 42),
                new Color(214, 58, 58),
                new Color(125, 28, 28));
        exitBtn.setPreferredSize(new Dimension(260, 45));
        exitBtn.addActionListener(e -> System.exit(0));
        card.add(exitBtn, gbc);

        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        backgroundPanel.add(card, mainGbc);
        this.setVisible(true);
    }



    /**
     * Creates and opens a modal dialog explaining how to play the game.
     */
    private void showHelpDialog() {
        JDialog dialog = new JDialog(this, "How to Play", true);
        dialog.setSize(440, 480);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        BackgroundPanel bg = new BackgroundPanel();
        bg.setLayout(new BorderLayout());
        dialog.setContentPane(bg);

        GlassPanel contentCard = new GlassPanel();
        contentCard.setLayout(new BorderLayout(0, 12));
        contentCard.setBorder(BorderFactory.createEmptyBorder(20, 22, 15, 22));

        JLabel headerLabel = new JLabel("HOW TO PLAY", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(new Color(101, 180, 255));
        contentCard.add(headerLabel, BorderLayout.NORTH);

        JTextArea plainText = new JTextArea();
        plainText.setEditable(false);
        plainText.setOpaque(false);
        plainText.setLineWrap(true);
        plainText.setWrapStyleWord(true);
        plainText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        plainText.setForeground(new Color(220, 220, 225));

        String instructions = "Goal: Prevent balloons from reaching the exit.\n\n" +
                "CONTROLS:\n" +
                "- Select: Click shop buttons or press Keys 1 / 2\n" +
                "- Place Tower: Click on grass area\n" +
                "- Upgrade ($100): Click on a placed tower\n" +
                "- Start Wave: Click Start button or press Spacebar\n\n" +
                "ENEMIES:\n" +
                "- Normal: Standard speed & health\n" +
                "- Speed: Fast-moving target\n" +
                "- Lead: Immune to normal darts (needs bomb explosions!)\n" +
                "- Tank: High health, slow movement";
        plainText.setText(instructions);
        contentCard.add(plainText, BorderLayout.CENTER);
        CustomButton closeBtn = new CustomButton("GOT IT!",
                new Color(13, 43, 206),
                new Color(37, 76, 245),
                new Color(9, 29, 140));
        closeBtn.setPreferredSize(new Dimension(150, 40));
        closeBtn.addActionListener(e -> dialog.dispose());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setOpaque(false);
        btnPanel.add(closeBtn);
        contentCard.add(btnPanel, BorderLayout.SOUTH);
        bg.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        bg.add(contentCard, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
}
