package dsaa.lab10;

import java.util.ArrayList;

public class DisjointSetForest implements DisjointSetDataStructure {

	class Element{
		int rank;
		int parent;
	}

	Element []arr;

	public DisjointSetForest(int size) {
		arr = new Element[size];
	}

	@Override
	public void makeSet(int item) {
		arr[item] = new Element();
		arr[item].parent = item;
		arr[item].rank = 0;
	}

	@Override
	public int findSet(int item) {
		if(item < 0 || item >= arr.length) return -1;
		int current = item;
		while(arr[current].parent != current)
		{
			current = arr[current].parent;
		}
		arr[item].parent = current;
		return current;
	}


	@Override
	public boolean union(int itemA, int itemB) {
		int setA = findSet(itemA), setB = findSet(itemB);
		if(setA == -1 || setB == -1 || setA == setB) return false;
		if(arr[setA].rank > arr[setB].rank)
		{
			arr[setB].parent = setA;
			return true;
		}
		arr[setA].parent = setB;
		if(arr[setA].rank == arr[setB].rank)
		{
			arr[setB].rank++;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("Disjoint sets as forest:");
		for(int i = 0; i < arr.length; i++)
		{
			res.append("\n").append(i).append(" -> ").append(arr[i].parent);
		}

		return res.toString();
	}

	@Override
	public int countSets() {
		int res = 0;
		for(int i = 0; i < arr.length; i++)
		{
			if(arr[i].parent == i) res++;
		}
		return res;
	}
}
