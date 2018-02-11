/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id1020;

import edu.princeton.cs.introcs.In;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author xiaolinzhao
 */
public class ParsedQuery {    
    
    

    public ArrayList<String>searchedWords=new ArrayList();
    private Property prop = Property.NO;
    private Direction dirx = Direction.NO;
    
    public ParsedQuery(String query) throws ParsingException {
        String[] words;
        int hasOrder = query.trim().indexOf("orderby");
        if (hasOrder < 0) {
            words = query.trim().split(" ");
            searchedWords.addAll(Arrays.asList(words));
        } else if (hasOrder > 0) {
            String[] twoParts = query.trim().split("orderby");
            if (twoParts.length < 2) {
                throw new ParsingException(0);
            } else if (twoParts.length > 2) {
                throw new ParsingException(1);
            } else {
                words = twoParts[0].trim().split(" ");
                searchedWords.addAll(Arrays.asList(words));
                String[] orderFactors = twoParts[1].trim().split(" ");
                if (orderFactors.length != 2) {
                    throw new ParsingException(2);
                } else {
                    this.prop = Property.getType(orderFactors[0]);
                    this.dirx = Direction.getType(orderFactors[1]);
                    if ((this.prop == Property.WRONG) || (this.dirx == Direction.WRONG)) {
                        throw new ParsingException(3);
                    }

                }
            }

        } else {
            throw new ParsingException(4);
        }

    }
    
    public Property getProperty(){
        return this.prop;
    
    }
    
    public Direction getDirection(){
        return this.dirx;
    
    }
    

    public static void main(String[] args) throws ParsingException {

        System.out.println("ParsedQuery Testing!");
        In input = new In(new Scanner(System.in));
        for (int i = 0; i < 6; i++) {
            try {

                System.out.print("Search: ");
                String query = input.readLine();
                ParsedQuery parseQuery = new ParsedQuery(query);

                System.out.println("Searched Words:");
                for (String word : parseQuery.searchedWords) {
                    System.out.println(word);
                }
                System.out.println("Property:" + parseQuery.getProperty().name());
                System.out.println("Direction:" + parseQuery.getDirection().name());

            } catch (ParsingException e) {
                e.printStackTrace(System.out);
            } finally {
                continue;
            }
        }
    }
}
