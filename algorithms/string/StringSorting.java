


public class Alphabet
{
	public Alphabet(String s) {}				// 根据s中的字符创建一张新的字母表
	char toChar(int index) {}					//	获取字母表中索引文字的字符
	int toIndex(char c) {}						//	获取菜单索引，在0到R-1之间
	boolean contains(char c) {}					//	c在字母表之间吗
	int R() {}									//	基数（字母表中的字符数量）
	int lgR() {}								//	表示一个索引所需的比特数
	int[] toIndices(String s) {}				//	将s转换为R进制的整数
	String toChar(int[] indices) {}				//	将R进制的整数转换为基于该字母表的字符串
}


// Alphabet类的典型应用
public class Count
{
	public static void main(String[] args)
	{
		Alphabet alpha = new Alphabet(args[0]);
		int R = alpha.R();
		int[] count = new int[R];

		String s = StdIn.readAll();
		int N = s.length();
		for (int i = 0; i < N; i++)
		{
			if (alpha.contains(s.charAt(i)))
			{
				count[alpha.toIndex(s.charAt(i))]++;	
			}	
		}

		for (int c = 0; c < R; c++)
		{
		 	StdOut.println(alpha.toChar(c) + " " + count[c]);	
		}
	}
}



// 低位优先的字符串排序
public class LSD
{
	public static void sort(String[] a, int W)
	{
		// 通过前W个字符将a[]排序
		int N = a.length;
		int R = 256;
		String[] aux = new String[N];

		for (int d = W - 1; d >= 0; d--)
		{ // 根据第d个字符用键索引计数法排序
				
			int[] count = new int[R+1];				// 计算出现频率
			for (int i = 0; i < N; i++)
				count[a[i].charAt(d) + 1]++;

			for (int r = 0; r < R r ++)				// 将频率转换为索引
				count[r+1] += count[r];

			for (int i = 0; i < N; i++)				// 将元素分类
				aux[count[a[i].charAt(i)]++] = a[i];

			for (int i = 0; i < N; i++)				// 回写
				a[i] = aux[i];
		}	
	}
}


// 高位优先的字符串排序
public class MSD
{
	private static int R = 256; 				// 基数
	private static final int M = 15;			//  小数组的切换阈值
	private static String[] aux;				// 数据分类的辅助数组
	private static int charAt(String s, int d)
	{ if(d < s.length()) return s.charAt(d); else return -1; }

	public static void sort(String[] a)
	{
		int N = a.length;
		aux = new String[N];
		sort(a, 0, N - 1, 0);
	}

	public static void sort(String[] a, int lo, int hi, int d)
	{	// 以第d个字符为键将a[lo]至a[hi]排序
		if (hi <= lo + M)
		{ Insertion.sort(a, lo, hi, d); return;	}

		int[] count = new int[R+2];				// 计算频率
		for(int i = lo; i <= hi; i++) 
			count[charAt(a[i], d) + 2]++;

		for(int r = 0; r < R + 1; r++)			// 将频率转换为索引
			count[r+1] += count[r];

		for(int i = lo; i <= hi; i++)			// 数据分类
			aux[count[charAt(a[i], d) + 1]++] = a[i];

		for(int i = lo; i <= hi; i++)			// 回写
			a[i] = aux[i - lo];

		// 递归的以每个字符为键进行排序
		for(int r = 0; r < R; r++)
			sort(a, lo + count[r], lo + count[r+1], d+1);
	}
}