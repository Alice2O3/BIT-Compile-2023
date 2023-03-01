	.file	"matrix_mul.c"
	.text
	.p2align 4
	.globl	new_array
	.type	new_array, @function
new_array:
.LFB39:
	.cfi_startproc
	endbr64
	pushq	%r13
	.cfi_def_cfa_offset 16
	.cfi_offset 13, -16
	movslq	%edi, %rdi
	pushq	%r12
	.cfi_def_cfa_offset 24
	.cfi_offset 12, -24
	movq	%rdi, %r12
	salq	$3, %rdi
	pushq	%rbp
	.cfi_def_cfa_offset 32
	.cfi_offset 6, -32
	movslq	%esi, %rbp
	pushq	%rbx
	.cfi_def_cfa_offset 40
	.cfi_offset 3, -40
	subq	$8, %rsp
	.cfi_def_cfa_offset 48
	call	malloc@PLT
	movq	%rax, %r13
	testl	%r12d, %r12d
	jle	.L1
	movq	%rax, %rbx
	leal	-1(%r12), %eax
	salq	$2, %rbp
	leaq	8(%r13,%rax,8), %r12
	.p2align 4,,10
	.p2align 3
.L3:
	movq	%rbp, %rdi
	addq	$8, %rbx
	call	malloc@PLT
	movq	%rax, -8(%rbx)
	cmpq	%r12, %rbx
	jne	.L3
.L1:
	addq	$8, %rsp
	.cfi_def_cfa_offset 40
	movq	%r13, %rax
	popq	%rbx
	.cfi_def_cfa_offset 32
	popq	%rbp
	.cfi_def_cfa_offset 24
	popq	%r12
	.cfi_def_cfa_offset 16
	popq	%r13
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE39:
	.size	new_array, .-new_array
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	"r"
.LC1:
	.string	"../../data.txt"
.LC2:
	.string	"%d %d\n"
.LC3:
	.string	"%d"
	.section	.text.startup,"ax",@progbits
	.p2align 4
	.globl	main
	.type	main, @function
main:
.LFB40:
	.cfi_startproc
	endbr64
	pushq	%r15
	.cfi_def_cfa_offset 16
	.cfi_offset 15, -16
	leaq	.LC0(%rip), %rsi
	leaq	.LC1(%rip), %rdi
	pushq	%r14
	.cfi_def_cfa_offset 24
	.cfi_offset 14, -24
	pushq	%r13
	.cfi_def_cfa_offset 32
	.cfi_offset 13, -32
	pushq	%r12
	.cfi_def_cfa_offset 40
	.cfi_offset 12, -40
	pushq	%rbp
	.cfi_def_cfa_offset 48
	.cfi_offset 6, -48
	pushq	%rbx
	.cfi_def_cfa_offset 56
	.cfi_offset 3, -56
	subq	$40, %rsp
	.cfi_def_cfa_offset 96
	movq	stdin(%rip), %rdx
	movq	%fs:40, %rax
	movq	%rax, 24(%rsp)
	xorl	%eax, %eax
	call	freopen@PLT
	leaq	12(%rsp), %rdx
	leaq	8(%rsp), %rsi
	xorl	%eax, %eax
	leaq	.LC2(%rip), %rdi
	movl	$0, 8(%rsp)
	movl	$0, 12(%rsp)
	call	__isoc99_scanf@PLT
	movl	12(%rsp), %esi
	movl	8(%rsp), %edi
	call	new_array
	movl	8(%rsp), %edx
	movq	%rax, %r12
	testl	%edx, %edx
	jle	.L8
	movl	12(%rsp), %eax
	movq	%r12, %r14
	xorl	%r13d, %r13d
	leaq	.LC3(%rip), %rbp
	.p2align 4,,10
	.p2align 3
.L9:
	xorl	%ebx, %ebx
	testl	%eax, %eax
	jle	.L11
	.p2align 4,,10
	.p2align 3
.L10:
	movq	(%r14), %rax
	movq	%rbp, %rdi
	leaq	(%rax,%rbx,4), %rsi
	xorl	%eax, %eax
	addq	$1, %rbx
	call	__isoc99_scanf@PLT
	movl	12(%rsp), %eax
	cmpl	%ebx, %eax
	jg	.L10
	movl	8(%rsp), %edx
