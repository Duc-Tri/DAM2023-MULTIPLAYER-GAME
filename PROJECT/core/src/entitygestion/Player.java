package entitygestion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.bagarre.Game;
import com.mygdx.client.ClientRetrievePlayer;


public class Player implements Entity{
    private  Game game;
    private Rectangle box;
    private TextureAtlas textureAtlas;
    private TextureRegion textureRegion;
    public Sprite sprite;
    private float x = 0;
    private float y = 0;
    public String uniqueID;
    public String serverUniqueID;
    public Color spriteColor; // from unique ID
    private int spriteColorInt;
    private String    findRegion = "UP_1";;
    private String textureAtlasPath = "tiny_16x16.atlas";
    private float scale = 2.0f;
    int compteurUp = 0;
    int compteurDown = 0;
    int compteurLeft = 0;
    int compteurRight = 0;



    public Player(Game game) {
        this.game = game;
        final int R = 10 + (int) (Math.random() * 90);
        final int V = 10 + (int) (Math.random() * 90);
        final int B = 10 + (int) (Math.random() * 90);
        uniqueID = "player" + R + V + B;
        spriteColor = new Color((float) R / 100, (float) V / 100, (float) B / 100, 1);
        this.spriteColorInt = spriteColor.toIntBits();
        System.out.println(uniqueID + " ■■■■■■■■■■■");
    }

    public Player(Player retrievePlayer) {
        setGame(retrievePlayer.getGame());
        setSprite(retrievePlayer.getSprite());
        setBox(retrievePlayer.getBox());
        setFindRegion(retrievePlayer.getFindRegion());
        setScale(retrievePlayer.getScale());
        setTextureAtlasPath(retrievePlayer.getTextureAtlasPath());
        setSpriteColorInt(retrievePlayer.getSpriteColorInt());
        setUniqueID(retrievePlayer.getUniqueID());
        setServerUniqueID(retrievePlayer.getServerUniqueID());
        setX(retrievePlayer.getX());
        setY(retrievePlayer.getY());
        setTextureRegion(retrievePlayer.getTextureRegion());
        this.initializeSprite();
//        initializeSprite(retrievePlayer.initializeSprite());

    }


    private String genID() {
        return "0";
    }

    public void initializeSprite() {
        box = new Rectangle(x, y, 0, 0);
        textureAtlas = new TextureAtlas(Gdx.files.internal(textureAtlasPath));

        textureRegion = textureAtlas.findRegion(findRegion);
        sprite = new Sprite(textureRegion);
        sprite.scale(scale);
        spriteColor = new Color(getSpriteColorInt());
        sprite.setColor(spriteColor);
    }

    public Sprite getSprite() {
        return sprite;
    }


    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setX(float x) {
        this.x = x;
        box.setX(x);
        sprite.setX(x);
    }

