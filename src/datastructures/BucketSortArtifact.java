package datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kunstvoorwerpen.Artifact;

public class BucketSortArtifact {

  public static void bucketSort(List<Artifact> artifacts) {
    if (artifacts.isEmpty()) return;

    int n = artifacts.size();
    
    // Zoek het maximum en minimum van de waarde om de buckets dynamisch te verdelen
    double maxValue = Double.MIN_VALUE;
    double minValue = Double.MAX_VALUE;

    for (Artifact artifact : artifacts) {
        if (artifact.getValue() > maxValue) {
            maxValue = artifact.getValue();
        }
        if (artifact.getValue() < minValue) {
            minValue = artifact.getValue();
        }
    }

    // Aantal buckets op basis van het bereik
    int bucketCount = (int) (maxValue - minValue) + 1;
    List<Artifact>[] buckets = new ArrayList[bucketCount];

    for (int i = 0; i < bucketCount; i++) {
        buckets[i] = new ArrayList<>();
    }

    // Plaats de artifacts in de juiste buckets
    for (Artifact artifact : artifacts) {
        int bucketIndex = (int) (artifact.getValue() - minValue);
        buckets[bucketIndex].add(artifact);
    }

    // Voeg de gesorteerde artifacts samen in de oorspronkelijke lijst
    int index = 0;
    for (List<Artifact> bucket : buckets) {
        // Gebruik Insertion Sort om de artifacts in de bucket te sorteren
        Collections.sort(bucket, (a1, a2) -> Double.compare(a1.getValue(), a2.getValue())); // Voor kleinere datasets

        for (Artifact artifact : bucket) {
            artifacts.set(index++, artifact);
        }
    }
}

}
