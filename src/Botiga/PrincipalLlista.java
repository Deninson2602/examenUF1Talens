package Botiga;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PrincipalLlista {
    public static void main(String[] args) throws IOException {
        List<ArticlesCompra> articlesCompraList = new ArrayList<>();
        while(true) {
            ArticlesCompra article = getArticleFromInput();
            if(article == null) break;
            articlesCompraList.add(article);
        }

        // Aquí puedes manipular la lista de Botiga.ArticlesCompra
        // por ejemplo, imprimir todos los elementos
        for(ArticlesCompra ac: articlesCompraList) {
            System.out.println(ac.getDescripcion());
            System.out.println(ac.getCantidad());
            System.out.println(ac.getUnidad());
            System.out.println(ac.getSeccion());
            System.out.println(ac.getPrecio());
            System.out.println("--------------------");
        }

        writeXML(articlesCompraList);
        writeSerializedFile(articlesCompraList);
    }

    private static ArticlesCompra getArticleFromInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Ingrese la descripción del artículo (o 'q' para terminar):");
        String Descripcio = reader.readLine();
        if(Descripcio.equals("q")) return null;

        System.out.println("Ingrese la cantidad del artículo:");
        double cantidad = Double.parseDouble(reader.readLine());

        System.out.println("Ingrese la unidad de medida:");
        String unidad = reader.readLine();

        System.out.println("Ingrese la sección donde se puede encontrar el producto:");
        String seccion = reader.readLine();

        System.out.println("Ingrese el precio del producto:");
        double precio = Double.parseDouble(reader.readLine());

        return new ArticlesCompra(Descripcio, cantidad, unidad, seccion, precio);
    }

    public static void writeXML(List<ArticlesCompra> list) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // raíz de los elementos
            Element rootElement = doc.createElement("llistacompra");
            doc.appendChild(rootElement);

            for (ArticlesCompra ac : list) {
                // Elemento article
                Element article = doc.createElement("article");
                rootElement.appendChild(article);

                // Elemento descripcio
                Element descripcio = doc.createElement("descripcio");
                descripcio.appendChild(doc.createTextNode(ac.getDescripcion()));
                article.appendChild(descripcio);

                // Elemento quantitat
                Element quantitat = doc.createElement("quantitat");
                quantitat.appendChild(doc.createTextNode(Double.toString(ac.getCantidad())));
                article.appendChild(quantitat);

                // Elemento unitat
                Element unitat = doc.createElement("unitat");
                unitat.appendChild(doc.createTextNode(ac.getUnidad()));
                article.appendChild(unitat);

                // Elemento seccio
                Element seccio = doc.createElement("seccio");
                seccio.appendChild(doc.createTextNode(ac.getSeccion()));
                article.appendChild(seccio);

                // Elemento precio
                Element precio = doc.createElement("precio");
                seccio.appendChild(doc.createTextNode(Double.toString(ac.getCantidad())));
                article.appendChild(precio);
            }

            // Escribir el contenido en un archivo .xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("lista_compra.xml"));

            transformer.transform(source, result);

            System.out.println("Archivo guardado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeSerializedFile(List<ArticlesCompra> list) {
        try {
            FileOutputStream fileOut = new FileOutputStream("lista_compra.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(list);
            out.close();
            fileOut.close();
            System.out.println("Se ha guardado la lista de la compra en lista_compra.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    //MÉTODO PARA LEER UN XML
    public static List<ArticlesCompra> readXML() {
        List<ArticlesCompra> list = new ArrayList<>();
        try {
            File inputFile = new File("lista_compra.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("article");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String descripcion = eElement.getElementsByTagName("descripcio").item(0).getTextContent();
                    double cantidad = Double.parseDouble(eElement.getElementsByTagName("quantitat").item(0).getTextContent());
                    String unidad = eElement.getElementsByTagName("unitat").item(0).getTextContent();
                    String seccion = eElement.getElementsByTagName("seccio").item(0).getTextContent();
                    double precio = Double.parseDouble(eElement.getElementsByTagName("precio").item(0).getTextContent());

                    list.add(new ArticlesCompra(descripcion, cantidad, unidad, seccion, precio));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //MÉTODO PARA DESERIALIZAR
    public static List<ArticlesCompra> readSerializedFile() {
        List<ArticlesCompra> list = null;
        try {
            FileInputStream fileIn = new FileInputStream("lista_compra.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            list = (List<ArticlesCompra>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("ArticlesCompra class not found");
            c.printStackTrace();
            return null;
        }

        return list;
    }
}
