package cfvbaibai.cardfantasy;

public class DeckBuildRuntimeException extends CardFantasyUserRuntimeException {

    private static final long serialVersionUID = 193208372894017945L;
    private static final String HELP_MSG =
            "<br />请仔细阅读帮助信息。<br />实在无法发现错误的话，<br />请清空当前卡组，<br />并使用<b>组卡</b>按钮组建卡组" +
            "<table border='0' cellspacing='1' cellpadding='5' bgcolor='#FFCCCC'>" +
            "<tr bgcolor='#FF9999'><th>常见错误</th><th>错误例子</th><th>改正</th></tr>" +
            "<tr bgcolor='white'><td>卡牌之间没有用逗号隔开</td><td>大剑圣*1机械飞龙*2</td><td>大剑圣*1,机械飞龙*2</td></tr>" +
            "<tr bgcolor='white'><td>使用了系统无法识别的文字</td><td>白白暂时举不出例子</td><td>这时候就使用组卡功能吧</td></tr>" +
            "<tr bgcolor='white'><td>有错别字</td><td>降临天使+国王之力5,雷域</td><td>降临天使+王国之力5,雷狱</td></tr>" +
            "<tr bgcolor='white'><td>卡牌符文名字不准确</td><td>剑圣</td><td>大剑圣</td></tr>" +
            "<tr bgcolor='white'><td>洗炼符号用成了减号</td><td>大剑圣-不动</td><td>大剑圣+不动</td></tr>" +
            "<tr bgcolor='white'><td>洗炼、等级和数量符号顺序错误</td><td>凤凰-15+转生5*5,震源岩蟾*1+不动</td><td>凤凰+转生5-15*5,震源岩蟾+不动*1</td></tr>" +
            "</table>";
    public DeckBuildRuntimeException(String message) {
        super(message, HELP_MSG);
    }

    public DeckBuildRuntimeException(String message, Exception inner) {
        super(message, HELP_MSG, inner);
    }

}