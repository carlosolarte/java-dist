
## Distributed Java Programming (G4INFPDJ)
---
### CM 4: Miscellaneous 
1. Annotations (and a bit of reflection)
2. Databases 
3. RESTful Web Services
4. JavaDoc

---
### Annotations

See more at [Annotations](https://docs.oracle.com/javase/tutorial/java/annotations/)

---
### Annotations

We have already seen one annotation:

`@Override` : quite useful!
- Avoid silly _mistakes_
- Better __"documentation"__ of the code
- Used by the Java Compiler and other applications
---
### Annotations
More generally, annotations may have _elements_ and _values_:

```java
@Annotation( 
   att1 = "val1", 
   att2="val2")
```

And the "special" element `value` can be omitted: 
```java
@Annotation(value="val1")

==

@Annotation("val1")
```

---
### Annotations

Annotations can "decorate" different parts of the code:
- _Classes_: 
- _Fields_:
- _Methods_:
- And even types:

```
@Readonly List< @Readonly T > { ... }

throws @Critical TemperatureException { ... }

```
---
### Annotations
#### Documenting code

See [Example](./code/Documentation.java)
---
### Annotations
- `@Deprecated`: signals that the current method should not be used in future versions.  
```java
/**
 * @deprecated
 * This method should not be longer used since it is unsafe...
 */
 @Deprecated
 publiv void m() { ... }
```
---
### Annotations
- `@Override`: The definition of the method overrides the one in the superclass. 

```java
// typical mistake
public String tostring(){...}
```

... but the compiler does not complain. 

---
### Annotations
- `@Override`: The definition of the method overrides the one in the superclass. 

```java
// typical mistake
@Override
public String tostring(){...}
```
... now the compiler says

```
error: method does not override or implement a method from a supertype
```

---
### Annotations

- `@FunctionalInterface` : the compiler (and the programmer!) is informed that the
  interface will be used as a functional interface. 
- A functional interface offers one method (but also some defaults methods). 

See [Example](./code/FInter.java)
---
### Annotations

All the operations on streams are decorated with `@FunctionalInterface`
```java
@FunctionalInterface
public interface Consumer< T >{
    void    accept(T t);
    // defaults...
}

@FunctionalInterface
public interface Function< T,R >{
 R  apply(T t);
 // defaults...
}

// The class Stream
void forEach(Consumer< ? super T > action)...
Stream< R > map(Function< ? super T,? extends R > mapper);
```

---
### Annotations

Meta-annotations
- Annotations that apply to other annotations
- For instance, it is possible to specify that the annotation can be used only for classes:
```java
import java.lang.annotation.*;
// This annotation cannot be used, e.g., for methods
@Target(ElementType.TYPE)
@interface ClassDocumentation {
   String author();
   String date();
   int currentRevision() default 1;
   String lastModified() default "N/A";
   String[] developers();
}
```

See [Example](./code/Documentation2.java)

---
### Annotations and Reflection API

- Reflection allows us to _introspect_ and manipulate the program 
- We can "discover" the classes on run time 
- We can also list the methods available for a class and call them
---
### Annotations and Reflection API
Assume that we have 
- Several classes, each of them implementing a __task__
- We need to run all those tasks in parallel
- More classes (implementing a task) might be added to the project in the future

> The classes do not implement `Runnable`, for the sake of the example. 

Example adapted from [here](http://blog.marcinchwedczuk.pl/creating-and-using-adnotations-in-java)

---
### Annotations and Reflection API

First an interface defining the attributes of the annotation
```java
@Target(ElementType.TYPE) // only on classes and interfaces 
@Retention(RetentionPolicy.RUNTIME) //To be used on runtime
public @interface Run {
    String method() default "run";
}
```
---
### Annotations and Reflection API

We then define some classes annotated with `Run`

```java
@Run
public class Task1 {
    public Task1(){}

    public void run(){
        ...
    }
}

@Run(method="execute")
public class Task2 {
    public Task2(){}

    public void execute(){
        ...
    }
}
```
---
### Annotations and Reflection API

We can generate the list of all the classes in the current directory.
We filter those that has been annotated with `Run`

```java
public static List<Class> allRunClasses(){
    List<Class> L = new ArrayList<>() ;
    File curDir = new File("./");
    for (File F : curDir.listFiles()){
        try{
            if (F.isFile()){
                String fname = F.getName();
                if (fname.endsWith(".class")){
                    String className = fname.replace(".class", "");
                    Class C = Class.forName(className);

                    if(C.getAnnotation(Run.class) != null)
                        L.add(C);
                }
            }
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
    return L;
}
```

---
### Annotations and Reflection API
With each `Class`, we create an object an invoke the needed method

```java
public static void runAll(){
    List< Class > L = Execute.allRunClasses();
    for (Class C : L){
        try{
            // Creating an instance of the class
            Object O = C.newInstance();
            Run runClass = (Run) C.getAnnotation(Run.class);
            // Obtaining the method to be run
            Method M = C.getMethod(runClass.method());
            // Executing the method in a thread
            Thread T = new Thread(){
                @Override
                public void run(){
                    try{
                        M.invoke(O);
                    }
                    catch(Exception E){
                        E.printStackTrace();
                    }
                }
            };
            T.start();
        }
        catch(Exception E){
            E.printStackTrace();
        }
    }

}
```
---
### JDBC and Databases
- `java.sql`: accessing and manipulating databases
- Different _drivers_ can be used to access different DBMS
- JDBC Api 

See more [here](https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html)

---
### JDBC and Databases

1. A JDBC connector is needed
2. Example with SQLite
3. Changing the DBMS is almost immediate (modulo discrepancies of SQL)

See the examples [here](./code/db/Connect.java)

---
### REST
#### REpresentational State Transfer

More details [here](https://docs.oracle.com/javaee/6/tutorial/doc/gijqy.html)

---
### REST

- Architectural style that specifies constraints, such as the uniform interface
- Enable services to work on the web
- Stateless communication protocol : HTTP
---
### REST

Key principles:
- _URI_ : The server exposes a set of resources identified by URIs
- _Uniform interface_ : HTTP methods and requests (GET, POST, DELETE, PUT). 
- _Self descriptive messages_ : (XML/JSON). 
- _Stateful interactions through hyperlinks_ : every interaction is stateless. Exchange state  via, e.g., cookies and hidden fields. 

---
### REST


We have two alternatives: synchronous or asynchronous requests. 

---
### REST

Example of [synchronous](./code/restful/Sync.java) request. 

---
### REST

Example of  [asynchronous](./code/restful/Async.java) request. 


---
### REST

Here another example of a RESTful API ([countries](https://restcountries.com/)):
- Many parameters to ease queries
- Here some information about [France](https://restcountries.com/v3.1/name/france)
-  Countries using the [Euro](https://restcountries.com/v3.1/currency/euro)
-  All the countries but showing only some [fields](https://restcountries.com/v2/all?fields=name,capital)

---
### REST

There are some libraries allowing to map JSON objects to Java Objects (or Maps). 

- [Here](./code/restful/Country.java)  an example using [gson](https://github.com/google/gson)
- It is also possible to use [jackson](https://github.com/FasterXML/jackson)

---
### JavaDoc

Standard way of documenting Java code

See [Javadoc](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html)

