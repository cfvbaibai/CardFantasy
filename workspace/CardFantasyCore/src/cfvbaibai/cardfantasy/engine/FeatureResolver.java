package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.engine.feature.ChainLighteningFeature;
import cfvbaibai.cardfantasy.engine.feature.SnipeFeature;

public class FeatureResolver {
    private Board board;
    private StageInfo stage;

    public FeatureResolver(Board board, StageInfo stage) {
        this.board = board;
        this.stage = stage;
    }
    
    public StageInfo getStage() {
        return this.stage;
    }
    
    public void resolvePreAttackFeature(CardInfo attacker, Player defender) {
        for (Feature feature : attacker.getUsableFeaturesOf(FeatureType.ChainLightening)) {
            ChainLighteningFeature.apply(feature, this, attacker, defender);
        }
    }
    
    public void resolvePostAttackFeature(CardInfo attacker, Player defender) {
        for (Feature feature : attacker.getUsableFeaturesOf(FeatureType.Snipe)) {
            SnipeFeature.apply(feature, this, attacker, defender);
        }
    }
    
    public void resolveCounterAttackFeature(CardInfo attacker, CardInfo defender, Feature feature) {
        
    }

    public OnDamangedResult applyDamage(CardInfo card, int damage) {
        card.setHP(card.getHP() - damage);
        OnDamangedResult result = new OnDamangedResult();
        if (card.getHP() <= 0) {
            result.cardDead = true;
            cardDead(card);
        }
        return result;
    }
    
    public void cardDead(CardInfo deadCard) {
        this.stage.getUI().cardDead(deadCard);
        Player owner = deadCard.getOwner();
        Field field = owner.getField();
        // Set field position to null
        for (int i = 0; i < field.size(); ++i) {
            CardInfo card = field.getCard(i);
            if (deadCard == card) {
                field.expelCard(i);
                owner.getGrave().addCard(card);
                break;
            }
        }
    }

    public OnAttackBlockingResult resolveAttackBlockingFeature(CardInfo attacker, CardInfo card, Feature feature) {
        OnAttackBlockingResult result = new OnAttackBlockingResult(false);
        if (feature == null) {
            // Normal attack could be blocked by Dodge or PARALYZED, FROZEN, TRAPPED status.
            CardStatusType statusType = attacker.getStatus().getType();
            if (statusType == CardStatusType.FROZEN || statusType == CardStatusType.PARALYZED || statusType == CardStatusType.TRAPPED) {
                stage.getUI().attackBlocked(attacker, card, feature, null);
                result.isBlocked = true;
            }
        }
        return result;
    }

    public void resolveDyingFeature(CardInfo attacker, CardInfo card, Feature feature) {
        // TODO Auto-generated method stub
        return;
    }
}
