import java.util.*;
public class ListeEnseignant { 

    private List<Enseignant> personnel = new ArrayList<>(); 
    public List<Enseignant> getPersonnel() { 
        return personnel;
    }
    public void addEnseignant(Enseignant enseignant) { 
        this.personnel.add(enseignant);
    }
}
