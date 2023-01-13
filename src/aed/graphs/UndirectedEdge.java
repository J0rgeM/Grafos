package aed.graphs;

public class UndirectedEdge implements Comparable<UndirectedEdge> {


	private static final int R = 17;

	private int hashCode;
	private int v1;
	private int v2;
	private float weight;

	public UndirectedEdge(int v1, int v2, float weight) {
		this.v1 = v1;
		this.v2 = v2;
		this.weight = weight;
		this.hashCode = 0;
	}

	public float weight() {
		return this.weight;
	}

	public int v1() {
		return this.v1;
	}

	public int v2() {
		return this.v2;
	}

	public int other(int v) {
		if (v == this.v1) return this.v2;
		else return this.v1;
	}

	public int compareTo(UndirectedEdge other) {
		if (this.weight < other.weight) return -1;
		else if (this.weight > other.weight) return 1;
		else return this.v1 - other.v1;
	}

	@Override
	public String toString() {
		return this.v1 + "-" + this.v2 + " " + this.weight;
	}

	@Override
	public int hashCode() {
		//HashCode determined using Horner's method.
		//See the slides of Aula 18 - Tabelas de dispersão for more details about this method.

		//Calculate the hashcode only the first time this method is called
		//this is a immutable class (the values of the properties of the object do not change)
		//and so we can use this technique to calculate the hashCode just once
		if (this.hashCode == 0) {
			int hash1, hash2;
			hash1 = hash2 = R * 11;
			hash1 += this.v1;
			hash1 = R * hash1 + this.v2;

			hash2 += this.v2;
			hash2 = R * hash2 + this.v1;
			//the difference between h1 and h2 is the order in which the hashCode was calculated

			//we need to do this to ensure that hash((A,B)) = hash((B,A))
			this.hashCode = Math.max(hash1, hash2);
		}

		return this.hashCode;
	}

	@Override
	public boolean equals(Object o) {
		// If the object is compared with itself then return true
		if (o == this) {
			return true;
		}

        /* Check if o is an instance of UndirectedEdge or not
          "null instanceof [type]" also returns false */
		if (!(o instanceof UndirectedEdge)) {
			return false;
		}

		// Typecast o to UndirectedEdge so that we can compare data members
		UndirectedEdge e = (UndirectedEdge) o;

		//Two undirected edges are considered to be equal if they share both vertices
		// the order is not relevant (A,B) is equal to (A,B) and equal to (B,A)
		return (this.v1 == e.v1 && this.v2 == e.v2) || (this.v1 == e.v2 && this.v2 == e.v1);

	}
}