package RingOfDestiny.monster.Ending;

import RingOfDestiny.RingOfDestiny;
import RingOfDestiny.actions.unique.ChangeStageAction;
import RingOfDestiny.actions.unique.CustomWaitAction;
import RingOfDestiny.cards.Colorless.*;
import RingOfDestiny.powers.Monster.Ending.ReflectionPower;
import RingOfDestiny.powers.Monster.Ending.SelfDetonatePower;
import RingOfDestiny.vfx.combat.*;
import basemod.abstracts.CustomMonster;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.AnimateJumpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import com.esotericsoftware.spine.AnimationState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import java.util.Iterator;

import RingOfDestiny.vfx.HealVerticalLineButHorizontalEffect;
import RingOfDestiny.helpers.*;

import com.esotericsoftware.spine.Bone;
import RingOfDestiny.vfx.AwakenedEyeParticle;
import java.util.ArrayList;
import com.megacrit.cardcrawl.vfx.AwakenedWingParticle;



public class Rita extends CustomMonster {
   public static final String ID = RingOfDestiny.makeID("Rita");
   private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
   public static final String NAME = monsterStrings.NAME;
   public static final String[] MOVES = monsterStrings.MOVES;
   public static final String[] DIALOG = monsterStrings.DIALOG;
   public static final String MURDER_ENCOUNTER_KEY = RingOfDestiny.makeID("Rita");
   private static final int HP_MIN = 48;
   private static final int HP_MAX = 54;
   private static final int A_2_HP_MIN = 50;
   private static final int A_2_HP_MAX = 56;
   private static final float HB_X = -8.0F;
   private static final float HB_Y = 10.0F;
   private static final float HB_W = 230.0F;
   private static final float HB_H = 240.0F;


   private boolean saidPower = false;
   private boolean talky = true;

    private int moveCount = 0;
    private int turnCounter = 0;
    private int formAmount = 0;
    private boolean isFirstMove = true;



    private int repuukenHitCount = 3;
    private int ExecutionHitCount = 10;
    private int DestructionHitCount = 15;
    private int HeavenHitCount = 3;
    private int PhoenixHitCount = 5;
    private int JudgementHitCount = 15;
    private int CutterHitCount = 2;


    private static final String[] PMTEXT = CardCrawlGame.languagePack.getUIString(RingOfDestiny.makeID("RitaPowermeter")).TEXT;
    public int powerMeter = 0;
    public int displayPowerMeter = 0;
    public final int maxPowerMeter = 1000;
    private Hitbox pmhb;
    private Texture pixel;

    public boolean superMoves = false;

    private Bone eye;
    private boolean animateParticles = false;
    private float fireTimer = 0.0F;
    private ArrayList<AwakenedWingParticle> wParticles = new ArrayList();

    private int timeLimit = 4;

    public boolean killStatue = false;


  public Rita() {
     super(NAME, ID, 160, 0.0F, -15.0F, 300.0F, 350.0F, null, 0.0F, 0.0F);

      this.pixel = AssetLoader.loadImage(RingOfDestiny.assetPath("img/misc/filledpixel.png"));
      this.pmhb = new Hitbox(this.drawX - this.hb.width / 2F, this.hb.y + this.hb.height, this.hb.width, 20F * Settings.scale);

    if (AbstractDungeon.ascensionLevel >= 7) {
       setHp(180);
    } else{
        setHp(160);
    }


      if (AbstractDungeon.ascensionLevel >= 19) {
          this.timeLimit = 3;
      } else{
          this.timeLimit = 4;
      }

//进阶4加伤害
      if (AbstractDungeon.ascensionLevel >= 4) {
         this.damage.add(new DamageInfo(this, 30));
         this.damage.add(new DamageInfo(this, 10));
          this.damage.add(new DamageInfo(this, 50));
          this.damage.add(new DamageInfo(this, 2));
          this.damage.add(new DamageInfo(this, 25));
          this.damage.add(new DamageInfo(this, 67)); //5
          this.damage.add(new DamageInfo(this, 3));
          this.damage.add(new DamageInfo(this, 15));
          this.damage.add(new DamageInfo(this, 15));
          this.damage.add(new DamageInfo(this, 3));//9
    } else {
         this.damage.add(new DamageInfo(this, 25));
         this.damage.add(new DamageInfo(this, 8));
          this.damage.add(new DamageInfo(this, 45));
          this.damage.add(new DamageInfo(this, 2));
          this.damage.add(new DamageInfo(this, 25));
          this.damage.add(new DamageInfo(this, 67));//5
          this.damage.add(new DamageInfo(this, 3));
          this.damage.add(new DamageInfo(this, 12));
          this.damage.add(new DamageInfo(this, 12));
          this.damage.add(new DamageInfo(this, 3));//9
    }


      this.type = AbstractMonster.EnemyType.BOSS;
      this.dialogX = -50.0F * Settings.scale;
      this.dialogY = 50.0F * Settings.scale;


             loadAnimation("RingOfDestiny/monsters/Rita/Rita.atlas", "RingOfDestiny/monsters/Rita/Rita.json", 1.0F);
             //loadAnimation("RingOfDestiny/DB/fyra/WhiteHair.spine", "RingOfDestiny/DB/fyra/1.json", 1.0F);

             AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
             e.setTime(e.getEndTime() * MathUtils.random());
             this.stateData.setMix("Hit", "Idle", 0.1F);
             this.stateData.setMix("Intimidate", "Idle", 0.1F);
             this.stateData.setMix("Idle", "Idle_3", 0.1F);
             this.flipHorizontal = true;

      this.eye = this.skeleton.findBone("Eye");

   }


