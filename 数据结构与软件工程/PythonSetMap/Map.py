"""
基于二叉搜索树底层逻辑实现的Map类，可以直接运行代码进行功能测试
author 作者：@黄河源 Heyuan Huang
teacher 指导教师：@MJ
"""
from queue import Queue


class Map:
    root = None
    size = 0

    class Node():
        def __init__(self, key=None, value=None, left=None, right=None, parent=None):
            self.key = key
            self.value = value
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

    def containsKey(self, key):
        return self.__getNode__(key) != None

    def __getNode__(self, key):
        if key == None:
            print("key不能为None")
            return
        node = self.root
        while node != None:
            if key < node.key:
                node = node.left
            elif key > node.key:
                node = node.right
            else:
                return node
        return None

    def containsValue(self, value):
        if self.root == None:
            return False
        queue = Queue()
        queue.put(self.root)
        while not queue.empty():
            node = queue.get()
            if (node.value == None if value == None else value == node.value):
                return True
            if node.left != None:
                queue.put(node.left)
            if node.right != None:
                queue.put(node.right)
        return False

    def put(self, key, value):
        node = self.root
        if key == None:
            print('输入key不能为None')
            return
        if self.root == None:
            self.root = self.Node(key, value, None, None, None)
            self.size = 1
            return None
        while node != None:
            parent = node
            if key < node.key:
                node = node.left
            elif key > node.key:
                node = node.right
            else:
                value_old = node.value
                node.value = value
                return value_old
        node_new = self.Node(key, value, None, None, parent)
        if key < parent.key:
            parent.left = node_new
        else:
            parent.right = node_new
        self.size += 1
        return value

    def get(self, key):
        node = self.__getNode__(key)
        return None if node == None else node.value

    def remove(self, key):
        node = self.__getNode__(key)
        if node == None:
            return None
        self.__remove__(node)
        return node.value

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
            node.key = successorNode.key;
            node.value = successorNode.value;
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
            print("key:" + str(node.key) + "  value:" + str(node.value))
            return False

    def __traversal__(self, node, visitor):

        if node == None or visitor.stop:
            return
        self.__traversal__(node.left, visitor)
        visitor.stop = visitor.visit(node)
        self.__traversal__(node.right, visitor)


if __name__ == '__main__':
    map = Map()
    map.put(9, "aaa");
    map.put(4, "kkk");
    map.put(2, "aaa");
    map.put(10, "ddd");
    map.put(77, "dd5");
    map.put(42, "d72");
    map.put(5, "gfgs");
    map.put(77, "gdfvgr");
    print("map的size是" + str(map.size))
    print("map是否为空" + str(map.isEmpty()))

    print("key为5的value值是" + str(map.get(5)))
    print("key为1241431的value值是" + str(map.get(1241431)))
    print("traversal选择中序遍历map为：")
    map.traversal(map.Visitor())
    print("是否包含key：5=" + str(map.containsKey(5)))
    print("是否包含key：9249=" + str(map.containsKey(9249)))
    print("是否包含value：13434=" + str(map.containsValue('13434')))
    print("是否包含value：kkk=" + str(map.containsValue('kkk')))
    print("删除key为2的节点值" + str(map.remove(2)))
    print("删除key为42的节点值" + str(map.remove(42)))
    print("traversal选择中序遍历map为：")
    map.traversal(map.Visitor())
    print("清空map")
    map.clear()
    print("map的size是" + str(map.size))
    print("map是否为空" + str(map.isEmpty()))
