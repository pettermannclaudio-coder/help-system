package main;

import connection.DatabaseInitializer;
import view.LoginView;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        DatabaseInitializer.initialize();

        SwingUtilities.invokeLater(() -> {
            configurarLookAndFeel();
            new LoginView();
        });
    }

    private static void configurarLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            System.out.println(
                    "Não foi possível aplicar o visual do sistema."
            );
        }
    }
}
