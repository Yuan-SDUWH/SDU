/**
 * Teacher:指导教师 @MJ
 * Student：学生@Heyuan Huang
 */
public class Main {
    //用来测试Map所有接口的代码
    public static void main(String[] args) {
        BST_Map<Integer,String > map = new BST_Map<>();
        map.put(9,"aaa");
        map.put(4,"vvv");
        map.put(2,"aaa");
        map.put(10,"ddd");
        map.put(77,"dd5");
        map.put(42,"d72");
        map.put(5,"gfgs");
        map.put(77,"gdfvgr");
        //基础接口size,isEmpty
        int size=map.size();
        System.out.println("map的size是"+size);
        Boolean emp=map.isEmpty();
        System.out.println("map是否为空"+emp);
        //get方法
        String s=map.get(5);
        System.out.println("key为5的value值是"+s);
        String s1=map.get(1241431);
        System.out.println("key为1241431的value值是"+s1);
        //打印map
        System.out.println("map="+map);

        //是否包含key/value
        Boolean s4=map.containsKey(5);
        System.out.println("是否包含key：5="+s4);
        Boolean s5=map.containsKey(9249);
        System.out.println("是否包含key：9249="+s5);
        Boolean s6=map.containsValue("13434");
        System.out.println("是否包含value：13434="+s6);
        Boolean s7=map.containsValue("vvv");
        System.out.println("是否包含value：vvv="+s7);
        //中序遍历visit打印
        System.out.println("按照key中序遍历");
        map.traversal(new Map.Visitor<Integer, String>() {
            @Override
            public boolean visit(Integer key, String value) {
                System.out.println("key:" + key + "，value:" + value);
                return false;
            }
        });
        //中序遍历visit到key=10为止
        System.out.println("按照key中序遍历到key=10为止");
        map.traversal(new Map.Visitor<Integer, String>() {
            @Override
            public boolean visit(Integer key, String value) {
                System.out.println("key:" + key + "，value:" + value);
                return key==10;
            }
        });

        //删除节点
        String s2 = map.remove(2);
        System.out.println("删除key为2的节点值"+s2);
        String s3=map.remove(42);
        System.out.println("删除key为42的节点值"+s3);
        System.out.println("map="+map);

        //清空map
        System.out.println("清空map");
        map.clear();
        System.out.println("clear后的map："+map);
        int size1=map.size();
        System.out.println("map的size是"+size1);
        Boolean emp1=map.isEmpty();
        System.out.println("map是否为空"+emp1);
    }
}
