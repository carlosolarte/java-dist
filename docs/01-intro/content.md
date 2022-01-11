
## Distributed Java Programming (G4INFPDJ)

--- 
Carlos Olarte

email: <olarte@lipn.univ-paris13.fr>

---
### About this course
#### CMs: 12h
- I will assume (good) knowledge of programming
- Some details are left for further self study

#### TDs: 24h
- (Hopefully) in a lab
- IDEs: Eclipse or Netbeans
- My favorite... vim ! ;-)

---
### About this course
- __CM1__: Introduction, inheritance, functional programming, streams. 
- __CM2__: Threads
- __CM3__: Network programming
- __CM4__: Annotations and tools
---
### About this course
#### Grades

1. Moderate size project (small distributed application). 
2. Two exams 

 N = (2 * exam + project)/3
 ---
### About this course

Three principal domains: 
- __Java programming__: Inheritance, functional programming, manipulation of streams.
- __Distributed programming__: Threads, network resources and RMI. 
- __Structure of distributed systems__: in classes, machines, etc. 

---
### Bibliography 
- Java Network Programming: Developing Networked Applications, by Elliotte Rusty Harold. 
- Java [Documentation](https://docs.oracle.com/en/java)
---
## CM 1
---
### CM 1
1. Recalling Object Programming Concepts
2. Inheritance 
3. Functional programming in Java
4. Streams 
---
### Classes

Class = Structure + Methods

---
### Classes

```java
import java.util.*;
public class Person {
    private String name;
    protected List < String >  othernames;
    public transient String company ;
    private static final String companyExp 
       = new String("unknown");

    public Person(Object o){
        assert o instanceof String ;
        String name = (String) o;
        this.name = (String) o;
    }

    public void hello(){
        System.out.println("Hi " + this.name);
    }

    public static void main(String arg[]){
        Person P = new Person("carlos");
        P.hello();
        Person P2 = new Person(3);
    }
}

```
Unnecessarily complicated... but:

---
### Classes
#### Attributes (Structure)

```java
import java.util.*;
public class Person {
    private String name;
    protected List < String >  othernames;
    public transient String company ;
    private static final String companyExp 
      = new String("unknown");
```
---
### Classes
#### Types
- _Basic types_ : `byte, int, boolean, char, float, double, long`
- __Parametrized types__: `List < String >`

---
### Classes
#### Access modifiers

- None: the field/method is available to any class in the same package. 
- _Private_: Only accessible inside the same class
- _Protected_: Accessible by subclasses
- _Public_ : Accessible everywhere 

>> __Encapsulation__: Restricting access to protect and hide the state of objects. 

---
### Classes
#### Getters and Setters

```java
private String address ;
public void setAddress(String address){ 
    this.address = address ;
}

pubic String getAddress(){
    return this.address;
}
```
* Fields should be `private`
* read/write control on field values
* easier for logging, security 
* getters and setters to be systematically used!

---
### Classes
#### Static members
- Accessible without instantiating the class
- Known also as class variables 

```java
    private static final String companyExp = 
       new String("unknown");
    ...

    System.out.println(Person.companyExp);
```

Alternatively
```java
    private static final String companyExp;

    static{
        companyExp= new String("unknown");
    }
``` 
---
### Classes
#### More variables modifiers. 
- _final_: Immutable after instanciation 
- _volatile_: Synchronization and memory order (CM2)
- _transient_: Non serializable (CM3)

---
### Classes
#### Constructors

```
public Person(Object o){
     ...
}
```
- Initialization of attributes 
- _Object_: The base class in the Java's class hierarchy
>> Methods such as `toString`, `equals`, `wait`, `notify`, etc are defined in `Object` (and __overridden__ by subclasses). 

---
### Classes
#### Main method

```java
    public static void main(String arg[]){
        ...
    }
```
Instantiating objects and calling methods: 
```java
Person P = new Person("carlos");
P.hello();
```
---
### Assertions 

```
  assert o instanceof String ;
```
- Check a boolean condition and rise an error if it evaluates to false. 
- The option `-ea` (enable assertions) must be used when executing the virtual machine. 
- _instanceof_: Checking if an object is an instance of a given class. 

---

---
### Inheritance 
- Java does not support multiple inheritance 

```java
class B extends A{
    ...
}
```

_BUT_ it is possible to implement several interfaces
```java
class B extends A implements X,Y {
    ...
}
```
---
### Abstract classes
```java
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
```
Some methods are implemented and others are `abstract`. 
---
### Abstract classes
```java
class Dog extends Animal{
    public Dog(float weight){
        super(weight);
    }

    @Override
    public String sound(){
        return "auf-auf";
    }
}
```
>> A class needs to be declared as `abstract` if it does not implement
all the inherited abstract methods. 

---
### Abstract classes

```java
class Cat extends Animal{
    public Cat(float weight){
        super(weight);
    }

    @Override
    public String sound(){
        return "miau";
    }
}
```

---
### Abstract classes and polymorphism 

```
class AnimalTest{
    public static void main(String arg[]){
        List< Animal > L = new ArrayList< >();
        L.add(new Dog(5));
        L.add(new Cat(1));

        for (Animal a :  L)
            System.out.println(a); // Polymorphic call
    }
}
```
---
### Inheritance 
#### Interfaces

```java
interface DigestListener {
    public void setDigest(byte[] digest);
}

public class DigestServer implements DigestListener { 
    ...
    @Override
    public void setDigest(byte[] digest) {
        ...
    }
    ...
}
```
---
### Exceptions and Error handling
```java
import java.util.NoSuchElementException;

class ExpandableArray {
    private Object[] data;
    private int size = 0;
    public static final int EXT = 10;

    public ExpandableArray(int cap) {
        data = new Object[cap];
    }

    public int size() {
        return this.size;
    }
```
---
### Exceptions and Error handling
```java
    public Object get(int i) throws NoSuchElementException {
        if (i<0 || i>= size)
            throw new NoSuchElementException();
        return this.data[i];
    }

    public void add(Object x) {
        if (size == data.length) {
            Object[] olddata = this.data;
            this.data = new Object[size + ExpandableArray.EXT];
            System.arraycopy(olddata, 0, this.data, 0, olddata.length);
      }
      this.data[this.size++] = x;
    }
```
- `throws E`: declare that the method may throw E

---
### Exceptions and Error handling
```java
    public static void main(String arg[]){
        ExpandableArray A = new ExpandableArray(5);
        Object x = A.get(0); //
```

Upss:
```
Exception in thread "main" java.util.NoSuchElementException
	at ExpandableArray.get(ExpandableArray.java:18)
	at ExpandableArray.main(ExpandableArray.java:33)
```
---
### Exceptions and Error handling

- Subclasses of `java.lang.RuntimeException` are _unchecked exceptions_. If not treated they propagate outside the method
- Subclasses of `java.lang.Exception` (except extensions of `RuntimeException`) are _checked exceptions_ that need to be reported/handled. 

---
### Exceptions and Error handling
```
class BadIndex extends Exception{
    public BadIndex(int i){
        super("Bad index: " + i);
    }
}

class ExpandableArrayEx {
    ...
    public Object get(int i) throws BadIndex {
        if (i<0 || i>= size)
            throw new BadIndex(i);
        return this.data[i];
    }

    public static void main(String arg[]){
        ExpandableArrayEx A = new ExpandableArrayEx(5);
        // Does not compile!
        A.get(0);
        ...
    }
}
```
Upss...
```
ExpandableArrayEx.java:34: error: unreported exception BadIndex; must be caught or declared to be thrown
        A.get(0);
```

---
### Exceptions and Error handling
```

class ExpandableArrayEx {
    ...
        public static void main(String arg[]){
        ExpandableArrayEx A = new ExpandableArrayEx(5);
        try{
            A.get(0);
            }
        catch(BadIndex E){
            System.out.println(E);
        }
    }
```
---
### Exceptions and Error handling

More generally:
```java
try{
    // Block try
}
catch (ExceptionType E1){
    // Executed if ExType is thrown 
}
...
catch (ExceptionType En){
    ...
}
finally{
    // Always executed
}
```

---
### Exceptions and Error handling
Next CMs:
- `IOException`
- `InterruptedException`
- ...
---
### Writing Programs
- 1 kind of object = 1 class
- 1 project = 1 executable = 1 set of classes (divided into packages)
- "ordinary" software => 1 project 
- client-server software => 2 projects
- 1 project =
  - 1 class Main (the main initializer) 
  - 1 class per kind of object
---
### Collections

Defining a simple class:
```java
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
```

---
### Collections
Storing several object of type `Enseignant`
```java
import java.util.*;
public class ListeEnseignant {

    private List < Enseignant > personnel = new ArrayList < >();
    public List< Enseignant > getPersonnel() {
        return personnel;
    }
    public void addEnseignant(Enseignant enseignant) {
        this.personnel.add(enseignant);
    }
}
```
---
### Collections
Alternatively, a static attribute can be used:

```java
public class Enseignant {
    private String nom;
    private int age;
    private float salaire;
    private static List < Enseignant > personnel = new ArrayList < >(); 
    ...

    public static void addEnseignant(Enseignant enseignant) { 
        Enseignant.personnel.add(enseignant);
    }
    ...
}
```
---
### Java 1.5

- Generic types
```java
List < String > L = new ArrayList < > () ;
```

- Note that `ArrayList` implements the interface `List`
```java
public interface List< E >
       extends Collection< E >
```

---
### Java 1.5

Iteration becomes easier:
```java
int v[] = new int[10];
for(int i=0; i < v.length ; i++)
    ... 
// New style
for (int i : v){
    ...
// Using iterators
List< String > C = new ArrayList < > ();
...
for(Iterator< String > I = C.iterator(); I.hasNext();){
    System.out.println(I.next());
    ...
}

```

---
### Java 1.5
- _Boxing_:  automatic conversion between primitive types and their associated classes, e.g. `int` and `Integer`).
```java
Integer I ;
int x = 4;
I = x ;
```

