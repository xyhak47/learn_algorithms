
// 二分查找
import java.util.Arrays;
public class BinarySearch
{
	public static int rank(int key, int[] a)
	{	// 数组必须是有序的
		int lo = 0;
		int hi = a.length - 1;
		while(lo <= hi)
		{	// 被查找的键要么不存在，要么必然存在于a[lo..hi]之中
			int mid = lo + (hi - lo) / 2;
			if 		(key < a[mid])	hi = mid - 1;
			else if (key > a[mid])	lo = mid + 1;
			else	return mid;
		}
		return -1;
	}


	public static void main(String[] args) 
	{
		int[] whitelist = In.readInts(args[0]);
		Arrays.sort(whitelist);
		while(!StdIn.isEmpty())
		{	// 读取键值，如果不存在于白名单中则将其打印
			int key = StdIn.readInt();
			if(rank(key, whitelist) < 0)
				StdOut.println(key);
		}
	}
}



// Dijkstra的双栈算术表达式求值算法
public class Evaluate
{
	public static void main(String[] args) 
	{
		Stack<String> ops = new Stack<String>();
		Stack<String> vals = new Stack<String>();
		while(!StdIn.isEmpty())
		{	// 读取字符，如果是运算符则压入栈
			String s = StdIn.readString();
			if 		(s.equals("("));
			else if (s.equals("+"))		ops.push(s);
			else if (s.equals("-"))		ops.push(s);
			else if (s.equals("*"))		ops.push(s);
			else if (s.equals("/"))		ops.push(s);
			else if (s.equals("sqrt"))	ops.push(s);
			else if (s.equals(")"))		
			{	// 如果字符为“)”，弹出运算符和操作符，计算结果并压入栈
				String op = op.pop();
				double v = vals.pop();
				if 		(op.equals("+"))	v = vals.pop + v;
				else if (op.equals("-"))	v = vals.pop - v;
				else if (op.equals("*"))	v = vals.pop * v;
				else if (op.equals("/"))	v = vals.pop / v;
				else if (op.equals("sqrt"))	v = Math.sqrt(v);
				vals.push(v);
			}	// 如果字符既非运算符也不是括号，将它作为double值压入找
			else vals.push(Double.parseDouble(s));
		}
		StdOut.println(vals.pop());
	}
}



// 下压（LIFO）栈（能够动态调整数组大小的实现）
import java.util.Iterator
public class ResizingArrayStack<Item> implements Iterable<Item>
{
	private Item[] a = (Item[]) new Object[1];	// 栈元素
	private int N = 0;							// 元素数量
	public boolean isEmpty()	{ return N == 0; }
	public int size()			{ return N; }

	private void resize(int max)
	{	// 将栈移动到一个大小为max的新数组
		Item[] temp = (Item[]) new Object[max];
		for(int i = 0; i < N; i ++)
			temp[i] = a[i];
		a = temp;
	}

	public  void push(Item item)
	{	// 将元素添加到栈顶
		if(N == a.length) resize(2*a.length);
		a[N++] = item;
	}

	public Item pop()
	{	// 从栈顶删除元素
		Item item = a[--N];
		a[N] = null;	// 避免对象游离
		if(N > 0 && N == a.length/4) resize(a.length/2);
		return item;
	}

	public Iterator<Item> iterator()
	{ return new ReverseArrayIterator(); }

	private class ReverseArrayIterator implements Iterator<Item>
	{	// 支持后进先出的迭代
		private int i = N;
		public boolean hasNext()	{ return i > 0; }
		public Item next()			{ return a[--i]; }
		public void remove()		{}
	}
}












