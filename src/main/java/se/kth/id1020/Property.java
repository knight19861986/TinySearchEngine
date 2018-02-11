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
public enum Property {
        NO,
        COUNT,
        POPULARITY,
        OCCURRENCE,
        WRONG;

        public static Property getType(String input) {
            if (input.equalsIgnoreCase("count")) {
                return COUNT;
            } else if (input.equalsIgnoreCase("cou")) {
                return COUNT;
            } else if (input.equalsIgnoreCase("popularity")) {
                return POPULARITY;
            } else if (input.equalsIgnoreCase("pop")) {
                return POPULARITY;
            } else if (input.equalsIgnoreCase("occurrence")) {
                return OCCURRENCE;
            } else if (input.equalsIgnoreCase("occ")) {
                return OCCURRENCE;
            } else {
                return WRONG;
            }
        }
    }
