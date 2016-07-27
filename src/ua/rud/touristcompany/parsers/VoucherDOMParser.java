package ua.rud.touristcompany.parsers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import ua.rud.touristcompany.voucher.Meals;
import ua.rud.touristcompany.voucher.Transport;
import ua.rud.touristcompany.voucher.Type;
import ua.rud.touristcompany.voucher.Voucher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VoucherDOMParser implements VoucherXMLParser {
    private List<Voucher> vouchers = new ArrayList<>();

    /*Methods*/
    @Override
    public List<Voucher> parseFile(String file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(file));

        //root element
        Element root = document.getDocumentElement();

        //list of child elements
        NodeList vouchersList = root.getElementsByTagName("vch:touristVoucher");

        for (int i = 0, n = vouchersList.getLength(); i < n; i++) {
            Element element = (Element) vouchersList.item(i);
            Voucher voucher = buildVoucher(element);
            vouchers.add(voucher);
        }

        return vouchers;
    }

    private Voucher buildVoucher(Element voucherEl) {
        Element hotelEl = (Element) voucherEl.getElementsByTagName("htl:hotel").item(0);
        Element airConditionEl = (Element) hotelEl.getElementsByTagName("htl:airCondition").item(0);
        Element tvEl = (Element) hotelEl.getElementsByTagName("htl:tv").item(0);
        Element costEl = (Element) voucherEl.getElementsByTagName("vch:cost").item(0);

        String id = voucherEl.getAttribute("id");
        Type type = Type.valueOf(getElementTextContent(voucherEl, "vch:type").toUpperCase());
        String country = getElementTextContent(voucherEl, "vch:country");
        int days = getElementIntContent(voucherEl, "vch:days");
        Transport transport = Transport.valueOf(getElementTextContent(voucherEl, "vch:transport").toUpperCase());
        int stars = getElementIntContent(hotelEl, "htl:stars");
        Meals meals = Meals.valueOf(getElementTextContent(hotelEl, "htl:meals").toUpperCase());
        int numberOfPersons = getElementIntContent(hotelEl, "htl:numberOfPersons");
        boolean airCondition = Boolean.parseBoolean(airConditionEl.getAttribute("available"));
        boolean tv = Boolean.parseBoolean(tvEl.getAttribute("available"));
        boolean transferIncluded = Boolean.parseBoolean(costEl.getAttribute("transferIncluded"));
        int cost = getElementIntContent(voucherEl, "vch:cost");

        Voucher voucher = new Voucher();
        voucher.setId(id);
        voucher.setType(type);
        voucher.setCountry(country);
        voucher.setDays(days);
        voucher.setTransport(transport);
        voucher.setStars(stars);
        voucher.setMeals(meals);
        voucher.setNumberOfPersons(numberOfPersons);
        voucher.setAirCondition(airCondition);
        voucher.setTv(tv);
        voucher.setTransferIncluded(transferIncluded);
        voucher.setCost(cost);

        return voucher;
    }

    private String getElementTextContent(Element element, String tag) {
        NodeList nodeList = element.getElementsByTagName(tag);
        Node node = nodeList.item(0);
        return node.getTextContent();
    }

    private int getElementIntContent(Element element, String tag) {
        String elementTextContent = getElementTextContent(element, tag);
        return Integer.parseInt(elementTextContent);
    }

    private boolean getElementBooleanContent(Element element, String tag) {
        String elementTextContent = getElementTextContent(element, tag);
        return Boolean.parseBoolean(elementTextContent);
    }
}
