/**
 * Teacher:指导教师 @MJ
 * Student：学生@Heyuan Huang
 */
public class Person implements Comparable<Person>{
    //Person类的属性不止一种
    private int age;
    private int height;
    private int id;
    public Person(int age,int height,int id){
        this.age=age;
        this.height=height;
        this.id=id;
    }
    //比较时先根据年龄排序，再根据身高排序
    @Override
    public int compareTo(Person o) {
        if (o == null) return 1;
        int ret = Integer.compare(age, o.age);
        return (ret != 0) ? ret : Integer.compare(height, o.height);
    }

    public static void main(String[] args) {
        Person p1=new Person(15,165,1);
        //p1和p6的排序元素相同，所以会自动去重，覆盖掉p1的id，不会添加新节点
        Person p6=new Person(15,165,6);
        Person p2=new Person(14,170,2);
        Person p3=new Person(16,168,3);
        Person p4=new Person(13,165,4);
        Person p5=new Person(14,172,5);

        TreeSet<Person> tst=new TreeSet<>();
        tst.add(p1);tst.add(p2);tst.add(p3);tst.add(p4);tst.add(p5);tst.add(p6);
        System.out.println("Person_TreeSet的size为"+tst.size());
        System.out.println("TreeSet是否为空："+tst.isEmpty());
        //遍历全部元素
        System.out.println("这是中序遍历-按自定义排序元素从小到大的顺序遍历:");
        tst.traversal(new TreeSet.Visitor<Person>() {
            @Override
            public boolean visit(Person element) {
                System.out.print("Person"+element.id+" ");
                return false;
            }
        });

        //遍历部分元素 到n终止遍历
        System.out.println();
        System.out.println("中序遍历-遍历部分元素-到Person5终止遍历:");
        Person n=p5;
        tst.traversal(new TreeSet.Visitor<Person>() {
            @Override
            public boolean visit(Person element) {
                System.out.print("Person"+element.id+" ");
                return element == n;
            }
        });
        System.out.println();
        System.out.println("是否包含Person5："+tst.contains(p5));
//        //假设赋值p7和p5元素值相等，根据自定义的compareTo规则，只比较age和height，因为age和height一样，所以即使不是同一个对象地址，也会判断包含p7
//        Person p7=new Person(14,172,7);
//        System.out.println("是否包含Person7："tst.contains(p7));
        System.out.println("删除Person4之后的TreeSet:");
        tst.remove(p4);
        tst.traversal(new TreeSet.Visitor<Person>() {
            @Override
            public boolean visit(Person element) {
                System.out.print("Person"+element.id+" ");
                return false;
            }
        });
        System.out.println();
        System.out.println("清空TreeSet");
        tst.clear();
        System.out.println("TreeSet是否为空："+tst.isEmpty());
        System.out.println("TreeSet的size为"+tst.size());
    }
}
