package by.clevertec.test.lobacevich.bank.di;

import by.clevertec.test.lobacevich.bank.exception.ClassScannerException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassScanner {

    private ClassScanner() {
    }

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
