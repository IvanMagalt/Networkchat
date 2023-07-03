import realization.client.log.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Config instance;
    private static final Logger LOGGER = Logger.getInstance();
    private static final String PATH = "./config/setting.txt";
    private int port;
    private String host;

    private Config() {
        try (FileReader fileReader = new FileReader(PATH)){
            Properties properties = new Properties();
            properties.load(fileReader);

            port = Integer.parseInt(properties.getProperty("port"));
            host = properties.getProperty("host");
        } catch (IOException e) {
            LOGGER.log("Ошибка файла" + Config.class.getName());
            e.printStackTrace();
        }
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }
}
