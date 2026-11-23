import App.AppBuilder;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> {
                    final AppBuilder appBuilder = new AppBuilder();

                    final JFrame application =
                            appBuilder
                                    .addSignupView()
                                    .addLoginView()
                                    .addLoggedInView()
                                    .addNewGameView()
                                    .addSignupUseCase()
                                    .addLoginUseCase()
                                    .build();

                    application.setSize(800, 600);
                    application.setLocationRelativeTo(null);
                    application.setVisible(true);
                });
    }
}
