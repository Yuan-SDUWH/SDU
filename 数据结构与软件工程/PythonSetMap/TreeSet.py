"""
没有调用任何封装库，用底层逻辑实现的二叉搜索树TreeSet类，可以直接运行进行功能测试
author 作者：@黄河源 Heyuan Huang
teacher 指导教师：@MJ
"""


class TreeSet:
    root = None
    size = 0

    class Node():
        def __init__(self, element=None, left=None, right=None, parent=None):
            self.element = element
            self.left = left
            self.right = right
            self.parent = parent

    def __size__(self):
        return self.size

    def isEmpty(self):
        return self.size == 0

    def clear(self):
        self.root = None
        self.size = 0

    def contains(self, element):
        return self.__getNode__(element) != None

    def __getNode__(self, element):
        if element == None:
            print("element不能为None")
            return
        node = self.root
        while node != None:
            if element < node.element:
                node = node.left
            elif element > node.element:
                node = node.right
            else:
                return node
        return None

    def add(self, element):
        node = self.root
        if element == None:
            print('输入element不能为None')
            return
        if self.root == None:
            self.root = self.Node(element, None, None, None)
            self.size = 1
            return None
        while node != None:
            parent = node
            if element < node.element:
                node = node.left
            elif element > node.element:
                node = node.right
            else:
                node.element = element
                return
        node_new = self.Node(element, None, None, parent)
        if element < parent.element:
            parent.left = node_new
        else:
            parent.right = node_new
        self.size += 1

    def remove(self, element):
        node = self.__getNode__(element)
        if node == None:
            return
        self.__remove__(node)

    def __remove__(self, node):
        if node.left == None and node.right == None:
            if node == self.root:
                self.root = None
            elif node == node.parent.left:
                node.parent.left = None
            else:
                node.parent.right = None
        elif ((node.left == None and node.right != None) or (node.left != None and node.right == None)):
            child = node.left if node.left != None else node.right
            if node == self.root:
                self.root = child
                self.root.parent = None
            else:
                child.parent = node.parent
                if node == node.parent.left:
                    node.parent.left = child
                else:
                    node.parent.right = child
        elif node.left != None and node.right != None:
            successorNode = self.__successor__(node)
            # 用后继节点的值覆盖node节点的值
            node.element = successorNode.element;
            # 删除后继节点
            self.__remove__(successorNode);
        self.size -= 1

    def __successor__(self, node):
        cur = node.right;
        if cur != None:
            while cur.left != None:
                cur = cur.left
            return cur
        while node.parent != None and node == node.parent.right:
            node = node.parent
        return node.parent

    def traversal(self, visitor=None):
        if visitor == None:
            return
        self.__traversal__(self.root, visitor)

    class Visitor():
        def __init__(self):
            self.stop = False

        # 自定义visit方法功能

        def visit(self, node):
            print(str(node.element) + "  ")
            return False

    def __traversal__(self, node, visitor):

        if node == None or visitor.stop:
            return
        self.__traversal__(node.left, visitor)
        visitor.stop = visitor.visit(node)
        self.__traversal__(node.right, visitor)


if __name__ == '__main__':
    tst = TreeSet()
    tst.add(4);
    tst.add(8);
    tst.add(9);
    tst.add(4);
    tst.add(4);
    tst.add(12);
    tst.add(5);
    print("TreeSet的size是" + str(tst.size))
    print("TreeSet是否为空：" + str(tst.isEmpty()))
    print("traversal选择中序遍历TreeSet为：")
    tst.traversal(tst.Visitor())
    print("TreeSet是否包含元素4:" + str(tst.contains(4)))
    print("TreeSet是否包含元素999:" + str(tst.contains(999)))
    tst.remove(7)
    tst.remove(8)
    tst.remove(4)
    print("删除节点4，8之后,traversal中序遍历TreeSet为：")
    tst.traversal(tst.Visitor())
    print("清空TreeSet")
    tst.clear()
    print("TreeSet的size是" + str(tst.size))
    print("TreeSet是否为空" + str(tst.isEmpty()))
