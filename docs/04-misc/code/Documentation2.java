/**
 * This is the definition of the annotation with its fields
 */
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

/** 
 * Now we can use the annotation ClassDocumentation
 * to document, in a uniform way, every class in the project
 */

class A{

    // This kind of annotation cannot be used on methods!
    @ClassDocumentation(
     author = "Author 1",
     date   = "01/01/2022",
     currentRevision = 2,
    developers = {"Developer1", "Developer2"}
    )
    int m(){
        return 3;
    }
}


@ClassDocumentation(
 author = "Author 2",
 date   = "01/01/2022",
developers = {"Developer3", "Developer2"}
)
class B{
}

@ClassDocumentation(
 author = "Author 1",
 date   = "01/01/2022",
developers = {"Developer2"}
)


public class Documentation2{
    @Override
    public String tostring(){return "a";}
	public static void main(String arg[]){
	}
}
