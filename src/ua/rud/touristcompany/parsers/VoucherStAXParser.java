package ua.rud.touristcompany.parsers;

import ua.rud.touristcompany.voucher.Meals;
import ua.rud.touristcompany.voucher.Transport;
import ua.rud.touristcompany.voucher.Type;
import ua.rud.touristcompany.voucher.Voucher;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VoucherStAXParser implements VoucherXMLParser {
    @Override
    public List<Voucher> parseFile(String file) throws IOException, XMLStreamException {
        List<Voucher> vouchers = new ArrayList<>();

        try (InputStream stream = new FileInputStream(new File(file))) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(stream);

            while (reader.hasNext()) {
                if (reader.next() == XMLStreamConstants.START_ELEMENT) {
                    String name = reader.getLocalName();
                    if (name.equals("touristVoucher")) {
                        vouchers.add(buildVoucher(reader));
                    }
                }
            }
            reader.close();
        }
        return vouchers;
    }

    private Voucher buildVoucher(XMLStreamReader reader) throws XMLStreamException {
        Voucher voucher = new Voucher();

        String id = reader.getAttributeValue(null, "id");
        voucher.setId(id);
        while (reader.hasNext()) {

            int t = reader.next();
            if (t == XMLStreamConstants.START_ELEMENT) {
                String name = reader.getLocalName();
                switch (name) {
                    case "type":
                        Type type = Type.valueOf(getXMLText(reader).toUpperCase());
                        voucher.setType(type);
                        break;
                    case "country":
                        String country = getXMLText(reader);
                        voucher.setCountry(country);
                        break;
                    case "days":
                        int days = getXMLInt(reader);
                        voucher.setDays(days);
                        break;
                    case "transport":
                        Transport transport = Transport.valueOf(getXMLText(reader).toUpperCase());
                        voucher.setTransport(transport);
                        break;
                    case "stars":
                        int stars = getXMLInt(reader);
                        voucher.setStars(stars);
                        break;
                    case "meals":
                        Meals meals = Meals.valueOf(getXMLText(reader).toUpperCase());
                        voucher.setMeals(meals);
                        break;
                    case "numberOfPersons":
                        int numberOfPersons = getXMLInt(reader);
                        voucher.setNumberOfPersons(numberOfPersons);
                        break;
                    case "airCondition":
                        String attributeValue = reader.getAttributeValue(null, "available");
                        boolean airCondition = Boolean.parseBoolean(attributeValue);
                        voucher.setAirCondition(airCondition);
                        break;
                    case "tv":
                        attributeValue = reader.getAttributeValue(null, "available");
                        boolean tv = Boolean.parseBoolean(attributeValue);
                        voucher.setTv(tv);
                        break;
                    case "cost":
                        attributeValue = reader.getAttributeValue(null, "transferIncluded");
                        boolean transferIncluded = Boolean.parseBoolean(attributeValue);
                        int cost = getXMLInt(reader);
                        voucher.setTransferIncluded(transferIncluded);
                        voucher.setCost(cost);
                }
            } else if (t == XMLStreamConstants.END_ELEMENT &&
                    reader.getLocalName().equals("touristVoucher")) {
                return voucher;
            }
        }
        return null;
    }


    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        reader.next();
        return reader.getText();
    }

    private int getXMLInt(XMLStreamReader reader) throws XMLStreamException {
        return Integer.parseInt(getXMLText(reader));
    }
}
