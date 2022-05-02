import com.mj.printer.BinaryTreeInfo;
/**
 * Teacher:指导教师 @MJ
 * Student：学生@Heyuan Huang
 */
public class TreeSet<E extends Comparable<E>> implements Set<E>,BinaryTreeInfo  {
//使用二叉搜索树实现TreeSet的排序和去重功能，使用BinaryTreeInfo打印树结构
    private int size;
    private Node<E> root;
//基础size、isEmpty、clear功能
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public void clear() {
        root = null;
        size = 0;
    }
//添加元素
    public void add(E element) {
        nullCheck(element);
        // 根节点为空
        if (root == null) {
            root = new Node<>(element);
            size = 1;
            return;
        }
        // 根节点非空
        Node<E> parent;
        // 当前跟element进行比较的节点
        Node<E> node = root;
        // 查找父节点
        int cmp;
        do {
            // 假设node就是父节点
            parent = node;
            cmp = element.compareTo(node.element);
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等
                node.element = element;
                // 直接返回
                return;
            }
        } while (node != null);

        // 创建新节点
        Node<E> newNode = new Node<>(element, parent);
        if (cmp > 0) { // 新添加的元素比父节点的元素大
            parent.right = newNode;
        } else { // 新添加的元素比父节点的元素小
            parent.left = newNode;
        }

        // 增加元素数量
        size++;
    }

    public void remove(E element) {
        // 找到对应的节点
        Node<E> node = node(element);
        if (node == null) return;
        remove(node);
    }

    /**
     * 查看元素是否存在
     */
    public boolean contains(E element) {
        return node(element) != null;
    }
    /**
     * 中序遍历，设成private方法
     */
    private void inorderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        inorderTraversal(root, visitor);
    }
    private void inorderTraversal(Node<E> root, Visitor<E> visitor) {
        if (root == null || visitor.stop) return;
        // 中序遍历左子树
        inorderTraversal(root.left, visitor);
        if (visitor.stop) return;
        // 访问根节点
        visitor.stop = visitor.visit(root.element);
        // 中序遍历右子树
        inorderTraversal(root.right, visitor);
    }
    public void traversal(Visitor<E> visitor) {
        inorderTraversal(visitor);
    }
    /**
     * 删除节点
     */
    private void remove(Node<E> node) {
        // 元素数量减少
        size--;
        // 节点的度
        int degree = node.degree();
        if (degree == 0) { // 删除度为0的节点（叶子节点）
            if (node == root) { // node是根节点
                root = null;
            } else if (node == node.parent.left) { // node是左子节点
                node.parent.left = null;
            } else { // node是右子节点
                node.parent.right = null;
            }
        } else if (degree == 1) { // 删除度为1的节点
            Node<E> child = (node.left != null) ? node.left : node.right;
            if (node == root) { // node是根节点
                root = child;
                root.parent = null;
            } else {
                child.parent = node.parent;
                if (node == node.parent.left) { // node是左子节点
                    node.parent.left = child;
                } else { // node是右子节点
                    node.parent.right = child;
                }
            }
        } else { // 删除度为2的节点
            // 找到前驱节点
            Node<E> predecessor = predecessor(node);
            // 用前驱节点的值覆盖node节点的值
            node.element = predecessor.element;
            // 删除前驱节点
            remove(predecessor);
        }
    }

    private Node<E> predecessor(Node<E> node) {
        Node<E> cur = node.left;
        if (cur != null) { // 左子节点不为null
            while (cur.right != null) {
                cur = cur.right;
            }
            return cur;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        return node.parent;
    }



    /**
     * 查找element所在的node
     */
    private Node<E> node(E element) {
        nullCheck(element);

        // 当前跟element进行比较的节点
        Node<E> node = root;

        // 查找父节点
        while (node != null)
        {
            int cmp = element.compareTo(node.element);
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等
                // 找到了对应的节点，直接返回
                return node;
            }
        }

        // 找不到对应的节点
        return null;
    }

    /**
     * 对element进行非null检测
     */
    private void nullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("元素不能为null");
        }
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node<E>) node).element;
    }


    private static class Node<E> {
        Node<E> left;
        Node<E> right;
        Node<E> parent;
        E element;
        Node(E element) {
            this.element = element;
        }
        Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }
        Node(E element, Node<E> left, Node<E> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }

        /**
         * 获取节点的度
         */
        int degree() {
            int ret = 0;
            if (left != null) ret++;
            if (right != null) ret++;
            return ret;
        }
    }
}
