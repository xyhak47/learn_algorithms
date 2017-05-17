
// 排序算法类的模板
public class Example
{
	public static void sort(Comparable[] a)
	{ }

	private static boolean less(Comparable v, Comparable w)
	{ return v.compareTo(w) < 0; }

	private static void exch(Comparable[] a, int i, int j)
	{ Comparable t = a[i]; a[i] = a[j]; a[j] = t; }

	private static void show(Comparable[] a)
	{	// 在单行中打印数组
		for(int i = 0; i < a.length; i++)
			StdOut.print(a[i] + " ");
		StdOut.println();
	}

	public static boolean isSorted(Comparable[] a)
	{	// 测试数组元素是否有序
		for(int i = 1; i < a.length; i++)
			if(less(a[i], a[i-1]))  return false;
		return true;
	}

	public static void main(String[] args) 
	{	// 从标准输入读取字符串，将它们排序并输出
		String[] a = In.readStrings();
		sort(a);
		asset isSorted(a);
		show(a);	
	}
}



// 选择排序
public class Selection
{
	public static void sort(Comparable[] a)
	{	// 将啊a[]按升序排列
		int N = a.length;		// 数组长度
		for(int i = 0; i < N; i++)
		{	// 将a[i]和a[i+1..N]中最小的元素交换
			int min = i;	// 最小元素的索引
			for(int j = i+1; j < N; j++)
				if(less(a[j], a[min]))  min = j;
			exch(a, i, min);
		}
	}
}



// 插入排序
public class Insertion
{
	public static void sort(Comparable[] a)
	{	// 将a[]按升序排列
		int N = a.length;
		for(int i = 1; i < N; i++)
		{	// 将a[i]插入到a[i-1],a[i-2],a[i-3]...之中
			for(int j = i; j > 0 && less(a[j], a[j-1]); j--)
				exch(a, j, j-1);
		}

	}
}



// 希尔排序(为了加快速度简单的改进了插入排序，交换不相邻的元素以对数组的局部进行排序，并最终用插入排序将局部有序的数组排序)
public class Shell
{
	public static void sort(Comparable[] a)
	{	// 将a[]按升序排列
		int N = a.length;
		int h = 1;
		while(h < N/3) h = 3*h + 1;	// 1, 4, 13, 40, 121, 364, 1093, ...
		while(h >= 1)
		{	// 将数组边为h有序
			for(int i = h; i < N; i++)
			{	// 将a[i]插入到a[i-h],a[i-2*h],a[i-3*h]...之中
				for(int j = i; j >= h && less(a[j], a[j-h]); j -= h)
					exch(a, j, j-h);
			}
			h = h/3;
		}
	}
}