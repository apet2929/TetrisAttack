package com.apet2929.tetris_attack;

public class Animation {
    public Pos start;
    public Pos end;
    public float t;
    public float endT;
    private Runnable onFinish;

    public Animation(Pos start, Pos end, float endT) {
        this.t = 0;
        this.start = start;
        this.end = end;
        this.endT = endT;
    }

    public Animation(Pos start, Pos end, float endT, Runnable onFinish) {
        this.t = 0;
        this.endT = endT;
        this.start = start;
        this.end = end;
        this.onFinish = onFinish;
    }

    public void update(float dt){
        if(!this.isFinished()){
            t += dt;
        }
        if(this.isFinished() && onFinish != null && t != -1){
            onFinish.run();
            t = -1;
        }

    }

    public boolean isFinished(){
        return t >= endT || t == -1;
    }

    public float getX(){
        return lerp(start.x, end.x, clamp(t / endT, 0, 1));
    }

    public float getY(){
        return lerp(start.y, end.y, clamp(t / endT, 0, 1));
    }

    private float lerp(float start, float end, float t) {
        return start + t * (end - start);
    }

    private float clamp(float val, float min, float max){
        return Math.min(Math.max(val, min), max);
    }
}
