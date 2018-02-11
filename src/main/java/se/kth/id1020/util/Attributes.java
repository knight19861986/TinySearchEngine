/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id1020.util;

/**
 *
 * @author xiaolinzhao
 */
public class Attributes {
    public final Document document;
    public final int occurrence;

    public Attributes(Document document, int occurrence){
        this.occurrence = occurrence;
        this.document = document;
    }

    @Override
    public String toString() {
        return "Attributes{" +
                "document='" + document + '\'' +
                ", occurrence=" + occurrence +
                '}';
    }
}
