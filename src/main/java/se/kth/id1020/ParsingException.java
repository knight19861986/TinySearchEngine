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
class ParsingException extends Exception {

    public ParsingException(int num) {
        System.out.println("Illegal query!");
        switch (num) {
            case 0:
                System.out.println("The key word \"orderby\" cannot be put at the end of the query.");
                break;
            case 1:
                System.out.println("The key word \"orderby\" cannot be put more than once.");
                break;
            case 2:
                System.out.println("Illegal orderby information has been input.");
                break;
            case 3:
                System.out.println("Illegal property or direction information has been input.");
                break;
            case 4:
                System.out.println("The key word \"orderby\" cannot be put at the beginning of the query.");
                break;
            default:
                System.out.println("An unkown error has been occured.");
                break;
        }
    }

}
