

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









