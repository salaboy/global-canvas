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
    private String color;

    public DrawEvent() {

    }

    public DrawEvent(String user, int x, int y, String type, String color) {
        this.user = user;
        this.x = x;
        this.y = y;
        this.type = type;
        eventDate = new Date();
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrawEvent drawEvent = (DrawEvent) o;

        if (x != drawEvent.x) return false;
        if (y != drawEvent.y) return false;
        if (!user.equals(drawEvent.user)) return false;
        if (!type.equals(drawEvent.type)) return false;
        if (!eventDate.equals(drawEvent.eventDate)) return false;
        return color.equals(drawEvent.color);

    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + type.hashCode();
        result = 31 * result + eventDate.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DrawEvent{" +
                "user='" + user + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", type='" + type + '\'' +
                ", eventDate=" + eventDate +
                ", color='" + color + '\'' +
                '}';
    }
}
