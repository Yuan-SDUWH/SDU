%% 球面写字
clear,clc,close all;
%% 画球面
%球心和半径
global Ox;global Oy;global Oz;global r;
Ox=-1000;Oy=0;Oz=200;r=500;[x,y,z] = sphere(800);
O = surf(r*x+Ox, r*y+Oy, r*z+Oz);O.EdgeColor = [0.94,0.76,0.80];%山大淡粉色
hold on;
%% 计算路径上的点的坐标:分成竖直线、横直线、撇捺三种路径
% 卡通版的山造型：两条斜线加一横一竖。大：1横-1竖-1撇-1捺。
point_heng1=Hengxian(-15,15,15) %计算横线上点的坐标
point_heng2=Hengxian(-10,10,0)%这里的角度值都可以任意自定义，可以画出任何位置和斜度的线
point_shu1=flip(Shuxian(15,30,0)) %计算竖线上点的坐标
point_shu2=flip(Shuxian(-10,10,0))%用flip倒转算出来的轨迹点的排列方向，方便下面画轨迹
point_xie1=flip(Piena(-30,-10,-20,0)) %计算撇捺上点的坐标
point_xie2=flip(Piena(-30,-10,20,0))
point_pie1=flip(Piena(15,30,-15,0))
point_na1=Piena(15,30,15,0)
%拼接所有的轨迹点
points=[point_pie1;point_heng1;point_na1;point_shu1;point_heng2;point_shu2;point_xie1;point_xie2]

view(90,1)%面向轨迹正面视角
% view([1,1,1])
%plot3的颜色标记这里指定的是'ro'，是实心圆点，所以会有点的凸起，改成'r-'就是红色的线
plot3(points(:,1),points(:,2),points(:,3),'r-','linewidth',3);hold on;
%% 定义机械臂
L(1)=Link('d', 151.9, 'a', 0, 'alpha', 0) %不输入theta代表theta是关节变量
L(2)=Link('d', 86.85, 'a', 0, 'alpha', pi/2)
L(3)=Link('d',0,'a',243.65,'alpha',0)
L(4)=Link('d',92.85,'a',213,'alpha',0)
L(5)=Link('d',83.4,'a',0,'alpha',-pi/2)
L(6)=Link('d',83.4,'a',0,'alpha',pi/2)
jxb = SerialLink(L,'base',transl(0 ,0 ,-250)); 
jxb.qlim=[-pi,pi;-pi,pi;-pi,pi;-pi,pi;-pi,pi;-pi,pi;]%限制关节转角
%% 计算每个点坐标对应的位姿矩阵，求逆运动学关节角
for i=1:length(points)
  T(:,:,i)=transl(points(i,:))*troty(-pi/2);%变换到点的位置并绕自身y轴转90度，使末端执行器坐标z轴正对轨迹
end
Q=jxb.ikunc(T) %用ikine计算逆解会报错fail to converge无法收敛,看了下实际演示发现可能是机械臂长度不够，够不到的点求不出逆解。查了下机器人工具箱函数释义手册发现还可以用ikunc算逆解
jxb.plot(Q)
%% 计算轨迹点的坐标
%计算球面竖线上点的坐标
function point_shu=Shuxian(theta1,theta2,phy) 
global Ox;global Oy;global Oz;global r; 
%球面上竖线的点的φ角度一样，r一样，但θ角度不一样
%theta1,2表示竖线底端到顶部的球坐标角度，phy是表示竖线位置的左右角度
X=[];Y=[];Z=[];%计算竖线上每个点的坐标
for theta=theta1:1:theta2 %以1为角度间隔取点
    % 球坐标到xyz坐标进行转换
    x1=Ox+r*cosd(theta)*cosd(phy);
    y1=Oy+r*cosd(theta)*sind(phy);
    z1=Oz+r*sind(theta);
    X=[X;x1];Y=[Y;y1];Z=[Z;z1];
end
point_shu=[X Y Z];
end
%%
%计算球面横线上点的坐标
function point_heng=Hengxian(phy1,phy2,theta) 
global Ox;global Oy;global Oz;global r;
%横线点的θ角度一样，r一样，但φ角度不一样
%phy1,phy2表示横线左边到右边的球坐标角度，theta是表示横线位置的上下角度
X=[];Y=[];Z=[];%计算横线上每个点的坐标
for phy=phy1:1:phy2 %以1为角度间隔取点
    % 球坐标到xyz坐标进行转换
    x1=Ox+r*cosd(theta)*cosd(phy);
    y1=Oy+r*cosd(theta)*sind(phy);
    z1=Oz+r*sind(theta);
    X=[X;x1];Y=[Y;y1];Z=[Z;z1];
end
point_heng=[X Y Z];
end
%% 
function point_piena=Piena(phy1,phy2,theta1,theta2) 
global Ox;global Oy;global Oz;global r;
%撇捺线的点r相同，θ,φ角度不相同。这里phy1,phy2表示撇捺的左右区间，theta12表示上下区间
X=[];Y=[];Z=[];%计算撇捺上每个点的坐标
N=phy2-phy1+1 %计算点的个数
shangxia=[theta1:(theta2-theta1)/(N-1):theta2];%等分上下区间角度
for phy=phy1:1:phy2 %以1为角度间隔取点
    theta=shangxia(phy-phy1+1)%取theta角
    % 球坐标到xyz坐标进行转换
    z1=Oz+r*sind(phy);
    x1=Ox+r*cosd(phy)*cosd(theta);
    y1=Oy+r*cosd(phy)*sind(theta);
    X=[X;x1];Y=[Y;y1];Z=[Z;z1];
end
point_piena=[X Y Z];
end