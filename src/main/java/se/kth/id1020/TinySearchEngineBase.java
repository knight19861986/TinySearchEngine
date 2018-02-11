/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id1020;

import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import java.util.List;
import se.kth.id1020.util.Word;

/**
 *
 * @author xiaolinzhao
 */
public interface TinySearchEngineBase {
    //Build the index
    public void insert(Word word, Attributes attr);
    
    //Sort the index
    public void sortIndexes();
    
    //Aggrigate the index
    public void aggregateIndexes();

    //Searching
    public List<Document> search(String query);

}
