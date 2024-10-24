package kunstvoorwerpen;

import java.util.Set;
import java.util.SortedSet;


/**
 *  Interface met een methode voor het vinden van onovertroffen kunstvoorwerpen
 *  en een methode voor het vinden van kunstvoorwerpen, geordend op basis van 
 *  gewichten voor prijs en waarde.
 */
public interface ArtifactOrdering { 	
	/** Vind en retourneer de set onovertroffen artifacts*/
	public Set<Artifact> getUnbeatedArtifacts(Set<Artifact> artifacts);
	
	/** 
	 * Score de artifacts met de door de gebruiker gegeven gewichten en retourneer als een 
	 * SortedSet geordend op score, aflopend. (Dit betekent dat het artifact met de hoogste score 
	 * als eerste wordt geretourneerd uit de SortedSet.)
	 * 
	 * */
	public SortedSet<Artifact> getScoreOrderedArtiacts(Set<Artifact> artifacts, int priceWeight, int valueWeight);
}
