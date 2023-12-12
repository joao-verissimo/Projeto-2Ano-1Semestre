.section .data
    .equ failOutput, 0x0
    .equ maxDiffSemChuva, 5 

.section .text
    .global sens_humd_atm

sens_humd_atm:
    pushq %rbp                      # save the base pointer
    movq %rsp, %rbp                 # set the base pointer to the stack pointer

    movb %dl, %al                   # move the byte in %dl into the byte in %al
    sarb $7, %al                    # shift the byte in %al right by 7 bits
    movb %al, %r8b                  # move the byte in %al into the byte in %r8b
    xorb %dl,%r8b                   # xor the byte in %dl with the byte in %r8b
    subb %al, %r8b                  # subtract the byte in %al from the byte in %r8b


    movb %dil, %al                  # move the byte in %dil into the byte in %al
    addb %dl, %al                   # add the byte in %dl to the byte in %al

    cmpb $100, %al                  # check if sum gets a value above 100
    ja fail_sens                    # if so, jump to fail_sens
    cmpb $0, %al                    # check if sum gets a value below 0
    jl fail_sens                    # if so, jump to fail_sens

    cmpb $0,%sil                    # check if the byte in %sil is 0
    je sens_humd_sem_chuva          # if so, jump to sens_humd_sem_chuva
    jmp end 

sens_humd_sem_chuva:
    cmpb $maxDiffSemChuva, %r8b     # check if the byte in %r8b is greater than maxDiffSemChuva
    jg fail_sens                    # if so, jump to fail_sens
    jmp end

fail_sens:
    movb $failOutput, %al           # load the fail output into %al
    movsbw %al, %ax                 # move the byte in %al into the word in %ax

end:
    movq %rbp, %rsp                 # set the stack pointer to the base pointer
    popq %rbp                       # restore the base pointer
    ret