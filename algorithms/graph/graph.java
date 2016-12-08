
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




