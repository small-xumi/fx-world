package hut.natsufumij.calc;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CalcController {

    private final JdbcTemplate jdbcTemplate;

    @FXML
    private Label myLabel;

    public CalcController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @FXML
    public void initialize() {
        String query = "SELECT text FROM channel WHERE id = 'msakBgGZLiNbmbyPUB44lfa'";
        try{
            String message = jdbcTemplate.queryForObject(query, String.class);
            myLabel.setText(message);
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }
}
