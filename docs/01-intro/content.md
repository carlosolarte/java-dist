
## Distributed Java Programming

--- 
Carlos Olarte

email: <olarte@lipn.univ-paris13.fr>

---
### About this course
#### CMs: 12h
- I will assume (good) knowledge of programming
- Some details are left for further self study

#### TPs: 24h
- IDEs: Eclipse or Netbeans
- My favorite... vim ! ;-)

<!-- [Question](https://app.wooclap.com/events/ASZRJB/questions/659fd8f1e86394d958e6bc96) -->

---
### About this course
- __CM1__: Introduction, inheritance, functional programming in Java, streams. 
- __CM2__: Threads
- __CM3__: Network programming
- __CM4__: Annotations and tools
---
### About this course
#### Grades

1. Moderate size project (small distributed application). 
2. Quizzes 

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
1. Recalling object oriented programming concepts
2. Inheritance and polymorphism 
3. Functional programming in Java
4. Streams 
---
### Classes

Class = Attributes (Data) + Methods (Behavior)

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

    public Person(Object o){ // Really? it can be a string ;-)
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
<!-- [Question](https://app.wooclap.com/events/ASZRJB/questions/659fda04dfed03840e20e1fc) -->
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
* Fields should be `private` (in general)
* Reading/writing on field values must be controlled 
* Easier for logging and security 
* Getters and setters must be systematically used!

---
### Classes
#### Static members
- Accessible without instantiating the class
- Known also as _class variables_ 

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
>> Methods such as `toString`, `equals`, `wait`, `notify`, etc are defined in
`Object` (and __overridden__ by subclasses). 

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
### Method toString
- Converting objects to strings 
- Quite useful for "printing" the state of the object (and debugging)

```java
@Override
public String toString(){
    return "Name= " + this.getName() + " email: " + this.getEmail();
}
```
---
### Equals

What is the result of 

```java
String s1 = "hello";
String s2 = s1 ;
String s3 = "hello" ;

// s1 == s2 ?
// s1 == s3 ?
```
<!-- [Question](https://app.wooclap.com/events/ASZRJB/questions/659fdac9fb3129089515cded) -->
---
### Equals
- `==` : The same object in memory
- `.equals()` : comparing based on the content
- The following method in class `Object` must be overwritten:

```java
public boolean equals(Object obj)
```

---
### Equals

An example:
```java
public boolean equals(Object obj) {
  if (!(obj instanceof Person)) {
    return false;
  }
  Person p = (Person) obj;
  // Assuming that ID is unique 
  return this.getId() == p.getId();
}
```

---
### Cloning 

What is the result of 
```java
Person p1 = new Person("carlos");
Person p2 = p1 ;
p2.setAddress("...");
```

Does `p1` change?

<!-- [Question](https://app.wooclap.com/events/ASZRJB/questions/659fdb1afb3129089515f105) -->

---
### Cloning 

- The method `clone` must be overridden 
- The class must implement the interface `Cloneable` (more on this later)

```java
Person p1 = new Person("carlos");
Person p2 = p1.clone() ;
```
The contract:
```java
- x.equals(x.clone())
- x != x.clone()
- x.getClass() == x.clone().getClass()
```

---
### Cloning 
```java
class Person implements Cloneable{
  ...
  @Override
  public Object clone() {
   Person leClone = null;
   try {
    leClone = (Person) super.clone();
   }
   catch (CloneNotSupportedException e) {
     throw new InternalError("The class does not support cloneable!");
  }
  // Clonning other attributes (if needed)
  return leClone;
 }
}
```

---
### Inheritance 

Why do we need it?

---
### Inheritance 

Why do we need it?

* Build classes from existing ones (specialization)
* Reuse the code of the superclass
* Define common abstractions

---
### Inheritance 
- Java does not support _multiple inheritance_ 

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
### Inheritance 

Is this problematic?

```
interface X{
    int m();
}
interface Y{
    int m();
}
class Z implements X,Y{
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

Why `final` in the method `getWeight()`?
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
>> A class needs to be declared `abstract` if it does not implement
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
### Polymorphism 

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
>> A non-abstract class must implement all the methods declared in the
interfaces it implements. 

---
### Exceptions and error handling
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
        Object x = A.get(0); // upss...
```

Upss:
```
Exception in thread "main" java.util.NoSuchElementException
	at ExpandableArray.get(ExpandableArray.java:18)
	at ExpandableArray.main(ExpandableArray.java:33)
```
---
### Exceptions and Error handling

- Subclasses of `java.lang.RuntimeException` are _unchecked exceptions_ (no need
to declare/handle them). 
- Subclasses of `java.lang.Exception`
(except extensions of `RuntimeException`) are _checked exceptions_ that need to
be reported/handled. 
- If an exception is not treated, it  propagates outside the method 

---
### Exceptions and error handling
```
class BadIndex extends Exception{
    public BadIndex(int i){
        super("Bad index: " + i);
    }
}

class ExpandableArray {
    ...
    public Object get(int i) throws BadIndex {
        if (i<0 || i>= size)
            throw new BadIndex(i);
        return this.data[i];
    }

    public static void main(String arg[]){
        ExpandableArrayEx A = new ExpandableArrayEx(5);
        // This program does not compile!
        A.get(0); // Problem!
        ...
    }
}
```
Upss (__unreported exception BadIndex__)...
```
ExpandableArrayEx.java:34: error: unreported exception BadIndex; must be caught or declared to be thrown
        A.get(0);
```

---
### Exceptions and error handling
```

class ExpandableArray {
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
    // Executed if an instance (subclasses included) 
    // of ExceptionType is thrown 
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
### Exceptions and error handling
Next CMs:
- `IOException`
- `InterruptedException`
- `HTTPException` 
- ...
---
### Writing programs
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

    private List < Enseignant > personnel = 
                 new ArrayList < >();
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
    private static List < Enseignant > personnel = 
      new ArrayList < >(); 
    ...

    public static void addEnseignant(Enseignant enseignant) { 
        Enseignant.personnel.add(enseignant);
    }
    ...
}
```
---
### Generic Types (From Java 1.5)

```java
List < String > L = new ArrayList < > () ;
```

- Note that `ArrayList` implements the interface `List`
```java
public interface List< E >
       extends Collection< E >
```

---
### Generic Types

```java
public class ArrayList< E > extends AbstractList< E >
implements List< E >, RandomAccess, Cloneable, Serializable{
    ArrayList(Collection< ? extends E > c){...}
    E get(int x);
    boolean add(E e);
    boolean	removeAll(Collection< ? > c);
    ...
}
```

---
### Generic Types
Assume that `A` is a subclass of `B`:

-  Is `List< A > ` a subclass of `List< B >`? 
-  An object of type `A` can be added in a `List< B >`?
-  An object of type `B` can be added in a `List< A >`?
-  Is `ArrayList < A >` a subclass of `List < A >`?

<!-- [Question](https://app.wooclap.com/events/ASZRJB/questions/659fddd226fca2b2cfd71250) -->

---
### Generic Types: Wildcards 
- `?` : unbounded wildcard
- `? extends C` : wildcard with an upper bound (all subtypes of `C`)
- `? super C` : wildcard with lowerbound (all supertypes of `C`)

---
### Generic Types: Wildcards 
Consider a method 
```java
void dosomething(List< ? extends C > L)
```
- What are valid parameters?
- Can `L.add(x)` be called inside `dosomething` ?
---

### Generic Types: Wildcards 
```java
class A{ }

class B extends A{ }

class C extends B{ }

public class Joker{
    public void m1 (List< ? extends B > L){ }
    public void m2 (List< ? super B > L){ }
    public void m3 (List< ? > L){ }

    public static void main(String arg[]){
        List< A > la = new ArrayList< >();
        List< B > lb = new ArrayList< >();
        List< C > lc = new ArrayList< >();
        Joker J = new Joker();
        J.m1(lb); J.m1(lc);
        J.m2(lb); J.m2(la);
        // J.m1(la); // error!!
        // J.m2(lc); // error!!
        J.m3(la); J.m3(lb); J.m3(lc);
    }
}
```

---
### Iteration

Iteration becomes easier from Java 1.5
```java
Interface Iterable< T >{
     Iterator< T > iterator() ;
     ...
}

Interface Iterator< E >{
    boolean hasNext();
    E next(); 
    ...
}
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
- Stubs for _RMI_ dynamically generated (more on CM3)
- _Annotations_ : allow intermediary softwares (compilers, interpreters,
environnements, ...) to test, verify or even add code (more on next CMs)

---
### Java 1.6
- JAX-WS : web services (CM4)
- JDBC : API to data bases (CM4)

---
### Java 1.8
- Lambda expressions (today!)
- Streams (today!)
- Security (CM4)

---
### Lambda expressions and streams

Programming actions in GUIs:
```java
interface EventHandler< T extends Event >{
    void handle(T event);}
```

How to use it?
```java
btn.setOnAction(new EventHandler< ActionEvent >() {
        public void handle(ActionEvent event) {
            System.out.println("Hello World!");
        }
});
```
- Creates an anonymous class _implementing_ `EventHandler` and 
_overriding_ the method `handle`.
- Too complicated, right?

---
### Lambda expressions
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
### Lambda Expressions
_General form_
```
 (x1,...,xn) -> exp
```

---
### Lambda expressions and streams
Consider the following method declared in class `Arrays`:
```java
public static < T > void sort(T[] a, 
                              Comparator< ? super T > c);
```

`Comparator` is defined as follows:
```java
public interface Comparator< T >{
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
      Comparator< String > CLength = 
        new Comparator < String > (){
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
### Intermezzo... `default` 

The interface `Compartor` __implements__ some `default` methods:
```java
default Comparator< T > reversed(){ ... }
``` 
Here an example:
```java
Comparator< String > C = (s1, s2) -> s1.length() - s2.length();
Arrays.sort(L, C.reversed());
System.out.println(Arrays.toString(L));
```

- Interface (abstract) methods __cannot have body__.
- But `default` methods can be implemented. 

---
### Streams
- __Lazy structures__! (elements are consumed when needed)
- Quite good for processing large amounts of data (without storing it in memory).
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
        List< Grade > L = new ArrayList<>();
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
Stream< Grade > S =
     L.stream()
      .filter((Grade G) -> G.getGrade() >= 5.0);

// Lazy: nothing is done at this point

// toArray materializes the stream
System.out.println(Arrays.toString(S.toArray()));
```
These are the signatures for _Filter_ and _Predicate_:  

```java
Stream< T > filter(Predicate< ? super T > predicate)
interface Predicate< T >{ boolean test(T t);}
```
---
### Streams
Without materializing the stream
```java
L.stream()
      .filter((Grade G) -> G.getGrade() >= 5.0)
      .forEach( (Grade G) -> System.out.println(G))
      ;
```
The signatures for _forEach_ and _Consumer_ are:
```java
void forEachOrdered(Consumer< ? super T > action)
interface Consumer< T >{void accept(T t); }
```
---
### Streams
What about applying a function to each element?
```java
L.stream()
    .map((Grade G) -> G.getGrade())
    .forEach( (Double n) -> System.out.println(n))
    ;
```
Here the signatures for _Map_ and _Function_:
```java
Stream< R > map(Function< ? super T,? extends R > mapper)
interface Function< T,R >{R apply(T t);}
```

---
### Streams
Some specialized Maps and aggregates
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
- _Functional interfaces_ provide target types for lambda expressions.
- The informative annotation `@FunctionalInterface` is recommended when a functional interface is declared
- The above mentioned interfaces are all functional interfaces. 

```java
@FunctionalInterface
public interface Predicate < T >
```
---

### Homework...
Consider this method in the interface `Stream`:
```java
Optional < T > reduce(BinaryOperator < T > accumulator)
```
- What is `Optional`
- What does `reduce` do?
- What is `BinaryOperator` ? 
- Can you use `reduce` in a simple example?

---

### Streams
Finally, functional programming in Java!

<img src="emoji.png" width=400>
---
### Lambda expressions
See more [here](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
