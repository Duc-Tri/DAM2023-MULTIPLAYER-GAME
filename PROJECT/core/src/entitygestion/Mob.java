package entitygestion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Mob implements Entity{

    private static TextureAtlas textureAtlas;
    private TextureRegion textureRegion;
    public Sprite sprite;
    public String mobID;
    public Color spriteTint;
    public Rectangle rect;

    private float x=0;
    private float y=0;
    private float width=0;
    private float height=0;

    public Mob() {
        this.rect = new Rectangle(this.x,this.y,this.width,this.height);
    }

    public Mob(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rect = new Rectangle(x,y,width,height);
    }

    public Mob(Rectangle rect) {
        this.rect = rect;
        this.x =  rect.getX();
        this.y =  rect.getY();
        this.width =  rect.getWidth();
        this.height =  rect.getHeight();
    }

    @Override
    public void initializeSprite() {
       // box = new Rectangle(0, 0, 0, 0);
//        batch = new SpriteBatch();

        if (textureAtlas == null)
            textureAtlas = new TextureAtlas(Gdx.files.internal("tiny_16x16.atlas"));

        textureRegion = textureAtlas.findRegion("UP_1");
        sprite = new Sprite(textureRegion);
        sprite.scale(2.0f);
        sprite.setColor(spriteTint);
    }

    @Override
    public void animate(String string) {

    }


    public String getMobID(){
        return mobID;
    }


}
