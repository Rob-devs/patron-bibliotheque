package ul.miage.patron.controller.backoffice.oeuvres;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ul.miage.patron.model.enumerations.GenreOeuvre;
import ul.miage.patron.model.objets.Oeuvre;

public class ControllerUpdateOeuvre {
    @FXML
    TextField tfTitre, tfAuteur;

    @FXML
    DatePicker dpDate;

    @FXML
    ChoiceBox<GenreOeuvre> cbGenreOeuvre = new ChoiceBox<GenreOeuvre>();

    private Stage popupStage;
    private Stage parentStage;

    Oeuvre currentOeuvre = null;

    public void fillInfos() {
        tfTitre.setText(currentOeuvre.getTitre());
        tfAuteur.setText(currentOeuvre.getAuteur());
        dpDate.setValue(currentOeuvre.getDatePublication());
        cbGenreOeuvre.setValue(currentOeuvre.getGenre());
    }

    public void updateOeuvre(Oeuvre oeuvre) {
        oeuvre.setTitre(tfTitre.getText());
        oeuvre.setAuteur(tfAuteur.getText());
        oeuvre.setDatePublication(dpDate.getValue());
        oeuvre.setGenre(cbGenreOeuvre.getValue());
    }

    public void close() {
        // Close the stage (pop-up window)
        if (popupStage != null) {
            popupStage.close();
        }
    }
}
