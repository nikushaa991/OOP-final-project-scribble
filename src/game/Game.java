package game;

public class Game {
    public Game(){}
    //public void addPlayer(id, socket){}
    //public

    private class Player{
        private int id;
        private int score;
        private int order;
        //socket

        public Player(int order, int id){
            this.id = id;
            this.order = order;
            score = 0;
            //socket = idk
        }

        public int getScore(){return score;}
        public int getId(){return id;}
        public int getOrder(){return order;}
        //getSocket
        public void increaseScore(int score){
            this.score += score;
        }
    }

}
