import java.util.*;
abstract class Animal{
    private float weight;
    public Animal(float weight){
        this.weight = weight ;
    }
    public abstract String sound();
    
    @Override
    public String toString(){
        return this.sound();
    }

    public final float getWeight(){
        return this.weight;
    }
}

class Dog extends Animal{
    public Dog(float weight){
        super(weight);
    }

    @Override
    public String sound(){
        return "auf-auf";
    }
}
class Cat extends Animal{
    public Cat(float weight){
        super(weight);
    }

    @Override
    public String sound(){
        return "miau";
    }
}

class AnimalTest{
    public static void main(String arg[]){
        List<Animal> L = new ArrayList<>();
        L.add(new Dog(5));
        L.add(new Cat(1));

        for (Animal a :  L)
            System.out.println(a); // Polymorphic call
    }
}


        
