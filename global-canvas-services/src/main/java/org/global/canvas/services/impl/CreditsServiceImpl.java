/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.global.canvas.services.impl;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.global.canvas.services.api.CreditsService;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class CreditsServiceImpl implements CreditsService {

    private List<String> users = new ArrayList<String>();

    public void addUser(String user) {
        users.add(user);
    }

    public List<String> getAllUsers() {
        return users;
    }

}
