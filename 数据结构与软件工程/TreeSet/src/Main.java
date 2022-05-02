import com.mj.printer.BinaryTrees;
/**
 * Teacher:指导教师 @MJ
 * Student：学生@Heyuan Huang
 */
public class Main {
    public static void main(String[] args) {
    TreeSet<Integer> tst= new TreeSet<>();
        tst.add(4);tst.add(8);tst.add(9);tst.add(4);tst.add(4);tst.add(12);tst.add(5);
        System.out.println("生成TreeSet的二叉搜索树形状为");
        BinaryTrees.println(tst);
        System.out.println("TreeSet的size为"+tst.size());
        System.out.println("TreeSet是否为空："+tst.isEmpty());
        System.out.println("TreeSet是否包含元素4："+tst.contains(4));
//        System.out.println(tst.contains(null));//因为不允许输入element为null，所以会输出报错信息
        System.out.println("TreeSet是否包含元素999："+tst.contains(999));

        //遍历全部元素
        System.out.println("这是中序遍历-按元素从小到大的顺序遍历");
        tst.traversal(new TreeSet.Visitor<Integer>() {
        public boolean visit(Integer element) {
            System.out.print(element+" ");
            return false;
        }
    });
        //遍历部分元素 到n终止遍历
        System.out.println();
        System.out.println("这是中序遍历-遍历部分元素-到8终止遍历");
        int n=8;
        tst.traversal(new TreeSet.Visitor<Integer>() {
        public boolean visit(Integer element) {
            System.out.print(element+" ");
            return element == n;
        }
    });

        tst.remove(7);
        tst.remove(8);
        tst.remove(4);
        System.out.println();
        System.out.println("删除节点4，8之后的二叉搜索树TreeSet形状为");
        BinaryTrees.println(tst);

        System.out.println("清空TreeSet");
        tst.clear();
        System.out.println("TreeSet是否为空："+tst.isEmpty());
        System.out.println("TreeSet的size为"+tst.size());
}
}
