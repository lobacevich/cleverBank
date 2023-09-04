package by.clevertec.test.lobacevich.bank.di;

import by.clevertec.test.lobacevich.bank.exception.ClassScannerException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * класс, который находит все классы проекта
 */
public class ClassScanner {

    private ClassScanner() {
    }

    /**
     * рекурсивно обходит все каталоги и файлы проекта, выбирая файлы, являющиеся классами и создает из них классы
     * @param path путь, по которому происходит поис классов
     * @return список всех найденных классов
     */
    public static List<Class<?>> getClassList(String path) {
        List<Class<?>> classes = new ArrayList<>();
        File file = new File(path);
        if (file.isFile() && file.getPath().endsWith(".java")) {
            classes.add(createClassFromPath(file.getPath()));
        } else if (file.isDirectory() && file.listFiles() != null) {
            for (File f : file.listFiles()) {
                classes.addAll(getClassList(f.getPath()));
            }
        }
        return classes;
    }

    /**
     * на основании пути к файлу получает название класса и формирует из него сам класс
     * @param path путь к файлу класса
     * @return готовый класс
     */
    private static Class<?> createClassFromPath(String path) {
        try {
            return Class.forName(path.replace(".\\src\\main\\java\\", "")
                    .replace(".java", "")
                    .replace("\\", "."));
        } catch (ClassNotFoundException e) {
            throw new ClassScannerException("Can't create class");
        }
    }
}
