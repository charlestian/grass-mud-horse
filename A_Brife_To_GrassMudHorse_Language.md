# 简介：什么是《草泥马语（暂定名）》 #

> 草泥马语是马勒戈壁第一款拥有自主知识产权的，以马勒戈壁上顽强生存的草泥马们为主体的编程语言：草泥马语（暂定名）与本日7时正式发行了。草泥马语语法生动丰富，内容健康活泼，是一门老少皆宜，人人适用的编程语言。它的出现弥补了我戈壁在国际编程语言界中的一项空白。
草泥马语是用了先进的JOT（Just Out of Time）编译引擎，并且运行于爪哇虚拟机中，运行速度大幅度\*降低\*同时，还使用了戈壁内外各种先进技术，使的草泥马语\*不\*十分可靠。

# 《草泥马语（暂定名）》的语言规范 #

> 草泥马语是一款根据国外同类型语言“[Whitespace](http://compsoc.dur.ac.uk/whitespace/index.php)”改编（替换关键字）而成的\*全新\*的编程语言，执行时使用“草泥马”的不同组合实现不同功能。
> > PS:在本文还没有完工之前（但是看起来已经完工了），可以先看[Whitespace语言入门](http://compsoc.dur.ac.uk/whitespace/tutorial.php)，然后吧其中的`[Space]`替换成`草`；`[Tab]`替换成`泥`；`[LF]`替换成`马`就可以了解草泥马语的大致用法了。

> 每个指令都有3部分组成，包括指令头，指令和可选的操作数

## 指令头 ##
> 草泥马语的指令头说明了指令的类型，列表如下：

|指令头|说明|鸟语说明|
|:--|:-|:---|
|`[草]`|栈操作|Stack Manipulation|
|`[泥][草]`|数学运算|Arithmetic|
|`[泥][泥]`|堆操作|Heap access|
|`[马]`|流程控制|Flow Control|
|`[泥][马]`|输入输出|I/O |

**感谢`````hyperbolica`````同学指出错误**

## 栈操作指令 [指令头：草] ##
> 栈用来存储指令调用的参数和结果，元素仅限整数，栈高度不限。
|指令|操作数|说明|鸟语说明|
|:-|:--|:-|:---|
|`[草]`|Number	|把某个数压栈|Push the number onto the stack|
|`[马][草]`|-  |复制栈顶元素|Duplicate the top item on the stack|
|`[泥][草]`|Number|复制第n个元素到栈顶|Copy the nth item on the stack (given by the argument) onto the top of the stack|
|[马][泥]|-  |交换栈顶两个元素的位置|Swap the top two items on the stack|
|[马][马]|-  |销毁栈顶元素|Discard the top item on the stack|
|[泥][马]|Number|销毁栈顶的n个元素，但保持栈顶|Slide n items off the stack, keeping the top item|

## 数学运算指令[指令头：泥草] ##
> 注意，草泥马语没有浮点运算，但是整数运算的精度是无限的。
> 操作规则：操作时先弹出一个栈元素为运算的右值，再弹出一个为左值，计算后将结果压栈。
|指令|操作数|说明|鸟语说明|
|:-|:--|:-|:---|
|[草][草]|-  |加 |Addition|
|[草][泥]|-  |减 |Subtraction|
|[草][马]|-  |乘 |Multiplication|
|[泥][草]|-  |除 |Integer Division|
|[泥][泥]|-  |取模|Modulo|

## 堆操作[指令头：泥泥] ##
> 与栈一样，堆也只能存储整数，草泥马虚拟机(AlpacaVM)最多支持65536个堆元素（0-65535），但是堆元素的大小不限制。
堆操作的规则：如果要存储，要先将要操作的堆地址压栈，再把要存储的数压栈，操作时弹出栈顶两元素，将栈顶元素存储到次栈顶指示的堆空间中；如果要取出，要先将地址压栈，操作时弹出栈顶元素，根据其指示的地址取出堆中元素压栈。
|指令|操作数|说明|鸟语说明|
|:-|:--|:-|:---|
|[草]|-  |存储栈顶元素x到堆地址为y的空间中，y是次栈顶元素|Store|
|[泥]|-  |先把y压栈,然后获取堆中地址为y空间的元素到栈顶|Retrieve|

## 流程控制[指令头:马] ##
> 标记一个程序点可以用来进行跳转或者函数调用，标记是一个任意长度的无符号整数，每个草泥马文件中，标记应该是唯一的，并且所有调用的标记应当存在。
|指令|操作数|说明|鸟语说明|
|:-|:--|:-|:---|
|[草][草]|Label|定义一个标记|Mark a location in the program|
|[草][泥]|Label|调用标记指示的函数|Call a subroutine|
|[草][马]|Label|无条件跳转到标记处|Jump unconditionally to a label|
|[泥][草]|Label|如果栈顶元素等于0则跳转到标记指示处|Jump to a label if the top of the stack is zero|
|[泥][泥]|Label|如果栈顶元素小于0则跳转到标记指示处|Jump to a label if the top of the stack is negative|
|[泥][马]|-  |函数结束，返回调用处|End a subroutine and transfer control back to the caller|
|[马][马]或者[河蟹]|-  |无条件结束程序运行|End the program|
> 注意:河蟹这个指令仅仅在河蟹这两个字连在一起时有效，分开是没有效果的。（**注意更正一个歧义表达，河蟹这个指令是没有指令头的。**）

## IO控制[指令头：泥马] ##
|指令|操作数|说明|鸟语说明|
|:-|:--|:-|:---|
|[草][草]|-  |弹出栈顶数字作为字符输出到标准输出|Output the character at the top of the stack|
|[草][泥]|-  |弹出栈顶数字以数字形式输出到标准输出|Output the number at the top of the stack|
|[泥][草]|-  |从标准输入内读取一个字符（回车结束)并且放到栈顶(弹出)所对应的堆空间|Read a character and place it in the location given by the top of the stack|
|[泥][泥]|-  |从标准输入内读取一个数字并且放到栈顶(弹出)所对应的堆空间|Read a number and place it in the location given by the top of the stack|

## 如何定义操作数 ##
> 操作数的定义是二进制的整数，对于流程控制指令来说是无符号的，否则是有符号的，第一位为符号位。
> 定义方法是：草为0;泥为1;马代表定义结束。对于符号位，草为正，泥为负。

## 举个例子吧 ##
> 这是首页的那个例子，输出1到10十个数，具体解释请看首页。
> > 草草草泥马
> > 马草草草泥草草草草泥泥马
> > 草马草
> > 泥马草泥
> > 草草草泥草泥草马
> > 泥马草草
> > 草草草泥马
> > 泥草草草
> > 草马草
> > 草草草泥草泥泥马
> > 泥草草泥
> > 马泥草草泥草草草泥草泥马
> > 马草马草泥草草草草泥泥马
> > 马草草草泥草草草泥草泥马
> > 草马马
> > 马马马