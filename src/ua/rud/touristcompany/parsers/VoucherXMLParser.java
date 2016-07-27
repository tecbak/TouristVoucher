package ua.rud.touristcompany.parsers;

import org.xml.sax.SAXException;
import ua.rud.touristcompany.voucher.Voucher;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public interface VoucherXMLParser {
    List<Voucher> parseFile(String file) throws ParserConfigurationException, IOException, SAXException, XMLStreamException;

}
