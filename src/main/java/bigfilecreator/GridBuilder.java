package bigfilecreator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

class GridBuilder
{    
    private final GridPane grid = new GridPane();
    private final PositionCounter pc;
    
    public GridBuilder(int maxC)
    {
        pc = new PositionCounter(maxC);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
    }
        
    public void add(Node child)
    {
        Position p = pc.nextPosition();
        if(child != null) { grid.add(child, p.c, p.v); }
    }
    
    public void add(Node child, int colSpan)
    {
        Position p = pc.nextPosition();

        if(child != null) { grid.add(child, p.c, p.v, colSpan, 1); }
        for(int i=0 ; i<colSpan-1 ; i++) { pc.nextPosition(); }
    }
    
    public GridPane getGrid() { return grid; }
    
    private class PositionCounter
    {
        private int c = -1;
        private int v = 0;
        private int maxC;
        
        private PositionCounter(int maxC) { this.maxC = maxC; }
                
        private Position nextPosition()
        {
            c++;
            if(c / maxC >= 1)
            {
                c %= maxC;
                v++;
            }
            return new Position(c, v);
        }
    }
    
    private class Position
    {
        private final int c, v;

        private Position(int c, int v)
        {
            this.c = c;
            this.v = v;
        }
    }
}