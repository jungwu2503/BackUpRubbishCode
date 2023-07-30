import java.util.*;
import java.io.*;

class City implements Serializable {
	
	String name;
	Vertex node;
	int x;
	int y;
	String description;
	
	City(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
//		this.description = description;
	}
	
	void setVertex(Vertex node) {
		this.node = node;
	}
	
	Vertex getVertex() {
		return node;
	}
	public String toString() {
		return name;
	}
}

class Edge implements Serializable {
	
	Vertex v1;
	Vertex v2;
	int weight;
	
	Edge(Vertex v1, Vertex v2, int weight) {
		this.v1 = v1;
		this.v2 = v2;
		this.weight = weight;
	}
	
	Vertex otherVertex(Vertex v) {
		if (v == v1) {
			return v2;
		}
		return v1;
	}
	
}

class Vertex implements Serializable {
	
	City userObject;
	ArrayList<Edge> outbound;
	boolean visited;
	int distance;
	Vertex fromNode;

	public String toString() {
		return userObject.toString();
	}
	
	Vertex(City city) {
		userObject = city;
		outbound = new ArrayList<Edge>();
		visited = false;
		distance = 0;
		fromNode = null;
	}
	
	void remove() {
		Iterator<Edge> i = outbound.iterator();
		
		while (i.hasNext()) {
			Edge edge = i.next();
			Vertex other = edge.otherVertex(this);
			other.remove(edge);
		}
	}

	void remove(Edge edge) {
		outbound.remove(edge);
	}

	void disconnect(Vertex toCity) {
		Iterator<Edge> i = outbound.iterator();
		
		while (i.hasNext()) {
			Edge edge = i.next();
			Vertex other = edge.otherVertex(this);

			if (toCity == other)
			{
				outbound.remove(edge);
				other.outbound.remove(edge);
				break; // 요거가 빠져 있었음
			}
		}
	}

	void connect(Vertex v, int weight) {
		Edge e = new Edge(this, v, weight);
		outbound.add(e);
		v.outbound.add(e);
	}	
	
	void add(Edge edge) {
		outbound.add(edge);
	}
	
	void setVisited(boolean flag) {
		visited = flag;
	}
	
	void setFromNode(Vertex v) {
		fromNode = v;
	}

	void setDistance(int x) {
		distance = 0;
	}

	void depthFirstTraverse() {
		if (visited) {
			return;
		}
		System.out.println(userObject.name);
		
		visited = true;
		
		Iterator<Edge> i = outbound.iterator();
		
		while (i.hasNext()) {
			Edge edge = i.next();
			Vertex other = edge.otherVertex(this);
			
			other.depthFirstTraverse();			
		}
		
	}
	
	void breadthFirstTraverse() {
		ArrayDeque<Vertex> queue = new ArrayDeque<Vertex>();
		System.out.println(userObject.name);
		visited = true;
		queue.add(this);
		
		while (!queue.isEmpty()) {
			Vertex node = queue.remove();

			Iterator<Edge> i = node.outbound.iterator();			
			while (i.hasNext()) {
				Edge edge = i.next();
				Vertex other = edge.otherVertex(node);
				if (other.visited == false && 
					queue.contains(other) == false)
				{
					System.out.println(other.userObject.name);
					other.visited = true;
					queue.add(other);
				}
			}			
		}
	}	
	void findShortestPast(Vertex to) {
		ArrayDeque<Vertex> queue = new ArrayDeque<Vertex>();
		visited = true;
		queue.add(this);
		
		while (!queue.isEmpty()) {
			Vertex node = queue.remove();

			if (node == to)
			{
				System.out.println(node.distance);
				System.out.print(node + " ");
				Vertex temp = node.fromNode;
				while(temp != null) {
					System.out.print(temp + " ");
					temp = temp.fromNode;
				}
				System.out.println();
				return;
			}

			Iterator<Edge> i = node.outbound.iterator();			
			while (i.hasNext()) {
				Edge edge = i.next();
				Vertex other = edge.otherVertex(node);
				if (other.visited == false)
				{
					if (other.distance == 0)
					{
						other.distance = node.distance + edge.weight;
						other.fromNode = node;
					} else if (other.distance > node.distance + edge.weight)
					{
						System.out.println("====");
						other.distance = node.distance + edge.weight;
						other.fromNode = node;
					}
					node.visited = true;
					if (queue.contains(other) == false)
					{
						queue.add(other);
					}
				}
			}	
		}
	}
}

