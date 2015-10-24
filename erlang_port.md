# 注意事项 #
  1. 输入文件应该为UTF-8编码 没有BOM头
  1. 可能MBCS支持仍然有问题，需要Code review.
  1. 代码在这里 [grass\_mud\_horse.erl](http://code.google.com/p/grass-mud-horse/source/browse/trunk/erlang/grass_mud_horse.erl) erl5.7.5 编译运行通过。
# 运行实例 #
```
1> Code = grass_mud_horse:c("test.txt").
[{push,1},
 {defun,67},
 dup,oint,
 {push,10},
 ochr,
 {push,1},
 add,dup,
 {push,11},
 sub,
 {jz,69},
 {jmp,67},
 {defun,69},
 pop,exit]
2> grass_mud_horse:r(Code).
1
2
3
4
5
6
7
8
9
10
{exit,[],
      {dict,0,16,16,8,80,48,
            {[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[]},
            {{[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[]}}}}
3>
```