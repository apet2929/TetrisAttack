package com.apet2929.tetris_attack;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.*;

public class Grid {
    public static final int START_X = 130;
    public static final int START_Y = 30;
    public static final int PANEL_SIZE = 32;
    public static final int GRID_HEIGHT = 20;
    public static final int GRID_WIDTH = 12;

    private PanelType[][] grid;

    public Grid(){
        grid = new PanelType[GRID_HEIGHT][GRID_WIDTH];
        reset();
    }

    public void tick(){
        removeMatches();
        fall();
    }

    public void reset(){
        for (int i = 0; i < GRID_HEIGHT; i++) {
            Arrays.fill(grid[i], PanelType.NONE);
        }
    }

    public void swap(Cursor cursor){
        swap(cursor.getX(), cursor.getY(), cursor.getX()+1, cursor.getY());
    }

    private void swap(int x1, int y1, int x2, int y2) {
        PanelType temp = grid[y1][x1];
        set(grid[y2][x2], x1, y1);
        set(temp, x2, y2);
    }

    private void removeMatches(){
        HashSet<Pos> matches = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Pos p0 = new Pos(j, i);
                ArrayList<Pos> matchesX = getMatches(p0, new Pos(1,0), new ArrayList<Pos>());
                ArrayList<Pos> matchesY = getMatches(p0, new Pos(0,1), new ArrayList<Pos>());
                if(matchesX.size() >= 3) {
                    matches.addAll(matchesX);
                }
                if(matchesY.size() >= 3){
                    matches.addAll(matchesY);
                }
            }
        }
        for (Pos pos : matches) {
            set(PanelType.NONE, pos.x, pos.y);
        }
    }

    private void fall(){
        HashSet<Pos> falling = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Pos p0 = new Pos(j, i);
                if(isInBounds(p0.add(0,-1)) && get(p0.add(0,-1)) == PanelType.NONE || falling.contains(p0.add(0,-1))) {
                    falling.add(p0);
                }
            }
        }

        PriorityQueue<Pos> lowestQueue = new PriorityQueue<>(falling.size(), (o1, o2) -> o1.y - o2.y);
        lowestQueue.addAll(falling);
        for (Pos pos : lowestQueue) {
            System.out.println("pos = " + pos);
            swap(pos.x, pos.y, pos.x, pos.y-1);
        }
    }

    private ArrayList<Pos> getMatches(Pos p0, Pos dir, ArrayList<Pos> matches) {
        PanelType pt = get(p0);
        matches.add(p0);
        Pos p1 = p0.add(dir);
        if(isInBounds(p1) && get(p1) == pt) getMatches(p1, dir, matches);
        return matches;
    }

    private boolean isInBounds(Pos pos) {
        return pos.x > 0 && pos.x < GRID_WIDTH && pos.y > 0 && pos.y < GRID_HEIGHT;
    }


    private PanelType get(Pos pos){
        return grid[pos.y][pos.x];
    }


    public void set(PanelType pt, int x, int y) {
        assert x > 0 && x < GRID_WIDTH && y > 0 && y < GRID_HEIGHT;
        grid[y][x] = pt;
    }

    public void initRandomRow(int y) {
        for (int i = 0; i < GRID_WIDTH; i++) {
            setRandom(i, y);
        }
    }

    private void setRandom(int x, int y) {
        int val = (int) (Math.random() * PanelType.NONE.ordinal());
        set(PanelType.values()[val], x, y);
    }

    public void draw(SpriteBatch sb, TextureAtlas textures) {
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                drawPanel(grid[i][j], j, i, sb, textures);
            }
        }
    }

    private void drawPanel(PanelType pt, int x, int y, SpriteBatch sb, TextureAtlas textures) {
        int realX = (x * PANEL_SIZE) + START_X;
        int realY = (y * PANEL_SIZE) + START_Y;
        sb.draw(textures.findRegion(pt.asset), realX, realY, PANEL_SIZE, PANEL_SIZE);
    }
}
