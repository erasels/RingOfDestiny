package RingOfDestiny.powers;

import RingOfDestiny.RingOfDestiny;
import RingOfDestiny.cards.ShadowFlower.ShadowRose;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ShadowMarkPower extends TwoAmountPower {
    public static final String POWER_ID = RingOfDestiny.makeID("ShadowMarkPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ShadowMarkPower(AbstractCreature owner, int amount) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = 0;
        updateDescription();
        loadRegion("infiniteBlades");
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0 && damageAmount < 5) {
            this.amount2 += (int)Math.floor(damageAmount * 1.5f);
        }
        return damageAmount;
    }


    public void updateDescription() {
        if (this.amount > 1) {
            this.description = powerStrings.DESCRIPTIONS[1] + this.amount + powerStrings.DESCRIPTIONS[2] + this.amount2 + powerStrings.DESCRIPTIONS[3];
        } else {
            this.description = powerStrings.DESCRIPTIONS[0] + this.amount2 + powerStrings.DESCRIPTIONS[3];
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new DamageAction(this.owner,new DamageInfo(null, this.amount2, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.amount2 = 0;
        addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
    }
}


