package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PendingPaymentTm {
    private String paymentNo;
    private double totalAmount;
    private double payAmount;
    private double toBePaidAmount;
}