   //----------------------------------------------------------------------------------------------------

    @Override
    public void refreshIntentHbLocation() {
        super.refreshIntentHbLocation();
        if(this.pmhb != null) {
            this.intentHb.y  += this.pmhb.height * 1.5F;
            this.intentHb.cY += this.pmhb.height * 2.5F;//----------调整意图图标位置

        }
    }


    public void changeState(String stateName) {
        switch (stateName) {
            case "Intimidate":
                this.state.setAnimation(0, "Intimidate", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
            case "Form2":
                this.state.setAnimation(0, "Idle", false);
                this.state.addAnimation(0, "Idle_3", true, 0.0F);
                break;
        }
    }


    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ChangeStageAction("Black1"));
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        CardCrawlGame.music.playTempBGM(RingOfDestiny.makeID("music16"));
        (AbstractDungeon.getCurrRoom()).cannotLose = true;
    }
    

       public void takeTurn() {
             int temp;

//第一阶段------------------------------------------------------
             switch (this.nextMove) {
                   case 2://威吓
                       CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Intimidate"));
                       AbstractDungeon.actionManager.addToTop(new ChangeStateAction(this, "Intimidate"));

                       AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.RED_TEXT_COLOR, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.2F));

                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
                       if (AbstractDungeon.ascensionLevel >= 19) {
                           AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Misstep(), 1, true, true, false, Settings.WIDTH * 0.2F, Settings.HEIGHT / 2.0F));
                           AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Slip(), 1, true, true, false, Settings.WIDTH * 0.2F, Settings.HEIGHT / 2.0F));
                           AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Blockade(), 1));
                       }else {
                           AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Misstep(), 1, true, true, false, Settings.WIDTH * 0.2F, Settings.HEIGHT / 2.0F));
                           AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Slip(), 1, true, true, false, Settings.WIDTH * 0.2F, Settings.HEIGHT / 2.0F));
                       }
                       break;

                 case 1://普通投
                     //AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.5F));
                     AbstractDungeon.actionManager.addToBottom(new VFXAction(new HitHeavyEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY), 0.0F));
                     AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.NONE));

                     this.powerMeter += 60 ;
                     if (this.powerMeter > 1000){
                         this.powerMeter = 1000;
                     }
                     break;


                   case 0://烈风拳
                       CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Repuuken"));
                       //AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.repuukenHitCount), 0.25F));

                       AbstractDungeon.actionManager.addToBottom(new VFXAction(new Repuuken0Effect(this.hb.x,this.hb.y), 0.0F));
                       AbstractDungeon.actionManager.addToBottom(new VFXAction(new Repuuken1Effect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.y,this.hb.x,this.hb.y), 0.0F));
                       AbstractDungeon.actionManager.addToBottom((new CustomWaitAction(0.16f)));

                       for (temp = 0; temp < this.repuukenHitCount; temp++) {
                           AbstractDungeon.actionManager.addToBottom(new VFXAction(new HitHeavyEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY), 0.0F));
                           AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AbstractGameAction.AttackEffect.NONE, true));
                       }
                       this.powerMeter += 60 ;
                       if (this.powerMeter > 1000){
                           this.powerMeter = 1000;
                       }
                       break;

                 case 3://灭族天国
                     CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Heaven"));
                     //AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.HeavenHitCount), 0.25F));
                     AbstractDungeon.actionManager.addToTop((new CustomWaitAction(0.5F)));
                     for (temp = 0; temp < this.HeavenHitCount; temp++) {
                         AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlashHeavyEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY), 0.0F));
                         AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(7), AbstractGameAction.AttackEffect.NONE, true));
                     }
                     displayPowerMeter = 0;
                     this.powerMeter = 0;
                     this.superMoves = false;
                     break;


