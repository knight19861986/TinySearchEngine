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
public class TestSearchEngine {

    public static void main(String[] args) throws Exception{

        System.out.println( "TestSearchEngine Started!");
        TinySearchEngineBase searchEngine = new TinySearchEngine();
        Driver.run(searchEngine);

    }

}
