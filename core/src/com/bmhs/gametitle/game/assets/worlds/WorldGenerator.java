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

    public WorldGenerator (int worldMapRows, int worldMapColumns) {
        this.worldMapRows = worldMapRows;
        this.worldMapColumns = worldMapColumns;
        numIslands = MathUtils.random(3,10);
        worldIntMap = new int[worldMapRows][worldMapColumns];

        genOcean();
        for(int temp = 0; temp <= numIslands-1; temp++){
            genSeed();
        }
        genSand();

        //call methods to build 2D array
        generateWorldTextFile();

        Gdx.app.error("WorldGenerator", "WorldGenerator(WorldTile[][][])");
    }


    private void genSeed(){
        Vector2 mapSeed = new Vector2(MathUtils.random(worldIntMap[0].length), MathUtils.random(worldIntMap.length));
        System.out.println(mapSeed.y+" "+mapSeed.x);

        worldIntMap[(int)mapSeed.y][(int)mapSeed.x]=4;

        for(int r = 0; r < worldIntMap.length; r++){
            for(int c = 0; c < worldIntMap[r].length; c++){
                Vector2 tempVector = new Vector2(c,r);
                if(tempVector.dst(mapSeed) < 15) {
                    worldIntMap[r][c] = 19;
                }
            }
        }
    }

    private void genSand(){
        int randInt;

        for(int r = 0; r < worldIntMap.length; r++){
            for(int c = 0; c < worldIntMap[r].length; c++){
                if (worldIntMap[r][c] == 19) {
                    randInt = (MathUtils.random(1,10));
                    if(randInt == 1){
                        Vector2 sandSeed = new Vector2(c,r);
                        for(int rows = 0; rows < worldIntMap.length; rows++){
                            for(int columns = 0; columns < worldIntMap[r].length; columns++){
                                Vector2 tempVector = new Vector2(columns,rows);
                                if(tempVector.dst(sandSeed) < 8) {
                                    worldIntMap[rows][columns] = 16;
                                }
                            }
                        }
                    }
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

                    worldIntMap[r][c] = 20;

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