//第二阶段------------------------------------------------------
                 case 10://黑暗屏障
                     CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Barrier"));
                     AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new FlameBarrierEffect(this.hb.cX, this.hb.cY), 0.5F));
                     AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 50));
                     //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BlurPower(this, 2)));
                     AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 2)));
                     if (AbstractDungeon.ascensionLevel >= 19) {
                         AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ReflectionPower(this, 3)));
                     }else{
                         AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ReflectionPower(this, 2)));
                     }


                     this.powerMeter += 60 ;
                     if (this.powerMeter > 1000){
                         this.powerMeter = 1000;
                     }
                     break;

                 case 11://凯撒波
                     CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Kaiser"));
                     AbstractDungeon.actionManager.addToTop((new CustomWaitAction(0.5F)));
                     AbstractDungeon.actionManager.addToBottom(new VFXAction(new Kaiser0Effect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY,this.hb.x,this.hb.cY), 0.0F));
                     AbstractDungeon.actionManager.addToBottom((new CustomWaitAction(0.08f)));
                     AbstractDungeon.actionManager.addToBottom(new VFXAction(new HitHeavyEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY), 0.0F));
                     //AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.5F));
                     AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(2), AbstractGameAction.AttackEffect.NONE));
                     AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 5)));
                     AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 2)));

                     this.powerMeter += 60 ;
                     if (this.powerMeter > 1000){
                         this.powerMeter = 1000;
                     }
                     break;

                 case 12://丽塔处刑
                     CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Execution"));
                     //AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.ExecutionHitCount), 0.25F));
                     for (temp = 0; temp < this.ExecutionHitCount; temp++) {
                         AbstractDungeon.actionManager.addToBottom(new VFXAction(new HitHeavyEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY), 0.0F));
                         AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(3), AbstractGameAction.AttackEffect.NONE, true));
                     }

                     break;

                 case 13://凯撒凤凰
                     CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_KaiserPhoenix"));

                     //AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.PhoenixHitCount), 0.25F));

                     for (temp = 0; temp < this.PhoenixHitCount; temp++) {
                         switch (MathUtils.random(2)){
                             case 0: AbstractDungeon.actionManager.addToBottom(new VFXAction(new Kaiser0Effect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY,this.hb.x,this.hb.cY), 0.0F));
                             break;
                             case 1: AbstractDungeon.actionManager.addToBottom(new VFXAction(new Kaiser1Effect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY,this.hb.x,this.hb.cY), 0.0F));
                                 break;
                             case 2: AbstractDungeon.actionManager.addToBottom(new VFXAction(new Kaiser2Effect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY,this.hb.x,this.hb.cY), 0.0F));
                                 break;
                         }
                         AbstractDungeon.actionManager.addToBottom((new CustomWaitAction(0.08f)));
                         AbstractDungeon.actionManager.addToBottom(new VFXAction(new HitHeavyEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY), 0.0F));
                         AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(8), AbstractGameAction.AttackEffect.NONE, true));
                         AbstractDungeon.actionManager.addToBottom((new CustomWaitAction(0.15f)));
                     }
                     displayPowerMeter = 0;
                     this.powerMeter = 0;
                     this.superMoves = false;
                     break;
 //第三阶段------------------------------------------------------
                 case 20://灭族切割
                     CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Cutter"));


                     AbstractDungeon.actionManager.addToBottom(new AnimateJumpAction(this));
                     //AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(AbstractDungeon.player.hb.cX + 60.0F * Settings.scale, AbstractDungeon.player.hb.cY, true), 0.1F));
                     AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlashHeavyEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY), 0.0F));
                     AbstractDungeon.actionManager.addToBottom(new VFXAction(new CutterEffect(AbstractDungeon.player.hb.x+AbstractDungeon.player.hb.width,AbstractDungeon.player.hb.cY), 0.0F));
                     AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(4), AbstractGameAction.AttackEffect.NONE));


                     AbstractDungeon.actionManager.addToBottom((new CustomWaitAction(0.1f)));
                     //AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(AbstractDungeon.player.hb.cX - 60.0F * Settings.scale, AbstractDungeon.player.hb.cY, true), 0.1F));
                     AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlashHeavyEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY), 0.0F));
                     AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(4), AbstractGameAction.AttackEffect.NONE));

                     this.powerMeter += 60 ;
                     if (this.powerMeter > 1000){
                         this.powerMeter = 1000;
                     }
                     break;

                 case 21://地狱压杀
                     CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Pressure"));
                     AbstractDungeon.actionManager.addToBottom(new VFXAction(new HeavenEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.y+Settings.HEIGHT *0.1f), 0.0F));
                     AbstractDungeon.actionManager.addToBottom(new VFXAction(new HitHeavyEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY), 0.0F));
                     //AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.5F));
                     AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(5), AbstractGameAction.AttackEffect.NONE));
                     //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new IntangiblePower(this, 1)));
                     break;

                 case 22://终极覆灭
                     CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Destruction"));
                     //AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.DestructionHitCount), 0.25F));
                     for (temp = 0; temp < this.DestructionHitCount-3; temp++) {
                         AbstractDungeon.actionManager.addToBottom(new VFXAction(new HitHeavyEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY), 0.0F));
                         AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(6), AbstractGameAction.AttackEffect.NONE, true));
                     }

                     AbstractDungeon.actionManager.addToBottom(new VFXAction(new CutterEffect(AbstractDungeon.player.hb.x+AbstractDungeon.player.hb.width,AbstractDungeon.player.hb.cY), 0.0F));

                     for (temp = 0; temp < 2; temp++) {
                         AbstractDungeon.actionManager.addToBottom((new CustomWaitAction(0.1f)));
                         AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlashHeavyEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY), 0.0F));

                         AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(6), AbstractGameAction.AttackEffect.NONE, true));
                     }

                     AbstractDungeon.actionManager.addToBottom((new CustomWaitAction(0.2f)));
                     AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlashHeavyEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY), 0.0F));
                     AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(6), AbstractGameAction.AttackEffect.NONE, true));

                     if (this.timeLimit <= 0) {
                         AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 50)));
                         this.timeLimit = 3;
                     }else {
                         if (AbstractDungeon.ascensionLevel >= 19) {
                             AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 6)));
                         } else {
                             AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 5)));
                         }
                     }

                     break;

                 case 23://最终裁决
                     CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Judgement"));
                     animateParticles = true;

                     //AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.JudgementHitCount), 0.25F));

                     for (temp = 0; temp < this.JudgementHitCount; temp++) {
                         AbstractDungeon.actionManager.addToBottom(new VFXAction(new HitHeavyEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY), 0.0F));
                         AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(9), AbstractGameAction.AttackEffect.NONE, true));
                     }

                     if (this.timeLimit <= 0){
                         AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 50)));
                         this.timeLimit = 3;
                     }else {
                         if (AbstractDungeon.ascensionLevel >= 19) {
                             AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 10)));
                         } else {
                             AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 8)));
                         }
                     }

                     displayPowerMeter = 0;
                     this.powerMeter = 0;
                     this.superMoves = false;
                     break;