class CityGraph implements Serializable {
	
	HashMap<String, City> table;
	
	CityGraph() {
		table = new HashMap<String, City>();
	}
	
	void add(String name, int x, int y) {
		City city = new City(name, x, y);
		Vertex vertex = new Vertex(city);
		city.setVertex(vertex);
		
		table.put(name, city);	
	}
	
	void add(String from, String to, int weight) {
		City fromCity = table.get(from);
		City toCity = table.get(to);
		
		Vertex v1 = fromCity.getVertex();
		Vertex v2 = toCity.getVertex();
		
//		v1.connect(v2, weight);
		Edge edge = new Edge(v1, v2, weight);
		v1.add(edge);
		v2.add(edge);
		
	}
	
	void remove(String name) {
		City city = table.get(name);
		table.remove(name);
		city.getVertex().remove();
	} 

	void remove(String from,String to) {
		City fromCity = table.get(from);
		City toCity = table.get(to);
		fromCity.getVertex().disconnect(toCity.getVertex());
	}
	
	void depthFirstTraverse(String name) {
		Collection<City> v = table.values();
		
		for (City x : v) {
			x.getVertex().setVisited(false);
		}
		
		City start = table.get(name);
		
		start.getVertex().depthFirstTraverse();
		
	}
	
	void breadthFirstTraverse(String name) {
		Collection<City> v = table.values();
		
		for (City x : v) {
			x.getVertex().setVisited(false);
		}
		
		City start = table.get(name);
		
		start.getVertex().breadthFirstTraverse();
		
	}
	void find(String from,String to) {
		City fromCity = table.get(from);
		City toCity = table.get(to);
		if (fromCity == null)
		{
			System.out.println("There is no city " + fromCity);
			return;
		}
		if (toCity == null)
		{
			System.out.println("There is no city " + toCity);
			return;
		}
		Collection<City> v = table.values();
		
		for (City x : v) {
			Vertex node = x.getVertex();
			node.setVisited(false);
			node.setDistance(0);
			node.setFromNode(null);
		}
		
		Vertex fromNode = fromCity.getVertex();
		Vertex toNode = toCity.getVertex();

		fromNode.findShortestPast(toNode);
	}
	void printDistance() {
		Collection<City> v = table.values();
		
		for (City x : v) {
			Vertex node = x.getVertex();
			System.out.println(node + " " + node.distance);
		}
	}
}

public class CityGraphDriver {

	public static void main(String[] args) {

/*		CityGraph map = new CityGraph();
		
		map.add("서울", 20, 30);
		map.add("대전", 30, 50);
		map.add("원주", 40, 30);
		map.add("광주", 60, 20);
		map.add("대구", 50, 70);
		map.add("강릉", 10, 30);
		map.add("포항", 40, 70);
		map.add("목포", 20, 50);
		map.add("순천", 90, 30);
		map.add("여수", 10, 80);
		map.add("부산", 60, 70);
		
		map.add("서울", "대전", 150);
		map.add("서울", "원주", 100);
		map.add("서울", "강릉", 110);
		map.add("대전", "원주", 80);
		map.add("원주", "강릉", 40);
		map.add("대전", "광주", 100);
		map.add("광주", "목포", 80);
		map.add("광주", "순천", 50);
		map.add("광주", "대구", 180);
		map.add("순천", "여수", 10);
		map.add("순천", "부산", 200);
		map.add("대전", "대구", 150);
		map.add("원주", "대구", 190);
		map.add("대구", "부산", 120);
		map.add("대구", "포항", 80);
		map.add("강릉", "포항", 230);
		map.add("대전", "대구", 150);
		map.add("목포", "순천", 30); */
		
		CityGraph map = load("map.ser");

		map.depthFirstTraverse("광주");

		System.out.println("==========");
		map.breadthFirstTraverse("서울");
				
		System.out.println("==========");
		map.find("여수", "강릉");	
		
		//save("map.ser", map);
	}

	static CityGraph load(String file) {
		CityGraph graph = null;
		try
		{
		    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
									new File(file)));
		    graph = (CityGraph)ois.readObject();
		    ois.close();
		}
		catch (Exception ex)
		{
		    ex.printStackTrace();
		}
		return graph;
	}

	static void save(String file, CityGraph map) {
		try
		{
		    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
									new File(file)));

		    oos.writeObject(map);	
		    oos.close();
		}
		catch (IOException ex)
		{
		    ex.printStackTrace();
		}
	}

}
