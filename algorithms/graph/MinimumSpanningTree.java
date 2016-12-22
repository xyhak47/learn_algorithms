
// 带权重的边的数据类型
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
		pq = new MinPQ<Edge>(); 		// 优先队列
		marked = new boolean[G.V()];
		mst = new Queue<Edge>();

		visit(G, 0);
		while(!pq.isEmpty())
		{
			Edge e = pq.delMin();			// 从pq中得到权重最小的边

			int v = e.either(), w = e.other(v);		
			if(marked[v] && marked[w]) continue;	// 跳过失效的边
			mst.enqueue(e);							// 将边加入到数中
			if(!marked[v]) visit(G, v);				//将顶点（v或w）添加到树中
			if(!marked[w]) visit(G, w);
		}
	}

	private void visit(EdgeWeightedGraph G, int v)
	{ // 标记顶点v并将所有连接v和未被标记顶点的边加入pq
		marked[v] = true;
		for(Edge e : G.adj(v))
			if(!marked[either.other(v)]) pq.insert(e);
	}

	public Iterable<Edge> edges()
	{ return mst; }

	public double weight() {}
}



// 最小生成树的Prim算法（即时版本）
public class PrimMST
{
	private Edge[] edgeTo;			// 距离树最近的边
	private double[] distTo;			// distTo[w]=edgeTo[w].weight()
	private boolean[] marked;		// 如果v在树中则为true
	private IndexMin<Double> pq;	// 有效的横切边

	public PrimMST(EdgeWeightedGraph G)
	{
		edgeTo = new Edge[G.V()];
		distTo = new double[G.V()];
		marked = new boolean[G.V()];
		for(int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;		// 无穷
		pq = new IndexMin<Double>(G.V());

		distTo[0] = 0.0;
		pq.insert(0, 0.0);				// 用顶点0和权重0初始化pq
		while(!pq.isEmpty())
			visit(G, pq.delMin());		// 将最近的顶点添加到树中
	}

	private void visit(EdgeWeightedGraph G, int v)
	{ // 将顶点v添加到树中，更新数据
		marked[v] = true;
		for(Edge e : G.adj(v))
		{
			int w = e.other(v);

			if(marked[w]) continue;		// v-w 失败
			if(e.weight() < distTo[w])
			{	// 连接w和树的最佳边Edge变为e
				edgeTo[w] = e;

				distTo[w] = e.weight();
				if(pq.contains(w)) pq.change(w, distTo[w]);
				else 				pq.insert(w, distTo[w]);
			}
		}
	}

	public Iterable<Edge> edges()
	{
		Bag<Edge> mst = new Bag<Edge>();
		for(int v = 1; v < edgeTo.length; v++)
			mst.addEdge(edgeTo[v]);
		return mst;
	}

	public double weight() {}
}




// 最小生成树的Kruskal算法(从一片有V棵单顶点的树构成的森林开始并不断将两颗树合并（用可以找到的最短边）
// 直到只剩下一棵树，它就是最小生成树)
public class KruskalMST
{
	private Queue<Edge> mst;

	public KruskalMST(EdgeWeightedGraph G)
	{
		mst = new Queue<Edge>();
		MinPQ<Edge> pq = new MinPQ<Edge>();

		for(Edge e : G.edges()) pq.insert(e);

		UF uf = new UF(G.V());

		while(!pq.isEmpty() && mst.size() < G.V()-1)
		{
			Edge e = pq.delMin();				// 从pq得到权重最小的边和它的顶点
			int v = e.either(), w = e.other(v);
			if(uf.connected(v, w)) continue; 	// 忽略失效的边

			uf.union(v, w);					// 合并分量
			mst.enqueue(e);					// 将边添加到最小生成树中
		}
	}

	public Iterable<Edge> edges()
	{ return mst; }

	public double weight() {}
}









