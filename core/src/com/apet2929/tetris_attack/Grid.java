package com.apet2929.tetris_attack;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import java.util.*;

public class Grid {
    public static final float TIME_TO_FALL = 0.2f;
    public static final int START_X = 130;
    public static final int START_Y = 30;
    public static final int PANEL_SIZE = 32;
    public static final int GRID_HEIGHT = 20;
    public static final int GRID_WIDTH = 12;

    private Panel[][] grid;
    private float time;

    public Grid(){
        grid = new Panel[GRID_HEIGHT][GRID_WIDTH];
        reset();
    }

    public void tick(float deltaTime){
        for (int i = 0; i <grid.length; i++) {
            for (int j = 0; j <grid[i].length; j++) {
                get(new Pos(j, i)).update(deltaTime);
            }
        }

        removeMatches();
        checkCollisions();
        this.time += deltaTime;
//        searchFalling();
//        fall();
    }

    public void reset(){
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                grid[i][j] = new Panel(PanelType.NONE, new Pos(j,i));
            }
        }
        time = 0;
    }

    public void swap(Cursor cursor){
        Pos p1 = new Pos(cursor.getX(), cursor.getY());
        Pos p2 = new Pos(cursor.getX() + 1, cursor.getY());
        // resets falling timer for any panel that was falling that is swapped
        get(p1).fallTimer = -1;
        get(p2).fallTimer = -1;

        swap(cursor.getX(), cursor.getY(), cursor.getX()+1, cursor.getY());
    }

    private void swap(int x1, int y1, int x2, int y2) {
        Pos pos1 = new Pos(x1, y1);
        Pos pos2 = new Pos(x2, y2);
        Panel p1 = get(pos1);
        Panel p2 = get(pos2);

        p1.swap(pos2);
        p2.swap(pos1);
        set(p1, pos2);
        set(p2, pos1);
    }

    private void removeMatches(){
        HashSet<Pos> matches = new HashSet<>();
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
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
        return get(pos).fallTimer > TIME_TO_FALL;
    }

    private int uniqueMatches(HashSet<Pos> known, ArrayList<Pos> current) {
        int i = 0;
        for(Pos pos : current) {
            if(!known.contains(pos)) i++;
        }
        return i;
    }

    private void searchFalling(){
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                Pos p0 = new Pos(j, i);
                if(isInBounds(p0.add(0,-1)) && get(p0.add(0,-1)).pt == PanelType.NONE) {
                    if(!isAlreadyFalling(p0)){
                        get(p0).fallTimer = 0f;
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
                    PanelType pt = get(pos).pt;
                    while(isInBounds(pos.add(0,-1)) && get(pos.add(0,-1)).pt == PanelType.NONE) {
                        pos = pos.add(0,-1);
                    }
                    swap(x, y, pos.x, pos.y); // maybe pos.y, maybe pos.y+1?
                    get(pos).fallTimer = -1;
                }
            }
        }
    }

    private boolean isAlreadyFalling(Pos pos){
        return get(pos).fallTimer != -1;
    }

    private ArrayList<Pos> getMatches(Pos p0, Pos dir, ArrayList<Pos> matches) {
        PanelType pt = get(p0).pt;
        if(pt == PanelType.NONE) return matches;
        matches.add(p0);
        Pos p1 = p0.add(dir);
        if(isInBounds(p1) && get(p1).pt == pt) getMatches(p1, dir, matches);
        return matches;
    }

    private boolean isInBounds(Pos pos) {
        return pos.x >= 0 && pos.x < GRID_WIDTH && pos.y >= 0 && pos.y < GRID_HEIGHT;
    }

    private Panel get(Pos pos){
//        return gridNew.get(pos);
        return grid[pos.y][pos.x];
    }
    private Panel get(int x, int y) {
        return get(new Pos(x, y));
    }

    public void set(Panel panel, int x, int y) {
        grid[y][x] = panel;
    }

    public void set(Panel panel, Pos pos) {
        grid[pos.y][pos.x] = panel;
    }

    public void set(PanelType pt, int x, int y){
        set(new Panel(pt, new Pos(x,y)), x, y);
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
                drawPanel(j, i, sb, textures);
            }
        }
    }

    private void drawPanel(int x, int y, SpriteBatch sb, TextureAtlas textures) {
        try {
            get(x, y).draw(sb, textures);
        } catch (NullPointerException e) {
            System.out.println("Error! at (" + x + ", " + y + ")");
            int realX = Grid.getPanelPosX(x);
            int realY = Grid.getPanelPosY(y);
            sb.draw(textures.findRegion("error"), realX, realY, PANEL_SIZE, PANEL_SIZE);
        }
    }
    private void checkCollisions(){
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                Pos p = get(j, i).pos;
                for (int k = 0; k < GRID_HEIGHT; k++) {
                    for (int l = 0; l < GRID_WIDTH; l++) {
                        if(l == j && k == i) continue;
                        if(get(l, k).pos == p){
                            System.out.println("Collision at " + p);
                            System.out.println("x1,y1,x2,y2 = " + Arrays.toString(new int[]{j,i,l,k}));
                        }
                    }
                }
            }
        }
    }

    public static int getPanelPosX(float x) {
        return (int) ((x * PANEL_SIZE) + START_X);
    }
    public static int getPanelPosY(float y) {
        return (int) ((y * PANEL_SIZE) + START_Y);
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
