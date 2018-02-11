/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id1020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import static se.kth.id1020.Driver.convertnanoTimeToString;
import se.kth.id1020.TinySearchEngine.Index;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;

/**
 *
 * @author xiaolinzhao
 */
public class TinySearchEngine implements TinySearchEngineBase {

    public class Index {

        protected final Word word;
        protected final Attributes attr;

        public Index(Word word, Attributes attr) {
            this.word = word;
            this.attr = attr;
        }

        public Word getWord() {
            return this.word;
        }

        public Attributes getAttributes() {
            return this.attr;
        }

        public Document getDocument() {
            return this.attr.document;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.word.toString());
            sb.append(this.attr.toString());
            return sb.toString();
        }
    };

    public class Index_Aggregated extends Index implements Aggrigation {

        int count = 1;

        public Index_Aggregated(Word word, Attributes attr) {
            super(word, attr);
        }

        public Index_Aggregated(Word word, Attributes attr, int count) {
            super(word, attr);
            this.count = count;
        }

        public Index_Aggregated(Index index) {
            super(index.word, index.attr);
        }

        public Index_Aggregated(Index index, int count) {
            super(index.word, index.attr);
            this.count = count;
        }

        public int getCount() {
            return this.count;
        }

        public void setCount(int num) {
            this.count = num;
        }

        public void increase() {
            this.count++;
        }

        public void increase(int num) {
            this.count += num;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.word.toString());
            sb.append(this.attr.toString());
            sb.append("Count=");
            sb.append(this.count);
            return sb.toString();
        }

    };

    //private List<Index> Indexes= new LinkedList<Index>();
    private List<Index> indexes = new ArrayList<Index>();
    private List<Index_Aggregated> indexes_aggregated = new ArrayList<Index_Aggregated>();

    public void insert(Word word, Attributes attr) {

        Index index = new Index(word, attr);
        indexes.add(index);

    }

    public List<Document> search(String query) {
        List<Index_Aggregated> result_original = new ArrayList<Index_Aggregated>();
        List<Document> result_final = new ArrayList<Document>();

        try {
            ParsedQuery parsedQuery = new ParsedQuery(query);
            for (String word : parsedQuery.searchedWords) {
                Index_Aggregated index_aggregated = searchByBinary(word);
                if (index_aggregated != null) {
                    result_original.add(index_aggregated);
                }
                if (!result_original.isEmpty()) {
                    int mid = indexes_aggregated.indexOf(index_aggregated);
                    for (int i = 1; ((mid + i) >= 0)
                            && ((mid + i) < indexes_aggregated.size())
                            && indexes_aggregated.get(mid + i).getWord().word.equals(word); i++) {
                        result_original.add(indexes_aggregated.get(mid + i));
                    }
                    for (int i = 1; ((mid - i) >= 0)
                            && ((mid - i) < indexes_aggregated.size())
                            && indexes_aggregated.get(mid - i).getWord().word.equals(word); i++) {
                        result_original.add(indexes_aggregated.get(mid - i));
                    }
                }
            }

            SearchResult searchResult = new SearchResult(result_original);
            searchResult.sortResultPlus(parsedQuery.getProperty(), parsedQuery.getDirection());
            for (SearchResult.DocumentPlus documentPlus : searchResult.resultPlus) {
                result_final.add(documentPlus);
            }            
        } catch (ParsingException e) {
            e.printStackTrace(System.out);            
        }

        return result_final;
    }

    public Index_Aggregated searchByBinary(String word) {

        int start = 0;
        int end = indexes_aggregated.size();
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (indexes_aggregated.get(mid).getWord().word.compareTo(word) < 0) {
                start = mid + 1;
            } else if (indexes_aggregated.get(mid).getWord().word.compareTo(word) > 0) {
                end = mid - 1;
            } else {
                return indexes_aggregated.get(mid);
            }
        }
        return null;
    }

    public void aggregateIndexes() {
        this.sortIndexes();
        Index_Aggregated index_aggrigated;
        long t1 = System.nanoTime();
        if (!indexes.isEmpty()) {
            index_aggrigated = new Index_Aggregated(indexes.get(0));
            indexes_aggregated.add(index_aggrigated);
        } else {
            return;
        }

        for (int i = 1; i < indexes.size(); i++) {
            index_aggrigated = new Index_Aggregated(indexes.get(i));
            if (indexes.get(i).getWord().word.equals(indexes_aggregated.get(indexes_aggregated.size() - 1).getWord().word)
                    && indexes.get(i).getDocument().name.equals(indexes_aggregated.get(indexes_aggregated.size() - 1).getDocument().name)) {
                indexes_aggregated.get(indexes_aggregated.size() - 1).increase();
            } else {
                indexes_aggregated.add(index_aggrigated);
            }

        }

        long e = System.nanoTime() - t1;
        System.out.println("Aggrigating the index done in " + convertnanoTimeToString(e));
        System.out.println("Size of aggrigated index is " + indexes_aggregated.size());

    }

    public void sortIndexes() {
        Comparator<Index> comparator = new Comparator<Index>() {

            public int compare(Index i1, Index i2) {

                if (!i1.getWord().word.equals(i2.getWord().word)) {
                    return i1.getWord().word.compareTo(i2.getWord().word);
                } else {

                    if (!i1.getDocument().name.equals(i2.getDocument().name)) {
                        return i1.getDocument().compareTo(i2.getDocument());
                    } else {
                        return i1.getAttributes().occurrence - i2.getAttributes().occurrence;
                    }

                }
            }
        };

        long t1 = System.nanoTime();
        Collections.sort(indexes, comparator);//Merge sort
        long e = System.nanoTime() - t1;
        System.out.println("Sorting the index done in " + convertnanoTimeToString(e));
        System.out.println("Size of index is " + indexes.size());

    }
}
