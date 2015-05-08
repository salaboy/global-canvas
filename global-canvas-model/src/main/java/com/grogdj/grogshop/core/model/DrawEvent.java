/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grogdj.grogshop.core.model;

import java.util.Date;

/**
 *
 * @author grogdj
 */
public class DrawEvent {

    private String user;

    private int x;
    private int y;
    private String type;
    private Date eventDate;

    public DrawEvent() {

    }

    public DrawEvent(String user, int x, int y, String type) {
        this.user = user;
        this.x = x;
        this.y = y;
        this.type = type;
        eventDate = new Date();
    }

    public String getUser() {
        return user;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }

    public Date getEventDate() {
        return eventDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.user != null ? this.user.hashCode() : 0);
        hash = 47 * hash + this.x;
        hash = 47 * hash + this.y;
        hash = 47 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 47 * hash + (this.eventDate != null ? this.eventDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DrawEvent other = (DrawEvent) obj;
        if ((this.user == null) ? (other.user != null) : !this.user.equals(other.user)) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if ((this.type == null) ? (other.type != null) : !this.type.equals(other.type)) {
            return false;
        }
        if (this.eventDate != other.eventDate && (this.eventDate == null || !this.eventDate.equals(other.eventDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DrawEvent{" + "user=" + user + ", x=" + x + ", y=" + y + ", type=" + type + ", eventDate=" + eventDate + '}';
    }

}
