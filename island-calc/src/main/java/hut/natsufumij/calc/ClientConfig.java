package hut.natsufumij.calc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("client.config")
public class ClientConfig {
    /**
     * 自定义客户端名称
     */
    private String myAppName;

    public String getMyAppName() {
        return myAppName;
    }

    public void setMyAppName(String myAppName) {
        this.myAppName = myAppName;
    }
}
