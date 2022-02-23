/**
 * Definition of the annotation
 */

import java.lang.annotation.*;

@Target(ElementType.TYPE) // only on classes and interface
@Retention(RetentionPolicy.RUNTIME) //To be used on runtime
public @interface Run {
    String method() default "run";
}
