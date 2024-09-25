package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString
@AllArgsConstructor
public class SupplierItem {
    private String supplierId;
    private String ItemId;
    private double price;
    private double weight;
    private Date date;
    private double netWeight;
    private String id;


}