- _Enumerations_: 
```java
    enum Saison { PRINTEMPS, ETE, AUTOMNE, HIVER };
    ...
    for (Saison saison : Saison.values())
      System.out.println(saison);
```
- Variable number of arguments 
```java
 void method(int x, int y, String ... s){
     // s is an array of String
```
---
### Java 1.5
- Stubs for RMI dynamically generated (more on CM3)
- _Annotations_ : allows intermediary softwares (compilers, interpreters, environnements, ...) to test, verify or even add code. (more on CM4)

---
### Java 1.6
- JAX-WS : web services (CM3/4)
- JDBC : API to data bases (CM4)

---
### Java 1.8
- Lambda expressions (today!)
- Streams (today!)
- Security (CM4)

---
### Lambda Expressions and Streams

Programming actions in GUIs:
```java
btn.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            System.out.println("Hello World!");
        }
});
```
- Creates an anonymous class (_overriding_) the method `handle` from the class `EventHandler < ... >`
- Too complicated, right?

---
### Lambda Expressions and Streams
What about data processing:
 - _filter_ the list of people according to some criteria
 - _for each_ person compute some information
 - ...

 Should we write different methods for different criteria? 

---
### Lambda Expressions
Here a class implementing an interface
```java
interface Operator{
        public int op(int a, int b);
}

class Plus implements Operator{
    public int op(int a, int b){
        return a+b;
    }
}

class LambdaTest1{
    public static void main(String arg[]){
        Plus P = new Plus();
        System.out.println(P.op(2,3));
    }
}
```
---
### Lambda Expressions
Now using an anonymous class 
```java
interface Operator{
        public int op(int a, int b);
}

class LambdaTest2{
    public static void main(String arg[]){
        Operator Plus = new Operator(){
            public int op(int a, int b){
                return a + b;
            }
        };
        System.out.println(Plus.op(2,3));
    }
}
```
---
### Lambda Expressions
But the implementation of `op` is so simple... can we do better?
```java
interface Operator{
        public int op(int a, int b);
}

class LambdaTest3{
    public static void main(String arg[]){
        Operator Plus = (int a, int b) -> a + b ;
        Operator Minus = (int a, int b) -> a - b ;

        System.out.println(Plus.op(1,2));
        System.out.println(Minus.op(1,2));

    }
}
```
---
### Lambda Expressions and Streams
Consider the following method declared in class `Arrays`:
```java
public static < T > void sort(T[] a, Comparator< ? super T > c);
```
- `< ? super T >` means `T` or superclasses of `T`

