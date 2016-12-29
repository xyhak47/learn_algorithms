

// 快速排序
public class Quick
{
	public static void sort(Comparable[] a)
	{
		StdRandom.shuffle(a);	// 消除对输出入的依赖
		sort(a, 0, a.length - 1);
	}

	private static void sort(Comparable[]a, int lo, int hi)
	{
		if(hi <= 0)	return;
		int j = partition(a, lo, hi);	// 切分
		sort(a, lo, j-1);				// 将左半部分a[lo..j-1]排序
		sort(a, j+1, hi);				// 将右半部分a[j..hi]排序
	}

	private static int partition(Comparable[] a, int lo, int hi)
	{	// 将数组切分为a[lo..i-1], a[i], a[i+1, hi]
		int i = lo, j = hi+1;	// 左右扫描指针
		Comparable v = a[lo];	// 切分元素
		while(true)
		{	// 扫描左右，检查扫描是否结束并交换元素
			while(less(a[++i], v))	if(i == hi) break;
			while(less(v, a[--j]))	if(j == lo) break;
			if(i >= j)	break;
			exch(a, i, j);
		}
		exch(a, lo, j);		// 将v = a[j]放入正确的位置
		return j;			// a[lo..j-1] <= a[j] <= a[j+1..hi]达成
	}
}



// 三向切分的快速排序
public class Quick3way
{
	private static void sort(Comparable[] a, int lo, int hi)
	{	
		if(hi <= lo)	return;
		int lt = lo, i = lo+1, gt = hi;
		Comparable v = a[lo];
		while(i <= gt)
		{
			if cmp = a[i].compareTo(v);
			if 		(cmp < 0) exch(a, lt++; i++)
			else if (cmp > 0) exch(a, i, gt--);
			else		i++;  
		}	// 现在a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]成立
		sort(a, lo, lt - 1);
		sort(a, gt + 1, hi);
	}
}