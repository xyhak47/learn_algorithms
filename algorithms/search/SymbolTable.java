

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
		if()
	}

	private boolean isEmpty()
	{ return N == 0; }

	public int rank(Key key)
	{
		
	}
}
