//------------------------------------------------------复活
                 case 99:

                     //移除负面
                     for (Iterator<AbstractPower> s = this.powers.iterator(); s.hasNext(); ) {
                         AbstractPower p = (AbstractPower)s.next();

                         if (p.type == AbstractPower.PowerType.DEBUFF ||p.ID.equals(RingOfDestiny.makeID("Reflection"))||p.ID.equals(RingOfDestiny.makeID("ArtGallery"))||p.ID.equals("IntangiblePlayer")) {
                             s.remove();
                         }
                     }

                     switch (this.formAmount){
                      case 1:

//复活：第二形态
                         if (AbstractDungeon.ascensionLevel >= 9) {
                             this.maxHealth = 340 + currentHealth;
                         }else {
                             this.maxHealth = 270 + currentHealth;
                         }




                         CardCrawlGame.music.silenceTempBgmInstantly();
                         CardCrawlGame.music.playTempBGM(RingOfDestiny.makeID("music18"));
                          CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Form1B"));
                         AbstractDungeon.actionManager.addToTop((new CustomWaitAction(1.0F)));
                         AbstractDungeon.actionManager.addToTop((new ShoutAction(this, DIALOG[2])));


                         AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new Bronze_Statue( -300.0F, 0.0F,0), true,0));
                         AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new Bronze_Statue( 200.0F, 0.0F,1), true,1));

                         AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SelfDetonatePower(this)));
                         //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtGalleryPower(this,0)));
                         AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BarricadePower(this)));

                          AbstractDungeon.actionManager.addToBottom(new ChangeStageAction("Black2"));
                          AbstractDungeon.actionManager.addToTop(new ChangeStateAction(this, "Form2"));


                         break;
