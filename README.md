# dn-modbus

在Modbus Slave中配置如下四个寄存器，
![图片](http://images.cnblogs.com/cnblogs_com/Dn9x/562583/o_13.pic_hd.jpg)
可以根据这个配置：
```
  <registers>
		<register>
			<address>0</address>
			<defaultValue>123</defaultValue>
			<type>DI</type>
		</register>
		<register>
			<address>1</address>
			<defaultValue>12</defaultValue>
			<type>DO</type>
		</register>
		<register>
			<address>2</address>
			<defaultValue>53</defaultValue>
			<type>IR</type>
		</register>
		<register>
			<address>3</address>
			<defaultValue>63</defaultValue>
			<type>RE</type>
		</register>
	</registers>
```
然后运行程序中Test,你就可以读取和设置模拟器上面的值了。
![](http://images.cnblogs.com/cnblogs_com/Dn9x/562583/o_14.pic_hd.jpg)
