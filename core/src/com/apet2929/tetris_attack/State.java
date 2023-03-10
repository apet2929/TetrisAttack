package com.apet2929.tetris_attack;

public abstract class State {

    public State() {

    }

    public void onEnter() {}

    public void onExit() {}

    public void render() {}

    public void update() {}

    public void handleInput() {}

}
