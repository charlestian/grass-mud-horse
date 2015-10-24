## 序言 ##
**前略，天国的河蟹们，草泥马现在是超越阿姆骡的存在**
# 发展中的草泥马语 #
> <a href='http://code.google.com/p/grass-mud-horse/wiki/A_Brife_To_GrassMudHorse_Language'><img src='http://static.icybear.net/2010/07/gmh.jpg'></img></a>
  * 为了庆祝天朝共产派对竟然能存活89年，特地发布[erlang版本的草泥马语虚拟机](http://code.google.com/p/grass-mud-horse/wiki/erlang_port)，欢迎参观指导。
  * 草泥马语终于拥有了一个[基于Javascript的浏览器实现](http://github.com/dexteryy/GrassMudMonkey)，为草泥马语向Web3.0前景铺平了道路！祝愿草泥马语在伟大的西朝鲜主题思想光芒照耀下发出更加耀眼的光芒！
  * 在我们随机调查的100个网友中，有<font color='red'>87.35%</font>的网友认为草泥马语非常好！
  * 草泥马语第一个社区请求（Grass Community Request）：[GCR001\_W3C\_DOM\_Support\_Draft](GCR001_W3C_DOM_Support_Draft.md) 于即日起开始草稿范本的起草工作，欢迎广大戈壁公民对该范本提出宝贵意见。
  * 我们很高兴的接受了[《中国人如何做爱》](http://code.google.com/p/chtml/)项目组提交的草泥马语的第一个issue: [issue 1](https://code.google.com/p/grass-mud-horse/issues/detail?id=1) 关于草泥马语的CHTML支持问题。对此，草泥马语项目组作出了积极响应并得到了戈壁中央的大力支持，我们一定能够与CHTML项目组一起，为了建设现代化的马勒戈壁而奋斗。
  * 热烈欢迎草泥马语的兄弟语言[八卦语](http://code.google.com/p/grass-mud-horse/issues/detail?id=1#c8)，虽然我们目前没有找到这个语言的官方网站，但是根据民法宪法刑法等有关法律，我们相信有官部门一定会解决这个困难，为了我朝网络的二胖化的进程奋斗！
  * 经过戈壁政府的不懈努力，值此2010年新春佳节之际，戈壁政府推出[草泥马虚拟机贺岁版](http://code.google.com/p/grass-mud-horse/wiki/GMHVMhesuiban)，草泥马语发展进程迈向新的台阶！
# Brief #
> This is a programing language based on stack & heap. The only tokens used are '草','泥','马' and "河蟹", other chars are ignored as comments.

# 简介 #
> `  `《草泥马语》是一个基于堆和栈的编程语言，该语言的最大特点在于：所有操作全部由“草”“泥”“马”“河蟹”这4个命令完成，文件内的其他字符都作为注释而不参与程序执行。

> `  `有人抱怨说不会用，这不是就凑合了一个[简易草泥马语学习指南](A_Brife_To_GrassMudHorse_Language.md)（好像差不多完了）

> `  `另外，由于草泥马语言形式过于震撼，所以为了调试，编写了草泥马语逆编译器：gmhd（但是没有正向编译，囧，谁贡献一个？）可以吧草泥马语言代码反编译成比较好懂的伪代码。

> `  `第二个工具是whitespace程序转换器：ws2gmh,简单的replace而已。

# Example #
> The code of grass-mud-horse language looks like following, this program loops from 1 to 10:
# 示例 #
> 写出来大概就是这个样子，下面是一个从1到10的循环：
> 感谢(卡库尔 / Kakur)同学提供

|草-草  草泥马|数字1压栈|Put a 1 on the stack|
|:-------|:----|:-------------------|
|马-草草 草泥草草草草泥泥马|在这里设置一个循环开始的标识|Set a Label at this point|
|草-马草    |复制栈顶元素|Duplicate the top stack item|
|泥马-草泥   |输出当前值|Output the current value|
|草草 草泥草泥草马|换行(10)压栈|Put 10 (newline) on the stack...|
|泥马-草草   |输出   |...and output the newline|
|草草 草泥马  |1压栈  |Put a 1 on the stack|
|泥草-草草   |相加栈内两个元素|Addition. This increments our current value.|
|草-马草    |复制栈顶以便比较|Duplicate that value so we can test it|
|草-草 草泥草泥泥马|11压栈 |Push 11 onto the stack|
|泥草-草泥   |相减，如果我们已经循环到11了应该会得到一个0到栈顶|Subtraction. So if we've reached the end, we have a zero on the stack.|
|马-泥草 草泥草草草泥草泥马|如果是0，跳到最后|If we have a zero, jump to the end|
|马-草马 草泥草草草草泥泥马|跳到开始 |Jump to the start   |
|马-草草 草泥草草草泥草泥马|设置一个标签标识循环结束|Set the end label   |
|草-马马    |清空堆栈，打扫卫生，整理整顿|Discard our accumulator, to be tidy|
|河蟹      |正如其言 |Just as it says.    |}}}

PS:<Learning>图片来源于互联网，请版权所有者与我们联系```