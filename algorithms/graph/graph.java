
// 无向图Graph的数据类型
public class Graph
{
	private final int V;   		//顶点数目
	private int E;				//边的数目
	private Bag<Integer>[] adj; //邻接表

	public Graph(int V)
	{
		this.V = V;
		this.E = 0;
		adj = (Bag<Integer>[])new Bag[V];
		for(int v = 0; v < V; v++)
			adj[v] = new Bag<Integer>();
	}

	public Graph(In in)
	{
		this(in.readInt()); // 读取V并将图初始化
		int E = in.readInt(); // 读取E
		for(int i = 0; i < E; i++)
		{ // 添加一条边
			int v = in.readInt();  // 读取一个顶点
			int w = in.readInt(); // 读取另一个顶点
			addEdge(v, w);			// 添加一条连接他们的边
		}
	}

	public int V() { return V; }
	public int E() { return E; }

	public void addEdge(int v, int w)
	{
		adj[v].add(w);
		adj[w].add(v);
		E++;
	}

	public Iterable<Integer> adj(int v) { return adj[v]; }
}


// 深度优先搜素
public class DepthFirstSearch
{
	private boolean[] marked;  // v和s是连通的吗
	private int count;				// 与s连通的顶点总数

	public DepthFirstSearch(Graph G, int s)
	{
		marked = new boolean[G.V()];
		dfs(G, s);
	}

	private void dfs(Graph G, int v)
	{
		marked[v] = true;
		count++;
		for(int w : G.adj(v))
			if(!marked[w]) dfs(G, w);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
	}

	public boolean marked(int w)
	{ return marked[w]; }

	public int count()
	{ return count; }
}


// 使用深度优先搜索查找图中的路径
public class DepthFirstPaths
{
	private boolean[] marked;		//这个顶点上调用过dfs()了吗？
	private int[] edgeTo;				//从起点到一个顶点的已知路径上的最后一个顶点
	private final int s;			// 起点

	public DepthFirstPaths(Graph G, int s)
	{
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		this.s = s;
		dfs(G, s);
	}

	private void dfs(Graph G, int v)
	{
		marked[v] = true;
		for(int w : G.adj(v))
			if (!marked[w])
			{
				edgeTo[w] = v;
				dfs(G, w);	
			}
	}

	public boolean hasPathTo(int v)
	{ return marked[V]; }

	public Iterable<Integer> pathTo(int v)
	{
		if(!hasPathTo(v)) return null;
		Stack<Integer> path = new Stack<Integer>();
		for(int x = v; x != s; x = edgeTo[x])
			path.push(x);
		path.push(s);
		return path;
	}
}



// 使用广度优先搜索查找图中的路径
public class BreadthFirstPaths
{
	private boolean[] marked;  // 到达该定点的最短路径已知吗？
	private int[] edgeTo;		// 到达该定点的已知路径上最后一个顶点
	private final int s;		// 起点

	public BreadthFirstPaths(Graph G, int s)
	{
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		this.s = s;
		bfs(G, s);
	}

	private void bfs(Graph G, int s)
	{
		Queue<Integer> queue = new Queue<Integer>();
		marked[s] = true;			// 标记起点
		queue.enqueue(s);			// 将它加入队列
		while(!queue.isEmpty())
		{
			int v = queue.dequeue(); // 从队列中删去下一个顶点
			for(int w : G.adj(v))
				if(!marked[w])			// 对每个未被标记的相邻顶点
				{
					edgeTo[w] = v;		// 保存最短路径的最后一条边
					marked[w] = true;	// 标记它，因为最短路径已知
					queue.enqueue(w);	// 并将它添加到队列中
				}
		}
	}

	// 和深度优先搜索一样
	public boolean hasPathTo(int v)
	{ return marked[v]; }

	// 和深度优先搜索一样
	public Iterable<Integer> pathTo(int v)
	{
		if(!hasPathTo(v)) return null;
		Stack<Integer> path = new Stack<Integer>();
		for(int x = v; x != s; x = edgeTo[x])
			path.push(x);
		path.push(s);
		return path;
	}
}



// 使用深度优先搜索找出图中的所有连通分量
public class CC
{
	private boolean[] marked;
	private int[] id;
	private int count;

	public CC(Graph G)
	{
		marked = new boolean[G.V()];
		id = new int[G.V()];
		for(int s = 0; s < G.V(); s++)
			if(!marked[s])
			{
				dfs(G, s)
				count++;
			}
	}

	private void dfs(Graph G, int v)
	{
		marked[v] = true;
		id[v] = count;
		for(int w : G.adj(v))
			if(!marked[w])
				dfs(G, w);
	}
			
	public boolean connected(int v, int w)
	{ return id[v] == id[w]; }

	public int id(int v)
	{ return id[v]; }

	public int count()
	{ return count; }	
}



// G是无环图吗？（假设不存在自环或平行边）
public class Cycle
{
	private boolean[] marked;
	private boolean hasCycle;

	public Cycle(Graph G)
	{
		marked = new boolean[G.V()];
		for(int s = 0; s < G.V(); s++)
			if(!marked[s])
				dfs(G, s, s);
	}

	private void dfs(Graph G, int v, int u)
	{
		marked[v] = true;
		for(int w : G.adj(v))
			if(!marked[w])
				dfs(G, w, v);
			else if(w != u) hasCycle = true;    // dfs遍历到 已经标记的节点 （除父节点外的， u是w的父节点 ） 就是环
	}

	public boolean hasCycle()
	{ return hasCycle; }
}