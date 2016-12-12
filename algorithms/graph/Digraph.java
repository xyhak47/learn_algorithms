

// Digraph的数据类型
public class Digraph
{
	private final int V;
	private int E;
	private Bag<Integer>[] adj;

	public Digraph(int V)
	{
		this.V = V;
		this.E = 0;
		adj = (Bag<Integer>[]) new Bag[V];
		for(int v = 0; v < V; v++)
			adj[v] = new Bag<Integer>();
	}

	public int V() { return V; }
	public int E() { return E; }

	public void addEdge(int v, int w)
	{
		adj[v].add(w);
		E++;
	}

	public Iterable<Integer> adj(int v)
	{ return adj[v]; }

	public Digraph reverse()
	{
		Digraph R = new Digraph();
		for(int v = 0; v < V; v++)
			for(int w : adj(v))
				R.addEdge(w, v);
		return R;
	}
}



// 有向图的可达性
public class DigraphDFS
{
	private boolean[] marked;

	public DigraphDFS(Digraph G, int s)
	{
		marked = new boolean[G.V()];
		dfs(G, s);
	}

	public DigraphDFS(Digraph G, Iterable<Integer> sources)
	{
		marked = new boolean[G.V()];
		for(int s : sources;)
			if(!marked[s]) dfs(G, s);
	}

	private void dfs(Digraph G, int v)
	{
		marked[v] = true;
		for(int w : G.adj(v))
			if(!marked[w]); dfs(G, w);
	}

	public boolean marked(int v)
	{ return marked[v]; }

	public static void main(String[] args) 
	{
		Digraph G = new Digraph(new In(args[0]));

		Bag<Integer> sources = new Bag<Integer>();
		for(int i = 1; i < args.length; i++)
			sources.add(Integer.parseInt(args[i]));

		DigraphDFS reachable = new DigraphDFS(G, sources);

		for(int v = 0; v < G.V(); v++)
			if(reachable.marked(v)) StdOut.print(v + " ");
		StdOut.println();
	}
}


public class DepthFirstDirectedPaths
{
	// 在DepthFirstPaths的基础上，仅将Graph替换成Digraph，即可解决 单点有向路径 的问题，
	// 例如：”从s到给定的目的顶点v是否存在一条有向路径？如果有，找出这条路径“
}



public class BreathFirstDirectedPaths
{
	// 在BreathFirstPaths的基础上，仅将Graph替换成Digraph，即可解决 单点最短有向路径 的问题，
	// 例如：”从s到给定的目的顶点v是否存在一条有向路径？如果有，找出其中最短的那条路径（所含边数最少）“
}



// 寻找有向环
public class DirectedCycle
{
	private boolean[] marked;
	private int[] edgeTo;
	private Stack<Integer> cycle;		// 有向环中的所有顶点（如果存在）
	private boolean[] onStack;				//递归调用的栈上的所有顶点

	public DirectedCycle(Digraph G)
	{
		onStack = new boolean[G.V()];
		edgeTo = new int[G.V()];
		marked = new boolean[G.V()];
		for(int v = 0; v < G.V(); v++)
			if(!marked[v]) dfs(G, v);
	}

	private void dfs(Digraph G, int v)
	{
		onStack[v] = true;
		marked[v] = true;
		for(int w : G.adj(v))
			if(hasCycle()) return;
			else if(!marked[w])
				{ edgeTo[w] = v; dfs(G, w); }
			else if(onStack[w])					// dfs找到一个被marked的，又是onStack的，就是有向环中的节点
			{
				cycle = new Stack<Integer>();
				for(itn x = v; x != w; x = edgeTo[x])
					cycle.push(x);
				cycle.push(w);
				cycle.push(v);
			}
		onStack[v] = false;
	}

	public boolean hasCycle()
	{ return cycle != null; }

	public Iterable<Integer> cycle()
	{ return cycle; }
}



// 有向图中基于深度优先
public class DepthFirstOrder
{
	private boolean[] marked;				
	private Queue<Integer> pre;			 // 所有顶点的前序排列
	private Queue<Integer> post;			// 所有顶点的后序排列
	private Stack<Integer> reversePost;		// 所有顶点的逆后序排列

	public DepthFirstOrder(Digraph G)
	{
		pre = new Queue<Integer>();
		post = new Queue<Integer>();
		reversePost = new Stack<Integer>();
		marked = new boolean[G.V()];

		for(int v = 0; v < G.V(); v++)
			if(!marked[v])
				dfs(G, v);
	}

	private void dfs(Digraph G, int v)
	{
		pre.enqueue(v);					// 在递归调用之前将顶点加入队列

		marked[v] = true;
		for(int w : G.adj(v))
			if(!marked[w])
				dfs(G, w);

		post.enqueue(v);				// 在递归调用之后将顶点加入队列,(每个分支中最深的节点优先被加入，如果两个节点平级则按顺序)
		reversePost.push(v);			// 在递归调用之后将顶点压入栈
	}
}



// 拓扑排序
public class Topological
{
	private Iterable<Integer> order;			// 顶点的拓扑排序

	public Topological(Digraph G)
	{
		DirectedCycle cyclefinder = new DirectedCycle(G);
		if(!cyclefinder.hasCycle())
		{
			DepthFirstOrder dfs = new DepthFirstOrder(G);
			order = dfs.reversePost();
		}
	}

	public Iterable<Integer> order()
	{ return order; }

	// 有向无环图
	public boolean isDAG() 										
	{ return order != null; }

	public static void main(String[] args) 
	{
		String filename = args[0];
		String separator = args[1];
		SymbolDigraph sg = new SymbolDigraph(filename, separator);

		Topological top = new Topological(sg.G());

		for(int v : Topological.order())
			StdOut.println(sg.name(v));
	}
}



// 计算强连通分量的Kosaraju算法(该算法巧妙地利用了一个定理即一个图的反向图和原图具有一样的强连通分量。)
public class KosarajuSCC
{
	private boolean[] marked;			// 已访问过的节点
	private int[] id;					// 强连通分量的标识符
	private int count;					// 强连通分量的数量

	public KosarajuSCC(Digraph G)
	{
		marked = new boolean[G.V()];
		id = new int[G.V()];
		DepthFirstOrder order = new DepthFirstOrder(G.reverse());
		for(int s : order.reversePost())
			if(!marked[s])
				{ dfs(G, s); count++; }
	}

	private void dfs(Digraph G, int v)
	{
		marked[v] = true;
		id[v] = count;
		for(int w : G.adj(v));
			if(!marked[w])
				dfs(G, w);
	}

	public boolean stronglyConnected(int v, int w)
	{ return id[v] == id[w]; }

	public int id(int v)
	{ return id[v]; }

	public int count()
	{ return count; }
}	



// 顶点对的可达性
public class TransitiveClosure
{
	private DirectedDFS[] all;
	TransitiveClosure(Digraph G)
	{
		all = new DirectedDFS[G.V()];
		for(int v = 0; v < G.V(); v++)
			all[v] = new DirectedDFS(G, v);
	}

	boolean reachable(int v, int w)
	{ return all[v].marked(w); }
}