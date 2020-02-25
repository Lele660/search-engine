package utility;

public class MergeSort {
	
	public void mergeSort(double[] array, int left, int right){
        
        if(left<right){
            
            int mid = (right-left)/2 + left;
            mergeSort(array, left, mid);
            mergeSort(array, mid+1, right);
            
            merge(array, left, mid, right);
        }
        
    }
    
    public void merge(double[] array, int left, int mid, int right){
        
        int length1 = mid-left+1;
        int length2 = right - mid;
        double[] array1 = new double[length1];
        double[] array2 = new double[length2];
        
        for(int i=0;i<length1;i++){
            array1[i] = array[left+i];
        }
        for(int i=0; i<length2; i++){
            array2[i] = array[mid+1+i];
        }
        
        int p1=0, p2=0, p=left;
        
        while(p1<length1 && p2<length2){
            if(array1[p1] > array2[p2]){
                array[p] = array1[p1];
                p1++;
            }else{
                array[p] = array2[p2];
                p2++;
            }
            p++;
        }
        
        while(p1<length1){
            array[p] = array1[p1];
            p1++;
            p++;
        }
        
        while(p2<length2){
            array[p] = array2[p2];
            p2++;
            p++;
        }
    }

}
