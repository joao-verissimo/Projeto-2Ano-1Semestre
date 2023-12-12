.section .data
	.global state
	.global inc
.section .text
	.global pcg32_random_r
pcg32_random_r:
	movq	state(%rip), %rcx # rcx = state;
	movq	inc(%rip), %rdx # rdx = inc
	movabsq	$6364136223846793005, %rax # rax = 6364136223846793005
	imulq	%rcx, %rax # rax = rax*rcx (oldstate * 6364136223846793005ULL)
	orq	$1, %rdx # inc|1
	addq	%rdx, %rax # rax = rax+inc (oldstate * 6364136223846793005ULL + (inc|1))
	movq	%rax, state(%rip) # state=rax
	movq	%rcx, %rax # rax = rcx
	shrq	$18, %rax # Shift right rax 18 bits ((oldstate >> 18u)
	xorq	%rcx, %rax # (oldstate >> 18u) ^ oldstate)
	shrq	$27, %rax # shift right 27 bits rax (((oldstate >> 18u) ^ oldstate) >> 27u)
	shrq	$59, %rcx # shift right rax 59 bits (oldstate >> 59u;)
	/*
	oldstate = rcx
	xorshifted  = rax
	rot = rcx 1
	*/
	rorl	%cl, %eax # right rotate eax 
	ret