`Comparator` is defined as follows:
```java
public interface Comparator<T>{
    int	compare(T o1, T o2);
    boolean	equals(Object obj);
}
```
- How can we use `Arrays.sort` ?
---
### Lambda Expressions and Streams
Defining an anonymous class:
```java
class LambdaTest4{
    public static void main(String arg[]){
        String[] L = new String[] {"car", "house", "building", "apple", "fruit"};
        Comparator<String> CLength = new Comparator <String> (){
            public int compare(String s1, String s2){
                return s1.length() - s2.length();
            }
        };

        Arrays.sort(L, CLength);
        System.out.println(Arrays.toString(L));
    }
}
```
---
### Lambda Expressions and Streams
Using lambdas! 
```java
class LambdaTest5{
    public static void main(String arg[]){
        String[] L = new String[] {"car", "house", "building", "apple", "fruit"};
        Arrays.sort(L, (s1,s2) -> s1.compareTo( s2));
        System.out.println(Arrays.toString(L));
        Arrays.sort(L, (s1,s2) -> s2.compareTo( s1));
        System.out.println(Arrays.toString(L));
        Arrays.sort(L, (s1,s2) -> s1.length() - s2.length()) ;
        System.out.println(Arrays.toString(L));
    }
}
```
---
### Streams
- Lazy structures! (elements are consumed when needed)
- Quite good for processing large amounts of data (without materializing it). 


