.section .data
    .equ tempLimPluvio,30
    .equ maxDiffTempPluvio, 5
    .equ failOutput, 0x0
    
.section .text
    .global sens_pluvio

sens_pluvio:
    pushq %rbp                      # save the base pointer
    movq %rsp, %rbp                 # set the base pointer to the stack pointer

    cmpb $0, %dil                   # check if the input is 0
    je sens_zero_pluvio             # if it is, jump to the zero function
    cmpb $tempLimPluvio, %sil       # check if the input is greater than the temperature limit
    jg sens_pluvio_inc              # if it is, jump to the check increase function
    jmp sens_add_pluvio             # if it is not, jump to the add function

sens_pluvio_inc:
    movb %dl, %al                   # move the input into %al
    sarb $7, %al                    # shift the input right by 7 bits
    movb %al, %r8b                  # move the input into %r8b
    xorb %dl,%r8b                   # xor the input with %dl
    subb %al, %r8b                  # subtract the input from %r8b

    cmpb $maxDiffTempPluvio, %dl    # check if the input is greater than the max difference
    jg fail_sens                    # if it is, jump to the fail function


sens_add_pluvio:
    movb %dil,%al                   # move the input into %al
    addb %dl, %al                   # add the input to %dl
    jc fail_sens                    # if there is a carry, jump to the fail function
    jmp end
    
sens_zero_pluvio:
    cmpb $0, %dl                    # check if the input is 0
    jg sens_add_pluvio              # if it is, jump to the add function
    movb $0, %al                    # load 0 into %al
    jmp end


fail_sens:
    movb $failOutput, %al           # load the fail output into %al
    movsbw %al, %ax                 # move the byte in %al into the 2 bytes in %ax

end:
    movq %rbp, %rsp                 # set the stack pointer to the base pointer
    popq %rbp                       # restore the base pointer
    ret