.L11:
	addl	$1, %r13d
	addq	$8, %r14
	cmpl	%edx, %r13d
	jl	.L9
.L8:
	leaq	20(%rsp), %rdx
	leaq	16(%rsp), %rsi
	xorl	%eax, %eax
	movl	$0, 16(%rsp)
	leaq	.LC2(%rip), %rdi
	movl	$0, 20(%rsp)
	call	__isoc99_scanf@PLT
	movl	20(%rsp), %esi
	movl	16(%rsp), %edi
	call	new_array
	movl	20(%rsp), %esi
	movq	%rax, %rbx
	movl	16(%rsp), %eax
	testl	%eax, %eax
	jle	.L15
	movq	%rbx, %r15
	xorl	%r14d, %r14d
	leaq	.LC3(%rip), %r13
	.p2align 4,,10
	.p2align 3
.L13:
	xorl	%ebp, %ebp
	testl	%esi, %esi
	jle	.L16
	.p2align 4,,10
	.p2align 3
.L14:
	movq	(%r15), %rax
	movq	%r13, %rdi
	leaq	(%rax,%rbp,4), %rsi
	xorl	%eax, %eax
	addq	$1, %rbp
	call	__isoc99_scanf@PLT
	movl	20(%rsp), %esi
	cmpl	%ebp, %esi
	jg	.L14
	movl	16(%rsp), %eax
.L16:
	addl	$1, %r14d
	addq	$8, %r15
	cmpl	%eax, %r14d
	jl	.L13
.L15:
	movl	8(%rsp), %edi
	call	new_array
	movl	8(%rsp), %edx
	movq	%rax, %r11
	testl	%edx, %edx
	jle	.L17
	movl	20(%rsp), %eax
	xorl	%r10d, %r10d
	.p2align 4,,10
	.p2align 3
.L18:
	testl	%eax, %eax
	jle	.L25
	movq	(%r11,%r10,8), %rsi
	xorl	%r9d, %r9d
	.p2align 4,,10
	.p2align 3
.L20:
	movl	$0, (%rsi)
	movl	12(%rsp), %eax
	leaq	0(,%r9,4), %rdi
	testl	%eax, %eax
	jle	.L23
	movq	(%r12), %r8
	xorl	%eax, %eax
	xorl	%ecx, %ecx
	.p2align 4,,10
	.p2align 3
.L22:
	movq	(%rbx,%rax,8), %rdx
	movl	(%rdx,%rdi), %edx
	imull	(%r8,%rax,4), %edx
	addq	$1, %rax
	addl	%edx, %ecx
	movl	%ecx, (%rsi)
	cmpl	%eax, 12(%rsp)
	jg	.L22
.L23:
	movl	20(%rsp), %eax
	addq	$1, %r9
	addq	$4, %rsi
	cmpl	%r9d, %eax
	jg	.L20
	movl	8(%rsp), %edx
.L25:
	addq	$1, %r10
	addq	$8, %r12
	cmpl	%r10d, %edx
	jg	.L18
.L17:
	movq	24(%rsp), %rax
	xorq	%fs:40, %rax
	jne	.L37
	addq	$40, %rsp
	.cfi_remember_state
	.cfi_def_cfa_offset 56
	xorl	%eax, %eax
	popq	%rbx
	.cfi_def_cfa_offset 48
	popq	%rbp
	.cfi_def_cfa_offset 40
	popq	%r12
	.cfi_def_cfa_offset 32
	popq	%r13
	.cfi_def_cfa_offset 24
	popq	%r14
	.cfi_def_cfa_offset 16
	popq	%r15
	.cfi_def_cfa_offset 8
	ret
.L37:
	.cfi_restore_state
	call	__stack_chk_fail@PLT
	.cfi_endproc
.LFE40:
	.size	main, .-main
	.ident	"GCC: (Ubuntu 9.4.0-1ubuntu1~20.04.1) 9.4.0"
	.section	.note.GNU-stack,"",@progbits
	.section	.note.gnu.property,"a"
	.align 8
	.long	 1f - 0f
	.long	 4f - 1f
	.long	 5
0:
	.string	 "GNU"
1:
	.align 8
	.long	 0xc0000002
	.long	 3f - 2f
2:
	.long	 0x3
3:
	.align 8
4:
