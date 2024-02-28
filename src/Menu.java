import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class Menu extends JFrame {
    private JPanel panel1;
    private JTextArea text;
    private JButton upravitButton;
    private JButton uložitButton;
    private JButton zavřítSouborButton;

    public Menu() {
        super("Projekt");
        setContentPane(panel1);
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        text.setEditable(false);

        pridaniMenu();
        upravitButton.addActionListener(e -> {
           JOptionPane.showMessageDialog(this, "Nyní můžete soubor upravovat!");
           text.setEditable(true);
        });
        uložitButton.addActionListener(e -> {
            text.setEditable(false);
            ulozDoSouboru();

        });
        zavřítSouborButton.addActionListener(e -> {
            text.setText(null);
        });
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.setVisible(true);
    }


    public void pridaniMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem otevri = new JMenuItem("Open");
        menu.add(otevri);

        otevri.addActionListener(e -> {
            vyberSoubor();
        });
    }
        private File zvolenySoubor;
    public void vyberSoubor() {
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            zvolenySoubor = fc.getSelectedFile();
            StringBuilder obsah = new StringBuilder();

            try (Scanner sc = new Scanner(new BufferedReader(new FileReader(zvolenySoubor)))) {
                while(sc.hasNextLine()) {
                    obsah.append(sc.nextLine()).append("\n");
                }
                text.setText(String.valueOf(obsah));
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Soubor nebyl nalezen: " + e.getLocalizedMessage());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Špatný zápis čísla: " + e.getLocalizedMessage());
            }
        }
    }

    public void ulozDoSouboru() {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(zvolenySoubor)))) {
            printWriter.write(text.getText());
            JOptionPane.showMessageDialog(this, "Soubor byl uložen!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Problém při ukládání souboru: " + e.getLocalizedMessage());
        }
    }
}

