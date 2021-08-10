import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
* @author Rimi Reza
* @since 8/6/2021
* */

public class HavelHakimi {

    public static boolean graphExists (List list, int noOfVertices ) {
        List<Integer> sequenceList = new ArrayList<Integer>(list);

        while (true) {
            Collections.sort(sequenceList, Collections.reverseOrder());

            if ((int) sequenceList.get(0) >= noOfVertices){
                return false;
            }

            if ((int) sequenceList.get(0) == 0) {
                return true;
            }

            int vertice = (int) sequenceList.get(0);
            sequenceList.remove(0);

            for (int i = 0; i < vertice; i++) {
                int updatedVal = (int)sequenceList.get(i) - 1;
                sequenceList.set(i, updatedVal);
            }

            if (sequenceList.contains(-1)) {
                return false;
            }

        }
    }
    
    public static int calcTotalNoOfEdges (List<Integer> degreeSequence) {
        int sum = degreeSequence.stream().mapToInt(Integer::intValue).sum();

        /*Since sum of the degree sequence is always going to be an even number*/
        int numberOfEdges = sum / 2;

        return numberOfEdges;
    }

    public static void getEdges (List<Integer> degreeSequence, int noOfVertices) {
        List<Integer> sequenceList = new ArrayList<Integer>(degreeSequence);
        int totalEdges = calcTotalNoOfEdges(degreeSequence);

        ArrayList<Integer> startVertices = new ArrayList<>() ;
        ArrayList<Integer> endVertices = new ArrayList<>() ;

        HashMap<Integer, Integer> degreeFrequencyMap = new HashMap<Integer, Integer>();

        int currentVertice = 1;
        int currentVertDegree = sequenceList.get(0);
        sequenceList.remove(0);

        /*populate the frequency map except the 1st vertice*/
        int sequenceCountIdx = 0;
        for (int vertice = noOfVertices; vertice > 1; vertice--) {
            degreeFrequencyMap.put(vertice, sequenceList.get(sequenceCountIdx));
            sequenceCountIdx++;
        }

        for (int j=0; j<currentVertDegree; j++) {
            int targetVert=2;
            while (targetVert<=degreeSequence.size()) {
                int targetVertFreq = 0;
                if ( totalEdges == 0) {
                    return;
                }

                if (currentVertice != 1) {
                    targetVertFreq = degreeFrequencyMap.get(targetVert);
                } else {
                    startVertices.add(currentVertice);
                    endVertices.add(targetVert);
                    System.out.println(currentVertice + " " + targetVert);
                    degreeFrequencyMap.put(targetVert, degreeFrequencyMap.get(targetVert)-1);
                    totalEdges--;
                }

                if (currentVertice != targetVert) {
                    if (targetVertFreq !=0) {
                        startVertices.add(currentVertice);
                        endVertices.add(targetVert);
                        System.out.println(currentVertice + " " + targetVert);
                        degreeFrequencyMap.put(targetVert, degreeFrequencyMap.get(targetVert)-1);
                        totalEdges--;
                    }
                }

                targetVert++;
            }

            currentVertice = endVertices.get(endVertices.size()-1);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int noOfVert = Integer.parseInt(br.readLine());
        String sequenceString = br.readLine();
        String[] sequenceArray = sequenceString.split(" ");

        List<Integer> degreeSequence = new ArrayList<Integer>();
        for (int i=0; i<noOfVert; i++) {
            degreeSequence.add(Integer.parseInt(sequenceArray[i]));
        }

        boolean isGraphic = graphExists(degreeSequence, noOfVert);
        if (isGraphic) {
            getEdges(degreeSequence, noOfVert);
        } else {
            System.out.println(-1);
        }
    }
}
