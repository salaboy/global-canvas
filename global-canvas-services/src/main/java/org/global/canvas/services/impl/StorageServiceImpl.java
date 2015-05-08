/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.global.canvas.services.impl;

import com.grogdj.grogshop.core.model.DrawEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.io.FileUtils;
import org.global.canvas.services.api.CanvasService;
import org.global.canvas.services.api.StorageService;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGSVGElement;

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class StorageServiceImpl implements StorageService{

    @Inject
    private CanvasService canvasService;

    @Inject
    private StorageService storageService;

    @PostConstruct
    public void init() throws IOException {

        Path directory = Paths.get("/tmp/canvas/");
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

        });
        System.out.println("Dir deleted.. let's create it now..");
        File dir = new File("/tmp/canvas/");
        boolean created = dir.mkdir();


    }


    public void merge() throws IOException, TranscoderException {
        File dir = new File("/tmp/canvas/");
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        SVGDocument doc = null;
        if(dir.isDirectory()) {
            File[] listOfFiles = dir.listFiles();
            System.out.println("Number of files: "+listOfFiles.length + " in dir: "+dir.getAbsolutePath());
            for (File file : listOfFiles) {
                System.out.println("File Name: "+file.getAbsolutePath());
                if(file.getAbsolutePath().endsWith(".svg")){
                    if(doc == null) {
                        String data = FileUtils.readFileToString(new File("" + file.getAbsolutePath()), "UTF-8");
                        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
                        doc = f.createSVGDocument("http://www.w3.org/2000/svg", inputStream);
                    }else{
                        String data = FileUtils.readFileToString(new File("" + file.getAbsolutePath()), "UTF-8");
                        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
                        Document doc2 = f.createDocument("http://www.w3.org/2000/svg", inputStream);
                        Node n = doc.importNode(doc2.getDocumentElement(), true);
                        doc.getDocumentElement().appendChild(n);

                    }
                }

            }

        }
        if(doc != null){

            doc.getDocumentElement().setAttribute("height", "800");
            doc.getDocumentElement().setAttribute("width", "1000");
            doc.getDocumentElement().setAttribute("viewBox", "0 0 1000 800");

            PNGTranscoder t = new PNGTranscoder();
            t.addTranscodingHint( PNGTranscoder.KEY_AOI, new Rectangle( 0, 0, 1000, 800 ) );

            TranscoderInput input = new TranscoderInput(doc);

           // OutputStream ostream = new FileOutputStream("/tmp/canvas/last-snapshot.png");
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(ostream);

            // Perform the transcoding.
            t.transcode(input, output);
            ostream.flush();
            ostream.close();

            byte[] finalResult = ostream.toByteArray();
            canvasService.setCurrentState(finalResult);

        }


    }
    
}
