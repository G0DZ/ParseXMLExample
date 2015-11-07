import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by L8p on 07.11.2015.
 */
public class TestXMLParse {
    static void getMapFile(){
        Document doc = null;
        try {
            //URL url = new URL("http://pogoda.yandex.ru/static/cities.xml");
            //URLConnection uc = url.openConnection();
            File inputFile = new File("cities.xml");
            //InputStream is = uc.getInputStream();//создали поток
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(inputFile);//непосредственно парсинг
            doc.getDocumentElement().normalize();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при запросе к Яндекс АПИ");
            return;
            //ex.printStackTrace();
        }
        parseDoc(doc);
    }


    private static void parseDoc(Document doc) {
        File cachefile = new File("_names.txt");
        try(FileWriter writer = new FileWriter(cachefile, false))
        {
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            //если есть интернет и запросы верны - файл не будет пуст.
            //все содержится в теге cities
            NodeList countryList = doc.getElementsByTagName("city");
            //NodeList countryList = doc.getElementsByTagName("cities").item(0).getChildNodes();
            for (int i = 0; i < countryList.getLength(); i++) {
                Node nNode = countryList.item(i);
                NamedNodeMap attr = nNode.getAttributes();
                String countryName = attr.getNamedItem("country").getNodeValue();
                String id = attr.getNamedItem("id").getNodeValue();
                String cityName = nNode.getTextContent();
                    if(i==0)
                        writer.write(id+"="+cityName+", "+countryName+"\n");
                    else
                        writer.append(id+"="+cityName+", "+countryName+"\n");
           }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String args[]){
        getMapFile();
    }
}
