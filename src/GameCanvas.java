import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

public class GameCanvas extends Canvas implements MouseMotionListener {
    final private Game game;

    private Camera camera;

    public GameCanvas(int width, int height) {
        super();

        game = new Game(this);

        this.setFocusable(true);
        this.setPreferredSize(new Dimension(width, height));
    }

    public void postWindowCreationLoading() {
        this.createBufferStrategy(2);
        this.camera = new Camera(Game.NUMBER_COL / 2, Game.NUMBER_ROW / 2, 400, this);
        this.render();
    }

    @Override
    public void paint(Graphics g) {
        render();
    }

    public void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();

        Graphics graphics = bufferStrategy.getDrawGraphics();

        drawBackground(graphics);

        drawGrid(graphics);

        drawCells(graphics);

        graphics.dispose();

        bufferStrategy.show();
    }

    private void drawBackground(Graphics graphics) {
        graphics.setColor(Color.BLACK);

        graphics.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawGrid(Graphics graphics) {
        graphics.setColor(Color.lightGray);

        int test = 0;

        for (int yIndex = 0; yIndex <= camera.getNumberDisplayedCellsY(); yIndex++) {
            int yPos = camera.getCellSize() * yIndex - camera.getYOffset();

            graphics.drawLine(0, yPos, getHeight(), yPos);
        }

        for (int xIndex = 0; xIndex <= camera.getNumberDisplayedCellsX(); xIndex++) {
            int xPos = camera.getCellSize() * xIndex - camera.getXOffset();

            graphics.drawLine(xPos, 0, xPos, getWidth());
        }
    }

    private void drawCells(Graphics graphics) {
        boolean[][] cellsArray = game.getCellGrid();
        graphics.setColor(Color.WHITE);

        for (int y = 0; y < this.getHeight(); y += camera.getCellSize()) {
            for (int x = 0; x < this.getWidth(); x += camera.getCellSize()) {
                Index index = camera.getCellIndexFromPosition(new Position(x, y));
                if(index==null){
                    continue;
                }
                if (cellsArray[index.getYIndex()][index.getXIndex()]) {
                    graphics.fillRect(x - camera.getXOffset() , y - camera.getYOffset(), camera.getCellSize(), camera.getCellSize());
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
    public void mouseMoved(MouseEvent e) {
    }

    public Game getGame() {
        return game;
    }
}
