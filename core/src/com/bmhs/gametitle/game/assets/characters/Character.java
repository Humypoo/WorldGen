package com.bmhs.gametitle.game.assets.characters;

import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;

public abstract class Character {
    private float x,y;
    protected WorldTile tile;
    public Character(WorldTile tile, float x, float y){
        this.x = x;
        this.y = y;
        this.tile = tile;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public WorldTile getTile() {
        return tile;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setTile(WorldTile tile) {
        this.tile = tile;
    }

    public void adjustX(float x){
        this.x += x;
    }

    public void adjustY(float y){
        this.y += y;
    }
}
