package ua.rud.touristcompany;

import org.xml.sax.SAXException;
import ua.rud.touristcompany.parsers.*;
import ua.rud.touristcompany.voucher.Voucher;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TouristCompany {
    public static final String XML_FILE = "data/vouchers.xml";
    public static final String XSD_FILE = "data/voucher.xsd";
    public static final String XSL_FILE = "data/voucher.xsl";
    public static final String HTML_FILE = "data/vouchers.html";
    private List<Voucher> vouchers = new ArrayList<>();

    /*Methods*/
    public void parseFile(String file, ParserType type) throws ParserConfigurationException, XMLStreamException, SAXException, IOException {
        VoucherXMLParser parser;
        switch (type) {
            case SAX:
                parser = new VoucherSAXParser();
                break;
            case DOM:
                parser = new VoucherDOMParser();
                break;
            case STAX:
                parser = new VoucherStAXParser();
                break;
            default:
                parser = null;
        }
        vouchers = parser.parseFile(file);
    }

    public void sort() {
        Collections.sort(vouchers, new Comparator<Voucher>() {
            @Override
            public int compare(Voucher o1, Voucher o2) {
                return o1.getCost() - o2.getCost();
            }
        });
    }

    public void printVouchers() {
        for (Voucher voucher : vouchers) {
            System.out.println(voucher);
        }
    }

    public boolean isValid(String xml, String xsd) throws SAXException, IOException {
        File schemaFile = new File(xsd);
        Source xmlFile = new StreamSource(new File(xml));
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaFile);
        Validator validator = schema.newValidator();
        try {
            validator.validate(xmlFile);
            return true;
        } catch (SAXException e) {
            return false;
        }
    }

    public void buildHTML(String xml, String xsl, String html) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer(new StreamSource(xsl));
        transformer.transform(new StreamSource(xml), new StreamResult(html));
    }

    /*Runner*/
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XMLStreamException, TransformerException {
        TouristCompany company = new TouristCompany();

        if (company.isValid(XML_FILE, XSD_FILE)) {
            company.buildHTML(XML_FILE, XSL_FILE, HTML_FILE);
            for (ParserType type : ParserType.values()) {
                System.out.println(type.toString());
                company.parseFile(XML_FILE, type);
                company.sort();
                company.printVouchers();
                System.out.println();
            }
        }
    }
}
