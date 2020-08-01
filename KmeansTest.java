import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

//import com.sun.tools.javac.util.ArrayUtils;

public class KmeansTest {

    public static void main(String [] args){
	
	try{
	    BufferedImage originalImage = ImageIO.read(new File("C:\\Users\\poorn\\Desktop\\old_image\\Penguins.jpg"));
	    int k=Integer.parseInt(args[0]);
	    BufferedImage kmeansJpg = kmeans_helper(originalImage,k);
	    ImageIO.write(kmeansJpg, "jpg", new File("C:\\Users\\poorn\\Desktop\\new_image.jpg")); 
	    
	}catch(IOException e){
	    System.out.println(e.getMessage());
	}	
    }
    
    private static BufferedImage kmeans_helper(BufferedImage originalImage, int k){
	int w=originalImage.getWidth();
	int h=originalImage.getHeight();
	BufferedImage kmeansImage = new BufferedImage(w,h,originalImage.getType());
	Graphics2D g = kmeansImage.createGraphics();
	g.drawImage(originalImage, 0, 0, w,h , null);
	// Read rgb values from the image
	int[] rgb=new int[w*h];
	int count=0;
	for(int i=0;i<w;i++){
	    for(int j=0;j<h;j++){
		rgb[count++]=kmeansImage.getRGB(i,j);
	    }
	}
	// Call kmeans algorithm: update the rgb values
	kmeans(rgb,k);

	// Write the new rgb values to the image
	count=0;
	for(int i=0;i<w;i++){
	    for(int j=0;j<h;j++){
		kmeansImage.setRGB(i,j,rgb[count++]);
	    }
	}
	return kmeansImage;
    }

    // Your k-means code goes here
    // Update the array rgb by assigning each entry in the rgb array to its cluster center
    private static void kmeans(int[] rgb, int k){
    	boolean isEqual;
    	Set<Integer> kvalues=new HashSet<>();
		while(kvalues.size()!=k) {
		Random random=new Random();
		//index of the k random values in rgb array
		kvalues.add(random.nextInt(rgb.length));
		}
		System.out.println(rgb.length);
		System.out.println(kvalues);
		// Randomly chosen k cluster centers
		int[] randomCenter=new int[kvalues.size()];
		int index=0;
		for(int i:kvalues){
			randomCenter[index]=rgb[i];	
			index=index+1;
		}
		int[] distances=new int[randomCenter.length];
		ArrayList<HashSet<Integer>> clusters = new ArrayList<HashSet<Integer>>();
		//clusters is a arraylist which has different clusters and points closer to thme
		for(int x=0;x<randomCenter.length;x++) {
			//pointsInCluster(set) will have the points belonging to clusters
			HashSet<Integer> pointsInCluster=new HashSet<Integer>();
			clusters.add(pointsInCluster);
		}
			
		ArrayList<HashSet<Integer>> present = new ArrayList<HashSet<Integer>>();
		for(int x=0;x<randomCenter.length;x++) {
			HashSet<Integer> a=new HashSet<Integer>();
			present.add(a);
		}
		ArrayList<HashSet<Integer>> previous = new ArrayList<HashSet<Integer>>();
		for(int x=0;x<randomCenter.length;x++) {
			HashSet<Integer> b=new HashSet<Integer>();
			previous.add(b);
		}
		
		do {
			previous=present;
		for(int p=0;p<(rgb.length);p++) {
			for(int q=0;q<(randomCenter.length);q++) {
			distances[q]=Math.abs(rgb[p]-randomCenter[q]);			
			}
			int min=distances[0];
			int minimumPos=0;
			
			//finding the minimum distance,the pos returned will be same as the index of the centroid
			for(int pos=0;pos<distances.length;pos++) {
				if(min>distances[pos]) {
					min=distances[pos];
					minimumPos=pos;
				}	
			}
			clusters.get(minimumPos).add(p);	
			present=clusters;
		}
		if(
			previous.equals(present)){
			isEqual=true;
		}else {isEqual=false;}
		}while(isEqual==false);
		
		for(int j=0;j<clusters.size();j++) {
		HashSet<Integer> individualClusters=new HashSet<Integer>();
		individualClusters=clusters.get(j);
		int sum=0;
		for(int i:individualClusters) {
		sum=sum+rgb[i];
		}
		int avgOfPoints=0;
		if(individualClusters.size()!=0) {
			avgOfPoints=sum/(individualClusters.size());
		}
		
		for(int i:individualClusters) {
			rgb[i]=avgOfPoints;
		}
		}
		
    }
   
}