---
### Streams
Let's consider a simple class storing grades:
```java
class Grade{
    private String name;
    private double grade ;

    public Grade(String name, double grade){
        this.name = name;
        this.grade = grade ;
    }

    public String toString(){
        return this.name  + ": " + this.grade;
    }
}
```
---
### Streams
And a list of grades
```java
class GradeStream{
    public static void main (String arg[]){
        List<Grade> L = new ArrayList<>();
        L.add(new Grade("one"  , 4.5 ));
        L.add(new Grade("two"  , 8.5 ));
        L.add(new Grade("three", 9.2 ));
        L.add(new Grade("four" , 10.0));
        L.add(new Grade("five" , 7.4 ));
        L.add(new Grade("six"  , 3.1 ));
        L.add(new Grade("seven", 9.1 ));
        ...
    }
}
```
---
### Streams
List all the grades above 5.0
```java
        Stream<Grade> S =
             L.stream()
              .filter((Grade G) -> G.getGrade() >= 5.0);

        // toArray materializes the stream!
        System.out.println(Arrays.toString(S.toArray()));
```
- _Filter_:  `Stream<T> filter(Predicate<? super T> predicate)`
- _Predicate_ `Interface Predicate<T>{ boolean test(T t);}`


---
### Streams
Without materializing the stream
```java
        L.stream()
              .filter((Grade G) -> G.getGrade() >= 5.0)
              .forEach( (Grade G) -> System.out.println(G))
              ;
```
- `forEach`: `void forEachOrdered(Consumer<? super T> action)`
- _Consumer_: `Interface Consumer<T>{void accept(T t); }`

---
### Streams
What about applying a function to each element?
```java
        L.stream()
            .map((Grade G) -> G.getGrade())
            .forEach( (Double n) -> System.out.println(n))
            ;
```

- _Map_: `Stream<R> map(Function<? super T,? extends R> mapper)`
- _Functional interface_: `Interface Function<T,R>{R apply(T t);}

---
### Streams
Some specialized Maps and aggragates
```java
        OptionalDouble R =
            L.stream()
            .mapToDouble((Grade G) -> G.getGrade())
            .max()
            ;
        System.out.println(R);
```
- `mapToDouble` returns a `DoubleStream`
- `DoubleStream` implements several functions as `average`, `sum`, `min`, etc. 


---
### Streams
Finally, functional programming in Java!

<img src="emoji.png" width=400>
---
### Lambda Expressions
See more [here](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
