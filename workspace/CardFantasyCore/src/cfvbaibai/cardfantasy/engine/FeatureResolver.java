package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureType;
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
    
    public void resolvePostAttackFeature(CardInfo card, Player defender) {
        for (Feature feature : card.getUsableFeaturesOf(FeatureType.Snipe)) {
            SnipeFeature.apply(feature, this, card, defender);
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
        return new OnAttackBlockingResult(false);
    }

    public void resolveDyingFeature(CardInfo attacker, CardInfo card, Feature feature) {
        // TODO Auto-generated method stub
        return;
    }
}