//复活：第三形态
                         case  2:
                             this.timeLimit = 8;
                         this.maxHealth = 280;

                         CardCrawlGame.music.silenceTempBgmInstantly();
                         CardCrawlGame.music.playTempBGM(RingOfDestiny.makeID("music21"));

                             CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Form2B"));
                         AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.RED_TEXT_COLOR, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.2F));
                         AbstractDungeon.actionManager.addToBottom((new ShoutAction(this, DIALOG[6])));

                             AbstractDungeon.actionManager.addToBottom(new ChangeStageAction("Rocket"));
                             AbstractDungeon.actionManager.addToTop(new ChangeStateAction(this, "Form2"));
                             animateParticles = true;
                             break;

//死亡
                          case 3:
                              AbstractDungeon.actionManager.addToTop(new ChangeStateAction(this, "Form2"));
                             (AbstractDungeon.getCurrRoom()).cannotLose = false;
                             this.halfDead = false;
                             die();
                             break;
                     }

                     AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));

                     if(formAmount >=3){
                         (AbstractDungeon.getCurrRoom()).cannotLose = false;
                     }//不会输为假



                     this.halfDead = false;//假死为假
                     break;

//------------------------------------------------------复活


                 }
                 this.timeLimit -= 1;

                      AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
             //轮回行动
           }


//声效（备用）
       private void playSfx() {
        int roll = MathUtils.random(2);
           if (roll == 0) {
               AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));
           } else if (roll == 1) {
               AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1B"));
                 } else {
               AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1C"));
                 }
           }

//死亡声效
       private void playDeathSfx() {
         int roll = MathUtils.random(2);
          if (roll == 0) {
               CardCrawlGame.sound.play("VO_CULTIST_2A");
             } else if (roll == 1) {
             CardCrawlGame.sound.play("VO_CULTIST_2B");
                 } else {
            CardCrawlGame.sound.play("VO_CULTIST_2C");
                 }
           }
    
 //死亡相关
       public void die() {
           if (!(AbstractDungeon.getCurrRoom()).cannotLose) {

               CardCrawlGame.music.silenceTempBgmInstantly();
               CardCrawlGame.music.unsilenceBGM();

               super.die();

               useFastShakeAnimation(5.0F);
               CardCrawlGame.screenShake.rumble(4.0F);

               onBossVictoryLogic();
               onFinalBossVictoryLogic();
               CardCrawlGame.stopClock = true;
           }

      }



