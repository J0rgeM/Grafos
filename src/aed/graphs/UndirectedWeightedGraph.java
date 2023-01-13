package aed.graphs;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class UndirectedWeightedGraph {

	private int vCount;
	private int eCount;
	private Object[] adj;
	private Hashtable<UndirectedEdge, UndirectedEdge> edges;

	@SuppressWarnings("unchecked")
	public UndirectedWeightedGraph(int vCount)
	{
		this.vCount = vCount;
		this.eCount = 0;
		this.adj = new Object[vCount];
		this.edges = new Hashtable<UndirectedEdge,UndirectedEdge>();

		//Initialise all adjacency lists
		for(int i = 0 ; i < vCount; i++)
		{
			this.adj[i] = new LinkedList<UndirectedEdge>();
		}
	}

	@SuppressWarnings("unchecked")
	public void addEdge(UndirectedEdge e)
	{
		((LinkedList<UndirectedEdge>)this.adj[e.v1()]).add(e);
		((LinkedList<UndirectedEdge>)this.adj[e.v2()]).add(e);
		this.edges.put(e,e);
		this.eCount++;
	}

	@SuppressWarnings("unchecked")
	public void removeEdge(UndirectedEdge e)
	{
		((LinkedList<UndirectedEdge>)this.adj[e.v1()]).remove(e);
		((LinkedList<UndirectedEdge>)this.adj[e.v2()]).remove(e);
		this.edges.remove(e);
		this.eCount--;
	}

	@SuppressWarnings("unchecked")
	public Iterable<UndirectedEdge> adj(int v)
	{
		return (LinkedList<UndirectedEdge>)this.adj[v];
	}

	public Iterable<UndirectedEdge> allEdges()
	{
		return this.edges.values();
	}

	public int vCount()
	{
		return this.vCount;
	}

	public int eCount()
	{
		return this.eCount;
	}

	@SuppressWarnings("unchecked")
	public int degree(int v)
	{
		return ((LinkedList<UndirectedEdge>)this.adj[v]).size();
	}

	public int maxDegree()
	{
		int max = 0;
		int degree;
		for(int i = 0; i < this.vCount ; i++)
		{
			degree = degree(i);
			if(degree > max) max = degree;
		}

		return max;
	}

	public float totalWeight()
	{
		float w = 0;
		for(UndirectedEdge e : this.edges.values())
		{
			w+=e.weight();
		}

		return w;
	}

	@SuppressWarnings("unchecked")
	public UndirectedWeightedGraph shallowCopy()
	{
		UndirectedWeightedGraph copy = new UndirectedWeightedGraph(this.vCount);

		copy.eCount = this.eCount;
		for(int i = 0; i < this.vCount; i++)
		{
			((LinkedList<UndirectedEdge>)copy.adj[i]).addAll((LinkedList<UndirectedEdge>)this.adj[i]);
		}

		copy.edges = (Hashtable<UndirectedEdge,UndirectedEdge>)this.edges.clone();

		return copy;
	}

	@SuppressWarnings("unchecked")
	public String toString()
	{
		String s = "V: " + this.vCount + " E: " + this.eCount + "\n";
		s += "Weight: " + totalWeight() + "\n";
		for(int i = 0; i < this.vCount ; i++)
		{
			s+= i + ": ";
			s+=listToString((LinkedList<UndirectedEdge>)this.adj[i],Integer.MAX_VALUE);
			s += "\n";
		}

		return s;
	}

	private String listToString(LinkedList<UndirectedEdge> list, int max)
	{
		//this is inefficient, but we need to do it for comparison reasons
		PriorityQueue<UndirectedEdge> pq = new PriorityQueue<UndirectedEdge>();
		for(UndirectedEdge e : list)
		{
			pq.add(e);
		}

		String s = "";
		int i = 0;
		while(!pq.isEmpty())
		{
			UndirectedEdge e = pq.remove();
			if(i++ >= max)
			{
				s+= "...;";
				return s;
			}
			s+= e.toString() + "; ";
		}

		return s;
	}

	@SuppressWarnings("unchecked")
	public String summary()
	{
		String s = "V: " + this.vCount + " E: " + this.eCount + "\n";
		s += "Weight: " + totalWeight() + "\n";
		for(int i = 0; i < this.vCount ; i++)
		{
			s+= i + ": ";
			s+=listToString((LinkedList<UndirectedEdge>)this.adj[i],3);
			s += "\n";
			if(i >= 3)
			{
				s += "...\n";
				break;
			}
		}

		return s;
	}

	public static UndirectedWeightedGraph parse(Scanner sc)
	{
		UndirectedWeightedGraph g = new UndirectedWeightedGraph(sc.nextInt());
		int v1,v2;
		float weight;
		int edges = sc.nextInt();

		for(int i = 0; i < edges; i++)
		{
			v1 = sc.nextInt();
			v2 = sc.nextInt();
			weight = sc.nextFloat();
			g.addEdge(new UndirectedEdge(v1,v2,weight));
		}

		return g;
	}
}