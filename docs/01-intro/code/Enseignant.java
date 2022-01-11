public class Enseignant {
    private String nom; 
    private int age; 
    private float salaire;

    public Enseignant(String nom, int age, float salaire){
        this.nom = nom; 
        this.age = age; 
        this.salaire = salaire;
    } 

    public String getNom() { return nom; }
    public int getAge() { return age; }
    public float getSalaire() { return salaire; }

    public void setSalaire(float salaire) {
        this.salaire = salaire ;
    }

    @Override
    public String toString() {
        return this.getNom() ;
    }
}
