import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class HighSeaTower extends Application {

    public static final int WIDTH = 350, HEIGHT = 480;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        Canvas canvas = new Canvas(WIDTH, HEIGHT);

        // Pour l'affichage du score
        Text score = new Text("0m");
        score.setFill(Color.WHITE);
        score.setFont(Font.font("Verdana", 20));
        TextFlow textFlow = new TextFlow(score);
        textFlow.setTextAlignment(TextAlignment.CENTER);
        textFlow.setPrefWidth(WIDTH);

        // Pour l'affichage des informations du mode debug
        VBox vBox = new VBox();

        root.getChildren().addAll(canvas, textFlow, vBox);

        Controleur controleur = new Controleur();

        GraphicsContext context = canvas.getGraphicsContext2D();

        controleur.draw(context);

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double deltaTime = (now - lastTime) * 1e-9;

                // Si la partie est commencée et qu'elle n'est pas sur pause
                if (!controleur.getAvantPartie() && !controleur.getPause()) {
                    // Si la partie est terminée
                    if (controleur.getFinPartie()) {
                        // Réinitialise le score
                        score.setText("0m");
                        // Réinitialise les entités
                        controleur.reset();
                        controleur.setAvantPartie(true);
                    }

                    // Si la partie est en mode debug
                    if (controleur.getDebug()) {
                        // Met à jour les informations pour le mode debug
                        for (int i = 0; i < 4; i++) {
                            // Information pour le mode debug
                            Text infoDebug = ((Text) vBox.getChildren().get(i));

                            infoDebug.setText(controleur.infosDebug().get(i));
                            infoDebug.setFill(Color.WHITE);
                            infoDebug.setFont(Font.font("Verdana", 10));
                        }
                    }

                    controleur.update(deltaTime);

                    // Mise à jour du score
                    score.setText(controleur.getScore());

                    controleur.draw(context);
                }

                lastTime = now;
            }
        };
        timer.start();

        scene.setOnKeyPressed((e) -> {
            // Si l'une des quatre touches espace, haut, droite ou gauche est appuyée, la partie commence si elle ne
            // l'était pas déjà et quitte le mode de pause si elle était déjà sur pause.
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.UP || e.getCode() == KeyCode.RIGHT
                    || e.getCode() == KeyCode.LEFT) {
                if (controleur.getAvantPartie()) {
                    controleur.setAvantPartie(false);
                    controleur.setFinPartie(false);
                }
                if (controleur.getPause()) {
                    controleur.setPause(false);
                }
            }
            switch (e.getCode()) {
                case ESCAPE:
                    // Met fin au programme
                    Platform.exit();
                    break;
                case RIGHT:
                    controleur.setAllerDroite(true);
                    controleur.setRegardeDroite(true);
                    break;
                case LEFT:
                    controleur.setAllerGauche(true);
                    controleur.setRegardeDroite(false);
                    break;
                case SPACE:
                case UP:
                    if (controleur.getParterre()) {
                        controleur.setSaute(true);
                    }
                    break;
                case T:
                    // Si la partie n'est pas en mode debug, elle le devient et les Text pour le mode debug sont créés.
                    // Sinon, si la partie est en mode debug, elle en sort et les Text pour le mode debug sont
                    // supprimés.
                    if (!controleur.getDebug()) {
                        controleur.setDebug(true);
                        for (int i = 0; i <= 4; i++) {
                            vBox.getChildren().add(new Text());
                        }
                    } else {
                        // Supprime les Text pour le mode debug
                        controleur.setDebug(false);
                        vBox.getChildren().clear();
                    }
                    break;
                case P:
                    // Met la partie sur pause si elle ne l'est pas déjà et si elle n'est pas en mode debug
                    if (!controleur.getPause() && !controleur.getDebug()) {
                        controleur.setPause(true);
                    } else if (controleur.getPause() && !controleur.getDebug()) {
                        controleur.setPause(false);
                    }
                    break;
                case R:
                    // Met fin à la partie
                    controleur.setFinPartie(true);
            }

        });
        scene.setOnKeyReleased((e) -> {
            switch (e.getCode()) {
                case RIGHT:
                    controleur.setAllerDroite(false);
                    break;
                case LEFT:
                    controleur.setAllerGauche(false);
                    break;
            }
        });

        primaryStage.setResizable(false);
        primaryStage.setTitle("High Sea Tower");
        primaryStage.getIcons().add(new Image("jellyfish1.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}