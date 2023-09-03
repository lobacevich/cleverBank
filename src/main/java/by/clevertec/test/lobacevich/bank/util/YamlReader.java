package by.clevertec.test.lobacevich.bank.util;

import lombok.Cleanup;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

public final class YamlReader {

    private static final String PATH = "src/main/resources/config.yml";

    private YamlReader() {
    }

    public static Map<String, Object> getMap() throws IOException {
        @Cleanup
        InputStream in = new FileInputStream(PATH);
        Yaml yaml = new Yaml();
        return yaml.load(in);
    }
}
