package ul.miage.patron.controller.emprunts;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import ul.miage.patron.database.helpers.HelperEmprunt;
import ul.miage.patron.database.helpers.HelperExemplaire;
import ul.miage.patron.model.actions.Emprunt;
import ul.miage.patron.model.enumerations.EtatEmprunt;
import ul.miage.patron.model.enumerations.EtatExemplaire;

public class ControllerRendre {
    @FXML ChoiceBox<EtatExemplaire> cbEtatExemplaire = new ChoiceBox<EtatExemplaire>();

    @FXML
    Button btnConfirm, btnCancel;

    private Emprunt selectedEmprunt;

    private Stage popupStage;
    private Stage parentStage;

    @FXML
    public void initialize(){

    }

    public void rendre(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateRenduReelle = LocalDate.parse(LocalDate.now().format(formatter), formatter);
    
        selectedEmprunt.setDateRenduReelle(dateRenduReelle);
        HelperEmprunt helperEmprunt = new HelperEmprunt();
        selectedEmprunt.setEtat(EtatEmprunt.TERMINE);
        helperEmprunt.rendreExemplaire(selectedEmprunt);

        HelperExemplaire helperExemplaire = new HelperExemplaire();
        helperExemplaire.switchDisponible(selectedEmprunt.getExemplaire());

        if(cbEtatExemplaire.getValue() != selectedEmprunt.getExemplaire().getEtat()){
            selectedEmprunt.getExemplaire().setEtat(cbEtatExemplaire.getValue());
            helperExemplaire.updateEtat(selectedEmprunt.getExemplaire());
        }
    }

    public void fillCbEtatExemplaire(){
        cbEtatExemplaire.getItems().addAll(EtatExemplaire.values());
        cbEtatExemplaire.setValue(selectedEmprunt.getExemplaire().getEtat());
    }

    public void confirmAdd(){
        rendre();
        popupStage.close();
    }

    public void setSelectedEmprunt(Emprunt selectedEmprunt) {
        this.selectedEmprunt = selectedEmprunt;
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void close() {
        // Close the stage (pop-up window)
        if (popupStage != null) {
            popupStage.close();
        }
    }
}
