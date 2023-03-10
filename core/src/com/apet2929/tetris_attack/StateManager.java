package com.apet2929.tetris_attack;


import java.util.ArrayList;

public class StateManager {
    ArrayList<State> queue;
    public StateManager() {
        queue = new ArrayList<State>();
    }

    public void push(State state) {
        queue.add(state);
        state.onEnter();
    }

    public void pop() {
        queue.get(queue.size()-1).onExit();
        queue.remove(queue.size()-1);
    }

    public void run() {
        queue.get(queue.size()-1).update();
        if (queue.size() > 1) {
            for (int i = 2; i > 0; i--) {
                queue.get(queue.size() - i).render();
            }
        }
        else {
            queue.get(queue.size()-1).render();
        }
        queue.get(queue.size()-1).handleInput();
    }
}
