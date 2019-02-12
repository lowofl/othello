package Game;

import java.util.*;
import javafx.util.Pair;

public class Tree {
    private Node root;
    private int searchDepth;
    public Tree(Game rootData, int searchDepth) {
        root = new Node(rootData);
        root.data = rootData;
        root.children = new ArrayList<>();
        this.searchDepth = searchDepth;
    }

    public int getSearchDepth() {
        return searchDepth;
    }

    public static class Node {
        private Game data;
        private Node parent;
        private List<Node> children;
        private boolean exploredChildren = false;

        public Node(Game data){
        	this.data = data;
            children = new ArrayList<>();
        }
        public Game getGame(){
            return data;
        }
    }

    public Pair<Node, Integer> minimax(Node position, int depth, int a, int b, boolean whitePlayer) {

        if(!position.exploredChildren && depth != 0) {
    		retrieveChildren(position, whitePlayer);
    		position.exploredChildren = true;
    	}


        Pair<Node, Integer> p  = new Pair<>(position, position.getGame().getScoreWhite() - position.getGame().getScoreBlack());


        if(depth == 0 || position.children.size() == 0) {
            //return score of node #TODO Discuss scoring
            return p;
        }

        Pair<Node, Integer> bp;
     	if(whitePlayer) {
    		int maxEval = -99999999;
    		for(Node child: position.children) {

    			bp = minimax(child,depth-1, a, b, false);

    			maxEval = Math.max(maxEval, bp.getValue());
    			System.out.println(bp.getValue() + " " + whitePlayer);
    			if(getSearchDepth() == depth && maxEval == bp.getValue()){
    			    p = bp;
                }

                if(getSearchDepth()-depth == 1 && maxEval == bp.getValue()){
                    p = new Pair<>(position,maxEval);
                }

    			a = Math.max(a, bp.getValue());
    			if(b <= a)
    					break;
    		}
    		return p;
    	}else {
    		int minEval = 99999999;
    		for(Node child: position.children) {
    			bp = minimax(child, depth-1, a, b, true);
                System.out.println(bp.getValue() + " " + whitePlayer);
                minEval = Math.min(minEval, bp.getValue());
                if(getSearchDepth() == depth && minEval == bp.getValue()){
                    p = bp;
                }
                if(getSearchDepth()-depth == 1 && minEval == bp.getValue()){
                    p = new Pair<>(position,minEval);
                }

    			b = Math.min(b, bp.getValue());
    			if(b <= a)
    					break;
    		}
    		return p;
    	}
    }

    private void retrieveChildren(Node node, boolean whitePlayer) {
    	Game gameState = node.data;
    	int player = -1; // assume black
    	if(whitePlayer) { // if white change color
    		player = 1;
    	}
    	ArrayList<int []> moves = new ArrayList<>();
    	for(int i = 1; i < 9; i++) {
    		for(int j = 1; j <9; j++){
    			if(gameState.checkMoveLegality(i, j, player)) {
    			    boolean betterMove = false;
                    int[] move = {i, j};
                    int o = 0, fin = 0;
                    if(moves.isEmpty()){
                        moves.add(move);
                    }else{
                        for(int[] m : moves){
                            if(node.getGame().scoreFromMove(m[0],m[1],player) < node.getGame().scoreFromMove(move[0],move[1],player) && !betterMove){
                                fin = o;
                                betterMove = true;
                            }
                            o++;
                        }
                        if(!betterMove){
                            moves.add(move);
                        }else{
                            moves.add(fin,move);
                        }
                    }
                }
    		}
    	}


        int[][] board = node.getGame().getBoard(); //weird solution, but if i don't copy the values, a change to any board will change all nodes
        ArrayList<int[][]> boards = new ArrayList<>();
        for(int i = 0; i < moves.size();i++){
            int[][] b1 = new int[10][10];
            for (int u = 0; u < 10; u++) {
                for (int k = 0; k < 10; k++)
                    b1[u][k] = board[u][k];
            }
            boards.add(b1);
        }
        for(int i = 0; i < moves.size();i++){
            Node child = new Node(new Game(boards.get(i)));
            System.out.print("mv " + moves.get(i)[0] + " " + moves.get(i)[1] + " " + whitePlayer + " " +child.getGame().scoreFromMove(moves.get(i)[0],moves.get(i)[1],player) + " -- ");
            child.getGame().makeMove(moves.get(i)[0],moves.get(i)[1],player);
            //child.getGame().printBoard(player);

            node.children.add(child);
        }
        System.out.println();

    }
}