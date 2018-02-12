## Tiny Search Engine 1.0

### Description
The goal of the project is to build a simple search engine for natural language text documents.

### Key Features
The search engine will support  
- Index documents based on their content.
- Find and list documents which contain a single provided key word.
- Order search results by different properties (e.g. relevance, popularity).
- Use a query language to support more involved searches.

### Requirements
- JAVA >= 7.0
- MAVEN >= 4.0

### Date source

BROWN CORPUS

A Standard Corpus of Present-Day Edited American
English, for use with Digital Computers.

by W. N. Francis and H. Kucera (1964)
Department of Linguistics, Brown University
Providence, Rhode Island, USA

Revised 1971, Revised and Amplified 1979

http://www.hit.uib.no/icame/brown/bcm.html

Distributed with the permission of the copyright holder,
redistribution permitted.

Location in the project:
/src/main/resources/brown


### Usage

Classpath: se.kth.id1020.TestSearchEngine

Search query: word1 [word2] ... [orderby cou/pop/occ asc/desc] 

- word1 [word2] ... : Words to be searched, at least one
- orderby: Order the result by count or popularity or occurrence
- direction: Set the direction of order, asc or desc


