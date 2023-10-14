package dsaa.lab10;

import java.util.*;
import java.util.Map.Entry;

public class Graph {
	int[][] arr;
	//TODO? Collection to map Document to index of vertex
	// You can change it
	HashMap<String,Integer> name2Int;

	//TODO? Collection to map index of vertex to Document
	// You can change it
	Entry<String, Document>[] arrDoc;

	@SuppressWarnings("unchecked")
	public Graph(SortedMap<String, Document> internet) {
		int size=internet.size();
		arr=new int[size][size];
		name2Int = new HashMap<>(size);
		arrDoc = (Map.Entry<String, Document>[])new Map.Entry[size];

		Document[] documentsNames = internet.values().toArray(new Document[size]);
		for (int i = 0; i < size; i++)
		{
			name2Int.put(documentsNames[i].name, i);
			arrDoc[i] = new HashMap.SimpleEntry<>(documentsNames[i].name, documentsNames[i]);
		}

		Iterator<Document> iterator = internet.values().iterator();
		for (int i = 0; i < size; i++)
		{
			name2Int.put(iterator.next().name, i);
		}

		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				arr[i][j] = Integer.MAX_VALUE;
			}
		}

		for (Document document : internet.values())
		{
			for (Link link : document.link.values())
			{
				if (internet.containsKey(link.ref))
				{
					arr[name2Int.get(document.name)][name2Int.get(link.ref)] = link.weight;
				}
			}
		}

		for (int i = 0; i < size; i++)
		{
			arr[i][i] = 0;
		}
	}
	
	public String bfs(String start) {
		if(!name2Int.containsKey(start)) return null;

		Queue<Integer> queue = new LinkedList<>();
		HashSet<Integer> visited = new HashSet<>();
//		boolean[] isVisited = new boolean[name2Int.size()];
		StringBuilder res = new StringBuilder("");
		int current;

		queue.add(name2Int.get(start));
		visited.add(name2Int.get(start));

		while(!queue.isEmpty())
		{
			current = queue.remove();
			visited.add(current);
			if(res.length() != 0) res.append(", ");
			res.append(arrDoc[current].getKey());

			for(Link link : arrDoc[current].getValue().link.values())
			{
				if (!visited.contains(name2Int.get(link.ref))) {
					queue.offer(name2Int.get(link.ref));
					visited.add(name2Int.get(link.ref));
				}
			}
		}
		return res.toString();
	}

	public String dfs(String start) {
		if (!name2Int.containsKey(start))
			return null;

		Set<Integer> explored = new HashSet<>();
		StringBuilder sb = new StringBuilder();

		recursiveDfs(name2Int.get(start), explored, sb);
		return sb.substring(0, sb.length() - 2);
	}

	void recursiveDfs(int start, Set<Integer> explored, StringBuilder sb) {
		if (explored.contains(start))
			return;
		explored.add(start);
		sb.append(arrDoc[start].getKey()).append(", ");
		for (Link link : arrDoc[start].getValue().link.values())
		{
			if (!explored.contains(name2Int.get(link.ref))) {
				recursiveDfs(name2Int.get(link.ref), explored, sb);
			}
		}
	}

	public int connectedComponents() {
		DisjointSetForest disjointSetForest = new DisjointSetForest(arr.length);
		for (int i = 0; i < arrDoc.length; i++) {
			disjointSetForest.makeSet(name2Int.get(arrDoc[i].getKey()));
		}

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (arr[i][j] != Integer.MAX_VALUE && arr[i][j] != 0) {
					disjointSetForest.union(i, j);
				}
			}
		}

		return disjointSetForest.countSets();
	}
}
