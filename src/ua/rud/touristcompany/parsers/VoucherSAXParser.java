package ua.rud.touristcompany.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.rud.touristcompany.voucher.Meals;
import ua.rud.touristcompany.voucher.Transport;
import ua.rud.touristcompany.voucher.Type;
import ua.rud.touristcompany.voucher.Voucher;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VoucherSAXParser extends DefaultHandler implements VoucherXMLParser {
    private List<Voucher> vouchers;
    private Voucher voucher;
    private String current = "";


    @Override
    public void startDocument() throws SAXException {
        vouchers = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "vch:touristVoucher":
                voucher = new Voucher();
                voucher.setId(attributes.getValue(0));
                break;
            case "vch:cost":
                boolean b = Boolean.parseBoolean(attributes.getValue(0));
                voucher.setTransferIncluded(b);
                current = qName;
                break;
            case "htl:airCondition":
                 b = Boolean.parseBoolean(attributes.getValue(0));
                voucher.setAirCondition(b);
                current = qName;
                break;
            case "htl:tv":
                 b = Boolean.parseBoolean(attributes.getValue(0));
                voucher.setTv(b);
                current = qName;
                break;
            case "vch:type":
            case "vch:country":
            case "vch:days":
            case "vch:transport":
            case "htl:stars":
            case "htl:meals":
            case "htl:numberOfPersons":
                current = qName;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s = new String(ch, start, length);
        switch (current) {
            case "vch:type":
                Type type = Type.valueOf(s.toUpperCase());
                voucher.setType(type);
                break;
            case "vch:country":
                voucher.setCountry(s);
                break;
            case "vch:days":
                int i = Integer.parseInt(s);
                voucher.setDays(i);
                break;
            case "vch:transport":
                Transport transport = Transport.valueOf(s.toUpperCase());
                voucher.setTransport(transport);
                break;
            case "htl:stars":
                i = Integer.parseInt(s);
                voucher.setStars(i);
                break;
            case "htl:meals":
                Meals meals = Meals.valueOf(s.toUpperCase());
                voucher.setMeals(meals);
                break;
            case "htl:numberOfPersons":
                i = Integer.parseInt(s);
                voucher.setNumberOfPersons(i);
                break;
            case "htl:airCondition":
                boolean b = Boolean.parseBoolean(s);
                voucher.setAirCondition(b);
                break;
            case "htl:tv":
                b = Boolean.parseBoolean(s);
                voucher.setTv(b);
            case "vch:cost":
                i = Integer.parseInt(s);
                voucher.setCost(i);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("vch:touristVoucher")) {
            vouchers.add(voucher);
        }
        current = "";
    }

    @Override
    public void endDocument() throws SAXException {

    }
    @Override
    public List<Voucher> parseFile(String file) throws ParserConfigurationException, SAXException, IOException {
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        parser.parse(new File(file), this);
        return vouchers;
    }
}
