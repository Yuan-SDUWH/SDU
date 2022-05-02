/**
 * Teacher:指导教师 @MJ
 * Student：学生@Heyuan Huang
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.Comparator;
public class BST_Map<K,V> implements Map<K,V>{
    //用二叉搜索树的节点作为存储键值对的单元
    private class Node<K,V>{
        //存储键值
        K key;
        V value;
        //每个节点有父节点和左右子节点
        Node<K, V> left=null;
        Node<K, V> right=null;
        Node<K, V> parent=null;
        //构造节点
        public Node(K key, V value, Node<K,V> left,Node<K,V> right,Node<K,V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.left=left;
            this.right=right;
        }
    }
    //定义比较器
    private final Comparator<? super K> comparator;
    //空参构造Map
    public BST_Map(){
        comparator=null;
    }
    //有参构造Map
    public BST_Map(Comparator<? super K> comparator){
        this.comparator=comparator;
    }
    //添加根节点
    private Node<K,V> root;
    //定义Map长度
    private int size;
    public int size(){
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public void clear() {
        root = null;
        size = 0;
    }
    public boolean containsKey(K key){
        return getNode(key) != null;
    };
    /**
     * 通过层序遍历 LevelOrderTraversal来获取每个节点的值，一一匹配
     */
    public boolean containsValue(V value){
        if(root == null) return false;
        // 创建一个队列
        Queue<Node<K, V>> queue = new LinkedList<>();
        // 将根节点入队
        queue.offer(root);
        while(!queue.isEmpty()){
            // 删除头结点
            Node<K, V> node = queue.poll();
            // 访问头结点 判断传入是否为null
            if(value == null ? node.value == null : value.equals(node.value)){
                return true;
            }
            // 将左子节点入队
            if(node.left != null){
                queue.offer(node.left);
            }
            // 将右子节点入队
            if(node.right != null){
                queue.offer(node.right);
            }
        }
        return false;
    };

    /**
     * 这里的遍历采用中序遍历，也可以更改private traversal的遍历顺序，从而使用前序、后序遍历
     */
    public void traversal(Visitor<K, V> visitor){
        if (visitor == null) return;
        traversal(root, visitor);
    };

    private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
        //选择中序遍历模式
        if (node == null || visitor.stop) return;
        //先中序遍历左子树
        traversal(node.left, visitor);
        if (visitor.stop) return;
        //访问根节点
        visitor.stop =visitor.visit(node.key, node.value);
        //再中序遍历右子树
        traversal(node.right, visitor);
    }

    // put方法
    public V put(K key, V value){
        Node<K,V> node=root;
        //输入key为空
        if (key==null){
            throw new NullPointerException("输入的key不能为空");
        }
        // 如果根节点为空，则添加根节点
        if (root == null) {
            root = new Node<>(key, value,null,null,null);
            size++;
            return null;
        }
        // 根节点不为空，先查找key是否存在
        //key存在则覆盖原来的value
        Node<K,V> parent=null;
        int cmp=0;
        //有自定义比较器，根据比较器的compare判断
        if (comparator!=null){
            while (node!=null){
                parent=node;
                cmp = comparator.compare(key,node.key);
                if (cmp<0){
                    node = node.left;
                }
                else if (cmp>0){
                    node = node.right;
                }else { // 相等
                    V value_old=node.value;
                    node.value = value;
                    return value_old;
                }
            }
        }
        //没有传入比较器则直接compareTo判断
        else {
            Comparable<? super K> k =(Comparable<? super K>)key;
            while (node!=null){
                parent=node;
                cmp=k.compareTo(node.key);
                if (cmp<0){
                    node = node.left;
                }
                else if (cmp>0){
                    node = node.right;
                }
                else {
                    V value_old=node.value;
                    node.value = value;
                    return value_old;
                }
            }
        }
        //没有查找到已有的节点 则添加新的节点
        Node<K,V> node_new=new Node<>(key,value,null,null,parent);
        if (cmp<0) {
            parent.left=node_new;
        }else{
            parent.right=node_new;
        }
        size++;
        return value;
    }

    // get方法实现
    public V get(K key){
        Node<K,V> node=getNode(key);
        return node==null ? null : node.value;
    }
    //通过查找Key获取Node节点,key可以是任意类型-Obj
    private Node<K,V> getNode(Object key){
        //如果key是null则报错
        if (key==null){
            throw new NullPointerException("key不能为null");
        }
        //从根节点开始查找
        Node<K,V> node=root;
        //传入比较器则根据比较器来判断
        if(comparator!=null){
            K k=(K)key;
            while (node!=null){
                int cmp=comparator.compare(k,node.key);
                if (cmp<0){
                    node = node.left;
                }
                else if (cmp>0){
                    node = node.right;
                }
                else {
                    return node;
                }
            }
            return null;//没查找到key
        }
        //没有传入比较器则直接compareTo判断
        else {
            Comparable<K> k =(Comparable<K>)key;
            while (node!=null){
                int cmp=k.compareTo(node.key);
                if (cmp<0){
                    node = node.left;
                }
                else if (cmp>0){
                    node = node.right;
                }
                else {
                    return node;
                }
            }
            return null;//没查找到key
        }
    }

    public V remove(K key){
        Node<K,V> node= getNode(key);
        if (node==null){//找不到该key
            return null;
        }
        remove(node);
        return node.value;
    }
    private void remove(Node<K,V> node) {
        //找得到该key:节点的度为0，1，2
        //度为0
        if(node.left==null&&node.right==null){
            if (node == root) { // node是根节点
                root = null;
            } else if (node == node.parent.left) { // node是左子节点
                node.parent.left = null;
            } else { // node是右子节点
                node.parent.right = null;
            }
        }
        //度为1
        else if ((node.left==null&&node.right!=null)|| (node.left!=null&&node.right==null)){
            Node<K,V> child = (node.left != null) ? node.left : node.right;
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
        }
        //度为2
        else if (node.left!=null&&node.right!=null){
            //用后继节点替换该点
            //找后继节点
            Node<K,V> successorNode =successor(node);
            // 用后继节点的值覆盖node节点的值
            node.key = successorNode.key;
            node.value=successorNode.value;
            // 删除后继节点
            remove(successorNode);
        }
        size--;
    };

    /**
     * 找到node的后继节点
     */
    private Node<K,V> successor(Node<K,V> node) {
        Node<K,V> cur = node.right;
        if (cur != null) { // 右子节点不为null
            while (cur.left != null) {
                cur = cur.left;
            }
            return cur;
        }
        // 从父节点、祖父节点中寻找后继节点
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }
        return node.parent;
    }
//重写toString方法便于打印map
    public String toString(){
        //如果是空map
        if (root==null){
            return "{}";
        }
        //非空map
        String map1="{";
        //开始拼接字符串
        String map2=concat(root);
        map1=map1+map2.substring(0,map2.length()-2)+"}";
        return map1;
    }

    private String concat(Node<K,V> node){
        String nodes="";
        if (node.left!=null){
            nodes+=concat(node.left);
        }
        nodes+="key："+node.key+"——value："+node.value+",";
        if (node.right!=null){
            nodes+=concat(node.right);
        }
        return nodes;
    }
}
