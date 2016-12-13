
// 带权重的变的数据类型
public class Edge implements Comparable<Edge>
{
	private final int v;			// 顶点之一
	private final int w;			//顶一个顶点
	private final double weight; 	//边的权重

	public Edge(int v, int w, double weight)
	{
		this.v = v;
		this.w = w;
		this.weight = weight;
	}

	public double weight() { return weight; }
	public int either() { return v; }
	public int other(int vertex)
	{
		if(vertex == v) return w;
		else if(vertex == w) return v;
		else throw new RuntimeException("Inconsistent edge"); // 不一致的
	}

	public int compareTo(Edge that)
	{
		if(this.weight() < that.weight())  return -1;
		else if(this.weight() > that.weight()) return 1;
		else return 0;
	}

	public String toString()
	{ return String.format("%d-%d %.2f", v, w, weight()); }
}


// 加权无向图的数据类型
public class EdgeWeightedGraph
{
	private final int V;	// 顶点总数
	private int E;			// 边的总数
	private Bag<Edge>[] adj;		// 临接表

	public EdgeWeightedGraph(int V)
	{
		this.V = V;
		this.E = 0;
		adj = (Bag<Edge>[]) new Bag<Edge>[V];
		for(int v = 0; v < V; v++)
			adj[v] = new Bag<Edge>();
	}

	public EdgeWeightedGraph(In in) {} 

	public int V() { return V; }
	public int E() { return E; }

	public void	addEdge(Edge e)
	{
		int v = e.either(), w = e.other(v);
		adj[v].add(e);
		adj[w].add(e);
		E++;
	}

	public Iterable<Edge> adj(int v)
	{ return adj[v]; }

	// 返回加权无向图中的所有边
	public Iterable<Edge> edges()		
	{
		Bag<Edge> b = new Bag<Edge>();
		for(int v = 0; v < V; v++)
			for(Edge e : adj[v])
				if(e.other(v) > v) b.add(e);  // 两个顶点间的边只有一条
		return b;
	}
}



// 最小生成树的Prim算法的延时实现
public class LazyPrimMST
{
	private boolean[] marked;			// 最小生成树的顶点
	private Queue<Edge> mst;			// 最小生成树的边
	private MinPQ<Edge> pq;				// 横切边（包括失效的边）

	public LazyPrimMST(EdgeWeightedGraph G)
	{
		pq = new MinPQ<Edge>();
		marked = new boolean[G.V()];
		mst = new Queue<Edge>();

		visit(G, 0);
		while(!pq.isEmpty())
		{
			Edge e = pq.delMin();
		}





	}

	private void visit(EdgeWeightedGraph G)
	{ // 标记顶点v并将所有连接v和违背标记顶点的边加入pq
		marked[v] = true;
		for(Edge e : G.adj(v))

	}
}



