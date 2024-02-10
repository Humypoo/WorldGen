package com.bmhs.gametitle.game.assets.worlds;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;
import com.bmhs.gametitle.gfx.utils.TileHandler;


public class WorldGenerator {

    private int worldMapRows, worldMapColumns;
    private int numIslands;
    private int[][] worldIntMap;
    private int[][] seedArray;

    public WorldGenerator (int worldMapRows, int worldMapColumns) {
        this.worldMapRows = worldMapRows;
        this.worldMapColumns = worldMapColumns;
        numIslands = MathUtils.random(3,10);
        worldIntMap = new int[worldMapRows][worldMapColumns];
        seedArray = new int[worldMapRows][worldMapColumns];

        genOcean();
        for(int temp = 0; temp <= numIslands; temp++){
            genSeed();
        }
        genSand();
        genGrass();
        genThirdLayer();
        genFourthLayer();
        genFifthLayer();
        cleanSeeds();


        //call methods to build 2D array
        generateWorldTextFile();

        Gdx.app.error("WorldGenerator", "WorldGenerator(WorldTile[][][])");
    }


    private void genSeed(){
        Vector2 mapSeed = new Vector2(MathUtils.random(worldIntMap[0].length), MathUtils.random(worldIntMap.length + 1));
        System.out.println(mapSeed.y+" "+mapSeed.x);
        int size;
        worldIntMap[(int)mapSeed.y][(int)mapSeed.x]=4;

        for(int r = 0; r < worldIntMap.length; r++){
            for(int c = 0; c < worldIntMap[r].length; c++){
                Vector2 tempVector = new Vector2(c,r);
                size = MathUtils.random(5,20);
                if(tempVector.dst(mapSeed) < size) {
                    worldIntMap[r][c] = 11;
                }
            }
        }
    }

    private void genSand(){
        int randInt;

        for(int r = 0; r < worldIntMap.length; r++){
            for(int c = 0; c < worldIntMap[r].length; c++){
                if (worldIntMap[r][c] == 11) {
                    randInt = (MathUtils.random(1,10));
                    if(randInt == 1){
                        Vector2 sandSeed = new Vector2(c,r);
                        seedArray[r][c] = 1;
                        for(int rows = 0; rows < worldIntMap.length; rows++){
                            for(int columns = 0; columns < worldIntMap[r].length; columns++){
                                Vector2 tempVector = new Vector2(columns,rows);
                                if(tempVector.dst(sandSeed) < 12) {
                                    worldIntMap[rows][columns] = 17;
                                }

                            }
                        }
                    }
                }
            }
        }
    }
    private void genGrass(){

        for(int r = 0; r < worldIntMap.length; r++){
            for(int c = 0;c  < worldIntMap[r].length; c++) {
                if (seedArray[r][c] == 1){
                    Vector2 seed = new Vector2(c,r);
                    for(int rows = 0; rows < worldIntMap.length; rows++){
                        for(int columns = 0; columns < worldIntMap[r].length; columns++){
                            Vector2 tempVector = new Vector2(columns,rows);
                            if(tempVector.dst(seed) < 9) {
                                worldIntMap[rows][columns] = 19;
                            }

                        }
                    }
                }
            }
        }
    }

    private void genThirdLayer(){

        for(int r = 0; r < worldIntMap.length; r++){
            for(int c = 0;c  < worldIntMap[r].length; c++) {
                if (seedArray[r][c] == 1){
                    Vector2 seed = new Vector2(c,r);
                    for(int rows = 0; rows < worldIntMap.length; rows++){
                        for(int columns = 0; columns < worldIntMap[r].length; columns++){
                            Vector2 tempVector = new Vector2(columns,rows);
                            if(tempVector.dst(seed) < 6) {
                                worldIntMap[rows][columns] = 16;
                            }

                        }
                    }
                }
            }
        }
    }

    private void genFourthLayer(){

        for(int r = 0; r < worldIntMap.length; r++){
            for(int c = 0;c  < worldIntMap[r].length; c++) {
                if (seedArray[r][c] == 1){
                    Vector2 seed = new Vector2(c,r);
                    for(int rows = 0; rows < worldIntMap.length; rows++){
                        for(int columns = 0; columns < worldIntMap[r].length; columns++){
                            Vector2 tempVector = new Vector2(columns,rows);
                            if(tempVector.dst(seed) < 4) {
                                worldIntMap[rows][columns] = 14;
                            }

                        }
                    }
                }
            }
        }
    }

    private void genFifthLayer(){

        for(int r = 0; r < worldIntMap.length; r++){
            for(int c = 0;c  < worldIntMap[r].length; c++) {
                if (seedArray[r][c] == 1){
                    Vector2 seed = new Vector2(c,r);
                    for(int rows = 0; rows < worldIntMap.length; rows++){
                        for(int columns = 0; columns < worldIntMap[r].length; columns++){
                            Vector2 tempVector = new Vector2(columns,rows);
                            if(tempVector.dst(seed) < 2) {
                                worldIntMap[rows][columns] = 13;
                            }

                        }
                    }
                }
            }
        }
    }

    private void cleanSeeds() {
        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {
                if(worldIntMap[r][c] == 11){
                    worldIntMap[r][c] = 21;
                }
            }
        }
    }


    public String getWorld3DArrayToString() {
        String returnString = "";

        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                returnString += worldIntMap[r][c] + " ";
            }
            returnString += "\n";
        }

        return returnString;
    }

    public void genOcean() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {

                    worldIntMap[r][c] = 21;

            }
        }
    }
    public void randomize() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldIntMap[r][c] = MathUtils.random(TileHandler.getTileHandler().getWorldTileArray().size-1);
            }
        }
    }

    public WorldTile[][] generateWorld() {
        WorldTile[][] worldTileMap = new WorldTile[worldMapRows][worldMapColumns];
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldTileMap[r][c] = TileHandler.getTileHandler().getWorldTileArray().get(worldIntMap[r][c]);
            }
        }
        return worldTileMap;
    }

    private void generateWorldTextFile() {
        FileHandle file = Gdx.files.local("assets/worlds/world.txt");
        file.writeString(getWorld3DArrayToString(), false);
    }

}
