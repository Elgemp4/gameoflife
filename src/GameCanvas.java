import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GameCanvas extends Canvas implements MouseMotionListener {
    final private Game game;

    public GameCanvas(int width, int height) {
        super();

        game = new Game(this);

        this.setFocusable(true);
        this.setPreferredSize(new Dimension(width, height));

    }

    @Override
    public void paint(Graphics g) {
        render();
        render();


    }

    public void render() {
        Graphics graphics = this.getGraphics();

        drawBackground(graphics);

        drawCells(graphics);

        graphics.dispose();
    }

    private void drawBackground(Graphics graphics) {
        graphics.setColor(Color.BLACK);

        graphics.fillRect(0,0,getWidth(), getHeight());
    }

    private void drawCells(Graphics graphics) {
        boolean[][] cellsArray = game.getCellGrid();
        graphics.setColor(Color.WHITE);

        for(int y = 0; y<Game.NUMBER_ROW; y++) {
            for(int x =0; x<Game.NUMBER_COL; x++){
                if(cellsArray[y][x]){
                    graphics.fillRect(x*(getWidth()/Game.NUMBER_COL), y*(getHeight() / Game.NUMBER_ROW), (getWidth()/Game.NUMBER_COL), (getHeight() / Game.NUMBER_ROW));
                }
            }
        }
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> game.putCell(e.getX(), e.getY());
            case MouseEvent.BUTTON2 -> game.removeCell(e.getX(), e.getY());
        }

        render();
        System.out.println("ici");
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    public Game getGame() {
        return game;
    }
}
