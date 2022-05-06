package be.elgem.gameoflife.gamelogic;

public class GameLoop implements Runnable{

    private boolean running;

    private double updateRate;

    private long timeForNextDebug;

    private long startTime, endTime;

    private int ups, storedUPS;

    private Thread worker;

    final private Game game;

    public GameLoop(int updateRate, Game game) {
        setUpdateRate(updateRate);

        this.game = game;
    }

    /**
     * Démarre le jeu et son thread
     */
    public void start() {
        if(!running) {
            worker = new Thread(this);
            worker.start();
        }

    }

    /**
     * Arrête le jeu et son thread
     */
    public void stop() {
        running = false;
        storedUPS = 0;
        worker = new Thread(this);
    }

    /**
     * Retourne vrai si le jeu est en cours d'exécution
     * @return
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * défini la vitesse de mise à jour du jeu
     * @param updateRate
     */
    public void setUpdateRate(double updateRate) {
        this.updateRate = (1.0 / updateRate) * 1000;
    }

    @Override
    public void run() {
        running = true;

        timeForNextDebug = System.currentTimeMillis() + 1000;

        while(running) {
            startTime = System.currentTimeMillis();

            update();
            debugUPS();

            endTime = System.currentTimeMillis();

            long deltaTime = endTime - startTime;

            if(updateRate - deltaTime > 0)
            try {
                Thread.sleep((long) (updateRate - deltaTime));
            }
            catch (InterruptedException e) {}
        }

    }

    /**
     * Mets à jour, si le moment est arrivé, le jeu
     */
    private void update() {
        game.update();
        game.render();
        ups++;
    }

    /**
     * Imprime dans la console le nombre d'image par seconde
     */
    private void debugUPS() {
        if(System.currentTimeMillis()>=timeForNextDebug) {
            System.out.printf("FPS : %d%n", ups);

            storedUPS = ups;
            ups = 0;

            timeForNextDebug = System.currentTimeMillis() + 1000;
        }
    }

    public int getUps() {
        return storedUPS;
    }
}
