package net.beast462.int2204.mimir.Core.Annotations.SQL;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    enum Types {
        Integer,
        Varchar,
        Text,
        Real,
        Null,
        Blob,
        Unspecified
    }

    String name() default "";
    int size() default -1;
    Types type() default Types.Unspecified;
    boolean primary() default false;
    boolean unique() default false;
}
