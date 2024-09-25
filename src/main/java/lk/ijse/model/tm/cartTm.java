package lk.ijse.model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class cartTm {
    private String type;
    private double weight;
    private double netWeight;
    private double buyPrice;
    private double total;
    private double cuttingAmount;
    private JFXButton btnRemove;
}