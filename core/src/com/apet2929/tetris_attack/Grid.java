package com.apet2929.tetris_attack;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.awt.*;
import java.util.*;

public class Grid {
    public static final float TIME_TO_FALL = 0.2f;
    public static final int START_X = 130;
    public static final int START_Y = 30;
    public static final int PANEL_SIZE = 32;
    public static final int GRID_HEIGHT = 20;
    public static final int GRID_WIDTH = 12;

    private PanelType[][] grid;
    private HashMap<Pos, Float> fallingPanels;
    private float time;

    public Grid(){
        grid = new PanelType[GRID_HEIGHT][GRID_WIDTH];
        this.fallingPanels = new HashMap<>();

        reset();
    }

    public void tick(float deltaTime){
        removeMatches();
        this.time += deltaTime;
        searchFalling();
        fall();
    }

    public void reset(){
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                grid[i][j] = PanelType.NONE;
                fallingPanels.put(new Pos(j, i), -1f);
            }
        }
        time = 0;
    }

    public void swap(Cursor cursor){
        Pos p1 = new Pos(cursor.getX(), cursor.getY());
        Pos p2 = new Pos(cursor.getX() + 1, cursor.getY());
        // resets falling timer for any panel that was falling that is swapped
        fallingPanels.put(p1, -1f);
        fallingPanels.put(p2, -1f);

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
                if(uniqueMatches(matches, matchesX) >= 3) {
                    matches.addAll(matchesX);
                }
                if(uniqueMatches(matches, matchesY) >= 3) {
                    matches.addAll(matchesY);
                }
            }
        }
        for (Pos pos : matches) {
            set(PanelType.NONE, pos.x, pos.y);
        }
    }

    private boolean shouldFall(Pos pos) {
        return this.time - fallingPanels.get(pos) > TIME_TO_FALL;
    }

    private int uniqueMatches(HashSet<Pos> known, ArrayList<Pos> current) {
        int i = 0;
        for(Pos pos : current) {
            if(!known.contains(pos)) i++;
        }
        return i;
    }

    private void searchFalling(){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Pos p0 = new Pos(j, i);
                if(isInBounds(p0.add(0,-1)) && get(p0.add(0,-1)) == PanelType.NONE) {
                    if(!isAlreadyFalling(p0)){
                        this.fallingPanels.put(p0, this.time);
                    }
                }
            }
        }
    }
    
    private void fall(){
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                Pos pos = new Pos(x, y);
                if(shouldFall(pos)){
                    PanelType pt = get(pos);
                    while(isInBounds(pos.add(0,-1)) && get(pos.add(0,-1)) == PanelType.NONE) {
                        pos = pos.add(0,-1);
                    }
                    swap(x, y, pos.x, pos.y); // maybe pos.y, maybe pos.y+1?
                    fallingPanels.put(pos, -1f);
                }
            }
        }

//
//        HashMap<Pos, PanelType> fp = new HashMap<>();
//        ArrayList<Pos> columns = new ArrayList<>();
//        for (Pos panel : fallingPanels.keySet()) {
//            if(shouldFall(panel)) fp.put(panel, get(panel));
//
//            // group panels into columns of falling panels (all need to fall the same dist)
//            boolean seen = false;
//            for (Pos column : columns) {
//                if(column.x == panel.x) {
//                    seen = true;
//                    if(panel.y < column.y) {
//                        column.y = panel.y;
//                    }
//                    break;
//                }
//            }
//            if(!seen) columns.add(panel);
//        }
//
//        for (Map.Entry<Pos, PanelType> panel : fp.entrySet()) {
//            Pos lowestFalling = panel.getKey();
//            while(get(lowestFalling) != PanelType.NONE) {
//                lowestFalling.add(0,-1);
//            }
//            int distToFall = 0;
//            Pos floor = lowestFalling;
//            while(get(floor) == PanelType.NONE) {
//                distToFall++;
//                floor = floor.add(0,-1);
//            }
//        }
    }

    private boolean isAlreadyFalling(Pos pos){
        return fallingPanels.get(pos) != -1;
    }

    private ArrayList<Pos> getMatches(Pos p0, Pos dir, ArrayList<Pos> matches) {
        PanelType pt = get(p0);
        if(pt == PanelType.NONE) return matches;
        matches.add(p0);
        Pos p1 = p0.add(dir);
        if(isInBounds(p1) && get(p1) == pt) getMatches(p1, dir, matches);
        return matches;
    }

    private boolean isInBounds(Pos pos) {
        return pos.x >= 0 && pos.x < GRID_WIDTH && pos.y >= 0 && pos.y < GRID_HEIGHT;
    }

    private PanelType get(Pos pos){
        return grid[pos.y][pos.x];
    }

    public void set(PanelType pt, int x, int y) {
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

//    private class PanelGroup {
//        private PriorityQueue<Pos> lowestQueue;
//        private Timer timer;
//
//        public PanelGroup(HashSet<Pos> panels){
//            lowestQueue = new PriorityQueue<>(panels.size(), (o1, o2) -> o1.y - o2.y);
//            lowestQueue.addAll(panels);
//            for (Pos pos : lowestQueue) {
//                swap(pos.x, pos.y, pos.x, pos.y-1);
//            }
//            System.out.println("NEW panel group");
//
//            this.timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    for (Pos pos : panels) {
//                        swap(pos.x, pos.y, pos.x, pos.y-1);
//                    }
//
//                }
//            },
//                    100L); // 100ms
//
//        }
//        public boolean contains(Pos pos) {
//            return this.lowestQueue.contains(pos);
//        }
//        public void remove(Pos pos) {
//            lowestQueue.remove(pos);
//        }
//
//    }
}
