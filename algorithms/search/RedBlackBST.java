
// 红黑树的插入算法
public class RedBlackBST<Key extends Comparable<Key>, Value>
{
	private Node root;

	private static final boolean RED = true;
	private static final boolean BLACK = false;

	private class Node
	{
		Key key;			// 键
		Value val;			// 相关联的值
		Node left, right;	// 左右子树
		int N;				// 这棵子树中的结点总数
		boolean color;		// 由其父结点指向她的链接的颜色	

		Node(Key key, Value val, int N, boolean color)
		{
			this.key = key;
			this.val = val;
			this.N = N;
			this.color = color;
		}
	}

	private boolean isRed(Node x)
	{
		if(x == null)  return false;
		return x.color == RED;
	}

	private Node rotateLeft(Node h)
	{
		Node x = h.right;
		h.right = x.left;
		x.left = h;

		x.color = h.color;
		h.color = RED;

		x.N = h.N;
		h.N = 1 + size(h.left) + size(h.right);

		return x;
	}

	private Node rotateRight(Node h)
	{
		Node x = h.left;
		h.right = x.left;
		x.right = h;

		x.color = h.color;
		h.color = RED;

		x.N = h.N;
		h.N = 1 + size(h.left) + size(h.right);

		return x;
	}

	private void flipColors(Node h)
	{
		h.color = RED;
		h.left.color = BLACK;
		h.right.color = BLACK;
	}

	public int size()
	{ return size(root); }

	private int size(Node x)
	{
		if(x == null)	return 0;
		else			return x.N;
	}

	public void put(Key key, Value val)
	{	// 查找key，找到则更新其值，否则为它新建一个结点
		root = put(root, key, val);
		root.color = BLACK;
	}

	private void put(Node h, Key key, Value val)
	{
		if(h == null)	// 标准的插入操作，和父结点用红链接相连
			return new Node(key, val, 1, RED);

		if cmp = key.compareTo(h.key);
		if 		(cmp < 0)	h.left = put(h.left, key, val);
		else if (cmp > 0)	h.right = put(h.right, key, val);
		else	h.val = val;

		if(isRed(h.right) && !isRed(h.left))	h = rotateLeft(h);
		if(isRed(h.left) && isRed(h.left.left))	h = rotateRight(h);
		if(isRed(h.left) && isRed(h.right))		h = flipColors(h);

		h.N = size(h.left) + size(h.right) + 1;
		return N;
	}

	private Node moveRedLeft(Node h)
	{	// 假设结点h为红色，h.left和h.left.left都为黑色
		// 将h.left或则h.left的子结点之一变红
		flipColors(h);
		if(isRed(h.right.left))
		{
			h.right = rotateRight(h.right);
			h.rotateLeft(h);
		}
		return h;
	}

	public void deleteMin()
	{
		if(!isRed(root.left) && !isRed(root.root))
			root.color = RED;
		root.deleteMin(root);
		if(!isEmpty())	root.color = BLACK;
	}

	private Node deleteMin(Node h)
	{
		if(h.left == null)
			return null;
		if(!isRed(h.left) && !isRed(h.left.left))
			h = moveRedLeft(h);
		h.left = deleteMin(h.left);
		return balance(h);
	}
}








