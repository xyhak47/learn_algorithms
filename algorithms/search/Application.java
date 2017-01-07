
// Dedup过滤器
public class Dedup
{
	public static void main(String[] args) 
	{
		HashSET<String> set;
		set = new HashSET<String>();
		while(!StdIn.isEmpty())
		{
			String key = StdIn.readString();
			if(!set.contains(key))
			{
				set.add(key);
				StdOut.print(key + " ");
			}
		}
	}
}


// 白名单过滤器
public class WhiteFilter
{
	public static void main(String[] args) {
		HashSET<String> set;
		set = new HashSET<String>();
		In in = new In(args[0]);
		while(!in.isEmpty())
			set.add(in.readString());
		while(!StdIn.isEmpty())
		{
			String word = StdIn.readString();
			if(set.contains(key))
				StdOut.print(word + " ");
		}
	}
}



// 字典的查找
public class LookupCSV
{
    public static void main(String[] args) 
    {
        In in = new In(args[0]);
        int keyField = Integer.parseInt(args[1]);
        int valField = Integer.parseInt(args[2]);
        String[] database = in.readAllLines();
        ST<String, String> st = new ST<String, String>();
        for (int i = 0; i < database.length; i++) {
            String[] tokens = database[i].split(",");
            String key = tokens[keyField];
            String val = tokens[valField];
            st.put(key, val);
        }

        while (!StdIn.isEmpty()) 
        {
            String query = StdIn.readString();
            if (st.contains(query)) 
            	StdOut.println(st.get(query));
        }
    }
}




// 索引（以及反向索引）的查找
public class LookupIndex 
{ 
    public static void main(String[] args)
    {
        String filename  = args[0];
        String separator = args[1];
        In in = new In(filename);

        ST<String, Queue<String>> st = new ST<String, Queue<String>>();
        ST<String, Queue<String>> ts = new ST<String, Queue<String>>();

        while (in.hasNextLine()) 
        {
            String line = in.readLine();
            String[] fields = line.split(separator);
            String key = fields[0];
            for (int i = 1; i < fields.length; i++)
            {
                String val = fields[i];
                if (!st.contains(key)) st.put(key, new Queue<String>());
                if (!ts.contains(val)) ts.put(val, new Queue<String>());
                st.get(key).enqueue(val);
                ts.get(val).enqueue(key);
            }
        }

        StdOut.println("Done indexing");

        // read queries from standard input, one per line
        while (!StdIn.isEmpty()) 
        {
            String query = StdIn.readLine();
            if (st.contains(query)) 
                for (String vals : st.get(query))
                    StdOut.println("  " + vals);
            if (ts.contains(query)) 
                for (String keys : ts.get(query))
                    StdOut.println("  " + keys);
        }
    }
}




// 文件索引
import java.io.File
public class FileIndex
{
	public static void main(String[] args) {
		ST<String, SET<File>> st = new ST<String, SET<File>>();
		for(String filename : args)
		{
			File file = new File(filename);
			In in = new In(file);
			while(!in.isEmpty())
			{
				String word = in.readString();
				if(st.contains(query))
					for(File file : st.get(query))
						StdOut.println(" " + file.getName());
			}
		}
	}
}



// 能够完成点乘的稀疏向量
public class SparseVector
{
	private HashSET<Integer, Double> st;
	public SparseVector()
	{
		st = new HashSET<Integer, Double>();
	}

	public int size()
	{ return st.size(); }

	public void put(int i, double x)
	{ st.put(i, x); }

	public double get(int i)
	{ 
		if(!st.contains(i)) return 0.0;
		else return st.get(i);
	}

	public double dot(double[] that)
	{
		double sum = 0.0;
		for(int i : st.keys())
			sum += that[i]*this.get(i);
		return sum;
	}
}








