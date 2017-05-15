package parsers;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import model.SmartPhone;

public class PhoneXMLParser {

    public static List<SmartPhone> parseFeed(String content) {
        try {
            boolean inDataItemTag = false;
            String currentTagName = "";
            SmartPhone phone = null;
            List<SmartPhone> phoneList = new ArrayList<>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        if (currentTagName.equals("products")){
                            inDataItemTag = true;
                            phone = new SmartPhone();
                            phoneList.add(phone);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        if (inDataItemTag && phone != null) {
                            switch (currentTagName){
                                case "productId":
                                    phone.setProductId(Integer.parseInt(parser.getText()));
                                    break;
                                case "productName":
                                    phone.setProductName(parser.getText());
                                    break;
                                case "category":
                                    phone.setCategory(parser.getText());
                                    break;
                                case "description":
                                    phone.setDescription(parser.getText());
                                    break;
                                case "price":
                                    phone.setPrice(parser.getText());
                                    break;
                                case "photo":
                                    phone.setPhoto(parser.getText());
                                    break;
                            }
                        }
                        break;
                }
                eventType = parser.next();
            }
            return phoneList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}