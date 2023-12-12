.section .data
    .equ failOutput, 0x0
    .equ maxDiffTemp, 30

.section .text
    .global sens_temp

sens_temp:
    pushq %rbp                      # save the base pointer
    movq %rsp, %rbp                 # set the base pointer to the stack pointer

    movb %sil, %al                  # load the first byte of the input into %al
    sarb $7, %al                    # shift the sign bit into the carry flag
    movb %al, %dl                   # copy the sign bit into %dl
    xorb %sil,%dl                   # xor the sign bit with the first byte of the input
    subb %al, %dl                   # subtract the sign bit from the result of the xor

    cmpb $maxDiffTemp, %dl          # compare the result with the max difference
    jge fail_sens                   # if the result is greater than or equal to the max difference, jump to fail_sens

    movb %dil, %al                  # load the second byte of the input into %al
    addb %sil, %al                  # add the first byte of the input to %al

fail_sens:
    movb $failOutput, %al           # load the fail output into %al
    movsbw %al, %ax                 # move the byte in %al into the word in %ax

end:
    movq %rbp, %rsp                 # set the stack pointer to the base pointer
    popq %rbp                       # restore the base pointer
    ret