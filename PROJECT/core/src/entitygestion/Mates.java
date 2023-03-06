package entitygestion;

import com.mygdx.bagarre.Game;

public class Mates extends Player implements Runnable{

    int timeout = 99999999;
    private int status;

    public Mates(Game game) {
        super(game);
    }

    public Mates(Player retrievePlayer) {
        super(retrievePlayer);
        System.out.println("retrievePlayer.getServerUniqueID() " + retrievePlayer.getServerUniqueID());
    }


    @Override
    public void run() {
        this.status =1;
        int random  = (int) (Math.random()*100);
        System.out.println("debut tache " + Thread.currentThread().getName());
        for(int i = 0  ; i < 50; i++){
            System.out.println("Hello  thread numéro random -> " + random );
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("fin tache");
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}