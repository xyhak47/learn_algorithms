
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

	Node rotateLeft(Node h)
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

	Node rotateRight(Node h)
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
}








