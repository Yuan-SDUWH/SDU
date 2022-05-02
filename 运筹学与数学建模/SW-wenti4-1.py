import networkx as nx
import matplotlib.pyplot as plt
import numpy as np
import time
np.random.seed(100) #保持每次随机数相同
start_time=time.time()
G = nx.Graph()
G.add_edges_from([(1,2),(1,3),(1,4),(1,5),(2,3),(2,4),(2,5),(3,4),(3,5),(3,7),(4,5),(4,8),(5,6),(6,7),(6,8),(6,9),(6,10),(7,8),(7,9),(7,10),(8,9),(8,10),(9,10)],weight=1)


cut_value=-1
originNodes=set(G)  #记录初始点集
originN=len(G)   #记录初始点的数量
n=len(G)
shousuo=[] #每一轮都收缩最后两个点 记下来每次收缩的两个点
globalMinCutN=-10
while n>1:
    nodes=set(G)
    # nx.draw(G,with_labels=True,node_color='pink')
    # plt.savefig('第'+str(10-n)+'次')  #画出每次收缩两个点之后新图的图像
    # plt.show()
    for item in nodes:
        G.nodes[item]['visit'] = 0
    wage = {}
    t = np.random.choice(G) #随机选一个起点
    G.nodes[t]['visit']=1
    for i in range(1,n): #i表示添加点的次数
        p=-1
        # print('i=',i,'t=',t,'wage=',wage)
        for v, e in G[t].items():# v是和t相接的点，e是tv之间的权重字典
            if G.nodes[v]['visit'] != 1:
                wage[v] = wage.get(v,0)+e["weight"]
                if p == -1 or wage[v]>wage.get(p,0):
                    p=v #找到当前wage最大的点v
        #如果和t相接的点全都访问过了，那就寻找当前最大的wage[v]
        if p==-1:
            newWage={}
            for item in nodes:
                if G.nodes[item]['visit']!=1 and wage.get(item,0)>0:
                    newWage[item] =wage[item]
            # print('newWage',newWage)
            maxV=max(newWage,key=newWage.get)
            p=maxV

        # print('i=',i,'p=',p,type(p))
        G.nodes[p]['visit']=1   #点p是当前找到的最大wage点加到A里
        if i==n-1:  #表示添加了n-2次点 这次添加的是最后一个点T就是p 倒数第二个点S是t
            for w, e in G[p].items():
                if w != t:
                    if w not in G[t]:
                        G.add_edge(t, w, weight=e["weight"])
                    else:
                        G[t][w]["weight"] += e["weight"]
            G.remove_node(p)
            shousuo.append((t,p))
            n=n-1

            if cut_value==-1:
                cut_value=wage[p]
                globalMinCutN=originN-1-n
            else:
                if cut_value>wage[p]:
                    cut_value=min(cut_value,wage[p])
                    globalMinCutN = originN - 1 - n

        t=p

# nx.draw(G,with_labels=True,node_color='pink')
# plt.savefig('第'+str(10-n)+'次')  #画出最后收缩两个点之后新图的图像
# plt.show()
F=nx.Graph()
F.add_edges_from(shousuo[0:globalMinCutN])
Terminal=shousuo[globalMinCutN][1] #全局最小割的T点
Tset=set(nx.single_source_shortest_path_length(F, Terminal)) #从T点出发，根据之前两两收缩的点可以展开和T相连的边，从而还原出包含T点的连通分量
partition = (list(Tset), list(originNodes - Tset)) #V-T即为包含S点的连通分量
print('全局最小割所分成的两个连通分量点集为',partition)
end_time=time.time()
time_used=end_time-start_time
print('算法耗时',time_used,'秒')


print('全局最小割值为',cut_value)
print('全局最小割出现在第',globalMinCutN+1,'次合并点时','即此时的ST最小割就是全局最小割')
print('所有两两合并的点的列表,前者为S，后者为T：',shousuo)
print('故全局最小割为(s,t)=',shousuo[globalMinCutN],'的st最小割')