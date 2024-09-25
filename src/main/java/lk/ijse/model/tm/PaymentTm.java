package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor@AllArgsConstructor
@ToString
public class PaymentTm {
    private String supID;
    private String orderNo;
    private String paymentNo;
    private String date;
    private double totalAmount;
    private double payAmount;
    private double toBePaAmount;
    private String description;
    private String paymentType;
    private String status;
}
