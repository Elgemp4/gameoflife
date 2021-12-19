package be.elgem.gameoflife.gamelogic;

public class GameLoop implements Runnable{

    private boolean running;

    private double updateRate;

    private long timeForNextDebug;

    private long timeSinceLastTick, currentTime, accumulator;

    private double timeBetweenTicks;

    private int fps;

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

        timeSinceLastTick = currentTime = System.currentTimeMillis();

        timeForNextDebug = System.currentTimeMillis() + 1000;

        while(running) {
            updateTime();

            tryUpdate();

            debugFPS();
        }

    }

    /**
     * Mets à jour les variables gérant le temps s'étant passé
     */
    private void updateTime() {
        currentTime = System.currentTimeMillis();

        timeBetweenTicks = currentTime - timeSinceLastTick;

        timeSinceLastTick = currentTime;

        accumulator+=timeBetweenTicks;
    }

    /**
     * Mets à jour, si le moment est arrivé, le jeu
     */
    private void tryUpdate() {
        while(accumulator>=updateRate) {
            game.update();
            game.render();
            fps++;

            accumulator-=updateRate;
        }

    }

    /**
     * Imprime dans la console le nombre d'image par seconde
     */
    private void debugFPS() {
        if(System.currentTimeMillis()>=timeForNextDebug) {
            System.out.printf("FPS : %d%n", fps);

            fps = 0;
            timeForNextDebug = System.currentTimeMillis() + 1000;
        }
    }

}
