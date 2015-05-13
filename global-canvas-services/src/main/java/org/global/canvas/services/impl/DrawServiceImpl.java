/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.global.canvas.services.impl;

import com.grogdj.grogshop.core.model.DrawEvent;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.global.canvas.services.api.DrawService;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.awt.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.awt.Stroke.*;


/**
 *
 * @author salaboy
 *
 */
public class DrawServiceImpl implements DrawService {

    private DOMImplementation domImpl;

    // Create an instance of org.w3c.dom.Document.
    private String svgNS = "http://www.w3.org/2000/svg";
    private Document document;
    private SVGGraphics2D svgGenerator;
    private List<Integer> currentX = new ArrayList<Integer>();
    private List<Integer> currentY = new ArrayList<Integer>();
    private String currentUser;

    public DrawServiceImpl() {

    }

    public void draw(DrawEvent event) {
        currentUser = event.getUser();
        if(event.getType().equals("dragstart")){
            domImpl = GenericDOMImplementation.getDOMImplementation();
            document = domImpl.createDocument(svgNS, "svg", null);
            svgGenerator = new SVGGraphics2D(document);
            System.out.println("Color here: "+event.getColor());
            svgGenerator.setColor(Color.decode(event.getColor()));
            svgGenerator.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0,
                    new float[] { 3, 1 }, 0));
        }

            currentX.add(event.getX());
            currentY.add(event.getY());

        
        if(event.getType().equals("dragend")){
            svgGenerator.drawPolyline(ArrayUtils.toPrimitive(currentX.toArray(new Integer[0])), ArrayUtils.toPrimitive(currentY.toArray(new Integer[0])), currentX.size());
            export();
            domImpl = null;
            document= null;
            svgGenerator = null;
            currentX.clear();
            currentY.clear();


        }
        
    }

    public void export() {
        System.out.println(" Exporting stuff.. user = "+currentUser);
        Writer out = null;

        try {
            boolean useCSS = true; // we want to use CSS style attributes
            out = new OutputStreamWriter(new FileOutputStream("/tmp/canvas/"+new Date().getTime()+"-"+currentUser.split("@")[0]+".svg"),"UTF-8");
            try {
                svgGenerator.stream(out, useCSS);
            } catch (SVGGraphics2DIOException ex) {
                Logger.getLogger(DrawServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DrawServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DrawServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(DrawServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void dispose() {

            System.out.println("disposing.... ");

    }
    

    
    
    
    
}
