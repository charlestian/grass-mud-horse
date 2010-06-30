%% (c) Copyright Bearice 2010.  All rights reserved. 
%% See COPYING for more copyrights details
%% TODO: Add description to grass_mud_horse

-module(grass_mud_horse).

%%
%% Include files
%%
-include("grass_mud_horse.hrl").
%%
%% Exported Functions
%%
-export([c/1,load/1,r/1]).

%%
%% API Functions
%%
c(Filename)->
	{ok,Data}=file:read_file(Filename),
	compile(Data,[]).

r(Code)->
	case load(Code) of 
		{ok,Code1}->
			run(Code,[],dict:new(),Code1);
		{error,R}->
			{error,R}
	end.

load(Code)->load0(Code,dict:new()).
load0([],Dict)->{ok,Dict};
load0([{defun,L}|Rest],Dict)->
	case dict:find(L, Dict) of
		error ->
			Dict0 = dict:store(L, Rest, Dict),
			load0(Rest,Dict0);
		_ ->
			{error,{duplicated_defuns,L}}
	end;
load0([_|Rest],Dict)->
	load0(Rest,Dict).
run([{push,N}|Rest],Stack,Dict,Code)->
	run(Rest,[N|Stack],Dict,Code);
run([dup|Rest],Stack,Dict,Code)->
	[T|_]=Stack,
	run(Rest,[T|Stack],Dict,Code);
run([{copy,N}|Rest],Stack,Dict,Code)->
	E = lists:nth(N, Stack),
	run(Rest,[E|Stack],Dict,Code);
run([swap|Rest],Stack,Dict,Code)->
	[T1|S1]=Stack,
	[T2|S2]=S1,
	Stack0 = [T2,T1|S2],
	run(Rest,Stack0,Dict,Code);
run([pop|Rest],Stack,Dict,Code)->
	[_|Stack0]=Stack,
	run(Rest,Stack0,Dict,Code);
run([{slide,N}|Rest],Stack,Dict,Code)->
	{[H|_],S0} = lists:split(N+1, Stack),
	run(Rest,[H,S0],Dict,Code);
run([add|Rest],Stack,Dict,Code)->
	[R|S1]=Stack,
	[L|S2]=S1,
	run(Rest,[L+R|S2],Dict,Code);
run([sub|Rest],Stack,Dict,Code)->
	[R|S1]=Stack,
	[L|S2]=S1,
	run(Rest,[L-R|S2],Dict,Code);
run([mul|Rest],Stack,Dict,Code)->
	[R|S1]=Stack,
	[L|S2]=S1,
	run(Rest,[L*R|S2],Dict,Code);
run(['div'|Rest],Stack,Dict,Code)->
	[R|S1]=Stack,
	[L|S2]=S1,
	run(Rest,[L/R|S2],Dict,Code);
run([mod|Rest],Stack,Dict,Code)->
	[R|S1]=Stack,
	[L|S2]=S1,
	run(Rest,[L rem R|S2],Dict,Code);
run([{set,N}|Rest],Stack,Dict,Code)->
	[H|Stack1]=Stack,
	Dict1 = dict:store(N, H, Dict),
	run(Rest,Stack1,Dict1,Code);
run([{load,N}|Rest],Stack,Dict,Code)->
	case catch dict:fetch(N, Dict) of
		X when is_integer(X)->
			run(Rest,[X|Stack],Dict,Code);
		_ ->
			{error,{'SIGSEGV',{heap,N}}}
	end;

run([{call,L}|Rest],Stack,Dict,Code)->
	case catch dict:fetch(L, Code) of
		CS when is_list(CS)->
			case run(CS,Stack,Dict,Code) of 
				{ret,NS,ND}->
					run(Rest,NS,ND,Code);
				ErrorOrExit->
					ErrorOrExit
			end;
		_ ->
			{error,{'SIGSEGV',{code,L}}}
	end;
run([{jmp,L}|_Rest],Stack,Dict,Code)->
	case catch dict:fetch(L, Code) of
		CS when is_list(CS)->
			run(CS,Stack,Dict,Code);
		_ ->
			{error,{'SIGSEGV',{code,L}}}
	end;
run([{jz,L}|Rest],Stack,Dict,Code)->
	[H|Stack0]=Stack,
	case H of
		0->
			case catch dict:fetch(L, Code) of
				CS when is_list(CS)->
					run(CS,Stack0,Dict,Code);
				_ ->
					{error,{'SIGSEGV',{code,L}}}
			end;
		_->
			run(Rest,Stack0,Dict,Code)
	end;
run([{jnz,L}|Rest],Stack,Dict,Code)->
	[H|Stack0]=Stack,
	case H of
		0->
			run(Rest,Stack0,Dict,Code);
		_->
			case catch dict:fetch(L, Code) of
				CS when is_list(CS)->
					run(CS,Stack0,Dict,Code);
				_ ->
					{error,{'SIGSEGV',{code,L}}}
			end
	end;
run([ret|_Rest],Stack,Dict,_Code)->
	{ret,Stack,Dict};
run([exit|_Rest],Stack,Dict,_Code)->
	{exit,Stack,Dict};

