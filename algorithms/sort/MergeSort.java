

// 原地归并的抽象方法
public static void merge(Comparable[] a, int lo, int mid, int hi)
{	// 将a[lo..mid]和a[mid+1..hi]归并
	int i = lo, j = mid+1;

	for(int k = lo; k <= hi; k++)	// 将a[lo..hi]复制到aux[lo..hi]
		aux[k] = a[k];

	for(int k = lo; k <= hi; k++)	// 归并回到a[lo..hi]
		if 		(i > mid)				a[k] = aux[j++];
		else if (j > hi)				a[k] = aux[i++];
		else if (less(aux[j], aux[i]))	a[k] = aux[j++];
		else							a[k] = aux[i++];
}



// 自顶向下的归并排序
public class Merge
{
	private static Comparable[] aux;	// 归并所需的辅助数组

	public static void sort(Comparable[] a)
	{
		aux = new Comparable[a.length];	// 一次性分配空间
		sort(a, 0, a.length - 1);
	}

	private static void sort(Comparable[] a, int lo, int hi)
	{	// 将数组a[lo..hi]排序
		if(hi <= lo)	return;
		int mid = lo + (hi - lo)/2;
		sort(a, lo, mid);		// 将左半边排序
		sort(a, mid+1, hi);		// 将右半边排序
		merge(a, lo, mid, hi); 	// 归并结构
	}
}



// 自底向上的归并排序(多次遍历整个数组，根据子数组大小进行两两归并。
// 最后一个子数组的大小只有在数组大小sz的偶数倍的时候才会等于sz（否则它会比sz小）)
public class MergeBU
{
	private static Comparable[] aux;	// 归并所需的辅助数组

	public static void sort(Comparable[] a)
	{	// 进行lgN次两两归并
		int N = a.length;
		aux = new Comparable[N];
		for(int sz = 1; sz < N; sz = sz+sz)	//sz子数组的大小
			for(int lo = 0; lo < N-sz; lo += sz+sz)	// lo:子数组索引
				merge(a, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
	}
}