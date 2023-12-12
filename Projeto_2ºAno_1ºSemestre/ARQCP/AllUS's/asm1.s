.section .data
    .equ failOutput, 0x0
    
.section .text
    .global sens_velc_vento

sens_velc_vento:
    pushq %rbp                      # save the base pointer
    movq %rsp, %rbp                 # set the base pointer to the stack pointer

    movb %dil, %al                  # move the byte in %dil into the byte in %al
    addb %sil, %al                  # add the byte in %sil to the byte in %al

    cmpb $0, %al                    # compare the byte in %al to 0
    jl fail_sens                    # if %al < 0, jump to fail_sens

fail_sens:
    movb $failOutput, %al           # load the fail output into %al
    movsbw %al, %ax                 # move the byte in %al into the word in %ax

end:
    movq %rbp, %rsp                 # set the stack pointer to the base pointer
    popq %rbp                       # restore the base pointer
    ret