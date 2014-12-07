package uk.ac.shef.wit.simmetrics.similaritymetrics;

public class SimonWhiteTest extends InterfaceStringMetricTest {

	@Override
	public InterfaceStringMetric getMetric() {
		return new SimonWhite();
	}

	@Override
	public T[] getTests() {
		return new T[] {
				new T(0.8889f, "test string1", "test string2"),
				new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
				new T(0.0000f, "a b c d", "a b c e"),
				new T(0.8000f, "Healed", "Sealed"),
				new T(0.5455f, "Healed", "Healthy"),
				new T(0.4444f, "Healed", "Heard"),
				new T(0.4000f, "Healed", "Herded"),
				new T(0.2500f, "Healed", "Help"),
				new T(0.0000f, "Healed", "Sold"),
				new T(0.2500f, "Healed", "Help"),
				new T(0.7273f, "Sam J Chapman", "Samuel John Chapman"),
				new T(0.8571f, "Sam Chapman", "S Chapman"),
				new T(0.2857f, "John Smith", "Samuel John Chapman"),
				new T(0.0000f, "John Smith", "Sam Chapman"),
				new T(0.0000f, "John Smith", "Sam J Chapman"),
				new T(0.0000f, "John Smith", "S Chapman"),
				new T(0.8163f, "Web Database Applications", "Web Database Applications with PHP & MySQL"),
				new T(0.7143f, "Web Database Applications", "Creating Database Web Applications with PHP and ASP"),
				new T(0.7018f, "Web Database Applications", "Building Database Applications on the Web Using PHP3"),
				new T(0.6667f, "Web Database Applications", "Building Web Database Applications with Visual Studio 6"),
				new T(0.5106f, "Web Database Applications", "Web Application Development With PHP"),
				new T(0.4878f, "Web Database Applications", "WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.0909f, "Web Database Applications", "Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.0488f, "Web Database Applications", "How to Find a Scholarship Online"),
				new T(0.5854f, "Web Aplications", "Web Database Applications with PHP & MySQL"),
				new T(0.5000f, "Web Aplications", "Creating Database Web Applications with PHP and ASP"),
				new T(0.4898f, "Web Aplications", "Building Database Applications on the Web Using PHP3"),
				new T(0.4615f, "Web Aplications", "Building Web Database Applications with Visual Studio 6"),
				new T(0.5641f, "Web Aplications", "Web Application Development With PHP"),
				new T(0.3243f, "Web Aplications", "WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.0690f, "Web Aplications", "Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.0606f, "Web Aplications", "How to Find a Scholarship Online"),
		};
	}

}
