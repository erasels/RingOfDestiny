package RingOfDestiny.vfx.combat;


import RingOfDestiny.RingOfDestiny;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;


public class CutterEffect extends AbstractGameEffect {
    private float x;
    private static TextureAtlas.AtlasRegion img = null;
    private float y;
    private static Texture[] ZERO = new Texture[100];
    private int time = 0;
    private float timer = 0;
    private float playSpeed = 0;
    private int gifLength = 0;
    private int gifWidth = 0;
    private int gifHeight = 0;
    private int imageWidth = 160;
    private int imageHeight = 171;
    private float gifScale = 2.5f;
    private int gifFrame = 15;
    private float Frameduration = 32.0f;
    private String  id = "Cutter";
    private boolean  soundPlayed  = false;


    public CutterEffect(float fixX,float fixY) {
        for (int i = 0; i < gifFrame; i++) {
            ZERO[i] = new Texture(String.format(RingOfDestiny.assetPath("img/vfx/")+ id +"/frame_" + "%02d.png", new Object[]{Integer.valueOf(i)}));
        }

        this.renderBehind = false;
        img = new TextureAtlas.AtlasRegion(ZERO[0],0,0,imageWidth,imageHeight);
        this.startingDuration = 2.0F;
        this.duration = this.startingDuration;
        this.scale = Settings.scale * gifScale;

        this.x = fixX - img.packedWidth / 2.0F;//.WIDTH * 0.5F - img.packedWidth / 2.0F +Settings.WIDTH * fixX;
        this.y = fixY - img.packedHeight / 2.0F;//-img.packedHeight / 2.0F +Settings.HEIGHT * fixY;

        this.color = Color.WHITE.cpy();
        this.playSpeed = Frameduration;
        this.gifLength = gifFrame;
        this.gifWidth = imageWidth;
        this.gifHeight = imageHeight;

    }

    public void update() {
        super.update();
        if (time >= gifFrame-1){
            this.isDone = true ;
            this.duration = 0;
        }else {
            this.isDone = false;
        }



        timer += (Gdx.graphics.getDeltaTime()* this.playSpeed) ;


        this.y += (Gdx.graphics.getDeltaTime()* this.playSpeed) *5.5F;


        if(this.gifLength-1 <= 0){
            time = 0;
        }else{
            time =  (int)timer % (this.gifLength);
        }



        img = new TextureAtlas.AtlasRegion(ZERO[time],0,0,this.gifWidth,this.gifHeight);



        if(!soundPlayed){
            CardCrawlGame.sound.playA(RingOfDestiny.makeID("Cutter"), MathUtils.random(0.0F, -0.15F));
            soundPlayed = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor( Color.WHITE.cpy());
        sb.draw(img, this.x, this.y, img.packedWidth / 2.0f, img.packedHeight / 2.0f, img.packedWidth, img.packedHeight, this.scale, this.scale, 0.0f);
    }
    public void dispose() {}
}

