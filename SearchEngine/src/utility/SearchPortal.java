package utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class SearchPortal {
	
	public static void main(String[] args) {
		List<String> doc1 = Arrays.asList("Lorem", "ipsum", "dolor", "ipsum", "sit", "ipsum");
        List<String> doc2 = Arrays.asList("Vituperata", "incorrupte", "at", "ipsum", "pro", "quo");
        List<String> doc3 = Arrays.asList("Has", "persius", "disputationi", "id", "simul");
//        List<List<String>> documents = Arrays.asList(doc1, doc2, doc3);
        List<List<String>> documents = new ArrayList<List<String>>();
        documents.add(doc1);
        documents.add(doc2);
        documents.add(doc3);
        
        Scanner scanner = new Scanner(System.in); 
        System.out.println("Enter search query");
        String[] query = scanner.nextLine().split(" ");
//        System.out.println("Query: "+query);
        
        //Save query into a string list
        List<String> docQ = new ArrayList<String>();
        
        for(String term:query) {
        	docQ.add(term);
        }
        
        documents.add(docQ);
        
        //Step 1: calculate two input terms' tf-idf values in all documents and query
        //map is a data structure, each element in the map is a key-value pair 
        //key:String(term), value: index of this term in tf-idf matrix
        Map<String,Integer> terms = new HashMap<String,Integer>();
        int index = 0;
        for(List<String> document : documents) {
        	for(String term : document) {
        		//detect whether this term has been seen
        		if(!terms.containsKey(term.toLowerCase())){
        			terms.put(term.toLowerCase(), index);
        			index++;
        		}
        	}
        }
        
        double[][] tfidfs = new double[documents.size()][terms.size()];
        
        TFIDFCalculator calculator = new TFIDFCalculator();
        
        for(Entry<String,Integer> term: terms.entrySet()) {
        	String key = term.getKey();
        	int ind = term.getValue();
        	for(int i=0;i<documents.size();i++) {
        		List<String> document = documents.get(i);
        		tfidfs[i][ind] = calculator.tfIdf(document, documents, key);
        	}
        }
        
        //Step 2: compare the cosine similarities between query and documents, and rank documents from greatest to lowest
        //Cosine similarity
        CosineSimilarity cosine = new CosineSimilarity();
        
        double[] similarities = new double[documents.size()-1];
        
        //array: length is 4, what is the latest index of this array
        
        for(int i=0; i<tfidfs.length-1;i++) {
        	similarities[i] = cosine.cosine(tfidfs[tfidfs.length-1], tfidfs[i]);
        }
        
        //merge sort the similarity array
        //the first element/integer in this array represent the index of the document which has greatest similarity with query
//        int[] ranking = new int[similarities.length];
        double[] copy = new double[similarities.length];
        
        for(int i=0; i<similarities.length; i++) {
        	copy[i]=similarities[i];
        }
        
        MergeSort ms = new MergeSort();
        
        ms.mergeSort(copy, 0, copy.length-1);
        
        System.out.println("Related documents:");
        //System.out.println(each terms in the doc with greatest similarity)
        //get the greatest to smallest similarity
        // similarity: [0.7, 0.8, 0.7]
        // copy [0.8, 0.7, 0.7]
        // print: doc2, doc1, doc3
        // our result: doc2 doc1 doc1
        
        // similarity: [0.0, 0.8, 0.7]
        for(double similarity:copy) {
        	for(int i=0; i<similarities.length; i++) {
        		if(similarity>0 && similarity==similarities[i]) {
        			
        			List<String> document = documents.get(i);
        			
        			for(String term : document) {// for(int j=0; j<document.size(); j++){ String term = document.get(j);
        				System.out.print(term+" ");
        			}
        			System.out.println();
        			
        			similarities[i] = -0.1;
        		}
        	}
        }
        
        
	}

}
