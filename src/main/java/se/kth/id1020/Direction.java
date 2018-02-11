/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id1020;

/**
 *
 * @author xiaolinzhao
 */
public enum Direction{
        NO,
        ASC,
        DESC,
        WRONG;
        
        public static Direction getType(String input) {
            if (input.equalsIgnoreCase("asc")) {
                return ASC;
            } else if (input.equalsIgnoreCase("desc")) {
                return DESC;
            } else {
                return WRONG;
            }
        }        
        
    }
