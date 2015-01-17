package org.tecpro.colectivos;

/**
 *	Implements an immutable 3-uple. 
*/
public class Pair<A,B> {

	// Private attributes
	
	 public A x;
	
	public B y;
	
	
	// Construction
	
	public Pair(A fst, B snd) {
		x = fst;
		y= snd;
	}
	
	// Getters
	
	public A x() {
		return x;
	}
	
	public B y() {
		return y;
	}
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (x == null) {
			if (other.x!= null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y!= null)
				return false;
		} else if (!y.equals(other.y))
			return false;
	
		return true;
	}
	
}