//气槽相关----------------------------------------------------------

    private float particletimer = 0;
    private RainbowColor color = new RainbowColor(Color.RED);
    private void resetColor() {
        color = new RainbowColor(Color.RED);
    }
    @Override
    public void update() {
        super.update();
        if(this.powerMeter > 0) {
            particletimer -= Gdx.graphics.getDeltaTime();
            if(particletimer < 0F) {
                particletimer = 0.05F + (maxPowerMeter - powerMeter) / 5000F;
                if(powerMeter < maxPowerMeter) {
                    AbstractDungeon.effectList.add(new HealVerticalLineButHorizontalEffect(this.pmhb.x, this.pmhb.y + MathUtils.random(this.pmhb.height), this.pmhb.width * 0.1F));
                } else {
                    AbstractGameEffect age = new LightningOrbPassiveEffect(this.pmhb.x + MathUtils.random(this.pmhb.width), this.pmhb.cY);
                    AbstractDungeon.effectList.add(age);
                }
            }
            if(this.powerMeter >= this.maxPowerMeter) {
                color.update();
            }
            this.pmhb.update();


        if(displayPowerMeter < powerMeter) {
            displayPowerMeter += maxPowerMeter / 100;
            if(displayPowerMeter > powerMeter) {
                displayPowerMeter = powerMeter;
            }
        } else if(displayPowerMeter > powerMeter) {
            displayPowerMeter -= maxPowerMeter / 100;
            if(displayPowerMeter < powerMeter) {
                displayPowerMeter = powerMeter;
            }
        }
 //超杀管理
        if(this.powerMeter >= this.maxPowerMeter && this.superMoves == false && this.halfDead == false){
            this.superMoves = true;
        }//超杀为真


            if (!this.isDying && this.animateParticles) {
                this.fireTimer -= Gdx.graphics.getDeltaTime();
                if (this.fireTimer < 0.0F) {
                    this.fireTimer = 0.1F;

                    AbstractDungeon.effectList.add(new AwakenedEyeParticle(this.skeleton.getX() + this.eye.getWorldX(), this.skeleton.getY() + this.eye.getWorldY()));
                    this.wParticles.add(new AwakenedWingParticle());
                }
            }


            for (Iterator<AwakenedWingParticle> p = this.wParticles.iterator(); p.hasNext(); ) {
                AwakenedWingParticle e = (AwakenedWingParticle)p.next();
                e.update();
                if (e.isDone) {
                    p.remove();
                }
            }
        }

    }


    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if(this.displayPowerMeter < this.maxPowerMeter) {
            sb.setColor(Color.BLACK);
            sb.draw(pixel, this.pmhb.x, this.pmhb.y, 0, 0, this.pmhb.width, this.pmhb.height, Settings.scale, Settings.scale, 0, 0, 0, 1, 1, false, false);
        }
        if(this.powerMeter > 0) {
            float width = this.pmhb.width * Math.min(this.maxPowerMeter, this.displayPowerMeter) / this.maxPowerMeter;
            sb.setColor(color);
            sb.draw(pixel, this.pmhb.x, this.pmhb.y, 0, 0, width, this.pmhb.height, Settings.scale, Settings.scale, 0, 0, 0, 1, 1, false, false);
            if(this.pmhb.hovered) {
                TipHelper.renderGenericTip(this.pmhb.x, this.pmhb.y - this.pmhb.height, PMTEXT[0], PMTEXT[1]);
            }
        }
        sb.setColor(Color.WHITE.cpy());

        FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, this.powerMeter + "/" + this.maxPowerMeter, this.hb.cX, this.hb.y + this.hb.height + 8F, Color.WHITE);

    }

 //-----------------------------------------------------------------



//行动规律-----------------

       protected void getMove(int num) {


 //第一阶段回合限制
           if (this.timeLimit == 0 && formAmount == 0){
               this.formAmount =  ++this.formAmount ;
               AbstractDungeon.actionManager.addToTop(new CustomWaitAction(0.5F));
               CardCrawlGame.music.silenceTempBgmInstantly();
               CardCrawlGame.music.playTempBGM(RingOfDestiny.makeID("music17"));

               //对话演出
               CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Form1A"));
               AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[0]));
               AbstractDungeon.actionManager.addToBottom((new CustomWaitAction(1.0F)));
               AbstractDungeon.actionManager.addToBottom((new ShoutAction(this, DIALOG[1])));

               AbstractDungeon.actionManager.addToTop(new ClearCardQueueAction());

               AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, AbstractMonster.Intent.UNKNOWN));


               return;
           }
           switch (formAmount){

//第一阶段
               case 0:
//超杀
                   if (this.superMoves == true && this.halfDead == false){
                       setMove(MOVES[3],(byte)3, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(7)).base, this.HeavenHitCount, true);
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangiblePlayerPower(this, 2)));
                       return;
                   }

