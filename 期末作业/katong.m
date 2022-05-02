%% ����д��
clear,clc,close all;
%% ������
%���ĺͰ뾶
global Ox;global Oy;global Oz;global r;
Ox=-1000;Oy=0;Oz=200;r=500;[x,y,z] = sphere(800);
O = surf(r*x+Ox, r*y+Oy, r*z+Oz);O.EdgeColor = [0.94,0.76,0.80];%ɽ�󵭷�ɫ
hold on;
%% ����·���ϵĵ������:�ֳ���ֱ�ߡ���ֱ�ߡ�Ʋ������·��
% ��ͨ���ɽ���ͣ�����б�߼�һ��һ������1��-1��-1Ʋ-1�ࡣ
point_heng1=Hengxian(-15,15,15) %��������ϵ������
point_heng2=Hengxian(-10,10,0)%����ĽǶ�ֵ�����������Զ��壬���Ի����κ�λ�ú�б�ȵ���
point_shu1=flip(Shuxian(15,30,0)) %���������ϵ������
point_shu2=flip(Shuxian(-10,10,0))%��flip��ת������Ĺ켣������з��򣬷������滭�켣
point_xie1=flip(Piena(-30,-10,-20,0)) %����Ʋ���ϵ������
point_xie2=flip(Piena(-30,-10,20,0))
point_pie1=flip(Piena(15,30,-15,0))
point_na1=Piena(15,30,15,0)
%ƴ�����еĹ켣��
points=[point_pie1;point_heng1;point_na1;point_shu1;point_heng2;point_shu2;point_xie1;point_xie2]

view(90,1)%����켣�����ӽ�
% view([1,1,1])
%plot3����ɫ�������ָ������'ro'����ʵ��Բ�㣬���Ի��е��͹�𣬸ĳ�'r-'���Ǻ�ɫ����
plot3(points(:,1),points(:,2),points(:,3),'r-','linewidth',3);hold on;
%% �����е��
L(1)=Link('d', 151.9, 'a', 0, 'alpha', 0) %������theta����theta�ǹؽڱ���
L(2)=Link('d', 86.85, 'a', 0, 'alpha', pi/2)
L(3)=Link('d',0,'a',243.65,'alpha',0)
L(4)=Link('d',92.85,'a',213,'alpha',0)
L(5)=Link('d',83.4,'a',0,'alpha',-pi/2)
L(6)=Link('d',83.4,'a',0,'alpha',pi/2)
jxb = SerialLink(L,'base',transl(0 ,0 ,-250)); 
jxb.qlim=[-pi,pi;-pi,pi;-pi,pi;-pi,pi;-pi,pi;-pi,pi;]%���ƹؽ�ת��
%% ����ÿ���������Ӧ��λ�˾��������˶�ѧ�ؽڽ�
for i=1:length(points)
  T(:,:,i)=transl(points(i,:))*troty(-pi/2);%�任�����λ�ò�������y��ת90�ȣ�ʹĩ��ִ��������z�����Թ켣
end
Q=jxb.ikunc(T) %��ikine�������ᱨ��fail to converge�޷�����,������ʵ����ʾ���ֿ����ǻ�е�۳��Ȳ������������ĵ��󲻳���⡣�����»����˹����亯�������ֲᷢ�ֻ�������ikunc�����
jxb.plot(Q)
%% ����켣�������
%�������������ϵ������
function point_shu=Shuxian(theta1,theta2,phy) 
global Ox;global Oy;global Oz;global r; 
%���������ߵĵ�ĦսǶ�һ����rһ�������ȽǶȲ�һ��
%theta1,2��ʾ���ߵ׶˵�������������Ƕȣ�phy�Ǳ�ʾ����λ�õ����ҽǶ�
X=[];Y=[];Z=[];%����������ÿ���������
for theta=theta1:1:theta2 %��1Ϊ�Ƕȼ��ȡ��
    % �����굽xyz�������ת��
    x1=Ox+r*cosd(theta)*cosd(phy);
    y1=Oy+r*cosd(theta)*sind(phy);
    z1=Oz+r*sind(theta);
    X=[X;x1];Y=[Y;y1];Z=[Z;z1];
end
point_shu=[X Y Z];
end
%%
%������������ϵ������
function point_heng=Hengxian(phy1,phy2,theta) 
global Ox;global Oy;global Oz;global r;
%���ߵ�ĦȽǶ�һ����rһ�������սǶȲ�һ��
%phy1,phy2��ʾ������ߵ��ұߵ�������Ƕȣ�theta�Ǳ�ʾ����λ�õ����½Ƕ�
X=[];Y=[];Z=[];%���������ÿ���������
for phy=phy1:1:phy2 %��1Ϊ�Ƕȼ��ȡ��
    % �����굽xyz�������ת��
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
%Ʋ���ߵĵ�r��ͬ����,�սǶȲ���ͬ������phy1,phy2��ʾƲ����������䣬theta12��ʾ��������
X=[];Y=[];Z=[];%����Ʋ����ÿ���������
N=phy2-phy1+1 %�����ĸ���
shangxia=[theta1:(theta2-theta1)/(N-1):theta2];%�ȷ���������Ƕ�
for phy=phy1:1:phy2 %��1Ϊ�Ƕȼ��ȡ��
    theta=shangxia(phy-phy1+1)%ȡtheta��
    % �����굽xyz�������ת��
    z1=Oz+r*sind(phy);
    x1=Ox+r*cosd(phy)*cosd(theta);
    y1=Oy+r*cosd(phy)*sind(theta);
    X=[X;x1];Y=[Y;y1];Z=[Z;z1];
end
point_piena=[X Y Z];
end