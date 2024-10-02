package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemTm {
    private String itemId;
    private String itemName;
    private String onHandWeight;
    private String deletes;
    private String itemType;
}