//第一回合行动
                  if (this.isFirstMove) {
                       setMove(MOVES[2],(byte)2, AbstractMonster.Intent.STRONG_DEBUFF);
                       this.isFirstMove = false;
                       return;
                  }

           switch (this.moveCount % 2) {
               case 0:
                   if (AbstractDungeon.aiRng.randomBoolean()) {
                       setMove(MOVES[0],(byte)0, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base, this.repuukenHitCount, true);
                       break;
                   }
                       setMove(MOVES[1],(byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);

                   break;


               case 1:
                   if (!lastMove((byte)1)) {
                      setMove(MOVES[1],(byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base); break;
                   }
                      setMove(MOVES[0],(byte)0, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base, this.repuukenHitCount, true);

                  break;
           }

                   this.moveCount++;
                  break;




//第二阶段
               case 1 :
//超杀
                   if (this.superMoves == true && this.halfDead == false){
                       setMove(MOVES[7],(byte)13, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(8)).base, this.PhoenixHitCount, true);
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangiblePlayerPower(this, 2)));
                       return;
                   }
//第一回合行动
               if (this.isFirstMove) {
                      setMove(MOVES[6], (byte)12, AbstractMonster.Intent.ATTACK, ((DamageInfo) this.damage.get(3)).base,this.ExecutionHitCount, true);
                   this.isFirstMove = false;
                   return;
               }


                   switch (this.moveCount % 3) {
                       case 0:
                               setMove(MOVES[4],(byte)10, Intent.BUFF);
                               break;

                       case 1:
                               setMove(MOVES[5],(byte)11, Intent.ATTACK_BUFF, ((DamageInfo)this.damage.get(2)).base);
                               break;


                       default:
                           setMove(MOVES[6], (byte)12, AbstractMonster.Intent.ATTACK, ((DamageInfo) this.damage.get(3)).base,this.ExecutionHitCount, true);
                           break;
                   }

                   this.moveCount++;
                    break;


//第三阶段
               case 2:
//超杀
                   if (this.superMoves == true && this.halfDead == false ){
                       setMove(MOVES[11],(byte)23, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(9)).base, this.JudgementHitCount, true);
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangiblePlayerPower(this, 2)));
                       return;
                   }

//第一回合行动
                   if (this.isFirstMove) {
                       setMove(MOVES[8], (byte)20, AbstractMonster.Intent.ATTACK, ((DamageInfo) this.damage.get(4)).base,2, true);
                       //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangiblePower(this, 1)));
                       this.isFirstMove = false;
                       return;
                   }


                   switch (this.moveCount % 3) {
                       case 0:
                           setMove(MOVES[9],(byte)21, AbstractMonster.Intent.ATTACK, ((DamageInfo) this.damage.get(5)).base);
                           break;

                       case 1:
                           setMove(MOVES[10],(byte)22, AbstractMonster.Intent.ATTACK, ((DamageInfo) this.damage.get(6)).base,DestructionHitCount,true);
                           break;


                       default:
                           setMove(MOVES[8], (byte)20, AbstractMonster.Intent.ATTACK, ((DamageInfo) this.damage.get(4)).base,CutterHitCount, true);
                           break;
                   }

                   this.moveCount++;
                   break;
           }

  }

