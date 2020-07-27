package RingOfDestiny.patches;

import RingOfDestiny.RingOfDestiny;
import RingOfDestiny.character.MagicBullet;
import RingOfDestiny.diamonds.AbstractDiamond;
import RingOfDestiny.diamonds.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.lang.reflect.Constructor;

import static basemod.BaseMod.getModdedCharacters;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;


public class EnergyPanelRenderPatches {

    @SpirePatch(
            clz = EnergyPanel.class,
            method=SpirePatch.CLASS
    )
    public static class PatchEnergyPanelField {
        public static SpireField<Boolean> canUseDiamond = new SpireField<>(() -> false);
        public static SpireField<DiamondManager> diamondManager = new SpireField<>(() -> new DiamondManager());

        public static SpireField<AbstractDiamond[]> diamonds  = new SpireField<>(() -> new  AbstractDiamond[]{
                new BlueDiamondSlot( -47.5f * Settings.scale , 58.5f * Settings.scale , 0.4f * Settings.scale,95.0f),
                new BlueDiamondSlot( -32.0f * Settings.scale,  69.0f * Settings.scale, 0.425f * Settings.scale,88.0f),
                new BlueDiamondSlot(-13.0f * Settings.scale,  75.0f * Settings.scale, 0.45f * Settings.scale, 15.0f),
                new PurpleDiamondSlot(9.0f * Settings.scale,  77.0f * Settings.scale, 0.5f * Settings.scale,2.0f),
                new PurpleDiamondSlot(31.0f * Settings.scale,  70.0f * Settings.scale, 0.525f * Settings.scale,-20.0f),
                new PurpleDiamondSlot( 52.0f * Settings.scale,  58.0f * Settings.scale, 0.575f * Settings.scale,-32.0f),
                new YellowDiamondSlot(68.0f * Settings.scale,   37.0f * Settings.scale, 0.6f * Settings.scale,-55.0f),
                new YellowDiamondSlot(76.0f * Settings.scale,  12.0f * Settings.scale, 0.65f * Settings.scale,-80.0f),
                new YellowDiamondSlot(74.0f * Settings.scale,  -18.0f * Settings.scale, 0.65f * Settings.scale,-100.0f),
                new YellowDiamondSlot(62.0f * Settings.scale,  -44.0f * Settings.scale, 0.65f * Settings.scale,-130.0f)


        });
    }

    @SpirePatch(
            clz = EnergyPanel.class,
            method = "render"
    )
    public static class ExtraEnergyPanelRenderPatch {
        @SpireInsertPatch(rloc = 5)
        public static SpireReturn<Void> Insert(EnergyPanel _instance, SpriteBatch sb) {
            if(PatchEnergyPanelField.canUseDiamond.get(_instance)){
                AbstractDiamond[] di = PatchEnergyPanelField.diamonds.get(_instance);
                DiamondManager diaManager = PatchEnergyPanelField.diamondManager.get(_instance);
                for(AbstractDiamond diamond : di){
                    diamond.render(sb);
                }

                diaManager.render(sb);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = EnergyPanel.class,
            method = "update"
    )
    public static class ExtraEnergyPanelUpdatePatch {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(EnergyPanel _instance) {
            if(PatchEnergyPanelField.canUseDiamond.get(_instance)){
                AbstractDiamond[] di = PatchEnergyPanelField.diamonds.get(_instance);
                DiamondManager diaManager = PatchEnergyPanelField.diamondManager.get(_instance);
                for(AbstractDiamond diamond : di){
                    diamond.cX = _instance.current_x;
                    diamond.cY = _instance.current_y;
                    diamond.update();
                }
                diaManager.tX = _instance.current_x + 30.0f * Settings.scale;
                diaManager.tY = _instance.current_y + 50.0f * Settings.scale;
                diaManager.update();
            }
            return SpireReturn.Continue();
        }
    }

}


