package by.clevertec.test.lobacevich.bank.util;

import lombok.Cleanup;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

public final class YamlReader {

    private YamlReader() {
    }

    public static Map<String, Object> getMap(String filePath) throws IOException {
        @Cleanup
        InputStream in = new FileInputStream(filePath);
        Yaml yaml = new Yaml();
        return yaml.load(in);
    }
}