//伤害相关
              public void damage(DamageInfo info) {
//无实体处理
                  if (info.output > 0 && hasPower("Intangible")) {
                      info.output = 1;
                  }

//计算气槽
                  int prevhp = this.currentHealth;
//结算伤害
                  super.damage(info);

                  if (prevhp > this.currentHealth) {
                      this.state.setAnimation(0, "Hit", false);

                      if (this.formAmount >= 1 && nextMove != 99){
                          this.state.addAnimation(0, "Idle_3", true, 0.0F);
                      }else{
                          this.state.addAnimation(0, "Idle", true, 0.0F);
                      }


                      this.powerMeter += Math.ceil(2500*((prevhp - this.currentHealth)/(float)this.maxHealth ));

                      if (this.powerMeter > 1000){
                          this.powerMeter = 1000;
                      }
                      if (this.powerMeter < 0){
                          this.powerMeter = 0;
                      }

                      if(this.powerMeter >= this.maxPowerMeter && this.superMoves == false && this.halfDead == false){
                          this.superMoves = true;
                      }

                      if (MathUtils.random(3) == 0 ){
                          CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Hit"+ MathUtils.random(1)));
                      }
                  }

//击杀濒死
               if (this.currentHealth <= 0 && !this.halfDead) {
//不会输->濒死
                   if ((AbstractDungeon.getCurrRoom()).cannotLose == true) {
                       this.halfDead = true;
                   }
//触发死亡结算
               for (AbstractPower p : this.powers) {
                    p.onDeath();
                   }
//触发击杀相关遗物
                for (AbstractRelic r : AbstractDungeon.player.relics) {
                    r.onMonsterDeath(this);
                   }

//清除出牌队列
                   AbstractDungeon.actionManager.addToTop(new ClearCardQueueAction());

//移除负面
                for (Iterator<AbstractPower> s = this.powers.iterator(); s.hasNext(); ) {
                    AbstractPower p = (AbstractPower)s.next();

                 if (p.type == AbstractPower.PowerType.DEBUFF ||p.ID.equals(RingOfDestiny.makeID("Reflection"))||p.ID.equals(RingOfDestiny.makeID("ArtGallery"))||p.ID.equals("IntangiblePlayer")) {
                    s.remove();
                    }
                  }


//切阶段相关
//对话演出



                setMove((byte)99, AbstractMonster.Intent.UNKNOWN);
                     createIntent();
                     switch (formAmount){
//1->2
                         case 0:

                             AbstractDungeon.actionManager.addToTop(new CustomWaitAction(0.5F));
                             CardCrawlGame.music.silenceTempBgmInstantly();
                             CardCrawlGame.music.playTempBGM(RingOfDestiny.makeID("music17"));

                             //对话演出
                             CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Form1A"));
                             AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[0]));
                             AbstractDungeon.actionManager.addToBottom((new CustomWaitAction(1.0F)));
                             AbstractDungeon.actionManager.addToBottom((new ShoutAction(this, DIALOG[1])));

                             AbstractDungeon.actionManager.addToTop(new ClearCardQueueAction());

                         break;


//2->3
                         case 1:

                             AbstractDungeon.actionManager.addToTop(new CustomWaitAction(0.5F));
                             CardCrawlGame.music.silenceTempBgmInstantly();
                             CardCrawlGame.music.playTempBGM(RingOfDestiny.makeID("music20"));
                             //对话演出

                             CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Form2A"));
                             AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[3]));
                             AbstractDungeon.actionManager.addToBottom((new CustomWaitAction(1.0F)));
                             AbstractDungeon.actionManager.addToBottom((new ShoutAction(this, DIALOG[4])));
                             AbstractDungeon.actionManager.addToBottom((new CustomWaitAction(3.2F)));
                             AbstractDungeon.actionManager.addToBottom((new ShoutAction(this, DIALOG[5])));
                             AbstractDungeon.actionManager.addToBottom((new CustomWaitAction(5.0F)));

                             AbstractDungeon.actionManager.addToTop(new ClearCardQueueAction());
                             this.killStatue = true;
                             break;

//死亡
                         case 2:
                             AbstractDungeon.actionManager.addToTop(new CustomWaitAction(0.5F));
                             CardCrawlGame.music.silenceTempBgmInstantly();
                             CardCrawlGame.music.playTempBGM(RingOfDestiny.makeID("music22"));



                             CardCrawlGame.sound.play(RingOfDestiny.makeID("VO_Rita_Form3A"));
                             AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[7]));
                             AbstractDungeon.actionManager.addToBottom((new CustomWaitAction(3.5F)));
                             AbstractDungeon.actionManager.addToBottom((new ShoutAction(this, DIALOG[8])));
                             AbstractDungeon.actionManager.addToBottom((new CustomWaitAction(9.0F)));
                             AbstractDungeon.actionManager.addToBottom((new ShoutAction(this, DIALOG[9])));
                             AbstractDungeon.actionManager.addToBottom((new CustomWaitAction(1.0F)));
                             AbstractDungeon.actionManager.addToTop(new ClearCardQueueAction());



                             break;

                     }


//切到复活意图
                       AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, AbstractMonster.Intent.UNKNOWN));
                     applyPowers();

                     this.isFirstMove = true;
                     this.formAmount = ++ this.formAmount ;
                       this.moveCount = 0;

    }
  }
}

