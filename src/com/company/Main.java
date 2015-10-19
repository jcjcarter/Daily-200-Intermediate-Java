package com.company;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private final static char EMPTY_SPACE = '.';

    public static class Tile {
        private final char contents;
        private final Rectangle tileRectangle;

        public Tile(Rectangle dims, char c){
            contents = c;
            tileRectangle = dims;
        }

        @Override
        public String toString(){
            return String.format("%dx%d tile of character '%c' located at (%d,%d)",tileRectangle.width,tileRectangle.height,contents,tileRectangle.x,tileRectangle.y);
        }
    }

    private final char[][] tileArray;
    private final boolean[][] partOfTile;

    public Main(char[][] tiles){
        tileArray = tiles;
        partOfTile = new boolean[tileArray.length][tileArray[0].length];
    }

    public List<Tile> findTiles(){
        for(int i = 0; i < partOfTile.length;i++){
            for(int j = 0; j < partOfTile[0].length; j++){
                partOfTile[i][j] = false;
            }
        }

        List<Tile> foundTiles = new ArrayList<>();
        for(int i = 0; i < tileArray.length;i++){
            for(int j = 0; j < tileArray[0].length; j++){
                if(!partOfTile[i][j] && tileArray[i][j]!=EMPTY_SPACE){
                    foundTiles.add(evaluateTile(i,j));
                }
            }
        }

        return foundTiles;
    }

    private Tile evaluateTile(int r, int c){
        final char tileChar = tileArray[r][c];

        int maxCollumn = c;
        while(maxCollumn < tileArray[0].length && tileArray[r][maxCollumn] == tileChar){
            maxCollumn++;
        }

        int maxRow = r;
        while(maxRow < tileArray.length && tileArray[maxRow][c] == tileChar){
            maxRow++;
        }

        for(int i = r; i < maxRow;i++){
            for(int j = c; j < maxCollumn; j++){
                partOfTile[i][j] = true;
            }
        }

        return new Tile(new Rectangle(r,c,maxRow-r,maxCollumn-c),tileChar);
    }
    public static void main(String args[]) throws IOException {
        File f = new File(args[0]);
        BufferedReader read = new BufferedReader(new FileReader(f));

        String[] dimensions = read.readLine().split(" ");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);

        char[][] grid = new char[width][height];
        for(int h = 0; h < height;h++){
            String line = read.readLine();
            for(int w = 0; w < width;w++){
                grid[w][h] = line.charAt(w);
            }
        }

        MetroTileReader reader = new MetroTileReader(grid);
        for(Tile t:reader.findTiles()){
            System.out.println(t);
        }
    }
}
