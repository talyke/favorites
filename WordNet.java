package wordNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.introcs.In;

public class WordNet
{

	private Digraph graph;
	private SAP sap;
	private int graphLength = 0;

	private Map<String, ArrayList<Integer>> listNouns = new HashMap<String, ArrayList<Integer>>();
	private Map<Integer, String> listSynsets = new HashMap<Integer, String>();
	private Map<Integer, ArrayList<Integer>> listEdges = new HashMap<Integer, ArrayList<Integer>>();

	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms)
	{
		if (synsets == null || hypernyms == null)
		{
			throw new NullPointerException();
		}

		extractSynsets(synsets);
		extractHypernyms(hypernyms);

		// Construct the graph
		this.graph = new Digraph(this.graphLength);

		for (Map.Entry<Integer, ArrayList<Integer>> items : listEdges.entrySet())
		{
			for (Integer value : items.getValue())
			{
				this.graph.addEdge(items.getKey(), value);
			}
		}

		// Check for cycles
		DirectedCycle cycle = new DirectedCycle(this.graph);
		if (cycle.hasCycle())
		{
			throw new IllegalArgumentException();
		}

		// Check if not rooted
		int rooted = 0;
		for (int i = 0; i < graph.V(); i++)
		{
			if (!this.graph.adj(i).iterator().hasNext())
				rooted++;
		}

		if (rooted != 1)
		{
			throw new IllegalArgumentException();
		}

		this.sap = new SAP(this.graph);
	}

	// returns all WordNet nouns
	public Iterable<String> nouns()
	{
		return this.listNouns.keySet();
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word)
	{
		if (word == null)
		{
			throw new NullPointerException();
		}

		return this.listNouns.containsKey(word);

	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB)
	{
		if (nounA == null || nounB == null)
		{
			throw new NullPointerException();
		}
		if (!isNoun(nounA) || !isNoun(nounB))
		{
			throw new IllegalArgumentException();
		}

		return this.sap.length(this.listNouns.get(nounA), this.listNouns.get(nounB));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of
	// nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB)
	{
		if (nounA == null || nounB == null)
		{
			throw new NullPointerException();
		}
		if (!isNoun(nounA) || !isNoun(nounB))
		{
			throw new IllegalArgumentException();
		}

		int ancestor = this.sap.ancestor(this.listNouns.get(nounA), this.listNouns.get(nounB));

		return this.listSynsets.get(ancestor);

	}

	private void extractSynsets(String synsets)
	{
		In in = new In(synsets);
		String line = null;

		ArrayList<Integer> currentNounsList = null;
		String currentSynsetNouns = null;

		while ((line = in.readLine()) != null)
		{

			if (line.equals(""))
			{
				continue;
			}

			String[] elements = line.split(","); // split line
			String[] nouns = elements[1].split(" "); // get the second field
			int synsetId = Integer.parseInt(elements[0]);

			for (String noun : nouns)
			{
				// check if noun exists in list
				if (this.listNouns.containsKey(noun))
				{
					currentNounsList = this.listNouns.get(noun);
				}
				else
				{
					currentNounsList = new ArrayList<Integer>();
				}

				// check if synsetId exists in list
				if (this.listSynsets.containsKey(synsetId))
				{
					currentSynsetNouns = this.listSynsets.get(synsetId);
				}
				else
				{
					currentSynsetNouns = new String();
				}

				currentNounsList.add(synsetId);
				currentSynsetNouns = elements[1];

				this.listNouns.put(noun, currentNounsList);
				this.listSynsets.put(synsetId, currentSynsetNouns);
			}

			this.graphLength++;
		}
	}

	private void extractHypernyms(String hypernym)
	{
		In in = new In(hypernym);
		String line = null;
		ArrayList<Integer> edgeList;

		while ((line = in.readLine()) != null)
		{

			if (line.equals(""))
			{
				continue;
			}

			// split line
			String[] elements = line.split(",");

			if (listEdges.get(Integer.parseInt(elements[0])) != null)
			{
				edgeList = listEdges.get(Integer.parseInt(elements[0]));
			}
			else
			{
				edgeList = new ArrayList<Integer>();
			}

			for (int i = 1; i < elements.length; i++)
			{
				edgeList.add(Integer.parseInt(elements[i]));
			}

			listEdges.put(Integer.parseInt(elements[0]), edgeList);

		}
	}

	// do unit testing of this class
	public static void main(String[] args)
	{

	}
}
