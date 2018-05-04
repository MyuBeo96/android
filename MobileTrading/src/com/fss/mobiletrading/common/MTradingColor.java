package com.fss.mobiletrading.common;

import android.graphics.Color;

public class MTradingColor {
    public static int highlight_color = Color.parseColor("#80dddddd");
    public static int highlight_backgroundcolor = Color.parseColor("#80C2C2C2");


    public enum PriceColor {
        hitCeil("hitCeil", Color.parseColor("#ff00ff")), hitFloor("hitFloor",
                Color.parseColor("#66ccff")), unchange("unchange", Color
                .parseColor("#FFCC00")), gainer("gainer", Color
                .parseColor("#00ff00")), loser("loser", Color
                .parseColor("#ff0000")), vol_color("vol_color", Color
                .parseColor("#D0D0D6"));
        private String name;
        private int color;

        private PriceColor(String name, int color) {
            this.name = name;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public int getColor() {
            return color;
        }

        public static int getColorByName(String name) {
            for (PriceColor color : PriceColor.values()) {
                if (color.getName().equals(name)) {
                    return color.getColor();
                }
            }
            return Color.WHITE;
        }
    }

}
