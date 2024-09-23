package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PaymentInfo {
    private String supID;
    private String orderNo;
    private String paymentNo;
    private String date;
    private double totalAmount;
    private double payAmount;
    private String description;
    /*private String status;*/
}
