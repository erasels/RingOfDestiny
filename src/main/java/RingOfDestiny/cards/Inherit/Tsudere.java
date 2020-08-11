package RingOfDestiny.cards.Inherit;

import RingOfDestiny.RingOfDestiny;
import RingOfDestiny.actions.Inherit.DoubleSubEnergyAction;
import RingOfDestiny.actions.Inherit.LoseMaxHPAction;
import RingOfDestiny.actions.Inherit.UseSubEnergyAction;
import RingOfDestiny.cards.AbstractInheritCard;
import RingOfDestiny.patches.EnergyPanelRenderPatches;
import RingOfDestiny.powers.Inherit.ConvictionPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.DoubleEnergyAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Tsudere extends AbstractInheritCard {
    public static final String ID = RingOfDestiny.makeID("Tsudere");
    public static final String IMG = RingOfDestiny.assetPath("img/cards/Inherit/12.png");
    public static final String SUB_IMG = RingOfDestiny.assetPath("img/cards/Inherit/61.png");
    private static final int COST = 0;
    private static final int SUB_GAIN = 0;
    private static final int SUB_GAIN2 = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Tsudere(boolean isDark) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET, SUB_IMG, isDark, SUB_GAIN,SUB_GAIN2);
        this.isDestructive = true;
    }

    @Override
    protected void initializeNumber1() {
    }

    @Override
    protected void initializeNumber2() {
    }

    public Tsudere() {
        this(false);
    }

    @Override
    protected void cardEffect1(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DoubleEnergyAction());
    }

    @Override
    protected void cardEffect2(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DoubleSubEnergyAction());
    }

    public AbstractCard makeCopy() {
        if (AbstractDungeon.player == null) {
            return new Tsudere();
        } else {
            return new Tsudere(EnergyPanelRenderPatches.PatchEnergyPanelField.isInDarkCpy);
        }
    }

    @Override
    protected void upgradeNumber1() {
        this.isInnate = true;
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    protected void upgradeNumber2() {
        this.isInnate = true;
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}