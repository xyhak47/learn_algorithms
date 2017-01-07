
// union-find的实现
public class UF
{
	private int[] id;		// 分量id（以触点作为索引）
	private int count;		// 分量数量

	public UF(int N)
	{	// 初始化分量id数组
		count = N;
		id = new int[N];
		for(itn i = 0; i < N; i ++)
			id[i] = i;
	}

	public int count()
	{ return count; }

	public boolean connected(int p, int q)
	{ return find(p) == find(q); }

	// --- 拓展-----
	public int find(int p) {}
	public void union(int p, int q) {}
	

	public static void main(String[] args) 
	{	// 解决有StdIn得到的动态连通性问题
		int N = StdIn.readInt();
		UF uf = new UF(N);
		while(!StdIn.isEmpty())
		{
			int p = StdIn.readInt();			// 读取整数对
			int q = StdIn.readInt();		
			if(uf.connected(p, q))	continue;	// 如果已经连通则忽略
			uf.union(p, q);						// 归并分量
			StdOut.println(p + " " + q);		// 打印链接
		}
		StdOut.println(uf.count() + "components");
	}
}



// --- 拓展----- quick-find算法
public int find(int p)
{ return id[p]; }
public void union(int p, int q)
{	// 将p和q归并到相同的分量中
	int pID = find(p);
	int qID = find(q);

	// 如果p和q已经在相同的分量之中则不需要采取任何行动
	if(pID == qID) return;

	// 将p的分量重命名为q的名称
	for(int i = 0; i < id.length; i++)
		if(id[i] == pID) id[i] = qID;
	count--;
}



// --- 拓展----- quick-union算法
private int find(int p)
{	// 找出分量的名称
	while(p != id[p])	p = id[p];	// 只有根结点 p = id[p];
	return p;
}
public void union(int p, int q)
{	// 将p和q的根节点统一
	int pRoot = find(p);
	int qRoot = find(q);
	if(pRoot == qRoot)	return;

	id[pRoot] = qRoot;

	count--;
}



// union-find算法的实现（加权quick-union算法）
public class WeightedQuickUnionUF
{
	private int[] id;		// 父链接的数组（由触点索引）
	private int[] sz;		// (由触点索引的)各个根节点所对应的分量的大小
	private int count;		// 连通分量的数量

	public WeightedQuickUnionUF(int N)
	{
		count = N;
		id = new int[N];
		for(int i = 0; i < N; i++) id[i] = i;
		sz = new int[N];
		for(int i = 0; i < N; i++)	sz[i] = 1;
	}
	
	public int count()
	{ return count; }

	public boolean connected(int p, int q)
	{ return find(p) == find(q); }

	public int find(int p)
	{	// 跟随链接找到根结点
		while(p != id[p])	p = id[p];	// 
		return p;
	}

	public void union(int p, int q)
	{
		int i = find(p);
		int j = find(q);
		if(i == j) return;

		// 将小树的根节点连接到大树的根节点
		if 		(sz[i] < sz[j])	{ id[i] = j; sz[j] += sz[i]; }
		else 					{ id[j] = i; sz[i] += sz[j]; }
		count--;
	}
}

