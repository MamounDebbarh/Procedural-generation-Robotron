package game;

import java.util.Random;

public class Wave {



    public boolean finished;
    public int waveNumber;

    public int enemySpawnCount;
    private int numEnemies;

    private int humanSpawnCount;
    public int numHumans;

    private int obstacleSpawnCount;
    public int numObstacles;

    public Wave() {
        this.waveNumber = 1;

        this.enemySpawnCount = Config.STARTING_ENEMIES;
        this.numEnemies = enemySpawnCount;

        this.humanSpawnCount = Config.STARTING_HUMANS;
        this.numHumans = humanSpawnCount;

        this.obstacleSpawnCount = Config.STARTING_OBSTACLES;
        this.numObstacles = obstacleSpawnCount;

        this.finished = false;
    }

    public void next(){
        waveNumber++;

        enemySpawnCount++;
        numEnemies = enemySpawnCount;

        if (waveNumber % Config.LEVEL_INCREASE_HUMANS == 0) humanSpawnCount++;
        numHumans = humanSpawnCount;
        if (waveNumber % Config.LEVEL_INCREASE_OBSTACLES == 0) obstacleSpawnCount++;
        numObstacles = obstacleSpawnCount;

        if (waveNumber % Config.PICKUP_INCREASE == 0) {
            Config.SPAWN_RATE_AMMO = (int) (Config.SPAWN_RATE_AMMO*0.9);
            Config.SPAWN_RATE_SPEED = (int) (Config.SPAWN_RATE_SPEED*0.9);
        }

        finished = false;
    }

    public void spawnEnemies(){
        numEnemies--;
        if (numEnemies <= 0) finished = true;
    }

    public void spawnHumans(){
        if (numHumans > 0) numHumans--;
    }

    public void spawnObstacles(){
        if (numObstacles > 0) numObstacles--;
    }
}
