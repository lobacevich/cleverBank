package by.clevertec.test.lobacevich.bank.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * классы, имеющие данную аннотацию помещаются в контейнет для дальнейшего внедрения в поля других классов
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Singleton {
}
