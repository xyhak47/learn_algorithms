

// 加权有向边的数据类型
public class DirectedEdge
{
	private final int v;			// 边的起点
	private final int w;			// 边的终点
	private final double weight;	// 边的权重

	public DirectedEdge(int v, int w, double weight)
	{
		this.v = v;
		this.w = w; 
		this.weight = weight;
	}

	public double weight()
	{ return weight; }

	public int from()
	{ return v; }

	public int to()
	{ return w; }

	public String toString()
	{ return String.format("%d->%d %.2f", v, w, weight); }
}



// 加权有向图的数据类型
public class EdgeWeightedDigraph
{
	private final int V;			// 顶点总数
	private int E;					// 边的总数
	private Bag<DirectedEdge>[] adj;		// 临接表

	public EdgeWeightedDigraph(int V)
	{
		this.V = V;
		this.E = 0;
		adj = (Bag<DirectedEdge>[]) new Bag[V];
		for(int v = 0; v < V; v++)
			adj[v] = new Bag<DirectedEdge>();
	}

	public EdgeWeightedDigraph(In in) {}

	public int V() { return V; }
	public int E() { return E; }
	public void addEde(DirectedEdge e)
	{
		adj[e.from()].add(e);
		E++;
	}

	public Iterable<DirectedEdge> adj(int v)
	{ return adj[v]; }

	public Iterable<DirectedEdge> edges()
	{
		Bag<DirectedEdge> bag = new Bag<DirectedEdge>();
		for(int v = 0; v < V; v++)
			bag.add(e);
		return bag;
	}
}



// 最短路劲 的Dijkstra算法
public class DijkstraSP
{
	private DirectedEdge[] edgeTo;
	private double[] distTo;
	private IndexMinPQ<Double> pq;

	public DijkstraSP(EdgeWeightedDigraph G, int s)
	{
		edgeTo = new DirectedEdge[G.V()];
		distTo = new double[G.V()];
		pq = new IndexMinPQ<Double>(G.V());

		for(int v = 0; v < V; v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[0] = 0;

		pq.insert(s, 0.0);
		while(!pq.isEmpty())
			relax(G, pq.delMin());
	}

	private void relax(EdgeWeightedDigraph G, int v)
	{
		for(DirectedEdge e : G.adj(v))
		{
			int w = e.to();
			if(distTo[w] > distTo[v] + e.weight())
			{
				distTo[w] = distTo[v] + e.weight();
				edgeTo[w] = e;
				if(pq.contains(w)) pq.change(w, distTo[w]);
				else				pq.insert(w, distTo[w]);
			}
		}
	}

	public double distTo(int v)
	{ return distTo[v]; }

	public boolean hasPathTo(int v)
	{ return distTo[v] < Double.POSITIVE_INFINITY; }

	public Iterable<DirectedEdge> pathTo(int v)
	{
		if(!hasPathTo(v)) return null;
		Stack<DirectedEdge> path = new Stack<DirectedEdge>();
		for(DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
			path.push(e);
		return path;
	}
}