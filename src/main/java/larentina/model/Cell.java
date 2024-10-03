package larentina.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cell {

    private int x,y;
    private CellType cellType;

    public enum CellType{
        EMPTY,
        FULL,
        KILLED_EMPTY,
        KILLED_SHIP,
        FORBIDDEN
    }
}
