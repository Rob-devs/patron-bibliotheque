package ul.miage.patron.controller.exemplaires;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ul.miage.patron.controller.oeuvres.ControllerOeuvre;
import ul.miage.patron.database.helpers.HelperExemplaire;
import ul.miage.patron.model.Exemplaire;
import ul.miage.patron.model.Oeuvre;
import ul.miage.patron.model.enumerations.EtatExemplaire;

public class ControllerAddExemplaire {
    @FXML
    Label lblTitre;
    
    @FXML
    Button btnConfirm, btnCancel;

    @FXML
    ChoiceBox<EtatExemplaire> cbEtatExemplaire = new ChoiceBox<EtatExemplaire>();

    private Stage popupStage;
    private Stage parentStage;

    Oeuvre currentOeuvre = null;

    public void fillInfos(){
        lblTitre.setVisible(true);
        lblTitre.setText(currentOeuvre.getTitre());
    }

    // Insérer un exemplaire
    public void insertExemplaire() {
        
        Exemplaire exemplaire = new Exemplaire(
            0,
            cbEtatExemplaire.getValue(),
            currentOeuvre
        );
        HelperExemplaire helperExemplaire = new HelperExemplaire();
        helperExemplaire.insertExemplaire(exemplaire);
    }

    public void fillCbEtatExemplaire(){
        cbEtatExemplaire.getItems().addAll(EtatExemplaire.values());
        cbEtatExemplaire.setValue(cbEtatExemplaire.getItems().get(0));
    }

    public void confirmAdd(){
        insertExemplaire();

        ControllerOeuvre controllerOeuvre = new ControllerOeuvre();
        controllerOeuvre.reloadListView();

        popupStage.close();

        if(parentStage != null){
            parentStage.show();
        }
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setCurrentOeuvre(Oeuvre currentOeuvre) {
        this.currentOeuvre = currentOeuvre;
    }
}