run([iint|Rest],Stack,Dict,Code)->
	case io:fread("", "~d") of 
		{ok,[Num]}->
			run(Rest,[Num|Stack],Dict,Code);
		{error,E}->
			io:write("Please inuput an integer! (~p)~n",[E]),
			run([iint|Rest],Stack,Dict,Code);
		eof ->
			{error,eof}
  	end;
run([oint|Rest],Stack,Dict,Code)->
	[T|S]=Stack,
	io:format("~p",[T]),
	run(Rest,S,Dict,Code);

run([ichr|Rest],Stack,Dict,Code)->
	case io:get_chars("",1) of 
		{ok,[[Num]]}->
			run(Rest,[Num|Stack],Dict,Code);
		{error,E}->
			{error,{'SIGPIPE',E}};
		eof ->
			{error,eof}
  	end;
run([ochr|Rest],Stack,Dict,Code)->
	[T|S]=Stack,
	io:format("~tc",[T]),
	run(Rest,S,Dict,Code);

run([{defun,_}|Rest],Stack,Dict,Code)->
	run(Rest,Stack,Dict,Code);
run([C|_],_Stack,_Dict,_Code)->
	{error,{bad_opcode,C}}.


	
compile(<<>>,Acc)->
	lists:reverse(Acc);
compile(D,Acc)->
	case compile_opc(D) of 
		{ok,Code,Rest}->
			compile(Rest,[Code|Acc]);
		{ingore,Rest}->
			compile(Rest,Acc)
  	end.

compile_opc(<<?PUSH,Rest/binary>>)->
	{ok,N,Rest1} = compile_number(Rest),
	{ok,{push,N},Rest1};
compile_opc(<<?DUP,Rest/binary>>)->
	{ok,dup,Rest};
compile_opc(<<?COPY,Rest/binary>>)->
	{ok,N,Rest1} = compile_number(Rest),
	{ok,{copy,N},Rest1};
compile_opc(<<?SWAP,Rest/binary>>)->
	{ok,swap,Rest};
compile_opc(<<?POP,Rest/binary>>)->
	{ok,pop,Rest};
compile_opc(<<?SLID,Rest/binary>>)->
	{ok,N,Rest1} = compile_number(Rest),
	{ok,{slide,N},Rest1};

compile_opc(<<?ADD,Rest/binary>>)->
	{ok,add,Rest};
compile_opc(<<?SUB,Rest/binary>>)->
	{ok,sub,Rest};
compile_opc(<<?MUL,Rest/binary>>)->
	{ok,mul,Rest};
compile_opc(<<?DIV,Rest/binary>>)->
	{ok,'div',Rest};
compile_opc(<<?MOD,Rest/binary>>)->
	{ok,mod,Rest};

compile_opc(<<?SET,Rest/binary>>)->
	{ok,N,Rest1} = compile_number(Rest),
	{ok,{set,N},Rest1};
compile_opc(<<?LOAD,Rest/binary>>)->
	{ok,N,Rest1} = compile_number(Rest),
	{ok,{load,N},Rest1};

compile_opc(<<?DEF,Rest/binary>>)->
	{ok,L,Rest1} = compile_lable(Rest),
	{ok,{defun,L},Rest1};
compile_opc(<<?CALL,Rest/binary>>)->
	{ok,L,Rest1} = compile_lable(Rest),
	{ok,{call,L},Rest1};
compile_opc(<<?JMP,Rest/binary>>)->
	{ok,L,Rest1} = compile_lable(Rest),
	{ok,{jmp,L},Rest1};
compile_opc(<<?JZ,Rest/binary>>)->
	{ok,L,Rest1} = compile_lable(Rest),
	{ok,{jz,L},Rest1};
compile_opc(<<?JNZ,Rest/binary>>)-> 
	{ok,L,Rest1} = compile_lable(Rest),
	{ok,{jnz,L},Rest1};
compile_opc(<<?RET,Rest/binary>>)-> 
	{ok,ret,Rest};
compile_opc(<<?EXIT,Rest/binary>>)->
	{ok,exit,Rest};
compile_opc(<<?EXIT2,Rest/binary>>)->
	{ok,exit,Rest};

compile_opc(<<?IINT,Rest/binary>>)->
	{ok,iint,Rest};
compile_opc(<<?OINT,Rest/binary>>)->
	{ok,oint,Rest};
compile_opc(<<?ICHR,Rest/binary>>)->
	{ok,ichr,Rest};
compile_opc(<<?OCHR,Rest/binary>>)->
	{ok,ochr,Rest};
compile_opc(<<_/utf8,Rest/binary>>)->
	{ingore,Rest}.


compile_number(<<?G,D/binary>>)->compile_binary(D, 0);
compile_number(<<?M,D/binary>>)->compile_binary(D, 0)*-1;
compile_number(_)->{error,not_binary}.

compile_lable(D)->compile_binary(D, 0).

compile_binary(<<?G,Rest/binary>>,Acc)->
	compile_binary(Rest,Acc bsl 1);
compile_binary(<<?M,Rest/binary>>,Acc)->
	compile_binary(Rest,(Acc bsl 1)+1);
compile_binary(<<?H,Rest/binary>>,Acc)->
	{ok,Acc,Rest};
compile_binary(<<_/utf8,Rest/binary>>,Acc)->
	compile_binary(Rest,Acc).
