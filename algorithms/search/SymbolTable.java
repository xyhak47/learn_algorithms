
// 默认实现
void delete(Key key) 		{ put(key, null); }
boolean contains(Key key) 	{ return get(key) != null; }
boolean isEmpty() 			{ return size() == 0; }


// 有序符号表中冗余有序性方法的默认实现
void deleteMin() { delete(min()); }
void deleteMax() { delete(max()); }

int size(Key lo, Key hi)
{
	if(hi.compareTo(lo) < 0)  return 0;
	else if (contains(hi)) 	   return rank(hi) - rank(lo) + 1;
	else					return rank(hi) - rank(lo);
}

Iterable<Key> keys() { return keys(min(), max()); }





// 符号表的用例
public class FrequencyCounter
{
	public static void main(String[] args) 
	{
		int minlen = Integer.parseInt(args[0]);  // 最小键长
		ST<String, Integer> st = new ST<String, Integer>();
		while(!StdIn.isEmpty())
		{ // 构造符号表并统计频率
			String word = StdIn.readString();
			if(word.length() < minlen) continue; // 忽略较短的单词
			if(!st.contains(word)) st.put(word, 1);
			else					st.put(word, st.get(word) + 1);
		}

		// 找出出现频率最高的单词
		String max = " ";
		st.put(max, 0);
		for(String word : st.keys())
			if(st.get(word) > st.get(max))
				max = word;

		StdOut.println(max + " " + st.get(max));
	}
}



// 顺序查找（基于无序链表）
public class SequentialSearchST<Key, Value>
{
	private Node first;		// 链表首结点
	private class Node
	{ // 链表结点的定义
		Key key;
		Value val;
		Node next;

		public Node(Key key, Value val, Node next)
		{
			this.key = key;
			this.val = val;
			this.next = next;
		}
	}

	public Value get(Key key)
	{ // 查找给定的键， 返回相关联的值
		for(Node x = first; x != null; x = x.next)
			if(key.equals(x.key))
				return x.val;		// 命中
		return null;				// 为命中
	}

	public void put(Key key, Value val)
	{ // 查找给定的键，找到则更新其值，否则在表中新建结点
		for(Node x = first; x != null; x = x.next)
			if(key.equals(x.key))   
			{
				x.val = val; 		// 命中，更新
				return;			
			}
		first = new Node(key, val, first);		// 为命中，新建结点
	}
}



// 二分查找（基于有序数组）
public class BinarySearchST<Key extends Comparable<Key>, Value>
{
	private Key[] keys;
	private Value[] vals;
	private int N;

	public BinarySearchST(int capacity)
	{	
		keys = (Key[]) new Comparable[capacity];
		vals = (Value[]) new Object[capacity];
	}

	public int size()
	{ return N; }

	public Value get(Key key)
	{
		if(isEmpty()) return null;
		int i = rank(key);

		if(i < N && keys[i].compareTo(key) == 0)  return vals[i];
		else 									  return null;
	}

	// 小于key的键的数量
	public int rank(Key key)
	{
		int lo = 0, hi = N-1;
		while(lo <= hi)
		{
			int mid = lo + (hi - lo) / 2;
			int cmp = key.compareTo(keys[mid]);
			if 			(cmp < 0) hi = mid - 1;
			else if 	(cmp > 0) lo = mid + 1;
			else return mid;
		}
		return lo;
	}

	public void put(Key key, Value val)
	{	// 查找键，找到则更新值，否则创建新的元素
		int i = rank(key);

		if(i < N && keys[i].compareTo(key) == 0)
		{ vals[i] = val; return; }

		for(int j = N; j > i; j--)
		{
			keys[j] = keys[j-1];
			vals[j] = vals[j-1];
		}

		keys[i] = key;
		vals[i] = val;
		N++;
	}

	public Key min() 				{ return keys[0]; }
	public Key max() 				{ return keys[N-1]; }
	public Key select(int k) 		{ return keys[k]; }

	// 大于等于key的最小键
	public Key ceiling(Key key) 	{ int i = rank(key); return keys[i]; }
	// 小于等于键的最大键
	public Key floor(Key key) 		{}
	public Key delete(Key key) 		{}

	public Iterable<Key> keys(Key lo, Key hi)
	{
		Queue<Key> q = new Queue<Key>();
		for(int i = rank(lo); i < rank(hi); i++)
			q.enqueue(keys[i]);
		if(contains(hi))
			q.enqueue(keys[rank(hi)]);
		return q;
	}
}