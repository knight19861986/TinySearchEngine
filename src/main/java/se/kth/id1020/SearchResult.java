/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id1020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static se.kth.id1020.Driver.convertnanoTimeToString;
import se.kth.id1020.util.*;

/**
 *
 * @author xiaolinzhao
 */
public class SearchResult {

    public class DocumentPlus extends Document implements Aggrigation {

        int count = 0;
        int leastOccurrence = 0;

        public DocumentPlus(Document document) {
            super(document.name);
        }

        public DocumentPlus(Document document, int count) {
            super(document.name);
            this.count = count;
        }

        public DocumentPlus(TinySearchEngine.Index_Aggregated index_aggrigated) {
            super(index_aggrigated.getAttributes().document.name);
            this.count = index_aggrigated.getCount();
            this.leastOccurrence = index_aggrigated.getAttributes().occurrence;
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

        public int getLeastOccurrence() {
            return this.leastOccurrence;
        }

        public void setLeastOccurrence(int num) {
            this.leastOccurrence = num;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Document{").append(name).append(", pop=").append(popularity).append("}");
            sb.append("Count=").append(this.getCount());
            sb.append(", The least occurrence=").append(this.getLeastOccurrence());
            return sb.toString();
        }
    }

    public List<TinySearchEngine.Index_Aggregated> resultOriginal = new ArrayList<TinySearchEngine.Index_Aggregated>();
    public List<DocumentPlus> resultPlus = new ArrayList<DocumentPlus>();

    public SearchResult(List<TinySearchEngine.Index_Aggregated> result) {

        this.resultOriginal = result;
    }

    public void aggregateResultOriginal() {
        this.sortResultOriginal();
        DocumentPlus documentPlus;
        long t1 = System.nanoTime();
        if (!resultOriginal.isEmpty()) {
            documentPlus = new DocumentPlus(resultOriginal.get(0));
            resultPlus.add(documentPlus);
        } else {
            return;
        }

        for (int i = 1; i < resultOriginal.size(); i++) {
            documentPlus = new DocumentPlus(resultOriginal.get(i));
            if (resultOriginal.get(i).getDocument().name.equals(resultPlus.get(resultPlus.size() - 1).name)) {
                resultPlus.get(resultPlus.size() - 1).increase(resultOriginal.get(i).getCount());
            } else {
                resultPlus.add(documentPlus);
            }

        }

        long e = System.nanoTime() - t1;
        System.out.println("Aggrigating the original result done in " + convertnanoTimeToString(e));
        System.out.println("Size of aggrigated result is " + resultPlus.size());

    }

    public void sortResultOriginal() {

        Comparator<TinySearchEngine.Index_Aggregated> comparator = new Comparator<TinySearchEngine.Index_Aggregated>() {

            public int compare(TinySearchEngine.Index_Aggregated i1, TinySearchEngine.Index_Aggregated i2) {

                if (!i1.getAttributes().document.name.equals(i2.getAttributes().document.name)) {
                    return i1.getAttributes().document.name.compareTo(i2.getAttributes().document.name);
                } else {
                    return i1.getAttributes().occurrence - i2.getAttributes().occurrence;
                }

            }
        };
        long t1 = System.nanoTime();
        //Collections.sort(resultOriginal, comparator);//Merge sort
        //Bubble sort:
        for (int i = 0; i < resultOriginal.size() - 1; i++) {
            for (int j = 1; j < resultOriginal.size() - i; j++) {
                TinySearchEngine.Index_Aggregated a = resultOriginal.get(j - 1);
                TinySearchEngine.Index_Aggregated b = resultOriginal.get(j);

                if (comparator.compare(a, b) > 0) {
                    TinySearchEngine.Index_Aggregated temp = resultOriginal.get(j - 1);
                    resultOriginal.set((j - 1), resultOriginal.get(j));
                    resultOriginal.set(j, temp);
                }
            }
        } 
        long e = System.nanoTime() - t1;
        System.out.println("Sorting the original result done in " + convertnanoTimeToString(e));
        System.out.println("Size of original result is " + resultOriginal.size());

    }

    public void sortResultPlus(Property prop, Direction dirx) {
        this.aggregateResultOriginal();
        Comparator<DocumentPlus> comparator;
        int direction = 0;
        if (prop.equals(Property.COUNT)) {
            comparator = new Comparator<DocumentPlus>() {
                public int compare(DocumentPlus d1, DocumentPlus d2) {
                    return d1.getCount() - d2.getCount();
                }
            };
        } else if (prop.equals(Property.POPULARITY)) {
            comparator = new Comparator<DocumentPlus>() {
                public int compare(DocumentPlus d1, DocumentPlus d2) {
                    return d1.popularity - d2.popularity;
                }
            };
        } else if (prop.equals(Property.OCCURRENCE)) {
            comparator = new Comparator<DocumentPlus>() {
                public int compare(DocumentPlus d1, DocumentPlus d2) {
                    return d1.getLeastOccurrence() - d2.getLeastOccurrence();
                }
            };
        } else {
            return;
        }
        if (dirx.equals(Direction.ASC)) {
            direction = 1;
        } else if (dirx.equals(Direction.DESC)) {
            direction = -1;
        } else {
            return;
        }
        
        //Bubble sort:
        long t1 = System.nanoTime();
        for (int i = 0; i < resultPlus.size() - 1; i++) {
            for (int j = 1; j < resultPlus.size() - i; j++) {
                DocumentPlus a = resultPlus.get(j - 1);
                DocumentPlus b = resultPlus.get(j);

                if (direction * comparator.compare(a, b) > 0) {
                    DocumentPlus temp = resultPlus.get(j - 1);
                    resultPlus.set((j - 1), resultPlus.get(j));
                    resultPlus.set(j, temp);
                }
            }
        } 
        long e = System.nanoTime() - t1;
        System.out.println("Sorting the final result done in " + convertnanoTimeToString(e));
        System.out.println("Size of final result is " + resultPlus.size());
    }
}
