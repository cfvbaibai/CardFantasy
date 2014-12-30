package cfvbaibai.cardfantasy.data;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;

public class QuestionStore {

    private List<Question> questions;
    private int maxQuestions;
    private static Randomizer randomizer = Randomizer.getRandomizer();

    public QuestionStore(int maxQuestions) {
        this.questions = new ArrayList<Question>();
        this.maxQuestions = maxQuestions;

        URL url = CardDataStore.class.getClassLoader().getResource("cfvbaibai/cardfantasy/data/SkillQuestions.xml");
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(url);
            @SuppressWarnings("unchecked")
            List<Node> cardNodes = doc.selectNodes("/Questions/Q");
            for (Node cardNode : cardNodes) {
                Question q = new Question();
                q.setTiebaId(cardNode.valueOf("@tiebaId"));
                q.setTitle(cardNode.getText());
                questions.add(q);
            }
        } catch (DocumentException e) {
            throw new CardFantasyRuntimeException("Cannot load question XML.", e);
        }
    }
    
    public List<Question> pickRandom() {
        return randomizer.pickRandom(this.questions, this.maxQuestions, true, null);
    }
}
