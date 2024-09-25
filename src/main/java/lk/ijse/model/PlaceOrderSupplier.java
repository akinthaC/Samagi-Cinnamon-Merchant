package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PlaceOrderSupplier {
    private List<SupplierItem> odlist;
    private List  odlist1;
    private List  sup;
}
