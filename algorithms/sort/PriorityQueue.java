
// 一个优先队列的用例
public class TopM
{
	public static void main(String[] args) 
	{	// 打印输入流中最大的M行
		int M = Integer.parseInt(args[0]);
		MinPQ<Transaction> pq = new MinPQ<Transaction>(M+1);
		while(StdIn.hasNextLine())
		{	// 为下一行输入创建一个元素并放入优先队列中
			pq.insert(new Transaction(StdIn.readLine()));
			if(pq.size() > M)
				pq.delMin();	// 如果优先队列中存在M+1个元素则删除其中最小的元素
		}	// 最大的M个元素都在优先队列中

		Stack<Transaction> stack = new Stack<Transaction>();
		while(!pq.isEmpty())	stack.push(pq.delMin());
		for(Transaction t : stack)	StdOut.println(t);
	}
}


// 基于堆的优先队列
public class MaxPQ<Key extends Comparable<key>>
{
	private Key[] pq;	// 基于堆的完全二叉树
	private int N = 0;	// 存储于pq[1..N]中，pq[0]没有使用

	public MaxPQ(int maxN)
	{ pq = (Key[]) new Comparable[maxN+1]; }

	public boolean isEmpty()
	{ return N == 0; }

	public int size()
	{ return N; }

	public void insert(Key v)
	{
		pq[++N] = v;
		swim(N);
	}

	public Key delMax()
	{
		Key max = pq[1];		// 从根结点得到最大元素
		exch(1, N--);			// 将其和最后一个结点交换
		pq[N+1] = null;			// 防止对象游离
		sink(1);				// 恢复堆的有序性
		return max;
	}

	private boolean less(int i, int j)
	{ return pq[i].compareTo(pq[j]) < 0; }

	private void exch(int i, int j)
	{ Key t = pq[i]; pq[i] = pq[j]; pq[j] = t; }

	private void siwm(int k)
	{
		while(k > 1 && less(k/2, k))
		{
			exch(k/2, k);
			k = k/2;
		}
	}

	private void sink(int k)
	{
		while(2*k <= N)
		{
			int j = 2*k;
			if(j < N && less(j, j++)) j++;
			if(!less(k, j)) break;
			exch(k, j);
			k = j;
		}
	}
}



// 索引优先队列的实现
public class IndexMinPQ<Key extends Comparable<Key>>
{
	private int N;			// PQ中 的元素数量
	private int[] pq;		// 索引二叉堆，由1开始
	private int[] qp;		// 逆序：qp[pq[i]] = pq[qp[i]] = i, qp[i]的值是i在pa[]中的位置（即索引j，pq[j]=i）
	private Key[] keys;		// 有优先级之分的元素

	public IndexMinPQ(int maxN)
	{
		keys = (Key[]) new Comparable[maxN + 1];
		pq = new int[maxN + 1];
		qp = new int[maxN + 1];
		for(int i = 0; i < maxN; i++)
			qp[i] = -1;
	}

	public boolean isEmpty()
	{ return N == 0; }

	public boolean contains(int k)
	{ return qp[k] != -1; }

	public void insert(int k, Key key)
	{
		N++
		qp[k] = N;
		pq[N] = k;
		keys[k] = key;
		siwm(N);
	}

	public Key min()
	{ return keys[pq[1]]; }

	public int delMin()
	{
		int indexOfMiN = pq[1]; 
		exch(1, N--);
		sink(1);
		keys[pq[N+1]] = null;
		qp[pq[N+1]] = -1;
		return indexOfMiN;
	}

	private void exch(int i, int j) 
	{
        int swap = pq[i]; pq[i] = pq[j]; pq[j] = swap;
        qp[pq[i]] = i; qp[pq[j]] = j;
    }

   	private void swim(int k)  
   	{
        while (k > 1 && greater(k/2, k)) 
        {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) 
    {
        while (2*k <= N) 
        {
            int j = 2*k;
            if (j < N && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j) 
    {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    public int minIndex()
    { return pq[1]; }

    public void change(int k, Key key)
    {
    	keys[i] = key;
    	siwm(qp[k]);
    	sink(qp[k]);
    }

    public void delete(int k)
    {
    	int index = qp[k];
    	exch(index, N--);
    	swim(index);
    	sink(index);
    	keys[k] = null;
    	qp[k] = -1;
    }
}



// 使用优先队列的多向归并
public class Multiway
{
	public static void merge(In[] streams)
	{
		int N = streams.length;
		IndexMinPQ<String> pq = new IndexMinPQ<String>(N);

		for(int i = 0; i < N; i++)
			if(!streams[i].isEmpty())
				pq.insert(i, streams[i].readString());

		while(!pq.isEmpty())
		{
			StdOut.println(pq.min());
			int i = pq.delMin();

			if(!streams[i].isEmpty())
				pq.insert(i, streams[i].readString());
		}
	}

	public static void main(String[] args) 
	{
		int N = args.length;
		In[] streams = new In[N];
		for(int i = 0; i < N; i++)
			streams[i] = new In(args[i]);
		merge(streams);
	}
}

