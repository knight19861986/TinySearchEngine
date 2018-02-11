/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id1020;

import java.util.ArrayList;
import java.util.List;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;

/**
 *
 * @author xiaolinzhao
 */
class TinySearchEngine_qinghuage implements TinySearchEngineBase {

    public ArrayList<Index> Indexs = new ArrayList<Index>();

    public void sortIndexes() {

    }

    public void aggregateIndexes() {
        
    }

    public class Index extends Article {

        public String letter;
        public Article Art = new Article();

        public void Index(Word word) {
            letter = word.word;

        }

        public void Index(Attributes atrbts) {
            Art.Article(atrbts);
// System.out.println("New Article added "+Art.name);

        }

        public void Increasecount() {
            Art.count++;
        }
    }

    public class Article {

        public String name;
        public int count, pop, occur;

        public void Article(Attributes atrbts) {
            name = atrbts.document.name;
            count = 1;//Why handle this? Not required in the Project file.
            pop = atrbts.document.popularity;
            occur = atrbts.occurrence;
        }
    }

    public TinySearchEngine_qinghuage() {
    }
    

    public void insert(Word word, Attributes atrbts) {
        System.out.println("Running insert!");
        if (word.word.equals(" ") || word.word.equals(".") || word.word.equals(",") || word.word.equals("!") || word.word.equals("?")) {
            return;
        }
        int s = Indexs.size();
        int place = s;
        if (s != 0 && Indexs.get(s - 1).Art.name.equals(atrbts.document.name)) {
            for (int i = s; i >= 0; i--) {
                place--;
// System.out.println("scanning for word:"+word.word+" "+Indexs.get(place).letter);
                if (Indexs.get(place).letter.equals(word.word)) {
                    Indexs.get(place).Increasecount();
// System.out.println("word already exists in this article: "+word.word+"count increased");
                    break;
                }
                if (place == 0 || Indexs.get(place).letter.equals("system:special&*mark")) {
                    Index a = new Index();
                    a.Index(word);
                    a.Index(atrbts);
                    Indexs.add(a);
// System.out.println("New word added: "+word.word); 
                    break;
                }
            }
        } else {
            Index mark = new Index();
            mark.letter = "system:special&*mark";
            Indexs.add(mark);
            Index cur = new Index();
            cur.Index(word);
            cur.Index(atrbts);
            Indexs.add(cur);
// if(s!=0)
// System.out.println("Article "+Indexs.get(s-1).Art.name+" finished reading, beginning to index Article: "+atrbts.document); 
// else
// System.out.println("beginning to index Article: "+atrbts.document); 
// System.out.println("New word added: "+word.word); 
        }
//        if (word.word.equals("stupefying")) {
//            ComparatorIndex comparator = new ComparatorIndex();
//            Collections.sort(Indexs, comparator);
//            System.out.println("done sorting Indexs");
//// int t=Indexs.size();
//// System.out.println("The indexs are as below:");
//// for(int i=0;i<t;i++){
//// Index a=Indexs.get(i);
//// System.out.println("Word: "+a.letter+" Article: "+a.Art.name+" count:"+a.Art.count);
//// }
//        }

    }

    public List<Document> search(String query) {
        System.out.println("Running search with " + query);
//        Index current = new Index();
//        int a = searchs(query);
//        int low = a;
//        int high = a;
//        if (a == -1) {
//            System.out.println("This word don't exist in the documents");
//            return null;
//        } else {
//            for (int i = a; i >= 0; i--) {
//                if (!query.equals(Indexs.get(i).letter)) {
//                    low = i + 1;
//                    break;
//                }
//            }
//            for (int j = a; j <= 1000; j++) {
//                if (!query.equals(Indexs.get(j).letter)) {
//                    high = j - 1;
//                    break;
//                }
//            }
//        }
//        for (int i = low; i <= high; i++) {
//            current = Indexs.get(i);
//            System.out.println(current.Art.name);
//        }
        return null;

    }

}
