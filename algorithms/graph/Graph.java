
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



// 使用广度优先搜索查找图中的路径(单点最短路径)
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



// G是二分图吗？(双色问题)
public class TwoColor
{
	private	boolean[] marked;
	private boolean[] color;
	private boolean isTwoColorable = true;
	public TwoColor(Graph G)
	{
		marked = new boolean[G.V()];
		color = new boolean[G.V()];
		for(int s = 0; s < G.V(); s++)
			if(!marked[s])
				dfs(G, s);
	}

	private void dfs(Graph G, int v)
	{
		marked[v] = true;
		for(int w : G.adj(v))
			if(!marked[w])
			{
				color[w] = !color[v];
				dfs(G, w);
			}
			else if (color[w] == color[v]) isTwoColorable = false; //子父节点颜色一致
	}

	public boolean isBipartite()
	{ return isTwoColorable; }
}



// 符号图的数据类型
public class SymbolGraph
{
	private ST<String, Integer> st;			// 符号名 -> 索引
	private String[] keys;					// 索引 -> 符号表
	private Graph G;						// 图

	public SymbolGraph(String stream, String sp)
	{
		st = new ST<String, Integer>();
		In in = new In(stream);				// 第一遍
		while (in.hasNextLine())			// 构造索引
		{
			String[] a = in.readLine().split(sp);	// 读取字符串

			for(int i = 0; i < a.length; i++)		// 为每个不同的字符串关联一个索引
				if(!st.contains(a[i]))
					st.put(a[i], st.size());
		}

		keys = new String[st.size()];  			// 用来获得顶点名的反向索引是一个数组

		for(String name : st.keys())
			keys[st.get(name)] = name;

		G = new Graph(st.size());
		in = new In(stream);				// 第二遍
		while(in.hasNextLine())				// 构造图
		{
			String[] a = Integer.readLine().split(sp);  // 将每一行的第一个顶点和该行的其他顶点相连
			int v = st.get(a[0]);
			for(int i = 1; i < a.length; i ++)
				g.addEdge(v, st.get(a[i]));
		}
	}

	public boolean contains(String s) { return st.contains(s); }
	public int index(String s)		  { return st.get(s); }
	public String name(int v)		  { return keys[v]; }
	public Graph G();				  { return G; }
}



// 间隔的度数
public class DegreesOfSeparation
{
	public static void main(String[] args)
	{
		SymbolGraph sg = new SymbolGraph(args[0], args[1]);

		Graph G = sg.G();

		String source = args[2];
		if(!sg.contains(source))
		{ StdOut.println(source + "not in database."); return; }

		int s = sg.index(source);
		BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

		while(!StdIn.isEmpty())
		{
			String sink = StdIn.readLine();
			if(sg.contains(sink))
			{
				int t = sg.index(sink);
				if(bfs.hasPathTo(t))
					for(int v : bfs.pathTo(t))
						StdOut.println("___" + sg.name(v));
				else StdOut.println("Not connected");
			}
			else StdOut.println("Not in database.");
		}
	}
}



