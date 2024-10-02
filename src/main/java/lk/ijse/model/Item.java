package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Item {
    private String itemId;
    private String itemName;
    private String onHandWeight;
    private String deletes;
    private String itemType;

}
