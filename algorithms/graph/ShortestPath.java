

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



// 任意顶点对之间的最短路径
public class DijkstraAllPairsSP
{
	private DijkstraSP[] all;

	DijkstraAllPairsSP(EdgeWeightedDigraph G)
	{
		all = new DijkstraSP[G.V()];
		for(int v = 0; v < G.V(); v++)
			all[v] = new DijkstraSP(G, v);
	}

	Iterable<DirectedEdge> path(int s, int t)
	{ return all[s].pathTo(t); }

	double dist(int s, int t)
	{ return all[s].distTo(t); }
}




// 无环加权有向图的最短路径算法(在拓扑排序后，构造函数会扫描整幅图并将每条边放松一次。
// 在已知加权图是无环的情况下它是找出最短路径的最好方法)
public class AcycliSP
{
	private DirectedEdge[] edgeTo;
	private double[] distTo;

	public AcycliSP(EdgeWeightedDigraph G, itn s)
	{
		edgeTo = new DirectedEdge[G.V()];
		distTo = new double[G.V()];

		for(int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[0] = 0.0;

		Topological top = new Topological(G);
		for(int v : top.order())
			relax(G, v);
	}

	private void relax(EdgeWeightedDigraph G, int v)
	{
		for(DirectedEdge e : adj(v))
		{
			int w = e.to();
			if(distTo[w] > distTo[v] + e.weight())
			{
				distTo[w] = distTo[v] + e.weight();
				edgeTo[w] = e;
			}
		}
	}

	// 最短路径树实现中的标准查询算法
	public double distTo(int v)		
	{ return distTo[v]; }

	public boolean hasPathTo(int v)
	{ return distTo[v] != Double.POSITIVE_INFINITY; } 

	public Iterable<DirectedEdge> pathTo(int v)
	{
		if(!hasPathTo(v)) return null;
		Stack<DirectedEdge> path = new Stack<DirectedEdge>();
		for(DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
			path.push(e);
		return path;
	}
}



// 优先级限制下的并行任务调度问题的关键路径方法(critical path method)
public class CPM 
{
	public static void main(String[] args) 
	{
		int N = StdIn.readInt();  StdIn.readLine();
		EdgeWeightedDigraph G;
		G = new EdgeWeightedDigraph(2*N + 2);
		int s = 2*N, t = 2*N + 1;
		for(int i = 0; i < N; i ++)
		{
			String[] a = StdIn.readLine().split("\\s+");
			double duration = Double.parseDouble(a[0]);
			G.addEde(new DirectedEdge(i, i+N, duration));
			G.addEde(new DirectedEdge(s, i, 0.0));

			G.addEde(new DirectedEdge(i+N, t, 0.0));
			for(int j = 1; j < a.length; j++)
			{
				int successor = Integer.parseInt(a[j]);
				G.addEde(new DirectedEdge(i+N, successor, 0.0));
			}
		}

		AcycliSP lp = new AcycliSP(G, s);

		StdOut.println("Start times");
		for(int i = 0; i < N; i++)
			StdOut.printf("%4d: %5.1f\n", i, lp.distTo(i));
		StdOut.printf("Finish time: %5.1f\n", lp.distTo(t));
	}
}		



// 基于队列的Bellman-Ford算法
public class BellmanFordSP
{
	private double[] distTo;			// 从起点到某个顶点的路径长度
	private DirectedEdge[] edgeTo;		// 从起点到某个顶点的最后一条边
	private boolean[] onQ;				// 该顶点是否存在于队列中
	private Queue<Integer> queue;		// 正在被放松的顶点
	private int cost;					// relax的调用次数
	private Iterable<DirectedEdge> cycle;  // edgeTo[]中是否有负权重环

	public BellmanFordSP(EdgeWeightedDigraph G, int s)
	{
		distTo = new double[G.V()];
		edgeTo = new DirectedEdge[G.V()];
		onQ = new boolean[G.V()];
		queue = new Queue<Integer>();
		for(int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[0] = 0.0;
		queue.enqueue(s);
		onQ[s] = true;
		while(!queue.isEmpty() && !hasNegativeCycle())
		{
			int v = queue.dequeue();
			onQ[v] = false;
			relax(G ,v);
		}
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
				if(!onQ[w])
				{
					queue.enqueue(w);
					onQ[w] = true;
				}
			}
			if(cost++ % G.V() == 0)
				findNegativeCycle();
		}
	}

	public double distTo(int v)
	{ return distTo[v]; }

	public boolean hasPathTo(int v)
	{ return distTo[v] != Double.POSITIVE_INFINITY; }

	public Iterable<DirectedEdge> pathTo(int v)
	{
		if(!hasPathTo(v)) return null;
		Stack<DirectedEdge> path = new Stack<DirectedEdge>();
		for(DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
			path.push(e);
		return path;
	}

	private void findNegativeCycle()
	{
		int V = edgeTo.length;
		EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
		for(int v = 0; v < V; v++)
			if(edgeTo[v] != null)
				spt.addEde(edgeTo[v]);

		EdgeWeightedCycleFinder cf = new EdgeWeightedCycleFinder(spt);

		cycle = cf.cycle();
	}

	public boolean hasNegativeCycle()
	{ return cycle != null; }

	public Iterable<DirectedEdge> negativeCycle()
	{ return cycle; }
}



// 货币兑换中的套汇
public class Arbitrage
{
	public static void main(String[] args) 
	{
		int V = StdIn.readInt();
		String[] name = new String[V];
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);
		for(int v = 0; v < V; v++)
		{
			name[v] = StdIn.readString();
			for(int w = 0; w < V; w++)
			{
				double rate = StdIn.readDouble();
				DirectedEdge e = new DirectedEdge(v, w, -Math.log(rate));
				G.addEde(e);
			}
		}

		BellmanFordSP spt = new BellmanFordSP(G ,0);
		if(spt.hasNegativeCycle())
		{
			double stake =  1000.0;
			for(DirectedEdge e : spt.negativeCycle())
			{
				StdOut.printf("%10.5f %s", stake, name[e.from()]);

				stake *= Math.exp(-e.weight());
				StdOut.printf("= %10.5f %s\n", stake, name[e.to()]);
			}
		}
		else StdOut.printf("No arbitrage opportunity");
	}
}


