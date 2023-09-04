package by.clevertec.test.lobacevich.bank.util;

import lombok.Cleanup;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

/**
 * класс, который считывает файл config.yml и возвращает мапу
 */
public final class YamlReader {

    private static final String PATH = "src/main/resources/config.yml";

    private YamlReader() {
    }

    /**
     * который считывает файл yaml
     * @return возвращает мапу со строкой и объектом
     * @throws IOException если вдруг что-то пошло не так, пробрасывает исключение
     */
    public static Map<String, Object> getMap() throws IOException {
        @Cleanup
        InputStream in = new FileInputStream(PATH);
        Yaml yaml = new Yaml();
        return yaml.load(in);
    }
}
