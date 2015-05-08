/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.UserAgentAdapter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
import java.io.*;
import java.util.Date;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.io.FileUtils;

import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGDocument;



/**
 *
 * @author salaboy
 */
public class SVGJUnitTest {

    public SVGJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void hello() throws UnsupportedEncodingException, SVGGraphics2DIOException, FileNotFoundException, TranscoderException, IOException {
        // Get a DOMImplementation.
        DOMImplementation domImpl
                = GenericDOMImplementation.getDOMImplementation();

        // Create an instance of org.w3c.dom.Document.
        String svgNS = "http://www.w3.org/2000/svg";
        Document document = domImpl.createDocument(svgNS, "svg", null);

        Document document2 = domImpl.createDocument(svgNS, "svg", null);
        // Create an instance of the SVG Generator.
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
        SVGGraphics2D svgGenerator2 = new SVGGraphics2D(document2);

        // Ask the test to render into the SVG Graphics2D implementation.
        paint(svgGenerator);
        paint2(svgGenerator2);

    // Finally, stream out SVG to the standard output using
        // UTF-8 encoding.
        boolean useCSS = true; // we want to use CSS style attributes
        Writer out = new OutputStreamWriter(new FileOutputStream("target/out1.svg"),"UTF-8");
        svgGenerator.stream(out, useCSS);
        
        Writer out2 = new OutputStreamWriter(new FileOutputStream("target/out2.svg"),"UTF-8");
        svgGenerator2.stream(out2, useCSS);
        
        // Create a JPEGTranscoder and set its quality hint.
        PNGTranscoder t = new PNGTranscoder();
//        t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,
//                   new Float(.8));
        
        // Set the transcoder input and output.
        String svgURI = new File("target/out1.svg").toURL().toString();
        TranscoderInput input = new TranscoderInput(svgURI);
        
        OutputStream ostream = new FileOutputStream("target/out1.png");
        TranscoderOutput output = new TranscoderOutput(ostream);

        // Perform the transcoding.
        t.transcode(input, output);
        ostream.flush();
        ostream.close();
        
        
        String svgURI2 = new File("target/out2.svg").toURL().toString();
        TranscoderInput input2 = new TranscoderInput(svgURI2);
        
        OutputStream ostream2 = new FileOutputStream("target/out2.png");
        TranscoderOutput output2 = new TranscoderOutput(ostream2);

        // Perform the transcoding.
        t.transcode(input2, output2);
        ostream2.flush();
        ostream2.close();
        
        
        
        
        
    }
    
    @Test
    public void merge() throws IOException, TranscoderException{



        String data = FileUtils.readFileToString(new File("target/out1.svg"), "UTF-8");
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);


        Document doc = f.createDocument("http://www.w3.org/2000/svg", inputStream);


        String data2 = FileUtils.readFileToString(new File("target/out2.svg"), "UTF-8");
        InputStream inputStream2 = new ByteArrayInputStream(data2.getBytes());

        Document doc2 = f.createDocument("http://www.w3.org/2000/svg", inputStream2);
        

        Node n = doc.importNode(doc2.getDocumentElement(), true);
        doc.getDocumentElement().appendChild(n);
        


        PNGTranscoder t = new PNGTranscoder();



        TranscoderInput input = new TranscoderInput(doc);

        OutputStream ostream = new FileOutputStream("target/merged.png");
        TranscoderOutput output = new TranscoderOutput(ostream);

        // Perform the transcoding.
        t.transcode(input, output);
        ostream.flush();
        ostream.close();
        
    
    }
    @Test
    public void mergeAll() throws IOException, TranscoderException {
        File dir = new File("target/");
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        Document doc = null;
        if(dir.isDirectory()) {
            File[] listOfFiles = dir.listFiles();

            for (File file : listOfFiles) {
                if(file.getName().endsWith(".svg")){
                   System.out.println("File: " + file.getName());
                   if(doc == null) {

                       String data = FileUtils.readFileToString(new File("target/" + file.getName()), "UTF-8");
                       InputStream inputStream = new ByteArrayInputStream(data.getBytes());
                       doc = f.createDocument("http://www.w3.org/2000/svg", inputStream);
                   }else{
                       String data = FileUtils.readFileToString(new File("target/" + file.getName()), "UTF-8");
                       InputStream inputStream = new ByteArrayInputStream(data.getBytes());
                       Document doc2 = f.createDocument("http://www.w3.org/2000/svg", inputStream);
                       Node n = doc.importNode(doc2.getDocumentElement(), true);
                       doc.getDocumentElement().appendChild(n);
                   }
                }

            }

        }
        if(doc != null){
            PNGTranscoder t = new PNGTranscoder();



            TranscoderInput input = new TranscoderInput(doc);

            OutputStream ostream = new FileOutputStream("target/"+new Date().getTime()+"-snapshot.png");
            TranscoderOutput output = new TranscoderOutput(ostream);

            // Perform the transcoding.
            t.transcode(input, output);
            ostream.flush();
            ostream.close();

        }

    }


    public void paint(Graphics2D g2d) {

        g2d.setPaint(Color.red);
        g2d.fill(new Rectangle(10, 10, 100, 100));
    }
    
    public void paint2(Graphics2D g2d) {

        g2d.setPaint(Color.green);
        g2d.fill(new Rectangle(90, 90, 100, 100));
    }

}
