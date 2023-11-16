package ul.miage.patron.controller;

import ul.miage.patron.database.helpers.HelperUsager;
import ul.miage.patron.model.Usager;

public class ControllerUsager {
    public void insertUsager(Usager usager){
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.insertUsager(usager);
    }

    public void updateUsager(Usager usager){
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.updateUsager(usager);
    }

    public void deleteUsager(Usager usager){
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.deleteUsager(usager);
    }

    public void selectUsager(){
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.selectUsager();
    }

    public void selectAllUsager(){
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.selectAllUsager();
    }
}
