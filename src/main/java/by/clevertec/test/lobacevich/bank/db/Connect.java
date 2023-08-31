package by.clevertec.test.lobacevich.bank.db;

import by.clevertec.test.lobacevich.bank.util.YamlReader;
import by.clevertec.test.lobacevich.bank.exception.ConfigFileException;
import by.clevertec.test.lobacevich.bank.exception.ConnectionException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class Connect {

    private static final String url;
    private static final String username;
    private static final String password;
    private static final Connection CONNECTION;
    public static final String PATH = "src/main/resources/config.yml";

    static {
        try {
            Map<String, Object> data = YamlReader.getMap(PATH);
            url = (String) data.get("Connect.url");
            username = (String) data.get("Connect.username");
            password = (String) data.get("Connect.password");
        } catch (IOException e) {
            throw new ConfigFileException("Load config file error");
        }
        try {
            CONNECTION = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new ConnectionException("Connection failed");
        }
    }

    private Connect() {
    }

    public static Connection getConnection() {
        return CONNECTION;
    }
}
