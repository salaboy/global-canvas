/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.global.canvas.services.api;

import com.grogdj.grogshop.core.model.DrawEvent;
import org.apache.batik.transcoder.TranscoderException;

import java.io.IOException;

/**
 *
 * @author salaboy
 */
public interface StorageService {

    void merge() throws IOException, TranscoderException;
}
