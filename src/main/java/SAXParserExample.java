
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class SAXParserExample extends DefaultHandler {

    List<Employee> employeeList;

    private String tempVal;

    //to maintain context
    private Employee tempEmp;

    public void runExample() {
        parseDocument();
        printData();
    }

    private void parseDocument() {
        // Create a factory that creates new SAX parers
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            // Build a new parser from the factory
            SAXParser sp = saxParserFactory.newSAXParser();

            // Parses the file using the overriden methods below
            sp.parse("employees.xml", this);

        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * Iterate through the list and print
     * the contents
     */
    private void printData() {

        System.out.println("No of Employees '" + employeeList.size() + "'.");

        Iterator<Employee> it = employeeList.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    // Called once at the beginning of parsing a new document
    @Override
    public void startDocument() {
        employeeList = new ArrayList<>();
    }

    // Overrides the method that is called whenever the parser encounters the starting tag of an XML element
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        // Reset the temporary value we are storing
        tempVal = "";
        if (qName.equalsIgnoreCase("Employee")) {
            // We encountered a new Employee tag and create a new instance of employee
            tempEmp = new Employee();
            tempEmp.setType(attributes.getValue("type"));
        }
    }

    // Overrides the method that is called whenever the parser encounters the characters in between XML start and
    // end elements
    @Override
    public void characters(char[] ch, int start, int length) {
        tempVal = new String(ch, start, length);
    }

    // Overrides the method that is called whenever the parser encounters the ending tag of an XML element
    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase("Employee")) {
            employeeList.add(tempEmp);
        } else if (qName.equalsIgnoreCase("Name")) {
            tempEmp.setName(tempVal);
        } else if (qName.equalsIgnoreCase("Id")) {
            tempEmp.setId(Integer.parseInt(tempVal));
        } else if (qName.equalsIgnoreCase("Age")) {
            tempEmp.setAge(Integer.parseInt(tempVal));
        }
    }

    public static void main(String[] args) {
        SAXParserExample saxParserExample = new SAXParserExample();
        saxParserExample.runExample();
    }
}
