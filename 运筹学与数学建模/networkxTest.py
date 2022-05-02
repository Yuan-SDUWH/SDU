import networkx as nx
import matplotlib.pyplot as plt
import numpy as np
import time
np.random.seed(100) #保持每次随机数相同
# 问题4-(1)中十个点的图为G1

G1 = nx.Graph()
G1.add_edges_from([(1,2),(1,3),(1,4),(1,5),(2,3),(2,4),(2,5),(3,4),(3,5),(3,7),(4,5),(4,8),(5,6),(6,7),(6,8),(6,9),(6,10),(7,8),(7,9),(7,10),(8,9),(8,10),(9,10)],weight=1)
time_start1 = time.time()
ans1=nx.stoer_wagner(G1)
time_end1 = time.time()
time_used1 = time_end1 - time_start1
print('问题4-(1)最小割数',ans1[0],'连通分量点集T',ans1[1][0])
print('G1用时',time_used1,'秒')

# 问题4-(2)附件data中BenchmarkNetwork的图为G2
G2 = nx.Graph()
edges2=[]
with open('BenchmarkNetwork.txt','r') as f:
    for line in f.readlines():
        edge2 = tuple(line.split())
        edges2.append(edge2)
# print(edges2)
print('G2边数',len(edges2))
G2.add_edges_from(edges2,weight=1)

time_start2 = time.time()
ans2=nx.stoer_wagner(G2)
time_end2 = time.time()
time_used2 = time_end2 - time_start2
print('BenchmarkNetwork最小割数',ans2[0],'连通分量点集T',ans2[1][0])
print('G2用时',time_used2,'秒')

# nx.draw(G2,with_labels=True)
# plt.title('BenchmarkNetwork')
# plt.show()

# 问题4-(2)附件data中Corruption_Gcc的图为G3

G3 = nx.Graph()
edges3=[]
with open('Corruption_Gcc.txt','r') as f:
    for line in f.readlines():
        edge3 = tuple(line.split())
        edges3.append(edge3)
# print(edges3)
print('G3边数',len(edges3))
G3.add_edges_from(edges3,weight=1)

time_start3 = time.time()
ans3=nx.stoer_wagner(G3)
time_end3 = time.time()
time_used3 = time_end3 - time_start3
print('Corruption_Gcc最小割数',ans3[0],'连通分量点集T',ans3[1][0])
print('G3用时',time_used3,'秒')

# nx.draw(G3,with_labels=True)
# plt.title('Corruption_Gcc')
# plt.show()
# print('G3-19邻居',G3['19'])

# 问题4-(2)附件data中Crime_Gcc的图为G4

G4 = nx.Graph()
edges4=[]
with open('Crime_Gcc.txt','r') as f:
    for line in f.readlines():
        edge4 = tuple(line.split())
        edges4.append(edge4)
# print(edges4)
print('G4边数',len(edges4))
G4.add_edges_from(edges4,weight=1)

time_start4 = time.time()
ans4=nx.stoer_wagner(G4)
time_end4 = time.time()
time_used4 = time_end4 - time_start4
print('Crime_Gcc最小割数',ans4[0],'连通分量点集T',ans4[1][0])
print('G4用时',time_used4,'秒')

# nx.draw(G4,with_labels=True)
# plt.title('Crime_Gcc')
# plt.show()
# print('G4-606邻点',G4['606'])

# 问题4-(2)附件data中PPI_gcc的图为G5

G5 = nx.Graph()
edges5=[]
with open('PPI_gcc.txt','r') as f:
    for line in f.readlines():
        edge5 = tuple(line.split())
        edges5.append(edge5)
# print(edges5)
print('G5边数',len(edges5))
G5.add_edges_from(edges5,weight=1)

time_start5 = time.time()
ans5=nx.stoer_wagner(G5)
time_end5 = time.time()
time_used5 = time_end5 - time_start5
print('PPI_gcc最小割数',ans5[0],'连通分量点集T',ans5[1][0])
print('G5用时',time_used5,'秒')

# nx.draw(G5,with_labels=True)
# plt.title('PPI_gcc')
# plt.show()
# print('G5-1760邻居',G5['1760'])

# 问题4-(2)附件data中RodeEU_gcc的图为G6

G6 = nx.Graph()
edges6=[]
with open('RodeEU_gcc.txt','r') as f:
    for line in f.readlines():
        edge6 = tuple(line.split())
        edges6.append(edge6)
# print(edges6)
print('G6边数',len(edges6))
G6.add_edges_from(edges6,weight=1)


time_start6 = time.time()
ans6=nx.stoer_wagner(G6)
time_end6 = time.time()
time_used6 = time_end6 - time_start6
print('RodeEU_gcc最小割数',ans6[0],'连通分量点集T',ans6[1][0])
print('G6用时',time_used6,'秒')

# nx.draw(G6,with_labels=True)
# plt.title('RodeEU_gcc')
# plt.show()
# print('G6-589邻居',G6['589'])