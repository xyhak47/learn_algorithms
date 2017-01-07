

// 基于拉链法的散列表
public class SeparateChainingHashST<Key, Value>
{
	private int N;			// 键值对总数
	private int M;			// 散列表的大小
	private SequentialSearchST<Key, Value>[] st;	// 存放链表对象的数组

	public SeparateChainingHashST()
	{ this(997); }

	public SeparateChainingHashST(int N)
	{	// 创建M条链表
		this.M = M;
		st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
		for(int i = 0; i < M; i++)
			st[i] = new SequentialSearchST();
	}

	private int hash(Key key)
	{ return (key.hashCode() & 0x7fffffff) % M; }

	public Value get(Key key)
	{ return (Value) st[hash(key).get(key)]; }

	public void put(Key key, Value val)
	{ st[hash(key)].put(key, val); }

	public Iterable<Key> keys() {}
}



// 基于线性探测的符号表
public class LinearProbingHashST<Key, Value>
{
	private int N;			// 符号表中键值对的总数
	private int M = 16;		// 线性探测表的大小
	private Key[] keys;		// 键
	private Value[] vals;	// 值

	public LinearProbingHashST()
	{
		keys = (Key[]) new Object[M];
		vals = (Value[]) new Object[M];
	}	

	private int hash(Key key)
	{ return (key.hashCode() & 0x7fffffff) % M; }

	private void resize(int cap)
	{
		LinearProbingHashST<Key, Value> t = new LinearProbingHashST<Key, Value>(cap);

		for(int i = 0; i < M; i++)
			if(keys[i] != null)
				t.put(keys[i], vals[i]);
		keys = t.keys;
		vals = t.vals;
		M = t.M;
	}

	public void put(Key key, Value val)
	{
		if(N >= M/2)	resize(2*M);	// 将M加倍

		int i;
		for(i = hash(key); keys[i] != null; i = (i + 1) % M)
			if(keys[i].equals(key))	{ vals[i] = val; return; }
		keys[i] = key;
		vals[i] = val;
		N++;
	}

	public Value get(Key key)
	{
		for(int i = hash(key); keys[i] != null; i = (i + 1) % M)
			if(keys[i].equals(key))
				return vals[i];
		return null;
	}

	public void delete(Key key)
	{
		if(!contains(key)) return;
		int i = hash(key);
		while(!key.equals(keys[i]))
			i = (i + 1) % M;
		keys[i] = null;
		vals[i] = null;
		i = (i + 1) % M；
		while(keys[i] != null)	 // 将簇中被删除键的右侧的所有键重新插入散列表
		{
			Key keyToRedo = keys[i];
			Value valToRedo = vals[i];
			keys[i] = null;
			vals[i] = null;
			N--;
			put(keyToRedo, valToRedo);
			i = (i + 1) % M;
		}
		N--;
		if(N > 0 && N == M/8) resize(M/2);
	}
}

