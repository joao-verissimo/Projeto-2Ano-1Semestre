.section .data
    .equ failOutput, 0x0
    .equ maxDiffVento, 60
    .equ maxValVento, 359

.section .text
    .global sens_dir_vento

sens_dir_vento:
    pushq %rbp                      # save the base pointer
    movq %rsp, %rbp                 # set the base pointer to the stack pointer

    movw %si, %ax                   # move 2 bytes in %si into the 2 bytes in %ax
    sarw $15, %ax                   # shift the 2 bytes in %ax right by 15 bits
    movw %ax, %dx                   # move the 2 bytes in %ax into the 2 bytes in %dx
    xorw %si,%dx                    # xor the 2 bytes in %si with the 2 bytes in %dx
    subw %ax, %dx                   # subtract the 2 bytes in %ax from the 2 bytes in %dx

    cmpw $maxDiffVento, %dx         # compare one byte in %dx with the byte in maxDiffVento
    jge fail_sens                   # jump to fail_sens if the comparison is true

    movw %di, %ax                   # move the 2 bytes in %di into the 2 bytes in %ax
    addw %si, %ax                   # add the 2 bytes in %si to the 2 bytes in %ax

    cmpw $0, %ax                    # compare one byte in %ax with the byte in 0
    jl fail_sens                    # jump to fail_sens if the comparison is true
    cmpw $maxValVento, %ax          # compare one byte in %ax with the byte in maxValVento
    jae fail_sens                   # jump to fail_sens if the comparison is true
    jmp end                         # jump to end

fail_sens:
    movb $failOutput, %al           # load the fail output into %al
    movsbw %al, %ax                 # move the byte in %al into the 2 bytes in %ax

end:
    movq %rbp, %rsp                 # set the stack pointer to the base pointer
    popq %rbp                       # restore the base pointer
    ret