    public void setY(float y) {
        this.y = y;
        box.setY(y);
        sprite.setY(y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void animate(String string) {
        float tempSpriteX = sprite.getX();
        float tempSpriteY = sprite.getY();

        if (string.contentEquals("LEFT")) {
        //    System.out.println("SWITCH LEFT " + compteurLeft);
            if (compteurLeft == 12) {
                compteurLeft = 0;
            }
            compteurDown = 0;
            compteurUp = 0;
            compteurRight = 0;
            compteurLeft++;

           findRegion = "LEFT_" + compteurLeft;


            textureRegion = textureAtlas.findRegion("LEFT_" + compteurLeft);
        }

        if (string.contentEquals("RIGHT")) {
       //     System.out.println("SWITCH RIGHT " + compteurRight);
            if (compteurRight == 12) {
                compteurRight = 0;
            }
            compteurDown = 0;
            compteurUp = 0;
            compteurLeft = 0;
            compteurRight++;


            findRegion = "RIGHT_" + compteurRight;
            textureRegion = textureAtlas.findRegion("RIGHT_" + compteurRight);
        }

        if (string.contentEquals("UP")) {
       //     System.out.println("SWITCH UP " + compteurUp);
            if (compteurUp == 12) {
                compteurUp = 0;
            }
            compteurDown = 0;
            compteurRight = 0;
            compteurLeft = 0;
            compteurUp++;


                findRegion = "UP_" + compteurUp;
            textureRegion = textureAtlas.findRegion("UP_" + compteurUp);
        }

        if (string.contentEquals("DOWN")) {
      //      System.out.println("SWITCH DOWN " + compteurDown);
            if (compteurDown == 12) {
                compteurDown = 0;
            }
            compteurUp = 0;
            compteurRight = 0;
            compteurLeft = 0;
            compteurDown++;

                findRegion = "DOWN_" + compteurDown;
            textureRegion = textureAtlas.findRegion("DOWN_" + compteurDown);
        }

        Sprite tempSprite = new Sprite(textureRegion);
        tempSprite.setX(tempSpriteX);
        tempSprite.setY(tempSpriteY);
        tempSprite.scale(2.0f);
        tempSprite.setColor(spriteColor);
        this.setSprite(tempSprite);

    }

    public Rectangle getBox() {
        return box;
    }

    public void setBox(Rectangle box) {
        this.box = box;
    }

    public  void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getServerUniqueID() {
        return serverUniqueID;
    }

    public void setServerUniqueID(String serverUniqueID) {
        this.serverUniqueID = serverUniqueID;
    }
    public Color getSpriteColor() {
        return spriteColor;
    }
    public void setSpriteColor(Color spriteColor) {
        this.spriteColor = spriteColor;
        sprite.setColor(spriteColor);
        setSpriteColorInt(spriteColor.toIntBits());
    }
    public void setSpriteColorInt(int spriteColorInt) {
        this.spriteColorInt = spriteColorInt;
    }
    public int getSpriteColorInt() {
        return spriteColorInt;
    }

    public String getFindRegion() {
        return findRegion;
    }

    public void setFindRegion(String findRegion) {
        this.findRegion = findRegion;
    }

    public String getTextureAtlasPath() {
        return textureAtlasPath;
    }

    public void setTextureAtlasPath(String textureAtlasPath) {
        this.textureAtlasPath = textureAtlasPath;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getCompteurUp() {
        return compteurUp;
    }

    public void setCompteurUp(int compteurUp) {
        this.compteurUp = compteurUp;
    }

    public int getCompteurDown() {
        return compteurDown;
    }

    public void setCompteurDown(int compteurDown) {
        this.compteurDown = compteurDown;
    }

    public int getCompteurLeft() {
        return compteurLeft;
    }

    public void setCompteurLeft(int compteurLeft) {
        this.compteurLeft = compteurLeft;
    }

    public int getCompteurRight() {
        return compteurRight;
    }

    public void setCompteurRight(int compteurRight) {
        this.compteurRight = compteurRight;
    }

    public void createBox() {
        setBox(new Rectangle(x,y,0,0));
    }

    public void createSprite() {

        setSprite(new Sprite());
        getSprite().setPosition(x,y);
    }

    public float getRealX() {
//        System.out.println("game.getSCREEN_WIDTH()  " + game.getSCREEN_WIDTH());
//        System.out.println("game.getCamera().position.x  " + game.getCamera().position.x);
//        System.out.println("x  " + x);
//        System.out.println("game.getSCREEN_WIDTH()  " + game.getSCREEN_WIDTH());
        float relativePlayerX = x - game.getSCREEN_WIDTH()/2.0f+game.getCamera().position.x;

////
//        System.out.println("x  " + x);
//        System.out.println("relativePlayerX  " + relativePlayerX  + " from   " +x);
        return relativePlayerX;//game.getCamera().position.x+relativePlayerX;
    }

    public float getRealY() {
//        System.out.println("game.getSCREEN_HEIGHT()  " + game.getSCREEN_HEIGHT());
//
//        System.out.println("game.getCamera().position.y  " + game.getCamera().position.y);
//        System.out.println("y  " + y);
        float relativePlayerY = y-game.getSCREEN_HEIGHT()/2.0f+game.getCamera().position.y+10;
//        System.out.println("y  " + y);
//        System.out.println("relativePlayerY  " + relativePlayerY + " from   " +y);
        return relativePlayerY;//game.getCamera().position.y+relativePlayerY;
    }

    public void setXFromRealX(float parseFloat) {

        float temp = parseFloat-game.getCamera().position.x+game.getSCREEN_WIDTH()/2.0f+10;
//        System.out.println("tempX " + temp + " from  " +parseFloat);

        setX(temp);
    }

    public void setYFromRealY(float parseFloat) {

        float temp = parseFloat-game.getCamera().position.y+game.getSCREEN_HEIGHT()/2.0f;
//        System.out.println("tempY " + temp);
        setY(temp);
    }

    public void createMissingMate(String[] sArr, Player player) {

        boolean[] finded = new boolean[sArr.length];
        int cpt0 = sArr.length;
        for(int i0 = 0; i0 < sArr.length ; i0++){
            for(int i1 = 0  ; i1<game.getMates().length; i1++){
               // System.out.print("sArr[i0]  " + sArr[i0]);
              //  System.out.println("    mates[i1].getServerUniqueID()  " + player.getGame().getMates()[i1].getServerUniqueID());
                if(sArr[i0].equalsIgnoreCase(player.getGame().getMates()[i1].getServerUniqueID())){
                //    System.out.println("Finded");
                    finded[i0] = true;
                    cpt0--;
                }
            }
        }
        System.out.println("cpt0 "+ cpt0);
        String[] haveToCreate  =  new String[cpt0];
        int cpt1= 0;
        for(int i = 0 ; i < sArr.length; i++){
            if(!finded[i]){
                haveToCreate[cpt1] = sArr[i];
                cpt1++;
            }

        }


        Mates[] tempNewMates = new Mates[player.getGame().getMates().length+cpt0];
        System.out.println("tempNewMates.length "+ tempNewMates.length);
        int cpt2=0;
        for(int i0 = 0; i0 <tempNewMates.length; i0++){
            if(i0<player.getGame().getMates().length){
                tempNewMates[i0] = player.getGame().getMates()[i0];
            }else{
                Player tempPlayer = new Player(game);
                tempPlayer.setServerUniqueID(haveToCreate[cpt2]);
                tempNewMates[i0] = new Mates(ClientRetrievePlayer.retrievePlayer(tempPlayer));
                cpt2++;
            }
        }
        player.getGame().setMates(tempNewMates);



    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void createMates(String[] sArr, Player player) {
        if(game.getMates() == null || game.getMates().length==0){
            game.setMates(new Mates[sArr.length]);
            System.out.println("mates.lenth  " + game.getMates().length);

            for(int i = 0 ; i < game.getMates().length; i++){
                Player tempPlayer = new Player(game);
                tempPlayer.setServerUniqueID(sArr[i]);
                tempPlayer.setGame(game);
                tempPlayer = ClientRetrievePlayer.retrievePlayer(tempPlayer);
                Mates tempMate = new Mates(tempPlayer);
                game.getMates()[i] = tempMate;

            }

        }
    }
}
