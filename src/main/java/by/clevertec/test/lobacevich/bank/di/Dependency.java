package by.clevertec.test.lobacevich.bank.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * указываются поля, куда надо внедрить классы из контейнера. в случае, если класс реализует интерфейс,
 * необходимо указать его название
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dependency {
    String implementation() default "";
}
