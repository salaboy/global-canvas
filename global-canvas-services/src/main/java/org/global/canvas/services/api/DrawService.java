/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.global.canvas.services.api;

import com.grogdj.grogshop.core.model.DrawEvent;

/**
 *
 * @author salaboy
 * All of these service needs to provide async implementations
 */
public interface DrawService {
    void draw(DrawEvent event);

    void export();

    public void dispose();
}
