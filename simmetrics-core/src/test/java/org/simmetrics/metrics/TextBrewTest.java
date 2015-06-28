package org.simmetrics.metrics;

import org.junit.Test;
import org.simmetrics.StringMetric;

/**
 * Created by pludu on 6/19/2015.
 */
public class TextBrewTest extends StringMetricTest{
    @Override
    protected StringMetric getMetric() {
        return new TextBrew();
    }

    @Test
    public void testSimpleExamples() {
        testSimilarity(getMetric(), new T(0.903448275862069f, "BRYN MAWR PIZZA II", "BR MA PI II"));
        testSimilarity(getMetric(), new T(0.8857142857142857f, "clmbs blvd", "columbus boulevard"));
        testSimilarity(getMetric(), new T(0.8666666666666667f, "hosp", "hospital"));
    }
    @Test
    public void testNewCosts()
    {
        TextBrew tb = new TextBrew();
        tb.setCosts(0, 0.5, 10, 1);
        testSimilarity(tb, new T(0.7586206896551724f, "BRYN MAWR PIZZA II", "BR MA PI II"));
        testSimilarity(tb, new T(0.7142857142857143f,"clmbs blvd", "columbus boulevard"));
        testSimilarity(tb, new T(0.6666666666666667f,"hosp", "hospital"));
        testSimilarity(tb, new T(0.4390243902439024f,"This is a text", "may be almost the same text"));
        tb.setCosts(0, 0.2, 10, 1);
        testSimilarity(tb, new T(0.903448275862069f, "BRYN MAWR PIZZA II", "BR MA PI II"));
        testSimilarity(tb, new T(0.8857142857142857f,"clmbs blvd", "columbus boulevard"));
        testSimilarity(tb, new T(0.8666666666666667f,"hosp", "hospital"));
        testSimilarity(tb, new T(0.6292682926829267f,"This is a text", "may be almost the same text"));
        testSimilarity(tb, new T(0.7379310344827585f,"United States of America", "U S A"));
    }
    @Override
    protected T[] getTests() {
        return new T[] {
                new T(0.5599999999999998f, "United States of America", "U.S.A."),
                new T(0.8f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
                new T(0.8571428571428572f, "a b c d", "a b c e"),
                new T(0.472340425531915f, "Web Aplications",
                        "How to Find a Scholarship Online"), };
    }


}
