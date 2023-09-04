package by.clevertec.test.lobacevich.bank.di;

import by.clevertec.test.lobacevich.bank.exception.DependenciesHandlerException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * класс, управляющий инверсией зависимостей
 */
public class DependenciesHandler {

    private DependenciesHandler() {
    }

    private static final Map<Class<?>, Object> MAP_BEANS = new HashMap<>();

    /**
     * класс, который внедряет зависимости
     * <p>
     *     класс, который управляет другими классами для внедрения зависимостей
     * </p>
     */
    public static void injectDependencies() {
        List<Class<?>> allClasses = ClassScanner.getClassList(".\\src\\main\\java");
        List<Class<?>> classes = findSingletonClasses(allClasses);
        List<Field> fields = findAnnotatedFields(allClasses);
        addBeansFromClasses(classes);
        addBeansFromFields(fields);
        setFieldsValues(fields);
    }

    /**
     * находит среди всех классов классы, аннотированные Singleton
     * @param classes все классы проекта, которые дает ClassScanner.getClassList
     * @return все классы, аннотированные Singleton
     */
    private static List<Class<?>> findSingletonClasses(List<Class<?>> classes) {
        return classes.stream().filter(cl -> cl.isAnnotationPresent(Singleton.class))
                .collect(Collectors.toList());
    }

    /**
     * находит поля, в которые надо внедрить зависимости, в списке всех классов
     * @param classes список всех классов проекта
     * @return список полей, аннотированных Dependency
     */
    private static List<Field> findAnnotatedFields(List<Class<?>> classes) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> cl : classes) {
            fields.addAll(Arrays.stream(cl.getDeclaredFields()).filter(f -> f.isAnnotationPresent(Dependency.class))
                    .collect(Collectors.toSet()));
        }
        return fields;
    }

    /**
     * применяет ко всем классам метод setMapBeans
     * @param classes классы, анноторованные Singleton
     */
    private static void addBeansFromClasses(List<Class<?>> classes) {
        classes.forEach(DependenciesHandler::setMapBeans);
    }

    /**
     * применяет ко всем классам, содержащим данные поля, метод setMapBeans
     * @param fields поля, анноторованные Dependency
     */
    private static void addBeansFromFields(List<Field> fields) {
        fields.stream().map(Field::getDeclaringClass).forEach(DependenciesHandler::setMapBeans);
    }

    /**
     * заполняет мапу MAP_BEANS значениями класс и объект класса
     * @param clazz принимает любой класс
     * @param <T> дженерик метода
     */
    private static <T> void setMapBeans(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            if (!MAP_BEANS.containsKey(clazz)) {
                MAP_BEANS.put(clazz, constructor.newInstance());
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new DependenciesHandlerException("Не могу создать объект класса");
        } catch (NoSuchMethodException e) {
            throw new DependenciesHandlerException("Не могу создать конструктор");
        }
    }

    /**
     * применяет ко всем полям метод setFieldValue
     * @param fields поля, в которые надо внедрит зависимости
     */
    private static void setFieldsValues(List<Field> fields) {
        fields.forEach(DependenciesHandler::setFieldValue);
    }

    /**
     * внедряет зависимость в поле
     * <p>
     *     ищет класс по названию класса, указанному в аннотации как имплементация, в иных случаях
     *     по названию поля
     * </p>
     * @param field поле, куда надо внедрить зависимость
     */
    private static void setFieldValue(Field field) {
        field.setAccessible(true);
        Object value;
        String implClassName = field.getAnnotation(Dependency.class).implementation();
        if (implClassName.isEmpty()) {
            value = MAP_BEANS.get(field.getType());
        } else {
            Optional<Class<?>> key = MAP_BEANS.keySet().stream().filter(x -> x.getSimpleName().equals(implClassName))
                    .findFirst();
            if (key.isPresent()) {
                value = MAP_BEANS.get(key.get());
            } else {
                throw new DependenciesHandlerException("Class not found in mapBeans");
            }
        }
        try {
            field.set(MAP_BEANS.get(field.getDeclaringClass()), value);
        } catch (IllegalAccessException e) {
            throw new DependenciesHandlerException("Error in injecting field value");
        }
    }
}
