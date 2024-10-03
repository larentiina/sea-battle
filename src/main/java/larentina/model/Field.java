package larentina.model;

import larentina.exceptions.ForbiddenCellException;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Field {
   public static final int X_SIZE = 10;
    public static final int Y_SIZE = 10;

    private final List<List<Cell>> field = new ArrayList<>();
    private Set<Cell> processedCells = new HashSet<>();

    public Field() {
        for (int i = 0; i < X_SIZE; i++) {
            List<Cell> row = new ArrayList<>();
            for (int j = 0; j <Y_SIZE; j++) {
                row.add(new Cell(i,j, Cell.CellType.EMPTY));
            }
            field.add(row);
        }
    }

    public boolean putShip(int x1,int y1,int x2,int y2){
        if(x1>x2){
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if(y1>y2){
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }
        if(x2> X_SIZE || y2> Y_SIZE || x1<= 0 || y1 <= 0)
        {
            return false;
        }
        List<Cell> fullCells = new ArrayList<>();
        for (int i = x1; i <= x2 ; i++) {
            for (int j = y1; j <= y2; j++) {
                try {
                    fullCells.add(fullCell(i, j));
                }catch (ForbiddenCellException e){
                    fullCells.forEach(c->c.setCellType(Cell.CellType.EMPTY));
                    return false;
                }

            }
        }
        fullCells.forEach(this::getAroundCellField);
        return true;
    }

    public Cell fullCell(int x, int y){
        if(x>X_SIZE || y>Y_SIZE || x<0 || y<0) {
            throw new ForbiddenCellException("Выход за пределы поля");
        }
        Cell cell = field.get(x-1).get(y-1);
        if(cell.getCellType() != Cell.CellType.EMPTY){
            throw new ForbiddenCellException("Клетка недоступна");
        }
        cell.setCellType(Cell.CellType.FULL);
        return cell;
    }

    private void getAroundCellField(Cell cell){
        for (int i = cell.getX()==0 ? 0 : cell.getX()-1 ; i < (cell.getX() == 9 ? 10 : cell.getX() + 2); i++) {
            for (int j = cell.getY() ==0 ? 0: cell.getY() - 1; j < (cell.getY() == 9 ? 10 : cell.getY() + 2 ) ; j++) {
                Cell curCell = field.get(i).get(j);
                if(!curCell.equals(cell) && !curCell.getCellType().equals(Cell.CellType.FULL)){
                    curCell.setCellType(Cell.CellType.FORBIDDEN);
                }
            }
        }
    }
    private void getAroundShootCellField(Cell cell){
        if (hasPartShipAround(cell) || processedCells.contains(cell) ) return;
        processedCells.add(cell);
        for (int i = cell.getX()==0 ? 0 : cell.getX()-1 ; i < (cell.getX() == 9 ? 10 : cell.getX() + 2); i++) {
            for (int j = cell.getY() ==0 ? 0: cell.getY() - 1; j < (cell.getY() == 9 ? 10 : cell.getY() + 2 ) ; j++) {
                Cell curCell = field.get(i).get(j);
                if(!curCell.equals(cell)){
                    if(curCell.getCellType().equals(Cell.CellType.KILLED_SHIP)) {
                        getAroundShootCellField(curCell);
                    }else {
                        curCell.setCellType(Cell.CellType.KILLED_EMPTY);
                    }
                }
            }
        }
    }

    private boolean hasPartShipAround(Cell cell){
        for (int i = cell.getX()==0 ? 0 : cell.getX()-1 ; i < (cell.getX() == 9 ? 10 : cell.getX() + 2); i++) {
            for (int j = cell.getY() ==0 ? 0: cell.getY() - 1; j < (cell.getY() == 9 ? 10 : cell.getY() + 2 ) ; j++) {
                Cell curCell = field.get(i).get(j);
                if(!curCell.equals(cell)){
                    if (curCell.getCellType().equals(Cell.CellType.FULL)){
                        return true;
                    }

                }
            }
        }
        return false;
    }



    public boolean shoot(int x, int y) {
        if (x < 0 || x > X_SIZE || y < 0 || y > Y_SIZE) {
            throw new ForbiddenCellException("В данную клетку нельзя выстрелить");
        }
        Cell cell = field.get(x-1).get(y-1);
        if(cell.getCellType() == Cell.CellType.EMPTY || cell.getCellType() == Cell.CellType.FORBIDDEN ) {
            cell.setCellType(Cell.CellType.KILLED_EMPTY);
            return false;
        } else if (cell.getCellType() == Cell.CellType.FULL) {
            cell.setCellType(Cell.CellType.KILLED_SHIP);
            getAroundShootCellField(cell);
            return true;
        }
        return false;
    }


}
