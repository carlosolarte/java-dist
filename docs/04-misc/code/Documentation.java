/**
 * This is the definition of the annotation with its fields
 */


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

@ClassDocumentation(
 author = "Author 1",
 date   = "01/01/2022",
 currentRevision = 2,
developers = {"Developer1", "Developer2"}
)
class A{
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


public class Documentation{
    @Override
    public String tostring(){return "a";}
	public static void main(String arg[]){
	}
}
