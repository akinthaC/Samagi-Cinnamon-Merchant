package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PaymentInfo {
    private String paymentNo;
    private String supID;
    /*private String orderNo;*/
    private double totalAmount;
    private String date;
    private double payAmount;
    private double toBePaAmount;
    private String paymentType;
    private String description;
    private String status;

}
