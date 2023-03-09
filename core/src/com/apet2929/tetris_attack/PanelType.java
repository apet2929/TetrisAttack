package com.apet2929.tetris_attack;

public enum PanelType {
    HEART("heart_panel"),
    DIAMOND("diamond_panel"),
    SQUARE("square_panel"),
    TRIANGLE("triangle_panel"),
    INVERTED_TRIANGLE("inverted_triangle_panel"),
    STAR("star_panel"),
    NONE("empty_panel");

    public final String asset;
    PanelType(String assetName) {
        this.asset = assetName;
    }
}
