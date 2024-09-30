package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierSearchTm {

    private String paymentNo;
    private String supID;
    /*private String orderNo;*/
    private double totalAmount;
    private String date;
    private double payAmount;
    private double toBePaAmount;
    private String paymentType;
    private String description;
    /*private String status;*/
}
