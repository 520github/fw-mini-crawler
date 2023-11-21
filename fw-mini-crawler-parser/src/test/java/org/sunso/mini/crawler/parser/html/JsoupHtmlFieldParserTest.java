package org.sunso.mini.crawler.parser.html;

import org.jsoup.nodes.Element;
import org.junit.Test;
import org.sunso.mini.crawler.parser.BaseTest;

/**
 * @author sunso520
 * @Title:JsoupHtmlFieldParserTest
 * @Description: <br>
 * @Created on 2023/11/21 11:45
 */
public class JsoupHtmlFieldParserTest extends BaseTest {

    @Test
    public void elementOneTest() {
        JsoupHtmlFieldParser parser = getJsoupHtmlFieldParser(url(), html());
        Element element = parser.elementOne("td:eq(0)");
        if (element != null) {
            print(element.html());
        }
        else {
            print("element为空");
        }
    }

    private String url() {
        return "https://data.eastmoney.com/xg/xg/?mkt=kzz";
    }

    private String html() {
        return "<table><tr id='kkk'><td>ddd</td><td>333</td></tr></table>";
//        return "<tr title=\"本次发行的可转债向发行人在股权登记日(2023年11月14日,T-1日)收市后中国结算上海分公司登记在册的原股东优先配售,原股东优先配售后余额部分(含原股东放弃优先配售部分)采用网上通过上交所交易系统向社会公众投资者发售的方式进行,余额由保荐人(主承销商)包销。\" data-index=\"0\">\n" +
//                " <td id='kkk' style=\"width:  60px; \">113680</td>\n" +
//                " <td style=\"width:  60px; \">丽岛转债</td>\n" +
//                " <td style=\"width:  60px; \"><a class=\"red\" href=\"/kzz/detail/113680.html\">详细</a>&nbsp;<a href=\"//guba.eastmoney.com/list,sh113680.html\">股吧</a></td>\n" +
//                " <td class=\"nowrap desc_col\" style=\"width:  110px; \">2023-11-15 周三</td>\n" +
//                " <td style=\"width:  60px; \">754937</td>\n" +
//                " <td style=\"width:  70px; \">100</td>\n" +
//                " <td style=\"width:  60px; \"><a href=\"//quote.eastmoney.com/unify/r/1.603937\">603937</a></td>\n" +
//                " <td style=\"width:  60px; \"><a href=\"/stockdata/603937.html\"><span title=\"丽岛新材\">丽岛新材</span></a></td>\n" +
//                " <td style=\"width:  60px; \">12.71</td>\n" +
//                " <td style=\"width:  60px; \">13.01</td>\n" +
//                " <td style=\"width:  70px; \">97.62</td>\n" +
//                " <td style=\"width:  60px; \">100.00</td>\n" +
//                " <td style=\"width:  60px; \"><span class=\"red\">2.44%</span></td>\n" +
//                " <td style=\"width:  55px; \"><span title=\"2023-11-14\">11-14</span></td>\n" +
//                " <td style=\"width:  55px; \">1.4360</td>\n" +
//                " <td style=\"width:  65px; \">3.00</td>\n" +
//                " <td style=\"width:  60px; \"><span title=\"2023-11-17\">11-17</span></td>\n" +
//                " <td style=\"width:  50px; \">0.0005%</td>\n" +
//                " <td style=\"width:  50px; \"><span title=\"-\">-</span></td>\n" +
//                " <td style=\"width:  50px; \">A+</td>\n" +
//                "</tr>, code=null), ConvertibleBondsDataCrawlerResult(body=<tr title=\"本次发行的可转换公司债券向发行人在股权登记日(2023年11月8日,T-1日)收市后中国结算深圳分公司登记在册的原股东优先配售,原股东优先配售后余额部分(含原股东放弃优先配售部分)通过深交所交易系统向社会公众投资者发行,余额由保荐人(主承销商)包销。\" data-index=\"1\">\n" +
//                " <td style=\"width:  60px; \">123231</td>\n" +
//                " <td style=\"width:  60px; \">信测转债</td>\n" +
//                " <td style=\"width:  60px; \"><a class=\"red\" href=\"/kzz/detail/123231.html\">详细</a>&nbsp;<a href=\"//guba.eastmoney.com/list,sz123231.html\">股吧</a></td>\n" +
//                " <td class=\"nowrap desc_col\" style=\"width:  110px; \">2023-11-09 周四</td>\n" +
//                " <td style=\"width:  60px; \">370938</td>\n" +
//                " <td style=\"width:  70px; \">100</td>\n" +
//                " <td style=\"width:  60px; \"><a href=\"//quote.eastmoney.com/unify/r/0.300938\">300938</a></td>\n" +
//                " <td style=\"width:  60px; \"><a href=\"/stockdata/300938.html\"><span title=\"信测标准\">信测标准</span></a></td>\n" +
//                " <td style=\"width:  60px; \">36.77</td>\n" +
//                " <td style=\"width:  60px; \">36.89</td>\n" +
//                " <td style=\"width:  70px; \">99.78</td>\n" +
//                " <td style=\"width:  60px; \">100.00</td>\n" +
//                " <td style=\"width:  60px; \"><span class=\"red\">0.22%</span></td>\n" +
//                " <td style=\"width:  55px; \"><span title=\"2023-11-08\">11-08</span></td>\n" +
//                " <td style=\"width:  55px; \">4.7895</td>\n" +
//                " <td style=\"width:  65px; \">5.45</td>\n" +
//                " <td style=\"width:  60px; \"><span title=\"2023-11-13\">11-13</span></td>\n" +
//                " <td style=\"width:  50px; \">0.0011%</td>\n" +
//                " <td style=\"width:  50px; \"><span title=\"-\">-</span></td>\n" +
//                " <td style=\"width:  50px; \">AA-</td>\n" +
//                "</tr>";
    }

    private JsoupHtmlFieldParser getJsoupHtmlFieldParser(String url, String html) {
        return new JsoupHtmlFieldParser(url, html);
    }



}
